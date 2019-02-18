package cc.project.busapp.controllers;

import cc.project.busapp.errors.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;


@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ExceptionResponse> handleNotFoundException(Exception exception, WebRequest request) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), exception.getMessage(), request.getDescription(false), HttpStatus.NOT_FOUND.getReasonPhrase());
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);


    }

    @ExceptionHandler({DirectionNotFoundException.class})
    public ResponseEntity<ExceptionResponse> handleUrlNotFoundException(Exception exception, WebRequest request) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), exception.getMessage(), request.getDescription(false), HttpStatus.NOT_FOUND.getReasonPhrase());
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);

    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), ex.getBindingResult().getFieldErrors().toString(), HttpStatus.BAD_REQUEST.getReasonPhrase());
        return new ResponseEntity<Object>(exceptionResponse, new HttpHeaders(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handle(Exception ex,
                                         HttpServletRequest request, HttpServletResponse response) {
        if (ex instanceof NullPointerException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    @ExceptionHandler(ErrorQuantityPurchase.class)
    public ResponseEntity<Object> handlePurchaseErrorNotValid(ErrorQuantityPurchase exception, WebRequest request){
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), exception.getMessage(), "Esta intentando comprar mas tiquetes de los que existen", HttpStatus.BAD_REQUEST.getReasonPhrase());

        return new ResponseEntity<Object>(exceptionResponse, new HttpHeaders(),HttpStatus.BAD_REQUEST);
    }

}
