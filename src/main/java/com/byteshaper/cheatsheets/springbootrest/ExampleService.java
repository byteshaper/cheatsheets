package com.byteshaper.cheatsheets.springbootrest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ExampleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleService.class);
    
    public String processInput(String input, Integer filterBy) {
    	LOGGER.debug("Debug: {}", input);
    	LOGGER.info("Info: {}", input);
    	LOGGER.warn("Warn: {}", input);
    	LOGGER.error("Error: {}", input);
        
        return "I received input=" + input + ", filterBy=" + filterBy;
    }
}
