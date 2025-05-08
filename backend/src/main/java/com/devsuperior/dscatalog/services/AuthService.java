package com.devsuperior.dscatalog.services;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.EmailDTO;
import com.devsuperior.dscatalog.dto.NewPasswordDTO;
import com.devsuperior.dscatalog.entities.PasswordRecover;
import com.devsuperior.dscatalog.entities.User;
import com.devsuperior.dscatalog.repositories.PasswordRecoverRepository;
import com.devsuperior.dscatalog.repositories.UserRepository;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

import jakarta.validation.Valid;

@Service
public class AuthService {

	@Value("${email.password-recover.token.minutes}")
	private Long tokenMinutes;

	@Value("${email.password-recover.uri}")
	private String recoverUri;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordRecoverRepository repository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional
	public void createRecoverToken(@Valid EmailDTO email) {

		User user = userRepository.findByEmail(email.getEmail());

		if (user == null) {
			throw new ResourceNotFoundException("Email não encontrado");
		}

		PasswordRecover recover = new PasswordRecover();

		String token = UUID.randomUUID().toString();

		recover.setEmail(email.getEmail());
		recover.setToken(token);
		recover.setExpiration(Instant.now().plusSeconds(tokenMinutes * 60L));

		recover = repository.save(recover);

		String body = "Para recuperar a senha acesse o link a seguir \n\n" + recoverUri + token
				+ "\n\nToken válido por " + tokenMinutes + " minutos";

		emailService.sendEmail(email.getEmail(), "Recuperação de senha", body);
	}

	@Transactional
	public void saveNewPassword(NewPasswordDTO body) {
		List<PasswordRecover> result = repository.searchValidTokens(body.getToken(), Instant.now());

		if (result.size() == 0) {
			throw new ResourceNotFoundException("Token inválido");
		}

		User user = userRepository.findByEmail(result.get(0).getEmail());
		user.setPassword(passwordEncoder.encode(body.getPassword()));

		user = userRepository.save(user);

	}

	protected User authenticated() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
			String username = jwtPrincipal.getClaim("username");
			return userRepository.findByEmail(username);
		} catch (Exception e) {
			throw new UsernameNotFoundException("Invalid user");
		}
	}
	
}
