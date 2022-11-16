package project.cloud.listener;

import java.text.ParseException;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
public class FileStorageListener {

	@Autowired
	ListenerUtil listenerUtil;

	@KafkaListener(topics = "fileDetails", groupId = "fileStorageGroup")
	public void fileStorageListener(@Payload String details) throws JSONException, ParseException {
		JSONObject obj = new JSONObject(details);
		String method = obj.getString("method");
		switch (method) {
		case "upload":
			listenerUtil.saveNewFileData(obj);
			break;
		case "delete":
			listenerUtil.deleteFile(obj);
			break;
		case "update":
			listenerUtil.updateFileName(obj);
			break;
		case "read":
			listenerUtil.updateReadFileCount(obj);
			break;
		case "download":
			listenerUtil.updateDownloadFileCount(obj);
			break;
		default:
			System.out.println("invalid option");
		}
	}
}
