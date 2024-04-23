package kr.co.basic.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.basic.bean.CodeDetail;
import kr.co.basic.bean.PageBean;
import kr.co.basic.bean.ProjectInfo;
import kr.co.basic.bean.UserInfo;
import kr.co.basic.bean.UserProjectInfo;
import kr.co.basic.dao.UserInfoDao;
import kr.co.basic.mapper.UserInfoMapper;

@Service
@PropertySource("/WEB-INF/properties/page.properties")
public class UserInfoService {

	@Autowired
	private UserInfoDao userInfoDao;

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Value("${page.listcnt}")
	private int page_listcnt;

	@Value("${page.paginationcnt}")
	private int page_paginationcnt;

	// 사원 정보 출력 (모든 조건이 null일 시 전체 회원 조회)
	public ArrayList<UserInfo> userAllSearch(UserInfo userInfo, int page, int listSize) {
		// 사원 정보 (보여질 리스트의 수 만큼)
		int start = (page - 1) * listSize;
		RowBounds rowBounds = new RowBounds(start, listSize);

		String userNmWithWildcard = "%" + userInfo.getUserNm().trim() + "%";
		userInfo.setUserNm(userNmWithWildcard);
		userInfo.setUserStateCd("2");
		ArrayList<UserInfo> allUserInfoList = userInfoDao.userAllSearch(userInfo, rowBounds);
		// allUserInfoList.removeIf(user -> user.getUserNm().equals("관리자"));

		return allUserInfoList;
	}

	// 페이징 처리
	public PageBean getSearchCnt(UserInfo userInfo, int currentPage, int listSize) {
		int content_cnt = userInfoMapper.getSearchCnt(userInfo);

		PageBean pageBean = new PageBean(content_cnt, currentPage, listSize, page_paginationcnt);
		return pageBean;
	}

	// 한 유저에 대한 정보
	public UserInfo getUserInfo(String userSeq) {
		return userInfoDao.getUserInfo(userSeq);
	}

	// 유저 삭제
	public void deleteUserPro(List<String> userSeq) {
		for (int i = 0; i < userSeq.size(); i++) {
			userInfoMapper.deleteUserPro(userSeq.get(i));
		}
	}

	// ============================================================UserDetail====================================================================================
	// 유저 한명의 프로젝트
	public List<UserProjectInfo> getUserProjectInfo(String userSeq) {
		return userInfoDao.getUserProjectInfo(userSeq);
	}

	// 역할
	public List<CodeDetail> getRoleNm() {
		return userInfoDao.getRoleNm();
	}

	@Transactional
	// 유저 프로젝트 업데이트 (투입일, 철수일, 역할)
	public boolean userPrjUpdatePro(List<UserProjectInfo> userProjectInfo) {
		try {
			for (UserProjectInfo userUpdate : userProjectInfo) {
				userInfoMapper.userPrjUpdatePro(userUpdate);
			}
			return true;
		} catch (DataAccessException e) {
			System.err.println("데이터 업데이트 중 예외 발생: " + e);
			return false;
		} catch (Exception e) {
			System.err.println("알 수 없는 예외 발생: " + e);
			return false;
		}
	}
	
	@Transactional
	// 프로젝트 삭제
	public boolean prjDeletePro(List<UserProjectInfo> userProjectInfos) {
		try {
			for(UserProjectInfo prjDelete : userProjectInfos) {
				userInfoMapper.prjDeletePro(prjDelete);
			}
			return true;
		} catch (Exception e) {
			System.err.println("알 수 없는 예외 발생: " + e);
			return false;
		}
	}
	
	// ============================================================Modal================================================================================
	// 해당 유저가 참여하지 않으며 검색 조건의 의한 프로젝트 조회
	public List<ProjectInfo> getConPrjList(UserProjectInfo userProjectInfo){
		userProjectInfo.setPrjNm("%" + userProjectInfo.getPrjNm() + "%");
		userProjectInfo.setCustomerNm("%" + userProjectInfo.getCustomerNm() + "%");
		
		List<ProjectInfo> tempPrjList = userInfoDao.getConPrjList(userProjectInfo);
		List<ProjectInfo> prjList = new ArrayList<ProjectInfo>();
		
		for(ProjectInfo prjInfo : tempPrjList) {
			List<String> prjSkills = userInfoMapper.getPrjSkill(prjInfo);
			String prjSkill = "";
			for(int i = 0; i < prjSkills.size(); i++) {
				if(i == prjSkills.size() - 1) {
					prjSkill += prjSkills.get(i);
				} else {
					prjSkill += prjSkills.get(i) + ", ";
				}
			}
			prjInfo.setSkill(prjSkill);
			prjList.add(prjInfo);
		}
		return prjList;
	}
	
	// 프로젝트에 인원 추가
	public boolean prjAddPro(List<UserProjectInfo> userProjectsInfo){
		boolean checked = checkList(userProjectsInfo);
		if(checked) {
			try {
				for(UserProjectInfo userProjectInfo : userProjectsInfo) {
					userInfoMapper.prjAddPro(userProjectInfo);
				}
				return true;
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}
	
	private boolean checkList(List<UserProjectInfo> userProjectsInfo) {
		for(int i = 0; i < userProjectsInfo.size(); i++) {
			if(userProjectsInfo.get(i).getUpStartDate() == null || userProjectsInfo.get(i).getUpStartDate().equals("")) {
				return false;
			} else if(userProjectsInfo.get(i).getRoleCd() == null || userProjectsInfo.get(i).getRoleCd() == "") {
				return false;
			}
		}
		return true;
	}
}
