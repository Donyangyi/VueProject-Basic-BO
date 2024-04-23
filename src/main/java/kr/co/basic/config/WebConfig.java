/*
 * package kr.co.basic.config;
 * 
 * import org.springframework.web.servlet.config.annotation.CorsRegistry; import
 * org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
 * 
 * public class WebConfig implements WebMvcConfigurer{
 * 
 * @Override public void addCorsMappings(CorsRegistry registry) {
 * System.out.println("호출됨!"); registry.addMapping("/**") // 모든 경로에 대해
 * .allowedOrigins("http://localhost:8080") // Vue.js 서버 주소
 * .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP 메소드
 * .allowedHeaders("*") // 모든 헤더 허용 .allowCredentials(true); // 쿠키를 포함한 요청 허용 }
 * }
 */