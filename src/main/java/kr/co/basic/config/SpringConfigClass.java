package kr.co.basic.config;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpringConfigClass extends AbstractAnnotationConfigDispatcherServletInitializer {

	// DispatcherServlet에 매핑할 요청 주소
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}


	 // Spring MVC 프로젝트 설정을 위한 클래스를 지정
	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return new Class[] { ServletAppContext.class };
	}


	 // 프로젝트에서 사용할 Bean들을 정의기 위한 클래스를 지정
	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return new Class[] { RootAppContext.class };
	}


	 // 파라미터 인코딩 필터 설정
	@Override
	protected Filter[] getServletFilters() {
		// TODO Auto-generated method stub
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");
		return new Filter[] { encodingFilter };
	}
	
	/*
		null : 사용자가 입력한 내용을 임시기억할 아파치톰켓에서 제공하는 서버의 임시기억장소
		52428800 : 업로드 데이터의 용량(1024*50) 50M
		524288000 : 파일 데이터를 포함한 전체용량 500M
		0 : 파일의 임계값(데이터를 받아서 자동으로 저장) (제한을 두지 않고 0으로 사용)
	*/	
	@Override
	protected void customizeRegistration(Dynamic registration) {
		// TODO Auto-generated method stub
		super.customizeRegistration(registration);
		
		MultipartConfigElement config1 = new MultipartConfigElement(null, 52428800, 524288000, 0);
		registration.setMultipartConfig(config1);
	}
}



