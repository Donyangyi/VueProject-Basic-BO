package kr.co.basic.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import kr.co.basic.bean.MenuBean;
import kr.co.basic.bean.UserInfo;
import kr.co.basic.bean.UserSkill;

public interface UserMapper {

	// 모든 스킬 정보 조회
	@Select("select dtlCode, dtlCodeNm from code_detail where dCode = 'D060' order by TO_NUMBER(dtlCode)")
	List<UserSkill> searchAllSkill();
	
	// 모든 성별 정보 조회
	@Select("select dtlCode, dtlCodeNm from code_detail where dCode = 'D010' order by TO_NUMBER(dtlCode)")
	List<UserSkill> searchAllGender();
	
	// 모든 직급 정보 조회
	@Select("select dtlCode, dtlCodeNm from code_detail where dCode = 'D020' order by TO_NUMBER(dtlCode)")
	List<UserSkill> searchAllPosition();
	
	// 모든 기술등급 정보 조회
	@Select("select dtlCode, dtlCodeNm from code_detail where dCode = 'D030' order by TO_NUMBER(dtlCode)")
	List<UserSkill> searchAllSkillLevel();
	
	// 모든 재직상태 조회
	@Select("select dtlCode, dtlCodeNm from code_detail where dCode = 'D040' order by TO_NUMBER(dtlCode)")
	List<UserSkill> searchAllWorkState();
	
	// 모든 고객사 정보 조회
	@Select("select dtlCode, dtlCodeNm from code_detail where dCode = 'D050' order by TO_NUMBER(dtlCode)")
	List<UserSkill> searchAllCustomer();
	
	// 모든 등록상태 조회
	@Select("select dtlCode, dtlCodeNm from code_detail where dCode = 'D070' order by TO_NUMBER(dtlCode)")
	List<UserSkill> searchAllRegiState();
	
	// 모든 전화번호 정보 조회
	@Select("select dtlCode, dtlCodeNm from code_detail where dCode = 'D100' order by TO_NUMBER(dtlCode)")
	List<UserSkill> searchAllPhoneNumber();
	
	// 모든 이메일 정보 조회
	@Select("select dtlCode, dtlCodeNm from code_detail where dCode = 'D110' order by TO_NUMBER(dtlCode)")
	List<UserSkill> searchAllEmail();
	
	// 리스트 수 조회
	@Select("select dtlCode, dtlCodeNm from code_detail where dCode = 'D120' order by TO_NUMBER(dtlCode)")
	List<UserSkill> searchAllListCount();
	
	// 아이디 중복 검사
	@Select("select userId from info_user where userId = #{userId}")
	String duplicateCheck(String userId);
	
	// 해당 회원의 보유 스킬
	@Select("select ius.dtlCode, cd.dtlCodeNm from info_user_skill ius "
			+ "left join code_detail cd on cd.dtlcode = ius.dtlcode and cd.dCode = 'D060' "
			+ "where userSeq = #{userSeq}")
	List<UserSkill> getUserSkills(String userSeq);
	
	
	// 유저 추가
	@SelectKey(statement = "select 'U'||lpad(userSeq_inc.nextval, 3, 0) from dual", keyProperty = "userSeq", before = true, resultType = String.class)
	@Insert("INSERT INTO INFO_USER "
			+ "( "
			+ "userSeq, userNm, userId, "
			+ "userPw, genderCd, phoneNumber, "
			+ "regiDate, posCd, skillRankCd, "
			+ "email, address, workStateCd, "
			+ "userStateCd, userRegiDate, userImage "
			+ ") "
			+ "VALUES "
			+ "( "
			+ "#{userSeq}, #{userNm}, #{userId}, "
			+ "#{userPw}, #{genderCd}, #{phoneNumber}, "
			+ "#{regiDate}, #{posCd, jdbcType=VARCHAR}, #{skillRankCd ,jdbcType=VARCHAR}, "
			+ "#{email, jdbcType=VARCHAR}, #{address}, #{workStateCd}, "
			+ "#{userStateCd}, sysdate, #{userImage})")
	void regiUserPro(UserInfo userInfo);
	
