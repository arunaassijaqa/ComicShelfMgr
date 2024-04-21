package com.revature.controllers;

import com.revature.daos.UserDAO;
import com.revature.models.Book;
import com.revature.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController //This makes a class a bean, and converts every response to JSON for us
@RequestMapping("/users") //All HTTP Requests ending in /users will be handled by this controller
public class UserController {

    private UserDAO userDAO;

    //Constructor injection (because this controller depends on the UserDAO)
    @Autowired
    public UserController(UserDAO userDAO) {
        this.userDAO = userDAO;
        System.out.println("-------Autowired UserController Contructor 1 with args------------");
    }

    //A method that inserts a new user (POST request)
    @PostMapping
    public ResponseEntity<User> insertUser(@RequestBody User user){

        System.out.println("-------insertUser POST /users/ STARTED----------------");

        User u = userDAO.save(user);


        if(u == null){
            System.out.println("-------insertUser POST /users/ FAILED SEND ERROR INTERNAL SERVER ERROR-----------");
            //this will a response with status 500, and NO RESPONSE BODY (which is what build() does)
            return ResponseEntity.internalServerError().build();
        }

        System.out.println("-------insertUser POST /users/ SUCCESS----------------");
        //this will return a response with status 201 (created), and also send the user back for us to see
        return ResponseEntity.status(201).body(u);

    }

    //This method will get all users (GET request)
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){

        System.out.println("-------getAllUsers GET /users/ STARTED----------------");
        //not much error handling needed, since there's no user input
        List<User> users = userDAO.findAll();


        if(users.isEmpty()) {
            System.out.println("-------getAllUsers GET /users/EMPTY FAILED----------------");
            return ResponseEntity.status(404).body(users);
        }

        System.out.println("-------getAllUsers GET /users/ SUCCESS----------------");
        return ResponseEntity.ok(users);

    }

    //This method will get a user by their username (GET request with a PathVariable)
    @GetMapping("/{firstname}")
    public ResponseEntity<User> getUserByFirstname(@PathVariable String firstname){

        System.out.println("-------getUserByFirstname GET /users/firstname STARTED----------------");

        User u = userDAO.findByFirstname(firstname);


        if(u == null){
            System.out.println("-------getUserByFirstname GET /users/firstname FAILED USER NOT FUOND----------------");
            //return a ResponseEntity with status 404 (not found)
            return ResponseEntity.notFound().build();
        }

        System.out.println("-------getUserByFirstname GET /users/firstname SUCCESS----------------");

        //return a ResponseEntity with status 200 (ok), and the user in the body
        return ResponseEntity.ok(u);

    }

    //this method will take an entire user object and replace an existing user with it
    @PutMapping("/{userId}")
    public ResponseEntity<Book> updateUser(@RequestBody User user, @PathVariable int userId){

        System.out.println("-------updateUser PUT /users/userId STARTED------------"+userId);
        //System.out.println(user.getFirstname());

        Optional<User> u = userDAO.findById(userId);

        if(u.isEmpty())
        {
            System.out.println("-------updateUser PUT /users/userId FAAID User not found------------");
            return ResponseEntity.status(404).build();
        }

        User newU = u.get();

        user.setUserId(newU.getUserId());

        User newuser = userDAO.save(user); //updates and inserts use the SAME JPA METHOD, save()

        System.out.println(newuser.getFirstname());

        System.out.println("-------updateUser PUT /users/userId SUCCESS------------");

        return ResponseEntity.status(200).build();

    }


}
