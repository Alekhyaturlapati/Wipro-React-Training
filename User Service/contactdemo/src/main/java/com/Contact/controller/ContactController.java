package com.Contact.controller;



import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Contact.entity.Contact;

@RestController
@RequestMapping("/contact")
public class ContactController {

    private List<Contact> contactList = List.of(
        new Contact(1L, "alekhya@email.com", "alekhya", 101L),
        new Contact(2L, "jashu@email.com", "jashu", 101L),
        new Contact(3L, "raj@email.com", "raj", 102L)
    );

    @GetMapping("/user/{userId}")
    public List<Contact> getContactsByUserId(@PathVariable Long userId) {
        return contactList.stream()
                .filter(c -> c.getUserId().equals(userId))
                .toList();
    }
}
