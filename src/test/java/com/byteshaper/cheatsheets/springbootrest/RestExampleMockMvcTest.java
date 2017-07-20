package com.byteshaper.cheatsheets.springbootrest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Launches the SpringBoot application and calls the REST-layer (directly, not via HTTP) using {@link MockMvc} which
 * allows some easy fluent basic validation.
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RestExampleMockMvcTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Test
    public void shouldReturnMessage() throws Exception {
        mockMvc
            .perform(get("/example/bla?filterBy=6"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json("{number: 42, message: \"I received input=bla, filterBy=6\"}", true))
            .andReturn();
    }
}
