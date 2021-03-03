package com.exsample.bookmark.Logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.exsample.bookmark.model.BookBean;
import com.exsample.bookmark.model.BookRepository;
import com.exsample.bookmark.model.UserInfoBean;

@Component
public class MyBookDisplay {

	@Autowired
	BookRepository bookRepository;

	//セッションに自分の本棚を格納
	public List<BookBean> myBookDisplay(Model model, UserInfoBean userInfoBean, BookRepository bookRepository){

		List<BookBean> myBookList = bookRepository.findByUserId(userInfoBean.getUserId());

		if(myBookList.size() == 0) {
			model.addAttribute("myBookIsEmpty", Messages.myBookIsEmpty);
		}

		return myBookList;
	}

	//検索結果のリストを本棚に格納
	public List<BookBean> searchBook(String searchTitle) {
		List<BookBean> searchResult = new GetBookData().searchBook(searchTitle, "", "");

		return (searchResult);
	}

	//API用
	public List<BookBean> myBookList(UserInfoBean userInfoBean){
		List<BookBean> myBookList = bookRepository.findByUserId(userInfoBean.getUserId());
		return myBookList;
	}
}
