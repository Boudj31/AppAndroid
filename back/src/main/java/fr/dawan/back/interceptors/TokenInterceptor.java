package fr.dawan.back.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import fr.dawan.back.tools.JwtTokenUtil;
import fr.dawan.back.tools.TokenSaver;

@Component
public class TokenInterceptor implements HandlerInterceptor{
	
	@Autowired
	JwtTokenUtil jwtTokenUtil;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println(request.getRequestURI());
		System.out.println(request.getRequestURI().contains("/login"));
		
		if(!request.getRequestURI().equals("/login") && !request.getRequestURI().equals("/save")) {
			
		
			
			String header = request.getHeader("Authorization");
			
			if(header == null && header.trim().equals("") && header.length() < 7) {
				throw new Exception("Token invalide");
			}
			
			String token = header.substring(7);
			
			if(jwtTokenUtil.isTokenExpired(token)) {
				throw new Exception("Token invalide");
			}
			
			String email = jwtTokenUtil.getUsernameFromToken(token);
			if(!TokenSaver.tokensByEmail.containsKey(email)  || !TokenSaver.tokensByEmail.get(email).equals(token)) {
				throw new Exception("Token invalide");
			}
		}
		
		
		return true;
	}
	
	

}
