package com.example.first_app.api;

import com.example.first_app.exceptions.ApplicationError;
import com.example.first_app.exceptions.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class ErrorHandler extends ResponseEntityExceptionHandler {

    //accessing properties in the properties.application file using @Value
    @Value("${api_doc_url}")
    private String detail;

    //when ever this exception is occurred in the application this will be returned.
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ApplicationError> handleCustomerNotFoundException(CustomerNotFoundException e, WebRequest webRequest){
        ApplicationError applicationError = new ApplicationError();
        applicationError.setCode(404);
        applicationError.setDetails(detail);
        applicationError.setMessage(e.getMessage());

        return new ResponseEntity<>(applicationError, HttpStatus.NOT_FOUND);

    }

}
