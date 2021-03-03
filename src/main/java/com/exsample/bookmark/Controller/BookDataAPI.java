package com.exsample.bookmark.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.exsample.bookmark.Logic.CreateBookData;
import com.exsample.bookmark.Logic.MyBookDisplay;
import com.exsample.bookmark.model.BookBean;
import com.exsample.bookmark.model.BookRepository;
import com.exsample.bookmark.model.UserInfoBean;

@RestController
public class BookDataAPI {

	@Autowired
	MyBookDisplay myBookDisplay;
	@Autowired
	CreateBookData createBookData;
	@Autowired
	BookRepository bookRepository;

	@CrossOrigin
	@RequestMapping(value="/getMyBookDataAPI", method= RequestMethod.POST)
	public List<BookBean> getMyBookList(@RequestBody UserInfoBean userInfoBean) {
		return myBookDisplay.myBookList(userInfoBean);
	}

	@CrossOrigin
	@RequestMapping(value="/saveBookDataAPI", method= RequestMethod.POST)
	public void getMyBookList(@RequestBody BookBean bookBean) {
		createBookData.saveBookData(bookBean);
	}

	@CrossOrigin
	@RequestMapping(value="/deleteBookDataAPI", method= RequestMethod.POST)
	public void deleteMyBookList(@RequestBody BookBean bookBean) {
		bookRepository.deleteById(bookBean.getBookId());
	}
}
