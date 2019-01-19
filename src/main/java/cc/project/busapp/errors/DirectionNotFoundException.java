package cc.project.busapp.errors;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DirectionNotFoundException extends RuntimeException{

    public DirectionNotFoundException(String message){
        super(message);
    }

}