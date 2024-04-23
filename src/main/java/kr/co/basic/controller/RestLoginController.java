package kr.co.basic.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.basic.bean.UserInfo;
import kr.co.basic.mapper.UserMapper;
import kr.co.basic.service.UserService;

@RestController
public class RestLoginController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserMapper userMapper;
	
	// 로그인
	@PostMapping("/check_login_pro")
	public ResponseEntity<?> login(@RequestBody UserInfo userInfo){
		boolean isLogin = userService.login(userInfo);
		UserInfo user = userMapper.login(userInfo);
		if(isLogin) {
			return ResponseEntity.ok(Map.of("success", true, "message", user.getUserNm() + "님 환영합니다."));
		} else {
			return ResponseEntity.ok(Map.of("success", false, "message", "잘못된 아이디 패스워드 입니다."));
		}
	}
	
	// 유저 등록 상태 업데이트
	@PutMapping("/user_info/update_userState")
	public ResponseEntity<?> updateUserState(@RequestBody List<String> userSeqList){
		try {
			for(String userSeq : userSeqList) {
				userMapper.updateUserState(userSeq);
			}
			return ResponseEntity.ok(Map.of("success", true, "message", "승인 처리 하였습니다."));
		} catch (NullPointerException e) {
			return ResponseEntity.ok(Map.of("success", false, "message", "삭제할 사원을 선택해 주세요"));
		}
	}
	
	// 유저 등록 거절
	@DeleteMapping("/user_info/reject_user")
	public ResponseEntity<?> rejectUser(@RequestBody List<String> userSeqList){
		try {
			for(String userSeq : userSeqList) {
				userMapper.rejectUser(userSeq);
			}
			return ResponseEntity.ok(Map.of("success", true, "message", "거절 처리 하였습니다."));
		} catch (NullPointerException e) {
			return ResponseEntity.ok(Map.of("success", false, "message", "삭제할 사원을 선택해 주세요"));
		}
	}
	
}
