package com.user.external;

import com.user.entity.Contact;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "CONTACT-SERVICE")
public interface ContactServiceFeingClient {

    @GetMapping("/contact/user/{userId}")
    List<Contact> getContactsByUserId(@PathVariable("userId") Long userId);
}
