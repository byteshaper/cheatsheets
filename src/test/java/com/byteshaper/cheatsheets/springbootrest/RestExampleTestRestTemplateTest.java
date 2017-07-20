package com.byteshaper.cheatsheets.springbootrest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Launches the whole SpringBoot application and performs test by calling the endpoints via {@link TestRestTemplate}.  
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RestExampleTestRestTemplateTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    public void getStuff() {
        Stuff stuff = restTemplate.getForObject("http://localhost:" + port + "/example/bla?filterBy=21", Stuff.class);
        assertThat(stuff, not(nullValue()));
        assertThat(stuff.message, equalTo("I received input=bla, filterBy=21"));
    }
}
