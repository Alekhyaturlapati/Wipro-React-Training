package com.Contact.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Contact.entity.Contact;
import com.Contact.repository.ContactRepository;

import java.util.List;

@Service
public class ContactService {
    @Autowired
    private ContactRepository contactRepository;

    public List<Contact> getContactsOfUser(Long userId) {
        return contactRepository.findByUserId(userId);
    }
}
