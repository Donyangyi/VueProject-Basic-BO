package kr.co.basic.service;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.io.FilenameUtils;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.co.basic.bean.UserInfo;
import kr.co.basic.bean.UserSkill;
import kr.co.basic.dao.UserDao;
import kr.co.basic.dao.UserInfoDao;
import kr.co.basic.mapper.UserMapper;

@Service
@PropertySource("/WEB-INF/properties/option.properties")
public class UserService {
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserInfoDao userInfoDao;
	
	@Resource(name = "loginUserBean")
	private UserInfo loginUserBean;

	@Value("${path.upload}")
	private String path_upload;

	//파일 세이브
	private String saveUploadFile(MultipartFile upload_file) {
		String file_name = System.currentTimeMillis() + "_" + // 아래 코드가 같은 파일명을 업로드시에 오류가 발생해서 시간을 붙여서 겹치지 않도록 함.
				FilenameUtils.getBaseName(upload_file.getOriginalFilename()) + "."
				+ FilenameUtils.getExtension(upload_file.getOriginalFilename());
		try {
			upload_file.transferTo(new File(path_upload + "/" + file_name));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file_name;
	}
	
	// 유저 추가
	public boolean regiUserPro(String name, String userId, String userPw, String gender, String phone,
							   String regiDate, String posCd, String skillRankCd, String email,
							   String address, String addressDetail, MultipartFile userImage, List<String> skills,
							   Boolean isAdmin, String workStateCd, String userStateCd) {
		UserInfo userInfo = new UserInfo();
		userInfo.setUserNm(name);
		userInfo.setUserId(userId);
		userInfo.setUserPw(encryptPassword(userPw));
		userInfo.setGenderCd(gender);
		userInfo.setPhoneNumber(phone);
		userInfo.setRegiDate(regiDate);
		userInfo.setPosCd(posCd);
		userInfo.setSkillRankCd(skillRankCd);
		userInfo.setEmail(email);
		userInfo.setAddress(address + " " + addressDetail);
		userInfo.setUploadUserImage(userImage);
		if(!isAdmin) {
			userInfo.setWorkStateCd("1");
			userInfo.setUserStateCd("1");
		} else {
			userInfo.setWorkStateCd(workStateCd);
			userInfo.setUserStateCd(userStateCd);
		}
		
		
		MultipartFile upload_file = userInfo.getUploadUserImage();
		try {
			if (upload_file.getSize() > 0) { // upload_file이 있다면
				String file_name = saveUploadFile(upload_file);
				userInfo.setUserImage(file_name);
			}
			
			if(!isAdmin) {
				userDao.regiUserPro(userInfo);
			} else {
				userDao.regiUserAdminPro(userInfo);
			}
			
			String userSeq = userMapper.getUserSeq(userInfo);
			
			for(int i = 0; i < skills.size(); i++) {
				UserSkill userSkill = new UserSkill();
				userSkill.setUserSeq(userSeq);
				userSkill.setDtlCode(skills.get(i));
				userMapper.regiSkill(userSkill);
			}
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	// 패스워드 암호화
	public static String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            return bytesToHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 알고리즘이 없습니다.", e);
        }
    }
	
	// 16진수로 바이트 포멧팅
	private static String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
	
	// 아이디 중복 검사
	public boolean duplicateCheck(String userId) {
		String checkUserId = userMapper.duplicateCheck(userId);
		if(checkUserId != null) {
			return false;
		} else {
			return true;
		}
	}
	
	// 유저 업데이트
	public boolean updateUserPro(UserInfo userInfo) {
	    try {
	        MultipartFile upload_file = userInfo.getUploadUserImage();
	        if (upload_file != null && upload_file.getSize() > 0) {
	            String file_name = saveUploadFile(upload_file);
	            userInfo.setUserImage(file_name);
	        } else {
	            UserInfo user = userInfoDao.getUserInfo(userInfo.getUserSeq());
	            // 기존에 등록된 이미지가 있을 경우에만 기존 이미지로 설정
	            if (user != null && user.getUserImage() != null) {
	                userInfo.setUserImage(user.getUserImage());
	            }
	        }

	        // 사용자 정보 업데이트 실행
	        userMapper.updateUserInfo(userInfo);

	        // 변경된 스킬 목록
	        List<String> skills = userInfo.getSkillList(); // 변경된 스킬
	        // 현재 보유 스킬 조회
	        List<UserSkill> haveSkills = userMapper.getUserSkills(userInfo.getUserSeq());
	        List<String> haveSkillsCodes = haveSkills.stream().map(UserSkill::getDtlCode).collect(Collectors.toList());

	        // 기존 스킬 중 삭제되어야 할 스킬 확인 및 삭제
	        for (UserSkill haveSkill : haveSkills) {
	            if (!skills.contains(haveSkill.getDtlCode())) {
	            	haveSkill.setUserSeq(userInfo.getUserSeq());
	                userMapper.deleteSkill(haveSkill);
	            }
	        }

	        // 새롭게 추가되어야 할 스킬 확인 및 추가
	        for (String skill : skills) {
	            if (!haveSkillsCodes.contains(skill)) {
	                UserSkill newSkill = new UserSkill();
	                newSkill.setUserSeq(userInfo.getUserSeq());
	                newSkill.setDtlCode(skill);
	                userMapper.insertSkill(newSkill);
	            }
	        }

	        return true;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	//로그인
	public boolean login(UserInfo userInfo) {
	    userInfo.setUserPw(encryptPassword(userInfo.getUserPw()));
	    UserInfo user = userMapper.login(userInfo);
	    if(user == null) {
	        return false;
	    } else {
	    	/*
	        // userSeq의 값이 U로 시작하는 경우 authorityCd를 2로 설정
	        if(user.getUserSeq().startsWith("U")) {
	            user.setAuthorityCd("2");
	        }
	        // userSeq의 값이 M으로 시작하는 경우 authorityCd를 1로 설정
	        else if(user.getUserSeq().startsWith("M")) {
	            user.setAuthorityCd("1");
	        }
	        */
	    	user.setAuthorityCd(userMapper.getAuth(user.getUserSeq()));
	    	
	        // UserInfo 객체의 정보를 loginUserBean에 복사
	        BeanUtils.copyProperties(user, loginUserBean);
	        loginUserBean.setLogin(true);
	        return true;
	    }
	}
	
	/*
	//로그인
	public boolean login(UserInfo userInfo) {
		userInfo.setUserPw(encryptPassword(userInfo.getUserPw()));
		UserInfo user = userMapper.login(userInfo);
		if(user == null) {
			return false;
		} else {
			BeanUtils.copyProperties(user, loginUserBean);
			loginUserBean.setLogin(true);
			return true;
		}
	}
	*/
}
