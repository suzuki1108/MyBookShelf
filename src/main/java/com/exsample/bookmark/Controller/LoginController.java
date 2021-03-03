package com.exsample.bookmark.Controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.exsample.bookmark.Logic.LoginLogic;
import com.exsample.bookmark.Logic.MyBookDisplay;
import com.exsample.bookmark.model.BookBean;
import com.exsample.bookmark.model.BookRepository;
import com.exsample.bookmark.model.LoginUserSession;
import com.exsample.bookmark.model.UserInfoBean;
import com.exsample.bookmark.model.UserRepository;

@Controller
public class LoginController {

	@Autowired
	UserRepository userRepository;
	@Autowired
	BookRepository bookRepository;
	@Autowired
	LoginUserSession loginUser;
	@Autowired
	HttpSession session;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {

		model.addAttribute("userInfoBean", new UserInfoBean());

		return "loginForm";
	}

	@PostMapping(value = "/login")
	public String login(@ModelAttribute UserInfoBean userInfoBean, Model model) {

	    UserInfoBean loginCheck = new LoginLogic().login(userRepository, userInfoBean, model);

		if(loginCheck != null) {
			loginUser.setUserInfoBean(loginCheck);
			//セッションにログインユーザの本棚をセット
			List<BookBean> myBookList = new MyBookDisplay().myBookDisplay(model, loginCheck, bookRepository);
			session.setAttribute("myBookList", myBookList);
			session.setAttribute("userName", loginCheck.getUserName().concat(" さんの本棚です。"));

			return "mainMenu";
		}else {
			return "loginForm";
		}
	}
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String logout(Model model) {

		session.invalidate();
		model.addAttribute("userInfoBean", new UserInfoBean());

		return "loginForm";
	}

	@RequestMapping(value = "/readme")
	public String readMe() {
		return "readme";
	}
}
