package com.revature.models;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

@Entity //This makes a class a DB table
@Table(name = "users") //This lets us name our DB table
@Component //Makes a class a bean (stereotype annotation)
public class User {

    @Id //This makes the field the PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) //This makes the PK auto-increment
    private int userId;

    //@Column isn't necessary UNLESS we need to specify constraints

    @Column(nullable = false, unique = true)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    //boilerplate code-------------------------


    public User() {
        System.out.println("-------User Contructor 1 no args------------");
    }

    public User(int userId, String firstname, String lastname) {
        this.userId = userId;
        this.firstname = firstname;
        this.lastname = lastname;
        System.out.println("-------User Contructor 2 with args------------");
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
