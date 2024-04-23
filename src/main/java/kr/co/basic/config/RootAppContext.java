package kr.co.basic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.context.annotation.SessionScope;

import kr.co.basic.bean.UserInfo;

//프로젝트 작업시 사용할 bean을 정의하는 클래스
@EnableScheduling // 스케줄링 활성화
@Configuration
public class RootAppContext {
	
	@Bean("loginUserBean")
	@SessionScope 
	public UserInfo loginUserBean() {
		return new UserInfo();
	}
	
}
