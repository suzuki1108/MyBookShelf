package com.exsample.bookmark.Logic;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.exsample.bookmark.model.BookBean;

public class SortBook {

	public List<BookBean> sortBook(List<BookBean> bookList, String sortType){

		switch(sortType) {
		case "titleAsc":
			Collections.sort(bookList, new Comparator<BookBean>() {
				public int compare(BookBean a, BookBean b) {
					return a.getBookTitleKana().compareTo(b.getBookTitleKana());
				}
			});
			break;
		case "titleDesc":
			Collections.sort(bookList, new Comparator<BookBean>() {
				public int compare(BookBean a, BookBean b) {
					return b.getBookTitleKana().compareTo(a.getBookTitleKana());
				}
			});
			break;
		case "dateAsc":
			Collections.sort(bookList, new Comparator<BookBean>() {
				public int compare(BookBean a, BookBean b) {
					return a.getSalesDate().compareTo(b.getBookTitle());
				}
			});
			break;
		case "dateDesc":
			Collections.sort(bookList, new Comparator<BookBean>() {
				public int compare(BookBean a, BookBean b) {
					return b.getSalesDate().compareTo(a.getBookTitle());
				}
			});
			break;
		case "priceAsc":
			Collections.sort(bookList, new Comparator<BookBean>() {
				public int compare(BookBean a, BookBean b) {
					return a.getPrice() - b.getPrice();
				}
			});
			break;
		case "priceDesc":
			Collections.sort(bookList, new Comparator<BookBean>() {
				public int compare(BookBean a, BookBean b) {
					return b.getPrice() - a.getPrice();
				}
			});
			break;
		}
		return bookList;
	}
}
