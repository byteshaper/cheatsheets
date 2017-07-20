package com.byteshaper.cheatsheets.springbootrest;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
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
public class RestExampleRestAssuredTest {

    @LocalServerPort
    private int port;
    
    @Test
    public void getStuff() {
        Stuff stuff = get("http://localhost:" + port + "/example/bla?filterBy=21")
                .then().assertThat().statusCode(200)
                .and().assertThat().contentType("application/json")
                .and().extract().response().as(Stuff.class);           
        assertThat(stuff, not(nullValue()));
        assertThat(stuff.message, equalTo("I received input=bla, filterBy=21"));
        assertThat(stuff.number, equalTo(42));
    }
}
