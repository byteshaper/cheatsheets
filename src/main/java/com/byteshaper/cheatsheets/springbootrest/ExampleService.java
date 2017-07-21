package com.byteshaper.cheatsheets.springbootrest;

import org.springframework.stereotype.Service;

@Service
public class ExampleService {

    public String processInput(String input, Integer filterBy) {
        if(1 == 1) {
            throw new NumberFormatException("Just a test");
        }
        
        return "I received input=" + input + ", filterBy=" + filterBy;
    }
}
