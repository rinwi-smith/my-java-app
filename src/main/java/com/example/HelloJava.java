package com.example;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

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

    @DeleteMapping("/message/{id}")
    public ResponseEntity<String> deleteMessage(@PathVariable Long id) {
        try {
        if (messageRepository.existsById(id)) {
            messageRepository.deleteById(id);
                return ResponseEntity.ok("Message with ID " + id + " deleted");
        }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting message: " + e.getMessage());
        }
    }

    @GetMapping("/messages")
    public List<String> getAllMessages() {
        return messageRepository.findAll()
                .stream()
                .map(Message::getContent)
                .collect(Collectors.toList());
    }

    @GetMapping("/message")
    public String getMessage() {
        return messageRepository.findById(1L)
                .map(Message::getContent)
                .orElse("No message found");
    }

    @PostMapping("/message")
    public String saveMessage(@RequestBody Message message) {
        if (messageRepository.existsById(message.getId())) {
            Message existing = messageRepository.findById(message.getId()).get();
            existing.setContent(message.getContent());
            messageRepository.save(existing);
        } else {
            messageRepository.save(message);
        }
        return "Message saved: " + message.getContent();
    }

    @GetMapping("/")
    public String hello() {
        return "Hello, Java!";
    }
}
