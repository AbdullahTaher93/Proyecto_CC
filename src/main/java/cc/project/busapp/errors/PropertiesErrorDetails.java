package cc.project.busapp.errors;


import java.util.Date;

public class PropertiesErrorDetails {

    private Date date;
    private String error;
    private String message;


    public PropertiesErrorDetails(Date date, String error, String message) {
        this.date = date;
        this.error = error;
        this.message =message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
