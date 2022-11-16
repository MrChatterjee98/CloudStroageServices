package project.cloud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import project.cloud.model.FileStorageInfo;
import project.cloud.service.FileStorageService;

@RestController
public class FileStorageController {

	@Autowired
	public FileStorageService fileStorageService;

	
	@GetMapping("/{fileName}")
	public FileStorageInfo getFileStorageInfo(@PathVariable String fileName) {
		return fileStorageService.findFileInfoByName(fileName);
	}
	@GetMapping("/")
	public List<FileStorageInfo> getAllFiles(){
		return fileStorageService.findAllFiles();
	}
}
