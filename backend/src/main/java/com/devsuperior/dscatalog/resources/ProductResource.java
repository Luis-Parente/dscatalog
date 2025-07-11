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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.dto.UriDTO;
import com.devsuperior.dscatalog.services.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/products")
@Tag(name = "Products", description = "Resource for Products")
public class ProductResource {

	@Autowired
	private ProductService service;

	@Operation(description = "Get all products paged", summary = "Paged all products", responses = {
			@ApiResponse(description = "Ok", responseCode = "200"), })
	@GetMapping(produces = "application/json")
	public ResponseEntity<Page<ProductDTO>> findAll(@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "0") String categoryId, Pageable pageable) {
		Page<ProductDTO> list = service.findAllPaged(name, categoryId, pageable);
		return ResponseEntity.ok().body(list);
	}

	@Operation(description = "Get product by id", summary = "Get product by id", responses = {
			@ApiResponse(description = "Ok", responseCode = "200"),
			@ApiResponse(description = "Not Found", responseCode = "404"), })
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
		ProductDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}

	@Operation(description = "Create a new product", summary = "Create a new product", responses = {
			@ApiResponse(description = "Created", responseCode = "201"),
			@ApiResponse(description = "Bad Request", responseCode = "400"),
			@ApiResponse(description = "Unauthorized", responseCode = "401"),
			@ApiResponse(description = "Forbidden", responseCode = "403"),
			@ApiResponse(description = "Unprocessable Entity", responseCode = "422") })
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OPERATOR')")
	@SecurityRequirement(name = "bearerAuth")
	@PostMapping(produces = "application/json")
	public ResponseEntity<ProductDTO> insert(@Valid @RequestBody ProductDTO dto) {
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

	@Operation(description = "Upload image", summary = "Upload image", responses = {
			@ApiResponse(description = "Created", responseCode = "201"),
			@ApiResponse(description = "Bad Request", responseCode = "400"),
			@ApiResponse(description = "Unauthorized", responseCode = "401"),
			@ApiResponse(description = "Forbidden", responseCode = "403"),
			@ApiResponse(description = "Unprocessable Entity", responseCode = "422") })
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OPERATOR')")
	@SecurityRequirement(name = "bearerAuth")
	@PostMapping(value = "/image", produces = "application/json")
	public ResponseEntity<UriDTO> uploadImage(@RequestParam("file") MultipartFile file) {
		UriDTO dto = service.uploadFile(file);
		return ResponseEntity.ok().body(dto);
	}
	
	@Operation(description = "Update a product", summary = "Update a product", responses = {
			@ApiResponse(description = "Ok", responseCode = "200"),
			@ApiResponse(description = "Bad Request", responseCode = "400"),
			@ApiResponse(description = "Unauthorized", responseCode = "401"),
			@ApiResponse(description = "Forbidden", responseCode = "403"),
			@ApiResponse(description = "Not Found", responseCode = "404"),
			@ApiResponse(description = "Unprocessable Entity", responseCode = "422") })
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OPERATOR')")
	@SecurityRequirement(name = "bearerAuth")
	@PutMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<ProductDTO> update(@PathVariable Long id, @Valid @RequestBody ProductDTO dto) {
		dto = service.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}

	@Operation(description = "Delete a product", summary = "Delete a product", responses = {
			@ApiResponse(description = "Sucess", responseCode = "204"),
			@ApiResponse(description = "Bad Request", responseCode = "400"),
			@ApiResponse(description = "Unauthorized", responseCode = "401"),
			@ApiResponse(description = "Forbidden", responseCode = "403"),
			@ApiResponse(description = "Not Found", responseCode = "404"),
			@ApiResponse(description = "Unprocessable Entity", responseCode = "422") })
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OPERATOR')")
	@SecurityRequirement(name = "bearerAuth")
	@DeleteMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
