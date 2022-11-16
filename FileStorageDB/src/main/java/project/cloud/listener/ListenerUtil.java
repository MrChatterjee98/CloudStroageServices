package project.cloud.listener;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import project.cloud.model.FileStorageInfo;
import project.cloud.service.FileStorageService;

@Component
public class ListenerUtil {

	@Autowired
	private FileStorageService service;

	public void saveNewFileData(JSONObject obj) {
		String id = obj.getString("filename").substring(0,2) + obj.getString("checksum").substring(25);
		FileStorageInfo fInfo = new FileStorageInfo(id,obj.getString("filename"), obj.getString("filetype"),
				obj.getString("size"), obj.getString("checksum"), (obj.getString("createdat")), 0, 0);
		service.saveFile(fInfo);
	}

	public void updateFileName(JSONObject obj) {
		String fileName = obj.getString("fileName");
		String newFileName = obj.getString("newFileName");
		service.updateFile(fileName, newFileName);
	}

	public void updateReadFileCount(JSONObject obj) {
		String fileName = obj.getString("fileName");
		service.updateReadCount(fileName);
	}

	public void updateDownloadFileCount(JSONObject obj) {
		String fileName = obj.getString("fileName");
		service.updateDownloadCount(fileName);
	}
	
	public void deleteFile(JSONObject obj) {
		String fileName = obj.getString("fileName");
		service.deleteFile(fileName);
	}
}
