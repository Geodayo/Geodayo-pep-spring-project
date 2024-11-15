package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AccountRepository accountRepository;

    //Create Message
    public Message createMessage(Message message) throws IllegalArgumentException {
        // Validate messageText
        if (message.getMessageText().isBlank()) {
            throw new IllegalArgumentException("Message text cannot be blank");
        }
        if (message.getMessageText().length() > 255) {
            throw new IllegalArgumentException("Message text exceeds 255 characters");
        }

        // Validate postedBy
        if (!accountRepository.existsById(message.getPostedBy())) {
            throw new IllegalArgumentException("Invalid user ID for postedBy");
        }

        return messageRepository.save(message);
    }

    //Get All Message
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }


    //Get Message By Id
    public Optional<Message> getMessageById(Integer messageId) {
        return messageRepository.findById(messageId);
    }

    //Delete Message by ID
    public int deleteMessageById(Integer messageId) {
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return 1;
        }
        return 0;
    }

    //Update Message
    public int updateMessageText(Integer messageId, String newMessageText) {
        if (newMessageText.isBlank() || newMessageText.length() > 255) {
            throw new IllegalArgumentException("Message text is invalid");
        }

        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if (optionalMessage.isPresent()) {
            Message message = optionalMessage.get();
            message.setMessageText(newMessageText);
            messageRepository.save(message);
            return 1;
        }
        return 0;
    }

    //Get User Messages
    public List<Message> getMessagesByUserId(Integer accountId) {
        return messageRepository.findByPostedBy(accountId);
    }
}
