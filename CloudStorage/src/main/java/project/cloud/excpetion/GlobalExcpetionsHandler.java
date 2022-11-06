package project.cloud.excpetion;

import java.net.SocketTimeoutException;
import java.sql.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExcpetionsHandler {
	
	@ExceptionHandler(value = ConnectionNotEstablishedException.class)
	public ResponseEntity<ErrorMessage> handleConnectionNotEstablishedExcpetion(ConnectionNotEstablishedException ex,WebRequest req) {
		ErrorMessage errMsg = new ErrorMessage("ConnctionNotEstablished", "Unable establish connection with the server", req.getContextPath(),new Date(System.currentTimeMillis()).toString());
		return new ResponseEntity<ErrorMessage>(errMsg,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = SocketTimeoutException.class)
	public ResponseEntity<ErrorMessage> connectionTimeOutHandler(SocketTimeoutException ex){
		ErrorMessage errMsg = new ErrorMessage("ConnctionNotEstablished", "Unable establish connection with the server", "",new Date(System.currentTimeMillis()).toString());
		return new ResponseEntity<ErrorMessage>(errMsg,HttpStatus.NOT_FOUND);
	}
}
