package com.revature.daos;

import com.revature.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookDAO extends JpaRepository<Book, Integer> {

    //find all books by user id
    public Book findByUserUserId(int userId);

}
