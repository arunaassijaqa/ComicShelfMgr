package com.revature.models;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.stereotype.Component;


@Entity
@Table(name="books")
@Component
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;
   // @Column(name="bookTitle", nullable = false)
    private String bookTitle;
    private String author;

    @ManyToOne(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    //@OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

   // @Transient //This makes a field that doesn't get persisted (saved/created) to the database
    //private int transientUserId;

    //Boilerplate code-----------------------------------

    //no args constructor
    public Book() {
        System.out.println("-------Book Contructor 1 no args------------");
    }

    //all args constructor
    public Book(int bookId, String bookTitle, String author, User user) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.author = author;
        this.user = user;
        System.out.println("-------Book Contructor 2 with args------------");
    }

    //getters and setters
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //toString
    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", bookTitle='" + bookTitle + '\'' +
                ", author='" + author + '\'' +
                ", user=" + user +
                '}';
    }
}



