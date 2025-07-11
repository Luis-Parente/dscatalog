package com.devsuperior.dscatalog.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.dscatalog.dto.UserDTO;
import com.devsuperior.dscatalog.dto.UserInsertDTO;
import com.devsuperior.dscatalog.dto.UserUpdateDTO;
import com.devsuperior.dscatalog.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/users")
@Tag(name = "Users", description = "Resource for Users")
public class UserResource {

	@Autowired
	private UserService service;

	@Operation(description = "Get all users paged", summary = "Paged all users", responses = {
			@ApiResponse(description = "Ok", responseCode = "200"), })
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@SecurityRequirement(name = "bearerAuth")
	@GetMapping(produces = "application/json")
	public ResponseEntity<Page<UserDTO>> findAll(Pageable pageable) {
		Page<UserDTO> list = service.findAll(pageable);
		return ResponseEntity.ok().body(list);
	}

	@Operation(description = "Get user by id", summary = "Get user by id", responses = {
			@ApiResponse(description = "Ok", responseCode = "200"),
			@ApiResponse(description = "Not Found", responseCode = "404"), })
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@SecurityRequirement(name = "bearerAuth")
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
		UserDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}

	@Operation(description = "Get user logged", summary = "Get user logged", responses = {
			@ApiResponse(description = "Ok", responseCode = "200"),
			@ApiResponse(description = "Unauthorized", responseCode = "401"), })
	@PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
	@SecurityRequirement(name = "bearerAuth")
	@GetMapping(value = "/me", produces = "application/json")
	public ResponseEntity<UserDTO> findMe() {
		UserDTO dto = service.findMe();
		return ResponseEntity.ok().body(dto);
	}

	@Operation(description = "Create a new user", summary = "Create a new user", responses = {
			@ApiResponse(description = "Created", responseCode = "201"),
			@ApiResponse(description = "Bad Request", responseCode = "400"),
			@ApiResponse(description = "Unauthorized", responseCode = "401"),
			@ApiResponse(description = "Forbidden", responseCode = "403"),
			@ApiResponse(description = "Unprocessable Entity", responseCode = "422") })
	@PostMapping(produces = "application/json")
	public ResponseEntity<UserDTO> insert(@Valid @RequestBody UserInsertDTO dto) {
		UserDTO newDto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newDto.getId()).toUri();
		return ResponseEntity.created(uri).body(newDto);
	}

	@Operation(description = "Update a product", summary = "Update a product", responses = {
			@ApiResponse(description = "Ok", responseCode = "200"),
			@ApiResponse(description = "Bad Request", responseCode = "400"),
			@ApiResponse(description = "Unauthorized", responseCode = "401"),
			@ApiResponse(description = "Forbidden", responseCode = "403"),
			@ApiResponse(description = "Not Found", responseCode = "404"),
			@ApiResponse(description = "Unprocessable Entity", responseCode = "422") })
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@SecurityRequirement(name = "bearerAuth")
	@PutMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<UserDTO> update(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO dto) {
		UserDTO newDto = service.update(id, dto);
		return ResponseEntity.ok().body(newDto);
	}

	@Operation(description = "Delete a user", summary = "Delete a user", responses = {
			@ApiResponse(description = "Sucess", responseCode = "204"),
			@ApiResponse(description = "Bad Request", responseCode = "400"),
			@ApiResponse(description = "Unauthorized", responseCode = "401"),
			@ApiResponse(description = "Forbidden", responseCode = "403"),
			@ApiResponse(description = "Not Found", responseCode = "404"),
			@ApiResponse(description = "Unprocessable Entity", responseCode = "422") })
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@SecurityRequirement(name = "bearerAuth")
	@DeleteMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
