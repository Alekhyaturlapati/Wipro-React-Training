package com.user.service;

import org.springframework.stereotype.Service;

import com.user.entity.Contact;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactService {

    private List<Contact> contactList = List.of(
        new Contact(1L, "lee@email.com", "Lee", 101L),
        new Contact(2L, "john@email.com", "John", 101L),
        new Contact(3L, "alex@email.com", "Alex", 101L),
        new Contact(3L, "example@email.com", "Arjun", 102L)
    );

    public List<Contact> getContactsOfUser(Long userId) {
        return contactList.stream()
            .filter(c -> c.getUserId().equals(userId))
            .collect(Collectors.toList());
    }
}
