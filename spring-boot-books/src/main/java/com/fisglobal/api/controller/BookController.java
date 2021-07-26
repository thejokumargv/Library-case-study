package com.fisglobal.api.controller;

import java.util.List;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fisglobal.api.model.Books;
import com.fisglobal.api.repository.BookRepository;


@RestController
public class BookController {
    @Autowired
	private BookRepository bookRepository;
    
    @PostMapping("/saveBook")
    public String saveBook(@RequestBody Books book) {
    	bookRepository.save(book);
    	return "Book saved";
    }
    
    @GetMapping("/books")
    public List<Books> getAll(){
    	return bookRepository.findAll();
    }

    @GetMapping("/books/{bookId}")
    public Books getBookByBookid(@PathVariable String bookId) {
    	return bookRepository.findBybookId(bookId);
    }
    
    @PatchMapping("/books/UpdateAvailability/{bookId}/{incremental_count}")
    public void updateBookAvailability(@PathVariable String bookId, @PathVariable int incremental_count)  {
    	Books bookExistingbook = bookRepository.findBybookId(bookId);   	
    	bookExistingbook.setCopiesAvailable(bookExistingbook.getCopiesAvailable()+incremental_count);
    	bookRepository.save(bookExistingbook);    	
    }
    

// This Kafka implementation of listen message is commented as I have some problem in starting kafka server 
//    @KafkaListener(topics = "SubscriptionTopic", groupId="Group1")
//    public void messageListner (ConsumerRecord<String, String> msg) throws Exception {
//        String message = msg.value();
//        System.out.println(message + "is available to subscribe now");
//    }

    
}
