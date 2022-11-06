package project.cloud.excpetion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class ErrorMessage {
	public String excpetionName;
	public String message;
	public String path;
	public String timeStamp;
}
