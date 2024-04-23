package kr.co.basic.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import kr.co.basic.bean.UserInfo;
import kr.co.basic.dao.UserDao;

public class InterceptorAuthority implements HandlerInterceptor{
	
	private UserInfo loginUserBean;
	private UserDao userDao;
	
	public InterceptorAuthority(UserInfo loginUserBean, UserDao userDao) {
		this.loginUserBean = loginUserBean;
		this.userDao = userDao;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// 요청 URL 확인
	    String requestUrl = request.getRequestURI();
	    
	    // 요청 URL이 'pro'로 끝나는 경우 인터셉터 로직을 건너뛰고 바로 통과
        if (requestUrl.endsWith("pro")) {
            return true; // 인터셉터 처리를 건너뛰고 요청을 그대로 진행
        }
		
		// 현재 로그인한 사용자의 userSeq 가져오기
	    String userSeq = loginUserBean.getUserSeq();

	    // 사용자의 권한에 따라 접근 가능한 menuUrl 리스트 조회
	    List<String> originalUrls = userDao.getUrlList(userSeq);

	    // 새로운 URL 리스트 생성
	    List<String> modifiedUrls = new ArrayList<>();
	    String contextPath = request.getContextPath();

	    for (String url : originalUrls) {
	        // ".." 제거 및 contextPath 추가
	        String modifiedUrl = url.replace("..", "");
	        modifiedUrl = contextPath + modifiedUrl;
	        modifiedUrls.add(modifiedUrl);
	    }

	    if (!modifiedUrls.contains(requestUrl)) {
	        response.sendRedirect(contextPath + "/user/not_permission");
	        return false;
	    }

	    return true;
	}
}
