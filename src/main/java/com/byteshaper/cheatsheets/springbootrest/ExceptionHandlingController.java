package com.byteshaper.cheatsheets.springbootrest;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Will handle exceptions hitting the REST controller layer and turn them into custom responses.
 * Simply add handler method for all Exceptions you want to handle in a certain way. 
 * 
 * If the REST-endpoints return application/json, the ResponseEntity returned by your custom handler method
 * also needs to return JSON so you need to wrap the message in a Java object like in this example.
 * 
 * If the REST-endpoints however return text/plain, it's ok to let the ResponseEntity wrap a plain String.
 *
 */
@ControllerAdvice
@ResponseBody
public class ExceptionHandlingController {
    
    /**
     * Wrapper for error message in case endpoints return JSON
     *
     */
    public static class ErrorJson {
        
        public final String message;
        
        public ErrorJson(String message) {
            this.message = message;
        }
    }
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlingController.class);
    
    /**
     * In case it's a application/json endpoint
     * 
     * @param e
     * @return
     * @throws IOException
     */
    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ErrorJson> numberFormatExceptionForJson(NumberFormatException e) throws IOException {
        
        LOGGER.warn(
                String.format(
                        "%s , errorMessage=%s", 
                        e.getClass().getSimpleName(), 
                        e.getMessage()), e);
        
        return ResponseEntity.status(400).body(new ErrorJson("NumberFormatError dude: " + e.getMessage()));
    }
    
   
    /**
     * That would work in case it's a application/json endpoint - if both were active, Spring would have problems 
     * since the handler would be ambiguous.
     * 
     * @param e
     * @return
     * @throws IOException
     */
    /*
    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<String> numberFormatExceptionForString(NumberFormatException e) throws IOException {
        
        LOGGER.warn(
                String.format(
                        "%s , errorMessage=%s", 
                        e.getClass().getSimpleName(), 
                        e.getMessage()), e);
        
        return ResponseEntity.status(400).body("NumberFormatError dude: " + e.getMessage());
    }
    */
}
