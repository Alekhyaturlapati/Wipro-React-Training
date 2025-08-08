package com.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    private Long userId;
    private String name;
    private String phone;
    private String address;

    @Transient
    private List<Contact> contact; // just add this

    public User() {}

    public User(Long userId, String name, String phone, String address) {
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    // Add this constructor for convenience (optional)
    public User(Long userId, String name, String phone, String address, List<Contact> contact) {
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.contact = contact;
    }

    // Getters and Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public List<Contact> getContact() { return contact; }
    public void setContact(List<Contact> contact) { this.contact = contact; }
}
