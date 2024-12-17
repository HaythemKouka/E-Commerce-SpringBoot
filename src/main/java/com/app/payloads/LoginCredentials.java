package com.app.payloads;
 
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginCredentials {
	
	
	@javax.validation.constraints.Email
	@Column(unique = true, nullable = false)
	private String email;

	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
