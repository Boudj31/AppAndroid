package fr.dawan.back.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.dawan.back.dto.DtoTools;
import fr.dawan.back.dto.LoginDto;
import fr.dawan.back.dto.LoginResponseDto;
import fr.dawan.back.entities.User;
import fr.dawan.back.repositories.UserRepository;
import fr.dawan.back.tools.JwtTokenUtil;
import fr.dawan.back.tools.TokenSaver;

@Service
public class UserService {
	
	@Autowired
	UserRepository repo;
	
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	public List<User> getAll(){
		return repo.findAll();
	}
	
	public User getById(long id) {
		 Optional<User> u = repo.findById(id);
		 if(u.isPresent())
			 return u.get();
		 
		 return null;
	}
	
	public User saveOrUpdate(User u) {
		return repo.saveAndFlush(u);
	}
	
	public User findByEmail(String email) {
		User u = repo.findByEmail(email);
		
		if(u != null)
			return u;
		return null;
	}
	
	public void delete(long userId) {
		repo.deleteById(userId);
	}
	
	public LoginResponseDto checkLogin(LoginDto loginDto) throws Exception {
		
		User u = repo.findByEmail(loginDto.getEmail());
		if(u != null && u.getPassword().equals(loginDto.getPassword())) {
			
			LoginResponseDto loginResponseDto = DtoTools.convert(u, LoginResponseDto.class);
			
			//Generer le token
			Map<String, Object> claims = new HashMap<String, Object>();
			claims.put("userId", u.getId());
			claims.put("fullname", u.getFirstName()+" "+u.getLastName());
			String token = jwtTokenUtil.doGenerateToken(claims, u.getEmail());
			
			loginResponseDto.setToken(token);
			TokenSaver.tokensByEmail.put(u.getEmail(), token);
			
			return loginResponseDto;
			
			
			
		}else {
			throw new Exception("Erreur connexion");
		}
		
	}

}
