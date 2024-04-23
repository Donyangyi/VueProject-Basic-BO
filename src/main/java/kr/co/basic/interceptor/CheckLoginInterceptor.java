package kr.co.basic.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import kr.co.basic.bean.UserInfo;

public class CheckLoginInterceptor implements HandlerInterceptor{

	private UserInfo loginUserBean;
	
	public CheckLoginInterceptor(UserInfo loginUserBean) {
		this.loginUserBean = loginUserBean;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// 로그인을 하지 않았다면
		if (loginUserBean.isLogin() == false) {
			// 로그인 하지 않은 경로를 호출하고
			String contextPath = request.getContextPath();
			// 로그인이 되지 않았으므로 웹브라우져에게 not_login을 요청하라고 지시함
			response.sendRedirect(contextPath + "/user/not_login");
			// 다음 단계로 이동하지 않음
			return false;
		}
		// 로그인 되어 있는 상태
		return true;
	}	
}
