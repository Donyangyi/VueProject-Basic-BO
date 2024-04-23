package kr.co.basic.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import kr.co.basic.bean.MenuBean;
import kr.co.basic.bean.UserInfo;
import kr.co.basic.dao.UserDao;

public class InterceptorSidebar implements HandlerInterceptor {
	
	private UserInfo loginUserBean;
	private UserDao userDao;
	
	public InterceptorSidebar(UserInfo loginUserBean, UserDao userDao) {
		this.loginUserBean = loginUserBean;
		this.userDao = userDao;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		request.setAttribute("loginUserBean", loginUserBean);
		
		if(loginUserBean.getUserSeq() == null) {
			return true;
		} else {
			List<MenuBean> menuBean = userDao.getMenuList(loginUserBean.getUserSeq());
			request.setAttribute("menuBean", menuBean);
		}
		return true;
	}

}
