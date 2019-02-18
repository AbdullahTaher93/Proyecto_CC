package cc.project.busapp.errors;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PojoExceptionResponse {

    private Date timestamp;
    private String message;
    private String details;
    private String httpCodeMessage;

    public PojoExceptionResponse(Date timestamp, String message, String details, String httpCodeMessage) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
        this.httpCodeMessage = httpCodeMessage;
    }
}
