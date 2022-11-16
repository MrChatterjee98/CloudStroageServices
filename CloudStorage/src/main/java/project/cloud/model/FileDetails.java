package project.cloud.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor 
public class FileDetails {
	
	private String fileName;
	private String fileType;
	private String size;
	private String createdAt;
	@Override
	public String toString() {
		return String.format("{filename:\"%s\",filetype:\"%s\",size:\"%s\",createdat:\"%s\"}",this.fileName,this.fileType,this.size,this.createdAt);
	}
}
