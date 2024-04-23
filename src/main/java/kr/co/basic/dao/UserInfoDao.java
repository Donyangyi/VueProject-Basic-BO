package kr.co.basic.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.basic.bean.CodeDetail;
import kr.co.basic.bean.ProjectInfo;
import kr.co.basic.bean.UserInfo;
import kr.co.basic.bean.UserProjectInfo;
import kr.co.basic.mapper.UserInfoMapper;

@Repository
public class UserInfoDao {

	@Autowired
	private UserInfoMapper userInfoMapper;

	// 사원 정보 출력 (모든 조건이 null일 시 전체 회원 조회)
	public ArrayList<UserInfo> userAllSearch(UserInfo userInfo, RowBounds rowBounds) {
		return userInfoMapper.userAllSearch(userInfo, rowBounds);
	}
	
	// 사원 정보 출력 (모든 조건이 null일 시 전체 회원 조회)
	public ArrayList<UserInfo> userAllSearchNoPage(UserInfo userInfo) {
		return userInfoMapper.userAllSearchNoPage(userInfo);
	}

	// ============================================================UserSearch====================================================================================
	// 한 유저에 대한 정보
	public UserInfo getUserInfo(String userSeq) {
		return userInfoMapper.getUserInfo(userSeq);
	}

	// 한 유저에 대한 스킬 정보
	public String getUserSkills(String userSeq) {
		String userSkillsPro = "";
		ArrayList<String> userSkills = userInfoMapper.getUserSkills(userSeq);
		if (userSkills.isEmpty()) {
			userSkillsPro += "보유 스킬이 없습니다.";
		} else {
			for (int i = 0; i < userSkills.size(); i++) {
				if (i == userSkills.size() - 1) {
					userSkillsPro += userSkills.get(i);
				} else {
					userSkillsPro += userSkills.get(i) + ", ";
				}
			}
		}
		return userSkillsPro;
	}

	// ============================================================UserDetail====================================================================================
	// 유저 한명의 프로젝트
	public List<UserProjectInfo> getUserProjectInfo(String userSeq) {
		return userInfoMapper.getUserProjectInfo(userSeq);
	}

	// 역할
	public List<CodeDetail> getRoleNm() {
		return userInfoMapper.getRoleNm();
	}

	// ========================================================Modal==================================================================================
	// 해당 유저가 참여하지 않으며 검색 조건의 의한 프로젝트 조회
	public List<ProjectInfo> getConPrjList(UserProjectInfo userProjectInfo){
		return userInfoMapper.getConPrjList(userProjectInfo);
	}
}
