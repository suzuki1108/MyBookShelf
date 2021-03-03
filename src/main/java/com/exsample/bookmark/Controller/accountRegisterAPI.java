package com.exsample.bookmark.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.exsample.bookmark.Logic.RegistUserLogic;
import com.exsample.bookmark.Logic.RegistUserLogic.RegisterResult;
import com.exsample.bookmark.model.UserInfoBean;

@RestController
public class accountRegisterAPI {

	@Autowired
	RegistUserLogic registeUserLogic;

	@CrossOrigin
	@RequestMapping(value="/accountRegisterAPI", method= RequestMethod.POST)
	public RegisterResult accountRegister(@RequestBody UserInfoBean userInfoBean) {

		return registeUserLogic.accountRegist(userInfoBean);
	}
}
