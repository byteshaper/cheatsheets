package com.byteshaper.cheatsheets.springbootrest;

import org.springframework.stereotype.Service;

@Service
public class ExampleService {

    public String processInput(String input, int filterBy) {
        return "I received input=" + input + ", filterBy=" + filterBy;
    }
}
