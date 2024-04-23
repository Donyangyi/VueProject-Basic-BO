package kr.co.basic.bean;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProjectInfo {
	private String prjSeq;
	private String prjNm;
	private String customerCd;
	private String prjStartDate;
	private String prjEndDate;
	private String prjDetail;
	
	private String customerNm;
	private String skill;
	private List<String> skills;
	
	//검색 조건용
	private String startFromDate;
	private String startToDate;
	private String endFromDate;
	private String endToDate;
	
}
