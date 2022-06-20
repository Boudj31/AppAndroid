package fr.dawan.back.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.dawan.back.entities.User;
import fr.dawan.back.services.FileService;
import fr.dawan.back.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	
	UserService userService;
	
	@Value("${storagefolder}")
	private String storageFolder;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	FileService fileService;
	
	
	@GetMapping(produces = "application/json")
	public List<User> getAll(){
		return userService.getAll();
	}
	
	@GetMapping(value="/{id}", produces = "application/json")
	public User findById(@PathVariable("id") Long id) {
		return userService.getById(id);
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Long> delete(@PathVariable("id") Long id){
		userService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).body(id);
	}
	
	
	//le user est sous format json {firstName: ------------}
	
	@PostMapping(value="/save", consumes="multipart/form-data", produces = "application/json")
	public ResponseEntity<User> save(@RequestParam("user") String userJson, @RequestPart("file") MultipartFile file ) throws Exception{
		
		File fichier = new File(storageFolder+"/"+file.getOriginalFilename());
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fichier));
		bos.write(file.getBytes());
		bos.close();
		
		User u = objectMapper.readValue(userJson, User.class);
		
		u.setImagePath(file.getOriginalFilename());
		
		User resultat= userService.saveOrUpdate(u);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(resultat);
		
	}
	
	@GetMapping(value="/image/{id}")
	public ResponseEntity<Resource> getUserImage(@PathVariable("id") Long id) throws IOException{
		User u =  userService.getById(id);
		Resource resource = fileService.download(u.getImagePath());
		
		Path path = resource.getFile().toPath();
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(path))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+resource.getFilename()+"\"")
				.body(resource);
		
		
	}

}
