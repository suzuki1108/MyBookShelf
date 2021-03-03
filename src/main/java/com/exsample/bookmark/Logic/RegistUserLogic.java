package com.exsample.bookmark.Logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.exsample.bookmark.model.UserInfoBean;
import com.exsample.bookmark.model.UserRepository;

import lombok.AllArgsConstructor;
import lombok.Data;

@Component
public class RegistUserLogic {

	@Autowired
	UserRepository userRepository;

	public boolean registUser(UserRepository userRepository, Model model, UserInfoBean userInfoBean) {

		//文字数、null、正規表現に問題がないかチェック、エラーメッセージ格納
		List<String> errorList = inputCheak(userInfoBean);

		if(errorList.size() == 0) {
			Optional<UserInfoBean> distinctCheak = userRepository
					.findAll()
					.parallelStream()
					.filter(s -> s.getLoginId().equals(userInfoBean.getLoginId()))
					.findFirst();

			//LoginIDが既存のユーザと重複していないかチェック、重複なしの場合DBにインサート
			//errorが存在する場合はfalseを返し、Modelにエラーリストを格納
			if(distinctCheak.isPresent()) {
				errorList.add(Messages.registUserDistincterror);
				model.addAttribute("errorMessages", errorList);
				return false;
			}else {
				userRepository.saveAndFlush(userInfoBean);
				model.addAttribute("userInfoBean", userInfoBean);
				return true;
			}
		}else {
			model.addAttribute("errorMessages", errorList);
			return false;
		}
	}

	public List<String> inputCheak(UserInfoBean userInfoBean) {

		List<String> errorList = new ArrayList<>();
		String userName = userInfoBean.getUserName();
		String loginId = userInfoBean.getLoginId();
		String password = userInfoBean.getPassWord();

		//nullチェック、8~15文字以内チェック、半角英数字チェックを実施。
		if(userName != null && userName.length() >= 5  && userName.length() <= 15 && Pattern.matches("^[0-9a-zA-Z]+$",userName)) {

		}else {
			errorList.add(Messages.registUserNameError);
		}

		if(loginId != null && loginId.length() >= 8  && loginId.length() <= 15 && Pattern.matches("^[0-9a-zA-Z]+$",loginId)) {

		}else {
			errorList.add(Messages.registUserIdError);
		}

		if(password != null && password.length() >= 8 && password.length() <= 15 && Pattern.matches("^[0-9a-zA-Z]+$",password)) {

		}else {
			errorList.add(Messages.registUserPassEror);
		}

		return errorList;
	}

	//API用Json返却結果用の枠組み生成
	@Data
	@AllArgsConstructor
	public class RegisterResult {
		public boolean result;
		public String loginId;
		public String userName;
		public String password;
		public String message;
	}

	public boolean result;
	public String loginId;
	public String userName;
	public String password;
	public String message;

	public RegisterResult accountRegist(UserInfoBean userInfoBean) {

		//入力の正規表現チェックを実施
		if(!userInfoBean.getLoginId().matches("^[a-z\\d]{8,15}$")
		  || !userInfoBean.getPassWord().matches("^(?=.*?[a-z])(?=.*?\\d)[a-z\\d]{8,15}$")
		  || !userInfoBean.getUserName().matches("^[a-z\\d]{5,15}$")) {
			result = false;
			message = "入力の形式が正しくありません";
			return new RegisterResult(result,loginId,userName,password,message);
		}

		//LoginIdの重複チェックを実施
		Optional<UserInfoBean> distinctCheak = userRepository
				.findAll()
				.parallelStream()
				.filter(s -> s.getLoginId().equals(userInfoBean.getLoginId()))
				.findFirst();

		//LoginIDが既存のユーザと重複していないかチェック、重複なしの場合DBにインサート
		//errorが存在する場合はfalseを返し、Modelにエラーリストを格納
		if(distinctCheak.isPresent()) {
			message = Messages.registUserDistincterror;
			return new RegisterResult(result,loginId,userName,password,message);
		}

		userRepository.saveAndFlush(userInfoBean);

		//登録成功時の返却結果生成
		result = true;
		userName = userInfoBean.getUserName();
		loginId = userInfoBean.getLoginId();
		password = userInfoBean.getPassWord();
		message = "登録に成功しました。";

		return new RegisterResult(result,loginId,userName,password,message);
	}

}
