package com.exsample.bookmark.Logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.exsample.bookmark.model.BookBean;
import com.exsample.bookmark.model.BookRepository;

@Component
public class CreateBookData {

	@Autowired
	BookRepository bookRepository;

	public void saveBookData(BookBean bookBean) {
		bookBean.setInsertDate(new java.sql.Date(System.currentTimeMillis()));
		bookRepository.saveAndFlush(bookBean);
	}

}
