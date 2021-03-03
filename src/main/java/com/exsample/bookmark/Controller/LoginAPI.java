package com.exsample.bookmark.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.exsample.bookmark.Logic.LoginLogic;
import com.exsample.bookmark.Logic.LoginLogic.LoginResult;
import com.exsample.bookmark.model.UserInfoBean;

@RestController
public class LoginAPI {

	@Autowired
	LoginLogic loginLogic;

	@CrossOrigin
	@RequestMapping(value="/loginAPI", method= RequestMethod.POST)
	public LoginResult login(@RequestBody UserInfoBean userInfoBean) {
		return loginLogic.loginAPI(userInfoBean);
	}
}