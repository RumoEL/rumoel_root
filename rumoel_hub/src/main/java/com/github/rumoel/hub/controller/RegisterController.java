package com.github.rumoel.hub.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.rumoel.hub.dao.UserRepo;
import com.github.rumoel.libs.core.model.Role;
import com.github.rumoel.libs.core.model.User;

@Controller
public class RegisterController {
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@RequestMapping("/register")
	public String page() {
		return "register";
	}

	@PostMapping("/register")
	public String registerUser(User user, Map<String, Object> model) {

		User userFromDb = userRepo.findByUsername(user.getUsername());
		if (userFromDb != null) {
			model.put("errorMessage", "User exists!");
			return "register";
		}

		user.setActive(true);
		user.setRoles(Collections.singleton(Role.USER));

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepo.save(user);

		return "redirect:/login";
	}
}
