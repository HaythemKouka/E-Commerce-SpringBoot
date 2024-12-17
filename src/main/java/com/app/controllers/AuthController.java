
package com.app.controllers;

import java.util.Collections;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.entites.User;
import com.app.exceptions.UserNotFoundException;
import com.app.payloads.LoginCredentials;
import com.app.payloads.UserDTO;
import com.app.security.JWTUtil;
import com.app.services.UserService;
 

@Controller
@RequestMapping("/api")
 public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/register")
	public ResponseEntity<Map<String, Object>> registerHandler(@Valid @RequestBody UserDTO user) throws UserNotFoundException {
		String encodedPass = passwordEncoder.encode(user.getPassword());

		user.setPassword(encodedPass);

		UserDTO userDTO = userService.registerUser(user);

		String token = jwtUtil.generateToken(userDTO.getEmail());

		return new ResponseEntity<Map<String, Object>>(Collections.singletonMap("jwt-token", token),
				HttpStatus.CREATED);
	}
	@GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());  // Crée un objet User vide pour le formulaire
        return "register";  // Renvoie la vue register.html
    }

 
	  @GetMapping("/login")
	    public String showLoginForm(Model model) {
	        model.addAttribute("user", new User());
	        return "login";
	    }

	    @PostMapping("/login")
	    public String processLogin(User user, Model model) {
	        User existingUser =new User();

	        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
	            model.addAttribute("message", "Login successful!");
	            return "index"; // Redirect to home page
	        }

	        model.addAttribute("error", "Invalid email or password");
	        return "register";
	    }

	    @GetMapping("/x")
	    public String homePage() {
	        return "index";
	    }
	 @GetMapping("/")
	    public String home(Model model) {
	        model.addAttribute("name", "Spring Boot");
	        return "index"; // Refers to the templates/index.html
	    }
}
