package project.cloud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.cloud.model.FileStorageInfo;
import project.cloud.repo.FileDetailsRepository;

@Service
public class FileStorageService {

	@Autowired
	FileDetailsRepository fileDetailsRepo;

	public void saveFile(FileStorageInfo info) {
		fileDetailsRepo.save(info);
	}

	public void updateFile(String fileName, String newField) {
		FileStorageInfo info = fileDetailsRepo.findFileByFileName(fileName);
		info.setFileName(newField);
		fileDetailsRepo.save(info);
	}

	public boolean deleteFile(String fileName) {
		FileStorageInfo info = fileDetailsRepo.findFileByFileName(fileName);
		fileDetailsRepo.delete(info);
		return true;
	}

	public FileStorageInfo findFileInfoByName(String filename) {
		return fileDetailsRepo.findFileByFileName(filename);
	}

	public List<FileStorageInfo> findAllFiles() {
		return fileDetailsRepo.findAll();
	}

	public void updateReadCount(String fileName) {
		FileStorageInfo info = fileDetailsRepo.findFileByFileName(fileName);
		info.setFileReadingCount(info.getFileReadingCount() + 1);
		fileDetailsRepo.save(info);
	}
	public void updateDownloadCount(String fileName) {
		FileStorageInfo info = fileDetailsRepo.findFileByFileName(fileName);
		info.setFileDownloadCount(info.getFileDownloadCount() + 1);
		fileDetailsRepo.save(info);
	}


}
