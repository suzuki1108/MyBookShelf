package com.exsample.bookmark.model;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LoginUserSession implements Serializable {

    private UserInfoBean userInfoBean;

    public UserInfoBean getUserInfoBean() {
        if (userInfoBean == null) {
        	userInfoBean = new UserInfoBean();
        }
        return userInfoBean;
    }

    public void setUserInfoBean(UserInfoBean userInfoBean) {
        this.userInfoBean = userInfoBean;
    }

    public String getUserName() {
		return userInfoBean.getUserName();
    }
}
