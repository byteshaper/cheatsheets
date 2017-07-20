package com.byteshaper.cheatsheets.springbootrest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Example for a REST controller.
 *
 */
@RestController
public class RestExample {
    
    @Autowired
    private ExampleService exampleService;
    
    @RequestMapping(value = "/example/{input}", method = RequestMethod.GET, produces = "application/json") 
    public Stuff getStuffAsJson(
            @PathVariable("input") String input,
            @RequestParam("filterBy") int filterBy) {
        Stuff stuff = new Stuff();
        stuff.message = exampleService.processInput(input, filterBy);
        stuff.number = 42;
        return stuff;
    }
}
