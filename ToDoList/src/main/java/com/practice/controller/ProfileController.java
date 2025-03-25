package com.practice.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

//import jakarta.annotation.Resource;

@Controller
public class ProfileController {
	private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/profiles/";

	@GetMapping("/profiles/{filename}")
	@ResponseBody
	public ResponseEntity<Resource> getProfileImage(@PathVariable String filename) {
	    Path file = Paths.get(UPLOAD_DIR).resolve(filename);
	    try {
	        Resource resource = new FileSystemResource(file.toFile());
	        if (resource.exists() || resource.isReadable()) {
	            return ResponseEntity.ok()
	                    .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(file))
	                    .body(resource);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    } catch (IOException e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

}
