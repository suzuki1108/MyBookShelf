package com.exsample.bookmark.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "book")
public class BookBean implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "book_id")
	private long bookId;

	@Column(name = "book_title")
	private String bookTitle;

	@Column(name = "book_title_kana")
	private String bookTitleKana;

	@Column(name = "author")
	private String author;

	@Column(name = "sales_date")
	private String salesDate;

	@Column(name = "img_path")
	private String imgPath;

	@Column(name = "description")
	private String description;

	@Column(name = "user_id")
	private long userId;

	@Column(name = "price")
	private int price;

	@Column(name = "insert_date")
	private Date insertDate;
}
