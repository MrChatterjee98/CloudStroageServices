package project.cloud.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import project.cloud.excpetion.ConnectionNotEstablishedException;
import project.cloud.model.FileDetails;
import project.cloud.repo.FileStroageRepo;

@Service
public class FileStorageService implements FileStroageRepo {

	private final String FTP_HOST_NAME;
	private final int FTP_PORT_NUM;
	private final String FTP_USERNAME;
	private final String FTP_PASSWORD;
	private final String WORKING_DIR;
	private FTPClient client = null;
	private String base_dir;

	public FileStorageService(@Value("${hostname}") String hostname, @Value("${portnumber}") int portnumber,
			@Value("${ftpusername}") String username, @Value("${ftppassword}") String password,
			@Value("${workingdir}") String dir) {
		this.FTP_HOST_NAME = hostname;
		this.FTP_PORT_NUM = portnumber;
		this.FTP_USERNAME = username;
		this.FTP_PASSWORD = password;
		this.WORKING_DIR = dir;
	}

	@Override
	public final void openConnection() throws IOException, Exception {
		try {
			this.client = new FTPClient();
			client.setConnectTimeout(2000);
			client.connect(FTP_HOST_NAME, FTP_PORT_NUM);
			boolean res = client.login(FTP_USERNAME, FTP_PASSWORD);
			if (!res)
				throw new ConnectionNotEstablishedException();
			client.enterLocalPassiveMode();
			client.setFileType(FTP.BINARY_FILE_TYPE);
			client.changeWorkingDirectory(client.printWorkingDirectory() + WORKING_DIR);
			this.base_dir = client.printWorkingDirectory();
		} catch (SocketException ex) {
			System.out.println("connection not established");
		}
	}

	@Override
	public final void closeConnection() throws IOException {
		client.disconnect();
	}

	@Override
	public ArrayList<FileDetails> getFilesList() throws IOException {
		ArrayList<FileDetails> list = new ArrayList<FileDetails>();
		SimpleDateFormat dateFormatter= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		FTPFile[] files = client.listFiles();
		for (FTPFile file : files)
			list.add(new FileDetails(file.getName(), file.getType() != FTPFile.DIRECTORY_TYPE ? "file" : "folder",
					file.getSize() + " Bytes", dateFormatter.format(new Date(file.getTimestamp().getTimeInMillis()))));
		return list;
	}

	@Override
	public boolean goToParentDirectory() throws IOException {
		return client.changeToParentDirectory();
	}

	@Override
	public boolean goToDirectory(String folderName) throws IOException {
		return client.changeWorkingDirectory(folderName);
	}

	@Override
	public boolean uploadFile(MultipartFile file) throws IOException {
		InputStream in = file.getInputStream();
		String filename = file.getOriginalFilename();
		String[] fileDetails = filename.split("\\.");
		String ext = fileDetails[1].toUpperCase();
		client.changeWorkingDirectory(base_dir);
		if (client.cwd(ext) == 550) {
			client.makeDirectory(ext);
		}
		
		client.changeWorkingDirectory(ext);
		boolean res = client.storeFile(filename, in);
		
		client.changeToParentDirectory();
		return res;
	}

	@Override
	public boolean readFile(String path, HttpServletResponse response) throws IOException {
		String folder = path.split("\\.")[1];
		client.changeWorkingDirectory(base_dir + "/" + folder.toUpperCase());
		boolean fileFound = false;
		FTPFile[] files = client.listFiles();
		for (FTPFile file : files) {
			if (file.getName().equalsIgnoreCase(path)) {
				client.retrieveFile(path, response.getOutputStream());
				fileFound = true;
			}
		}
		client.changeWorkingDirectory(base_dir);
		return fileFound;
	}

	@Override
	public boolean getFileFromFTP(String path, HttpServletResponse response) throws IOException {
		String folder = path.split("\\.")[1];
		client.changeWorkingDirectory(base_dir + "/" + folder.toUpperCase());
		FTPFile[] files = client.listFiles();
		boolean fileFound = false;
		for (FTPFile file : files) {
			if (file.getName().equalsIgnoreCase(path)) {
				client.retrieveFile(file.getName(), response.getOutputStream());
				fileFound = true;
				break;
			}
		}
		client.changeWorkingDirectory(base_dir);
		return fileFound;
	}

	@Override
	public boolean removeFile(String path) throws IOException {
		String folder = path.split("\\.")[1];
		client.changeWorkingDirectory(base_dir + "/" + folder.toUpperCase());
		boolean ret = client.deleteFile(path);
		client.changeWorkingDirectory(base_dir);
		return ret;

	}

	@Override
	public boolean updateFile(String path, String newName) throws IOException {
		String folder = path.split("\\.")[1];
		client.changeWorkingDirectory(base_dir + "/" + folder.toUpperCase());
		FTPFile[] files = client.listFiles();
		for (FTPFile file : files) {
			if (file.getName().equalsIgnoreCase(path)) {
				client.rename(path, newName);
				return true;
			}
		}
		client.changeWorkingDirectory(base_dir);
		return false;
	}

}
