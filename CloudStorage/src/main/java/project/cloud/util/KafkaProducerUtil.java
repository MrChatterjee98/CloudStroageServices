package project.cloud.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import project.cloud.model.FileDetails;

@Component
public class KafkaProducerUtil {
	
	@Autowired
	KafkaTemplate<String, String> fileStorageTemplate;
	
	public void uploadFileInfo(MultipartFile file) throws IOException {
		String checkSum = DigestUtils.md5DigestAsHex(file.getInputStream());
		SimpleDateFormat dateFormatter= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		FileDetails info = new FileDetails(file.getOriginalFilename(),file.getOriginalFilename().split("\\.")[1],file.getSize()+" Bytes",dateFormatter.format(new Date(System.currentTimeMillis())));
		JSONObject fileInfo = new JSONObject(info.toString());
		fileInfo.put("checksum", checkSum);
		fileInfo.put("method","upload");
		fileStorageTemplate.send("fileDetails",fileInfo.toString());
	}
	
	public void deleteFileInfo(String fileName) {
		JSONObject fileInfo = new JSONObject();
		fileInfo.put("fileName", fileName);
		fileInfo.put("method","delete");
		fileStorageTemplate.send("fileDetails",fileInfo.toString());
	}
	
	public void downloadFileInfo(String fileName) {
		JSONObject fileInfo = new JSONObject();
		fileInfo.put("fileName", fileName);
		fileInfo.put("method", "download");
		fileStorageTemplate.send("fileDetails",fileInfo.toString());
	}
	
	public void readFileInfo(String fileName) {
		JSONObject fileInfo = new JSONObject();
		fileInfo.put("fileName",fileName);
		fileInfo.put("method", "read");
		fileStorageTemplate.send("fileDetails",fileInfo.toString());
	}
	
	public void updateFileInfo(String fileName,String newFileName) {
		JSONObject fileInfo = new JSONObject();
		fileInfo.put("fileName",fileName);
		fileInfo.put("newFileName", newFileName);
		fileInfo.put("method", "update");
		fileStorageTemplate.send("fileDetails",fileInfo.toString());
	}
}

