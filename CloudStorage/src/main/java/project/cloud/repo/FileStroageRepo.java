package project.cloud.repo;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import project.cloud.model.FileDetails;

public interface FileStroageRepo {
	public void openConnection() throws IOException, Exception;

	public void closeConnection() throws IOException;

	public boolean goToParentDirectory() throws IOException;

	public boolean goToDirectory(String folderName) throws IOException;

	public boolean uploadFile(MultipartFile file) throws IOException;

	public ArrayList<FileDetails> getFilesList() throws IOException;

	public boolean getFileFromFTP(String filename, HttpServletResponse response) throws IOException;

	public boolean readFile(String path, HttpServletResponse response) throws IOException;

	public boolean removeFile(String path) throws IOException;
	
	public boolean updateFile(String path,String newName) throws IOException;

}
