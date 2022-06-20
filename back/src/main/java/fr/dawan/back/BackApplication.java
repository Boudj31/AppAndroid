package fr.dawan.back;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import fr.dawan.back.entities.User;
import fr.dawan.back.interceptors.TokenInterceptor;
import fr.dawan.back.repositories.UserRepository;

@SpringBootApplication
public class BackApplication implements CommandLineRunner {
	
	@Autowired
	private TokenInterceptor tokenInterceptor;
	
	@Autowired
	UserRepository repo;

	public static void main(String[] args) {
		SpringApplication.run(BackApplication.class, args);		
		
	}
	
	//CROSS ORIGINE
	
	@Bean
	public WebMvcConfigurer mvcConfigurer() {
		return new WebMvcConfigurer() {

			
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				// TODO Auto-generated method stub
				
				registry.addMapping("/**")
				.allowedOrigins("*")
				.allowedMethods("GET", "POST", "PUT", "DELETE");
			}

			@Override
			public void addInterceptors(InterceptorRegistry registry) {
				registry.addInterceptor(tokenInterceptor);
			}
			
		};
	}

	@Override
	public void run(String... args) throws Exception {
		
		//repo.save(new User("admin", "admin", "admin@dawan.fr", "admin", ""));
		
	}

}
