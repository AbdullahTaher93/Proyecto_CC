package cc.project.busapp.errors;

import java.util.Date;

public class PropertiesErrorDetails {

    private Date timestamp;
    private String message;
  //  private String details;

    public PropertiesErrorDetails(Date timestamp, String message) {
        this.timestamp = timestamp;
        this.message = message;
    }
}
