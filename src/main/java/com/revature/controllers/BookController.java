package com.revature.controllers;

import com.revature.daos.BookDAO;
import com.revature.daos.UserDAO;
import com.revature.models.Book;
import com.revature.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    private BookDAO bookDAO;
    private UserDAO userDAO;

    @Autowired
    public BookController(BookDAO bookDAO, UserDAO userDAO) {
        this.bookDAO = bookDAO;
        this.userDAO = userDAO;
        System.out.println("-------Autowired BookController Contructor 1 with args------------");
    }


    @PostMapping("/{userId}")
    public ResponseEntity<Book> insertBook(@RequestBody Book book, @PathVariable int userId){

        System.out.println("-------insertBook POST /books/userId STARTED------------");
        //we want to send a user id int from postman, but we only have the User object in the Book class.
        //we have to send in the user id as a Path Variable, and get the user by ID, to attach to the Book.
        User u = userDAO.findById(userId).get();




        book.setUser(u); //attach the given User to the book object before we send it to the DB

        Book g = bookDAO.save(book); //Remember, save() also RETURNS the object that was saved

        System.out.println("-------insertBook POST /books/userId SUCCESS------------");

        return ResponseEntity.status(201).body(g);

    }

    //this method will take an entire book object and replace an existing book with it
    @PutMapping("/{userId}")
    public ResponseEntity<Book> updateBook(@RequestBody Book book, @PathVariable int userId){

        System.out.println("-------updateBook PUT /books/userId STARTED------------");

        User u = userDAO.findById(userId).get();



        book.setUser(u);

        Book g = bookDAO.save(book); //updates and inserts use the SAME JPA METHOD, save()

        System.out.println("-------updateBook PUT /books/userId SUCCESS------------");

        return ResponseEntity.ok(g);

    }

    //this method will update ONLY the title of a book
    @PatchMapping("/{bookId}")
    public ResponseEntity<Object> updateBookTitle(@RequestBody String title, @PathVariable int bookId){

        System.out.println("-------updateBookTitle PATCH /books/bookId STARTED------------");

        //get the book by Id, set the new title (setter), save it back to the DB :)

        Optional<Book> g = bookDAO.findById(bookId);



        //if the Optional is empty, send an error message, otherwise send the updated book
        if(g.isEmpty())
        {
            return ResponseEntity.status(404).body("No book found with ID of: " + bookId);
        }

        //extract the Book from the Optional
        Book book = g.get();

        //update the title, using the setter
        book.setBookTitle(title);

        //finally, save the updated book back to the DB
        bookDAO.save(book);

        System.out.println("-------updateBookTitle PATCH /books/bookId SUCCESS------------");

        return ResponseEntity.accepted().body(book);

    }

    //this method will delete a book by its ID
    @DeleteMapping("/{bookId}")
    public ResponseEntity<Object> deleteBook(@PathVariable int bookId){

        System.out.println("-------deleteBook DALETE /books/bookId STARTED------------");


        Optional<Book> g = bookDAO.findById(bookId);


        if(g.isEmpty()){
            return ResponseEntity.status(404).body("No book found with ID of: " + bookId);
        }

        Book book = g.get();

        bookDAO.deleteById(bookId);

        System.out.println("-------deleteBook DALETE /books/bookId SUCCESS------------");


        return ResponseEntity.accepted().body("Book " + book.getBookTitle() + " has been deleted");

    }

}

