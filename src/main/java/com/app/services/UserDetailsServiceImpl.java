package com.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.config.UserInfoConfig;
import com.app.entites.User;
import com.app.exceptions.ResourceNotFoundException;
import com.app.repositories.UserRepo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;
	  @Override
	    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	        // Récupérer l'utilisateur par email
	        User user = userRepo .findByEmail(email)
	                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

	        // Convertir les rôles en authorities
	        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(
	            user.getRoles().stream()
	                .map(role -> role.getRoleName()) // Assurez-vous que 'role.getName()' donne un rôle sous forme de chaîne
	                .toArray(String[]::new)
	        );

	        // Retourner un objet User de Spring Security avec les autorités
	        return new org.springframework.security.core.userdetails.User(
	                user.getEmail(),
	                user.getPassword(),
	                authorities
	        );
	    }
}