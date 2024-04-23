package kr.co.basic.config;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.co.basic.bean.UserInfo;
import kr.co.basic.dao.UserDao;
import kr.co.basic.interceptor.CheckLoginInterceptor;
import kr.co.basic.interceptor.InterceptorAuthority;
import kr.co.basic.interceptor.InterceptorSidebar;
import kr.co.basic.mapper.MainPageMapper;
import kr.co.basic.mapper.ProjectInfoMapper;
import kr.co.basic.mapper.UserInfoMapper;
import kr.co.basic.mapper.UserMapper;

@Configuration
@EnableWebMvc
@ComponentScan("kr.co.basic.controller")
@ComponentScan("kr.co.basic.service")
@ComponentScan("kr.co.basic.dao")
@PropertySource("/WEB-INF/properties/db.properties")
@PropertySource("/WEB-INF/properties/application.properties")
public class ServletAppContext implements WebMvcConfigurer {

	@Value("${db.classname}")
	private String db_classname;

	@Value("${db.url}")
	private String db_url;

	@Value("${db.username}")
	private String db_username;

	@Value("${db.password}")
	private String db_password;
	
	@Resource(name = "loginUserBean")
	private UserInfo loginUserBean;
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		/*
		// view로 보내질 최종 요청응답에 관한 환경설정
		WebMvcConfigurer.super.configureViewResolvers(registry);
		registry.jsp("/WEB-INF/views/", ".jsp");
		*/
	}
	
	@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 모든 경로에 대해
            .allowedOrigins("http://localhost:8081")  // Vue.js 서버 주소
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // 허용할 HTTP 메소드
            .allowedHeaders("*")  // 모든 헤더 허용
            .allowCredentials(true);  // 쿠키를 포함한 요청 허용
    }

	// 정적 파일의 경로를 매핑
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.addResourceHandlers(registry);
		registry.addResourceHandler("/**").addResourceLocations("/resources/");
	}
	
	@Bean
	   public static PropertySourcesPlaceholderConfigurer PropertySourcesPlaceholderConfigurer() {
	      return new PropertySourcesPlaceholderConfigurer();
	   }
	
	//인터셉터 (접근 제한 및 로그인 객체 주입)
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		WebMvcConfigurer.super.addInterceptors(registry);
		
		InterceptorSidebar interceptorSidebar = new InterceptorSidebar(loginUserBean, userDao); // 사이드 바
		
		InterceptorRegistration reg1 = registry.addInterceptor(interceptorSidebar);
		reg1.addPathPatterns("/**");
		
		CheckLoginInterceptor checkLoginInterceptor = new CheckLoginInterceptor(loginUserBean); // 로그인 여부
		
		InterceptorRegistration reg2 = registry.addInterceptor(checkLoginInterceptor);
		reg2.addPathPatterns("/main_page/*", "/user_info/*", "/project_info/*");
		
		InterceptorAuthority interceptorAuthority = new InterceptorAuthority(loginUserBean, userDao); // 권한에 맞는 url 접근
		
		InterceptorRegistration reg3 = registry.addInterceptor(interceptorAuthority);
		reg3.addPathPatterns("/project_info/project_search", "/project_info/project_register",
							 "/user_info/user_search", "/user_info/user_regi_admin",
							 "/user_info/user_regi_list", "/project_info/project_edit",
							 "/user_info/user_edit");
	}

	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource res = new ReloadableResourceBundleMessageSource();
		res.setBasenames("/WEB-INF/properties/error_message");
		res.setDefaultEncoding("UTF-8");
		return res;
	}

	@Bean
	public BasicDataSource dataSource() {
		BasicDataSource source = new BasicDataSource();
		source.setDriverClassName(db_classname);
		source.setUrl(db_url);
		source.setUsername(db_username);
		source.setPassword(db_password);

		return source;
	}

	@Bean
	public StandardServletMultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver(); //객체 생성하여 반환
	}

	// 쿼리문과 접속 정보를 관리하는 객체
	@Bean
	public SqlSessionFactory factory(BasicDataSource source) throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(source);
		SqlSessionFactory factory = factoryBean.getObject();
		return factory;
	}
	
	//mapper 관리
	@Bean
	public MapperFactoryBean<UserInfoMapper> getUserInfoMapper(SqlSessionFactory factory) throws Exception {
		MapperFactoryBean<UserInfoMapper> factoryBean = new MapperFactoryBean<UserInfoMapper>(UserInfoMapper.class);
		factoryBean.setSqlSessionFactory(factory);
		return factoryBean;
	}
	
	@Bean
	public MapperFactoryBean<MainPageMapper> getMainPageMapper(SqlSessionFactory factory) throws Exception {
		MapperFactoryBean<MainPageMapper> factoryBean = new MapperFactoryBean<MainPageMapper>(MainPageMapper.class);
		factoryBean.setSqlSessionFactory(factory);
		return factoryBean;
	}
	
	@Bean
	public MapperFactoryBean<UserMapper> getUserMapper(SqlSessionFactory factory) throws Exception {
		MapperFactoryBean<UserMapper> factoryBean = new MapperFactoryBean<UserMapper>(UserMapper.class);
		factoryBean.setSqlSessionFactory(factory);
		return factoryBean;
	}
	
	@Bean
	public MapperFactoryBean<ProjectInfoMapper> getProjectInfoMapper(SqlSessionFactory factory) throws Exception {
		MapperFactoryBean<ProjectInfoMapper> factoryBean = new MapperFactoryBean<ProjectInfoMapper>(ProjectInfoMapper.class);
		factoryBean.setSqlSessionFactory(factory);
		return factoryBean;
	}

}
