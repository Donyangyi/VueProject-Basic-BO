package kr.co.basic.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserProjectInfo {
	private String userSeq;
	private String prjSeq;
	private String upStartDate;
	private String upEndDate;
	private String roleCd;
	
	private String roleNm;
	private String customerNm;
	private String prjNm;
	
	private String prjStartDate;
	private String prjEndDate;
	
	private String userNm;
	private String posCd;
	private String skillRankCd;
}
