package com.exsample.bookmark.Logic;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.exsample.bookmark.model.UserInfoBean;
import com.exsample.bookmark.model.UserRepository;

import lombok.AllArgsConstructor;
import lombok.Data;

@Component
public class LoginLogic {

	@Autowired
	UserRepository userRepository;

	public UserInfoBean login(UserRepository userRepository, UserInfoBean userInput, Model model) {

		//入力されたID、パスワードともに一致するレコードを検索
		Optional<UserInfoBean> loginCheak = userRepository.findAll()
				.parallelStream()
				.filter(s -> s.getLoginId().equals(userInput.getLoginId())
						&& s.getPassWord().equals(userInput.getPassWord()))
				.findFirst();

		if(!loginCheak.isPresent()) {
			model.addAttribute("loginError",Messages.loginError);
		}

		return loginCheak.orElse(null);
	}

	//API用Json返却結果用の枠組み生成
	@Data
	@AllArgsConstructor
	public class LoginResult {
		public boolean result;
		long userId;
		public String loginId;
		public String userName;
		public String password;
		public String message;
	}

	public LoginResult loginAPI(UserInfoBean userInput) {
		//入力されたID、パスワードともに一致するレコードを検索
		Optional<UserInfoBean> loginCheak = userRepository.findAll()
				.parallelStream()
				.filter(s -> s.getLoginId().equals(userInput.getLoginId())
						&& s.getPassWord().equals(userInput.getPassWord()))
				.findFirst();

		if(!loginCheak.isPresent()) {
			return new LoginResult(false,0,userInput.getLoginId(),userInput.getUserName(),userInput.getPassWord(),Messages.loginError);
		}else {
			return new LoginResult(true,loginCheak.get().getUserId(),userInput.getLoginId(),userInput.getUserName(),userInput.getPassWord(),Messages.loginSuccess);
		}
	}
}
