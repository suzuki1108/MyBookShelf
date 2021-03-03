package com.exsample.bookmark.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookBean, Long> {

	public List<BookBean> findByUserId(long userId);

}
