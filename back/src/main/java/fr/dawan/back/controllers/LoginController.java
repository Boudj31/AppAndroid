package fr.dawan.back.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.dawan.back.dto.LoginDto;
import fr.dawan.back.dto.LoginResponseDto;
import fr.dawan.back.services.UserService;

@RestController
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping(value="/login", consumes = "application/json", produces = "application/json")
	public LoginResponseDto checkLogin(@RequestBody LoginDto loginDto) throws Exception{
		return userService.checkLogin(loginDto);
	}

}
