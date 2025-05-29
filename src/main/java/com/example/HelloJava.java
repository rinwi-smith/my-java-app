package com.example;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.annotation.PostConstruct;

@SpringBootApplication
@RestController
public class HelloJava {
    private final MessageRepository messageRepository;

    public HelloJava(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(HelloJava.class, args);
    }

    @PostConstruct
    public void init() {
        messageRepository.save(new Message(1L, "Hello, Database!"));
    }

    @GetMapping("/message")
    public String getMessage() {
        return messageRepository.findById(1L)
                .map(Message::getContent)
                .orElse("No message found");
    }

    @GetMapping("/")
    public String hello() {
        return "Hello, Java!";
    }
}
