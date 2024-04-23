package kr.co.basic.bean;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserInfo {
	private String userSeq;
	@Pattern(regexp = "^[가-힣a-zA-Z]{2,15}$")
	private String userNm;
	@Pattern(regexp = "^[a-zA-Z0-9]{7,20}$")
	private String userId;
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$])[A-Za-z\\d!@#$]{7,20}$")
	private String userPw;
	private String genderCd;
	@Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$")
	private String phoneNumber;
	@Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
	private String regiDate;
	private String posCd;
	private String skillRankCd;
	@Size(max = 100)
	@Pattern(regexp = "^[\\w-.]+@[\\w-]+(\\.[\\w-]+)+$")
	private String email;
	@Pattern(regexp = "^[^!@#$%^&*_+=\\[\\]{};'\"\\|,.<>?`~]*$")
	@NotNull
	private String address;
	@Pattern(regexp = "^[^!@#$%^&*_+=\\[\\]{};'\"\\|,.<>?`~]*$")
	@NotNull
	private String addressDetail;
	private String workStateCd;
	private String userStateCd;
	private Date userRegiDate;
	private String userImage;
	private MultipartFile uploadUserImage;
	
	private String gender;
	private String position;
	private String skillRank;
	private String workState;
	private String userState;
	private String startDate;
	private String endDate;
	private String dtlCode;
	private List<String> skillList;
	private boolean admin;
	private boolean login;
	private List<String> authorityCd;
	
	public UserInfo() {
		this.login = false;
	}
}
