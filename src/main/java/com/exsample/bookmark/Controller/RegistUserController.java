package com.exsample.bookmark.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.exsample.bookmark.Logic.Messages;
import com.exsample.bookmark.Logic.RegistUserLogic;
import com.exsample.bookmark.model.UserInfoBean;
import com.exsample.bookmark.model.UserRepository;

@Controller
public class RegistUserController {

	@Autowired
	UserRepository userRepository;

	@RequestMapping(value = "/createAccount")
	public String createAccount(Model model) {
		model.addAttribute("userInfoBean", new UserInfoBean());
		return "createAccount";
	}

	@RequestMapping(value = "/registUser")
	public String registUser(@ModelAttribute UserInfoBean userInfoBean ,Model model) {

		if(new RegistUserLogic().registUser(userRepository, model, userInfoBean)) {
			model.addAttribute("registResult", Messages.registUserSuccess);
			return "loginForm";
		}else {
			return "createAccount";
		}
	}
}
