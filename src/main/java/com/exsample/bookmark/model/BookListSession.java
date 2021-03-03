package com.exsample.bookmark.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BookListSession implements Serializable {

	private List<BookBean> bookList;

    public List<BookBean> getBookList() {
        if (bookList == null) {
        	bookList = new ArrayList<BookBean>();
        }
        return bookList;
    }

    public void setBookList(List<BookBean> bookList) {
        this.bookList = bookList;
    }
}
