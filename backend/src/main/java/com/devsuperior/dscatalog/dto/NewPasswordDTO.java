package com.devsuperior.dscatalog.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class NewPasswordDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Campo obrigatório")
	private String token;

	@NotBlank(message = "Campo obrigatório")
	@Size(min = 8, message = "Deve ter no minimo 8 caracteres")
	private String password;

	public NewPasswordDTO() {

	}

	public NewPasswordDTO(String token, String password) {
		super();
		this.token = token;
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public String getPassword() {
		return password;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
