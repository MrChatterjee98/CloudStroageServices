package project.cloud.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class FileStorageInfo {
	
	@Id
	private String id;
	private String fileName;
	private String fileType;
	private String size;
	private String checksum;
	private String createdAt;
	private int fileDownloadCount;
	private int fileReadingCount;
	
}
