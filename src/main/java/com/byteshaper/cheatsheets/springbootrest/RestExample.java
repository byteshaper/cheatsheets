package com.byteshaper.cheatsheets.springbootrest;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    @ApiOperation(
            value = "This endpoint returns some magic stuff derived from the input :-)", 
            produces = "application/json")
    public Stuff getStuffAsJson(
            
            @PathVariable("input") // required = true has no effect here
            @ApiParam(value = "Input text", required = true) 
            String input,
            
            @RequestParam(value = "filterBy", required = false) // => but has an effect here, and is picked up by Swagger as well
            @ApiParam(value = "Some integer value") 
            Integer filterBy) {
        Stuff stuff = new Stuff();
        stuff.message = exampleService.processInput(input, filterBy);
        stuff.number = 42;
        return stuff;
    }    
}
