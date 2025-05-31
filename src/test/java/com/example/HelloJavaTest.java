package com.example;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class HelloJavaTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MessageRepository messageRepository;

    @Test
    public void testGetRoot() throws Exception {
        mockMvc.perform(get("/"))
               .andExpect(status().isOk())
               .andExpect(content().string("Hello, Java!"));
    }

    @Test
    public void testGetMessage() throws Exception {
        mockMvc.perform(get("/message"))
               .andExpect(status().isOk())
               .andExpect(content().string("Hello, Database!"));
    }

    @Test
    public void testGetAllMessages() throws Exception {
        mockMvc.perform(get("/messages"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0]").value("Hello, Database!"));
    }

    @Test
    public void testPostMessage() throws Exception {
        String json = "{\"id\":3,\"content\":\"Test Message\"}";
        mockMvc.perform(post("/message")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
               .andExpect(status().isOk())
               .andExpect(content().string("Message saved: Test Message"));
    }

    @Test
    public void testDeleteMessage() throws Exception {
        Message message = new Message(4L, "Temp Message");
        messageRepository.save(message);
        mockMvc.perform(delete("/message/4"))
               .andExpect(status().isOk())
               .andExpect(content().string("Message with ID 4 deleted"));
    }
}
