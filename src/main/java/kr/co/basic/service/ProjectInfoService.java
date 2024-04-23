package kr.co.basic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.basic.bean.ProjectInfo;
import kr.co.basic.dao.ProjectInfoDao;
import kr.co.basic.mapper.ProjectInfoMapper;

@Service
public class ProjectInfoService {
	
	@Autowired
	private ProjectInfoDao projectInfoDao;
	
	@Autowired
	private ProjectInfoMapper projectInfoMapper;
	
	// 모든 프로젝트 조회
	public List<ProjectInfo> getAllPrj(){
		List<ProjectInfo> prjList = projectInfoDao.getAllPrj();
		
		for(ProjectInfo prjInfo : prjList) {
			String skill = "";
			List<String> skills = projectInfoDao.getPrjSkill(prjInfo);
			
			if(skills.size() != 0) {
				for(int i = 0; i < skills.size(); i++) {
					if(i == skills.size() - 1) {
						skill += skills.get(i);
					}else {
						skill += skills.get(i) + ", ";
					}
				}
			} else {
				skill = null;
			}
			prjInfo.setSkill(skill);
		}
		return prjList;
	}
	
	// 조건에 맞는 프로젝트 조회(모든 값 null = 모든 프로젝트 조회)
	public List<ProjectInfo> getPrjList(ProjectInfo projectInfo){
		List<ProjectInfo> prjList = projectInfoDao.getPrjList(projectInfo);
		
		for(ProjectInfo prjInfo : prjList) {
			String skill = "";
			List<String> skills = projectInfoDao.getPrjSkill(prjInfo);
			
			if(skills.size() != 0) {
				for(int i = 0; i < skills.size(); i++) {
					if(i == skills.size() - 1) {
						skill += skills.get(i);
					}else {
						skill += skills.get(i) + ", ";
					}
				}
			} else {
				skill = null;
			}
			prjInfo.setSkill(skill);
		}
		return prjList;
	}
	
	// 해당하는 프로젝트 정보 조회
	public ProjectInfo getPrjInfo(String prjSeq) {
		String skill = "";
		ProjectInfo prjInfo = projectInfoDao.getPrjInfo(prjSeq);
		List<String> skills = projectInfoDao.getPrjSkill(prjInfo);
		
		if(skills.size() != 0) {
			for(int i = 0; i < skills.size(); i++) {
				if(i == skills.size() - 1) {
					skill += skills.get(i);
				}else {
					skill += skills.get(i) + ", ";
				}
			}
		} else {
			skill = null;
		}
		prjInfo.setSkill(skill);
		return prjInfo;
	}
	
	// 프로젝트 등록
	@Transactional
	public boolean addProject(ProjectInfo projectInfo) {
		try {
			projectInfoMapper.addProject(projectInfo);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	// 프로젝트 요규 스킬 등록
	public boolean addPrjSkill(ProjectInfo projectInfo) {
		try {
			if(projectInfo.getSkills() != null) {
				List<String> dtlCodes = projectInfo.getSkills();
				for(String dtlCode : dtlCodes) {
					projectInfoMapper.addPrjSkill(projectInfo.getPrjSeq(), dtlCode);
				}
				return true;
			} else {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	// 선택 프로젝트 삭제
	@Transactional
	public boolean deleteProject(List<String> prjSeqList){
		try {
			for(String prjSeq : prjSeqList) {
				projectInfoMapper.deleteProject(prjSeq);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	// 해당 프로젝트 정보 업데이트
	@Transactional
	public boolean modifyPrjPro(ProjectInfo projectInfo) {
		try {
			projectInfoMapper.modifyPrjPro(projectInfo);
			projectInfoMapper.deletePrjSkill(projectInfo.getPrjSeq());
			List<String> skills = projectInfo.getSkills();
			for(String skill : skills) {
				projectInfoMapper.addPrjSkill(projectInfo.getPrjSeq(), skill);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
