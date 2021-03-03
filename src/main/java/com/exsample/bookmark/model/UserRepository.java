package com.exsample.bookmark.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserInfoBean, Integer> {

}
