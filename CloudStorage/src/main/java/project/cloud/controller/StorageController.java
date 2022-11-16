package project.cloud.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import project.cloud.model.FileDetails;
import project.cloud.service.FileStorageService;
import project.cloud.util.KafkaProducerUtil;

@RestController
@RequestMapping("/file/")
public class StorageController {
	@Autowired
	FileStorageService fileStorage;
	@Autowired
	KafkaProducerUtil producerUtil;
	@Autowired
	Environment env;
	

	@PostConstruct
	public void uponConstruct() throws IOException, Exception {
		fileStorage.openConnection();
	}

	@PreDestroy
	public void beforeDestory() throws IOException {
		fileStorage.closeConnection();
		System.out.println("connection closed");
	}


	@GetMapping("/")
	public ArrayList<FileDetails> getFiles() throws IOException {

		return fileStorage.getFilesList();
	}

	@GetMapping("/{fileName}")
	public ArrayList<FileDetails> getFilesByFolderName(@PathVariable String fileName) throws IOException {
		fileStorage.goToDirectory(fileName);
		return fileStorage.getFilesList();
	}

	@GetMapping("/back")
	public ArrayList<FileDetails> goToParent() throws IOException {
		fileStorage.goToParentDirectory();
		return fileStorage.getFilesList();
	}

	@PostMapping("/")
	public boolean uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
		producerUtil.uploadFileInfo(file);
		boolean res =fileStorage.uploadFile(file);
		return res;
	}

	@GetMapping("/read/{filename}")
	public ResponseEntity<?> readFile(@PathVariable String filename, HttpServletResponse resp) throws IOException {
		ResponseEntity<?> returnResp = new ResponseEntity<>(HttpStatus.OK);
		producerUtil.readFileInfo(filename);
		if (fileStorage.readFile(filename, resp))
			ResponseEntity.status(HttpStatus.NOT_FOUND);
		return returnResp;
	}

	@GetMapping("/download/{filename}")
	public ResponseEntity<?> downloadFile(@PathVariable String filename, HttpServletResponse resp) throws IOException {
		resp.addHeader("Content-disposition", "attachment; filename=" + filename);
		resp.setContentType("application/octet-stream");
		producerUtil.downloadFileInfo(filename);
		if (!fileStorage.getFileFromFTP(filename, resp))
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@PutMapping("/update/{filename}")
	public ResponseEntity<?> updateFile(@PathVariable String filename, @RequestBody HashMap<String, String> fileParams)
			throws IOException {
		String newFileName = fileParams.get("newName");
		producerUtil.updateFileInfo(filename,newFileName);
		if (!fileStorage.updateFile(filename, newFileName))
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/delete/{filename}")
	public ResponseEntity<?> deleteFile(@PathVariable String filename) throws IOException {
		producerUtil.deleteFileInfo(filename);
		if (!fileStorage.removeFile(filename))
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
