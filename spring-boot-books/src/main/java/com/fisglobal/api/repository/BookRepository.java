package com.fisglobal.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fisglobal.api.model.*;

public interface BookRepository extends JpaRepository<Books, String> {
	Books findBybookId(String bookId);

}
