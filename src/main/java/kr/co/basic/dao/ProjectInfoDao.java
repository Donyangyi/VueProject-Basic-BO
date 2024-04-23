package kr.co.basic.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.basic.bean.ProjectInfo;
import kr.co.basic.bean.ProjectSkill;
import kr.co.basic.bean.UserInfo;
import kr.co.basic.bean.UserProjectInfo;
import kr.co.basic.mapper.ProjectInfoMapper;

@Repository
public class ProjectInfoDao {
	
	@Autowired
	private ProjectInfoMapper projectInfoMapper;
	
	// 모든 프로젝트 조회
	public List<ProjectInfo> getAllPrj(){
		return projectInfoMapper.getAllPrj();
	}
	
	// 해당하는 프로젝트 스킬
	public List<String> getPrjSkill(ProjectInfo projectInfo) {
		return projectInfoMapper.getPrjSkill(projectInfo);
	}
	
	// 조건에 맞는 프로젝트 조회(모든 값 null = 모든 프로젝트 조회)
	public List<ProjectInfo> getPrjList(ProjectInfo projectInfo){
		return projectInfoMapper.getPrjList(projectInfo);
	}
	
	// 해당하는 프로젝트 정보 조회
	public ProjectInfo getPrjInfo(String prjSeq) {
		return projectInfoMapper.getPrjInfo(prjSeq);
	}
	
	// 프로젝트에 참여하고 있는 인원 조회
	public List<UserProjectInfo> getUserList(String prjSeq){
		return projectInfoMapper.getUserList(prjSeq);
	}
	
	// 해당 프로젝트에 참여하고 있지 않은 인원 조회
	public List<UserInfo> getConUserList(UserProjectInfo userProjectInfo){
		return projectInfoMapper.getConUserList(userProjectInfo);
	}
	
	// 해당 프로젝트의 요구 스킬
	public List<ProjectSkill> getSelectedPrjSkill(String prjSeq){
		return projectInfoMapper.getSelectedPrjSkill(prjSeq);
	}
	
}
