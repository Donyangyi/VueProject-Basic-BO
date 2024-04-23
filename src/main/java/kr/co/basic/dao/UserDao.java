package kr.co.basic.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.basic.bean.MenuBean;
import kr.co.basic.bean.UserInfo;
import kr.co.basic.bean.UserSkill;
import kr.co.basic.mapper.UserMapper;

@Repository
public class UserDao {
	@Autowired
	private UserMapper userMapper;
	
	// 모든 스킬 정보 조회
	public List<UserSkill> searchAllSkill() {
		return userMapper.searchAllSkill();
	}
	
	// 모든 성별 정보 조회
	public List<UserSkill> searchAllGender() {
		return userMapper.searchAllGender();
	}
	
	// 모든 직급 정보 조회
	public List<UserSkill> searchAllPosition() {
		return userMapper.searchAllPosition();
	}
	
	// 모든 기술등급 정보 조회
	public List<UserSkill> searchAllSkillLevel() {
		return userMapper.searchAllSkillLevel();
	}
	
	// 모든 전화번호 정보 조회
	public List<UserSkill> searchAllPhoneNumber() {
		return userMapper.searchAllPhoneNumber();
	}
	
	// 모든 도메인 정보 조회
	public List<UserSkill> searchAllEmail() {
		return userMapper.searchAllEmail();
	}
	
	// 모든 재직상태 조회
	public List<UserSkill> searchAllWorkState() {
		return userMapper.searchAllWorkState();
	}
	
	// 리스트 수 조회
	public List<UserSkill> searchAllListCount() {
		return userMapper.searchAllListCount();
	}
	
	// 모든 이메일 정보 조회
	public void regiUserPro(UserInfo userInfo) {
		userMapper.regiUserPro(userInfo);
	}
	
	// 모든 이메일 정보 조회
	public void regiUserAdminPro(UserInfo userInfo) {
		userMapper.regiUserAdminPro(userInfo);
	}
	
	// 모든 고객사 정보 조회
	public List<UserSkill> searchAllCustomer() {
		return userMapper.searchAllCustomer();
	}
	
	// 해당 회원의 보유 스킬
	public List<UserSkill> getUserSkills(String userSeq){
		return userMapper.getUserSkills(userSeq);
	}
	
	// 모든 등록상태 조회
	public List<UserSkill> searchAllRegiState() {
		return userMapper.searchAllRegiState();
	}
	
	// 한 유저의 권한에 따른 메뉴 보이기
	public List<MenuBean> getMenuList(String userSeq) {
		return userMapper.getMenuList(userSeq);
	}
	
	// 한 유저의 권한에 따른 메뉴 보이기
	public List<String> getUrlList(String userSeq) {
		return userMapper.getUrlList(userSeq);
	}
}