	@SelectKey(statement = "select 'U'||lpad(userSeq_inc.nextval, 3, 0) from dual", keyProperty = "userSeq", before = true, resultType = String.class)
	@Insert("INSERT INTO INFO_USER "
			+ "( "
			+ "userSeq, userNm, userId, "
			+ "userPw, genderCd, phoneNumber, "
			+ "regiDate, posCd, skillRankCd, "
			+ "email, address, workStateCd, "
			+ "userStateCd, userRegiDate, userImage "
			+ ") "
			+ "VALUES "
			+ "( "
			+ "#{userSeq}, #{userNm}, #{userId}, "
			+ "#{userPw}, #{genderCd}, #{phoneNumber}, "
			+ "#{regiDate}, #{posCd, jdbcType=VARCHAR}, #{skillRankCd ,jdbcType=VARCHAR}, "
			+ "#{email, jdbcType=VARCHAR}, #{address}, #{workStateCd}, "
			+ "#{userStateCd}, sysdate, #{userImage})")
	void regiUserAdminPro(UserInfo userInfo);
	
	// 유저의 고유번호 조회
	@Select("select userSeq from info_user "
			+ "where userNm = #{userNm} and phonenumber = #{phoneNumber}")
	String getUserSeq(UserInfo userInfo);
	
	// 유저 스킬 추가
	@Insert("insert into info_user_skill (userSeq, dtlCode) "
			+ "values (#{userSeq}, #{dtlCode})")
	void regiSkill(UserSkill userSkill);
	
	@Update("UPDATE INFO_USER "
			+ "SET "
			+ "    userNm = #{userNm}, "
			+ "    userPw = #{userPw}, "
			+ "    genderCd = #{genderCd}, "
			+ "    phoneNumber = #{phoneNumber}, "
			+ "    regiDate = #{regiDate}, "
			+ "    posCd = #{posCd}, "
			+ "    skillRankCd = #{skillRankCd}, "
			+ "    email = #{email}, "
			+ "    address = #{address}, "
			+ "	   workStateCd = #{workStateCd} "
			+ "WHERE userSeq = #{userSeq}")
	void updateUserInfo(UserInfo userInfo);
	
	// 스킬 추가
	@Insert("INSERT INTO info_user_skill (userSeq, dtlCode) VALUES (#{userSeq}, #{dtlCode})")
	void insertSkill(UserSkill userSkill);

	// 스킬 삭제
	@Delete("DELETE FROM info_user_skill WHERE userSeq = #{userSeq} AND dtlCode = #{dtlCode}")
	void deleteSkill(UserSkill userSkill);
	
	// 로그인
	@Select("SELECT userSeq, "
			+ "        userNm, "
			+ "        userId, "
			+ "        userPw, "
			+ "        genderCd, "
			+ "        phoneNumber, "
			+ "        regiDate, "
			+ "        posCd, "
			+ "        skillRankCd, "
			+ "        email, "
			+ "        address, "
			+ "        workStateCd, "
			+ "        userStateCd, "
			+ "        userRegiDate, "
			+ "        userImage "
			+ "FROM info_user "
			+ "WHERE userId = #{userId} "
			+ "and userPw = #{userPw} "
			+ "and userStateCd = '2'")
	UserInfo login(UserInfo userInfo);
	
	// 로그인 객체 권한 여부
	@Select("SELECT authSeq from auth_user "
			+ "where userSeq = #{userSeq}")
	List<String> getAuth(String userSeq);
	
	// 유저 등록 상태 업데이트
	@Update("update info_user "
			+ "set userStateCd = '2' "
			+ "where userSeq = #{userSeq}")
	void updateUserState(String userSeq);
	
	// 한 유저의 권한에 따른 메뉴 보이기
	@Select("SELECT DISTINCT m.menuSeq, "
			+ "    m.menuType, "
			+ "    m.menuNm, "
			+ "    m.menuUrl, "
			+ "    m.parentSeq "
			+ "FROM MENU m "
			+ "JOIN AUTH_MENU am ON m.menuSeq = am.menuSeq "
			+ "JOIN AUTH_USER au ON am.authSeq = au.authSeq "
			+ "WHERE au.userSeq = #{userSeq} "
			+ "ORDER BY TO_NUMBER(m.menuSeq)")
	List<MenuBean> getMenuList(String userSeq);
	
	// 접근 가능한 URL 정보
	@Select("SELECT DISTINCT m.menuUrl "
			+ "FROM MENU m "
			+ "JOIN AUTH_MENU am ON m.menuSeq = am.menuSeq "
			+ "JOIN AUTH_USER au ON am.authSeq = au.authSeq "
			+ "WHERE au.userSeq = #{userSeq}")
	List<String> getUrlList(String userSeq);
	
	// 유저 등록 거절
	@Delete("CALL Delete_User(#{userSeq, mode=IN, jdbcType=VARCHAR})")
	void rejectUser(String userSeq);
}
