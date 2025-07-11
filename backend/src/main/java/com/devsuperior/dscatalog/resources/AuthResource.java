package com.devsuperior.dscatalog.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dscatalog.dto.EmailDTO;
import com.devsuperior.dscatalog.dto.NewPasswordDTO;
import com.devsuperior.dscatalog.services.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
@Tag(name = "Auth", description = "Resource for Auth")
public class AuthResource {

	@Autowired
	private AuthService service;

	@Operation(description = "Generate recover token", summary = "Send recover token to email", responses = {
			@ApiResponse(description = "Ok", responseCode = "200"), })
	@PostMapping(value = "/recover-token", produces = "application/json")
	public ResponseEntity<Void> createRecoverToken(@Valid @RequestBody EmailDTO body) {
		service.createRecoverToken(body);
		return ResponseEntity.noContent().build();
	}
	
	@Operation(description = "Save new password", summary = "Save new password", responses = {
			@ApiResponse(description = "Ok", responseCode = "200"),
			@ApiResponse(description = "Forbidden", responseCode = "403"),})
	@PutMapping(value = "/new-password", produces = "application/json")
	public ResponseEntity<Void> saveNewPassword(@Valid @RequestBody NewPasswordDTO body) {
		service.saveNewPassword(body);
		return ResponseEntity.noContent().build();
	}

}
