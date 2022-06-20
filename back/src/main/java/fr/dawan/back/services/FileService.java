package fr.dawan.back.services;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

@Service
public class FileService {
	
	@Value("${storagefolder}")
	private String storageFolder;
	
	public Resource download(String fileName) {
		try {
	
			Path path = Paths.get(storageFolder).resolve(fileName);
			Resource resource = new UrlResource(path.toUri());
			
			if(resource.exists()) {
				return resource;
			}else {
				throw new RuntimeException();
			}
		
	}catch (Exception e) {
		throw new RuntimeException();
	}

}
	}
