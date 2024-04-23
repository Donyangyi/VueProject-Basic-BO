package kr.co.basic.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import kr.co.basic.bean.CodeDetail;
import kr.co.basic.bean.ProjectInfo;
import kr.co.basic.bean.UserInfo;
import kr.co.basic.bean.UserProjectInfo;

public interface UserInfoMapper {

	// UserSearch
	// 사원 정보 출력 (모든 조건이 null일 시 전체 회원 조회)
	@Select("SELECT iu.userSeq, iu.userNm, iu.userId, iu.userPw, "
			+ "       gd.dtlCodeNm AS gender, "
			+ "       iu.phoneNumber, iu.regiDate, "
			+ "       pd.dtlCodeNm AS position, "
			+ "       skd.dtlCodeNm AS skillRank, "
			+ "       iu.email, iu.address, "
			+ "       wsd.dtlCodeNm AS workState, "
			+ "       usd.dtlCodeNm AS userState, "
			+ "       iu.userRegiDate, iu.userImage "
			+ "FROM INFO_USER iu "
			+ "INNER JOIN CODE_DETAIL gd ON iu.genderCd = gd.dtlCode AND gd.dCode = 'D010' "
			+ "LEFT JOIN CODE_DETAIL pd ON iu.posCd = pd.dtlCode AND pd.dCode = 'D020' "
			+ "LEFT JOIN CODE_DETAIL skd ON iu.skillRankCd = skd.dtlCode AND skd.dCode = 'D030' "
			+ "INNER JOIN CODE_DETAIL wsd ON iu.workStateCd = wsd.dtlCode AND wsd.dCode = 'D040' "
			+ "INNER JOIN CODE_DETAIL usd ON iu.userStateCd = usd.dtlCode AND usd.dCode = 'D070' "
			+ "WHERE (iu.userNm LIKE #{userNm, jdbcType=VARCHAR} OR #{userNm, jdbcType=VARCHAR} IS NULL) "
			+ "  AND (pd.dtlCode = #{posCd, jdbcType=VARCHAR} OR #{posCd, jdbcType=VARCHAR} IS NULL) "
			+ "  AND (wsd.dtlCode = #{workStateCd, jdbcType=VARCHAR} OR #{workStateCd, jdbcType=VARCHAR} IS NULL) "
			+ "  AND (iu.regiDate BETWEEN #{startDate, jdbcType=VARCHAR} AND #{endDate, jdbcType=VARCHAR} OR (#{startDate, jdbcType=VARCHAR} IS NULL AND #{endDate, jdbcType=VARCHAR} IS NULL)) "
			+ "  AND iu.userStateCd = #{userStateCd} "
			+ "  ORDER BY iu.userSeq")
	ArrayList<UserInfo> userAllSearch(UserInfo userInfo, RowBounds rowBounds);
	
	// 사원 정보 출력 (모든 조건이 null일 시 전체 회원 조회)
		@Select("SELECT iu.userSeq, iu.userNm, iu.userId, iu.userPw, "
				+ "       gd.dtlCodeNm AS gender, "
				+ "       iu.phoneNumber, iu.regiDate, "
				+ "       pd.dtlCodeNm AS position, "
				+ "       skd.dtlCodeNm AS skillRank, "
				+ "       iu.email, iu.address, "
				+ "       wsd.dtlCodeNm AS workState, "
				+ "       usd.dtlCodeNm AS userState, "
				+ "       iu.userRegiDate, iu.userImage "
				+ "FROM INFO_USER iu "
				+ "INNER JOIN CODE_DETAIL gd ON iu.genderCd = gd.dtlCode AND gd.dCode = 'D010' "
				+ "LEFT JOIN CODE_DETAIL pd ON iu.posCd = pd.dtlCode AND pd.dCode = 'D020' "
				+ "LEFT JOIN CODE_DETAIL skd ON iu.skillRankCd = skd.dtlCode AND skd.dCode = 'D030' "
				+ "INNER JOIN CODE_DETAIL wsd ON iu.workStateCd = wsd.dtlCode AND wsd.dCode = 'D040' "
				+ "INNER JOIN CODE_DETAIL usd ON iu.userStateCd = usd.dtlCode AND usd.dCode = 'D070' "
				+ "WHERE (iu.userNm LIKE #{userNm, jdbcType=VARCHAR} OR #{userNm, jdbcType=VARCHAR} IS NULL) "
				+ "  AND (pd.dtlCode = #{posCd, jdbcType=VARCHAR} OR #{posCd, jdbcType=VARCHAR} IS NULL) "
				+ "  AND (wsd.dtlCode = #{workStateCd, jdbcType=VARCHAR} OR #{workStateCd, jdbcType=VARCHAR} IS NULL) "
				+ "  AND (iu.regiDate BETWEEN #{startDate, jdbcType=VARCHAR} AND #{endDate, jdbcType=VARCHAR} OR (#{startDate, jdbcType=VARCHAR} IS NULL AND #{endDate, jdbcType=VARCHAR} IS NULL)) "
				+ "  AND iu.userStateCd = #{userStateCd} "
				+ "  ORDER BY iu.userSeq")
		ArrayList<UserInfo> userAllSearchNoPage(UserInfo userInfo);
	
	
	
	// 검색 조건에 해당하는 사원 수
	@Select("SELECT COUNT(*) FROM INFO_USER iu "
	        + "INNER JOIN CODE_DETAIL gd ON iu.genderCd = gd.dtlCode AND gd.dCode = 'D010' "
	        + "LEFT JOIN CODE_DETAIL pd ON iu.posCd = pd.dtlCode AND pd.dCode = 'D020' "
	        + "LEFT JOIN CODE_DETAIL skd ON iu.skillRankCd = skd.dtlCode AND skd.dCode = 'D030' "
	        + "INNER JOIN CODE_DETAIL wsd ON iu.workStateCd = wsd.dtlCode AND wsd.dCode = 'D040' "
	        + "INNER JOIN CODE_DETAIL usd ON iu.userStateCd = usd.dtlCode AND usd.dCode = 'D070' "
	        + "WHERE (iu.userNm LIKE #{userNm, jdbcType=VARCHAR} OR #{userNm, jdbcType=VARCHAR} IS NULL) "
	        + "AND (pd.dtlCode = #{posCd, jdbcType=VARCHAR} OR #{posCd, jdbcType=VARCHAR} IS NULL) "
	        + "AND (wsd.dtlCode = #{workStateCd, jdbcType=VARCHAR} OR #{workStateCd, jdbcType=VARCHAR} IS NULL) "
	        + "AND (iu.regiDate BETWEEN #{startDate, jdbcType=VARCHAR} AND #{endDate, jdbcType=VARCHAR} OR (#{startDate, jdbcType=VARCHAR} IS NULL AND #{endDate, jdbcType=VARCHAR} IS NULL))")
	int getSearchCnt(UserInfo userInfo);
	
	// 한 유저에 대한 정보
	@Select("SELECT iu.userSeq, iu.userNm, iu.userId, iu.userPw, "
			+ "       iu.genderCd, gd.dtlCodeNm AS gender, "
			+ "       iu.phonenumber, iu.regidate, "
			+ "       iu.posCd ,pd.dtlCodeNm AS position, "
			+ "       iu.skillRankCd, skd.dtlCodeNm AS skillRank, "
			+ "       iu.email, iu.address, "
			+ "       iu.workStateCd, wsd.dtlCodeNm AS workState, "
			+ "       iu.userStateCd, usd.dtlCodeNm AS userState, "
			+ "       iu.userRegiDate, iu.userImage "
			+ "FROM INFO_USER iu "
			+ "INNER JOIN CODE_DETAIL gd ON iu.genderCd = gd.dtlCode AND gd.dCode = 'D010' "
			+ "LEFT JOIN CODE_DETAIL pd ON iu.posCd = pd.dtlCode AND pd.dCode = 'D020' "
			+ "LEFT JOIN CODE_DETAIL skd ON iu.skillRankCd = skd.dtlCode AND skd.dCode = 'D030' "
			+ "INNER JOIN CODE_DETAIL wsd ON iu.workStateCd = wsd.dtlCode AND wsd.dCode = 'D040' "
			+ "INNER JOIN CODE_DETAIL usd ON iu.userStateCd = usd.dtlCode AND usd.dCode = 'D070' "
			+ "where iu.userSeq = #{userSeq}")
	UserInfo getUserInfo(String userSeq);

	@Select("select cd.dtlCodeNm from INFO_USER_SKILL ius "
			+ "join CODE_DETAIL cd on ius.dtlcode = cd.dtlcode and cd.dcode = 'D060' "
			+ "where userSeq = #{userSeq}")
	ArrayList<String> getUserSkills(String userSeq);
	
	// 유저 삭제
	@Select("CALL Delete_User(#{userSeq, mode=IN, jdbcType=VARCHAR})")
	void deleteUserPro(@Param("userSeq") String userSeq);
	
	// ============================================================UserDetail====================================================================================
	// 유저 한명이 참여하고 있는 프로젝트
	@Select("select iup.userSeq, iup.prjSeq, iup.upStartDate, "
			+ "iup.upEndDate, roleCd, rd.dtlcodenm as roleNm, "
			+ "ctd.dtlcodenm as customerNm, ip.prjNm, "
			+ "ip.prjStartDate, ip.prjEndDate "
			+ "from info_user_project iup "
			+ "inner join info_project ip on ip.prjSeq = iup.prjSeq "
			+ "inner join code_detail rd on rd.dtlCode = iup.rolecd and rd.dCode = 'D080' "
			+ "inner join code_detail ctd on ctd.dtlCode = ip.customerCd and ctd.dCode = 'D050' "
			+ "where userSeq = #{userSeq}")
	List<UserProjectInfo> getUserProjectInfo(String userSeq);
	
	// 역할
	@Select("select dtlCode, dtlCodeNm from code_detail where dCode = 'D080'")
	List<CodeDetail> getRoleNm();
	
	// 유저 프로젝트 업데이트 (투입일, 철수일, 역할)
	@Update("update info_user_project "
			+ "set upStartDate = #{upStartDate}, "
			+ "upEndDate = #{upEndDate}, "
			+ "roleCd = #{roleCd} "
			+ "where userSeq = #{userSeq} and prjSeq = #{prjSeq}")
	void userPrjUpdatePro(UserProjectInfo userProjectInfo);
	
	// 프로젝트 삭제
	@Delete("delete from info_user_project where userSeq = #{userSeq} and prjSeq = #{prjSeq}")
	void prjDeletePro(UserProjectInfo userProjectInfos);
	
	// ============================================================Modal================================================================================
	// 해당 유저가 참여하지 않으며 검색 조건의 의한 프로젝트 조회
	/* prjDetail의 자료 형태가 VARCHAR2일때
	@Select("SELECT DISTINCT "
			+ "    ip.prjSeq, "
			+ "    ip.prjNm, "
			+ "    ip.customerCd, "
			+ "    ip.prjStartDate, "
			+ "    ip.prjEndDate, "
			+ "    ip.prjDetail, "
			+ "    cnd.dtlCodeNm AS customerNm "
			+ "FROM "
			+ "    info_project ip "
			+ "LEFT JOIN "
			+ "    info_user_project iup ON ip.prjSeq = iup.prjSeq AND iup.userSeq = #{userSeq} "
			+ "JOIN "
			+ "    code_detail cnd ON ip.customerCd = cnd.dtlCode AND cnd.dCode = 'D050' "
			+ "WHERE "
			+ "    iup.prjSeq IS NULL "
			+ "    AND ip.prjNm LIKE NVL(#{prjNm, jdbcType=VARCHAR}, '%') "
			+ "    AND cnd.dtlCodeNm LIKE NVL(#{customerNm, jdbcType=VARCHAR}, '%')  "
			+ "ORDER BY "
			+ "    ip.prjSeq")
	List<ProjectInfo> getConPrjList(UserProjectInfo userProjectInfo);
	*/
	
	// prjDetail의 자료 형태가 CLOB일때
	@Select("SELECT DISTINCT "
	        + "    ip.prjSeq, "
	        + "    ip.prjNm, "
	        + "    ip.customerCd, "
	        + "    ip.prjStartDate, "
	        + "    ip.prjEndDate, "
	        // CLOB 데이터를 VARCHAR로 변환하는 예시 추가
	        // DBMS_LOB.substr 함수를 사용하여 CLOB 데이터의 처음 4000 바이트(혹은 문자)를 추출
	        + "    DBMS_LOB.substr(ip.prjDetail, 4000, 1) as prjDetail, "
	        + "    cnd.dtlCodeNm AS customerNm "
	        + "FROM "
	        + "    info_project ip "
	        + "LEFT JOIN "
	        + "    info_user_project iup ON ip.prjSeq = iup.prjSeq AND iup.userSeq = #{userSeq} "
	        + "JOIN "
	        + "    code_detail cnd ON ip.customerCd = cnd.dtlCode AND cnd.dCode = 'D050' "
	        + "WHERE "
	        + "    iup.prjSeq IS NULL "
	        + "    AND ip.prjNm LIKE NVL(#{prjNm, jdbcType=VARCHAR}, '%') "
	        + "    AND cnd.dtlCodeNm LIKE NVL(#{customerNm, jdbcType=VARCHAR}, '%')  "
	        + "ORDER BY "
	        + "    ip.prjSeq")
	List<ProjectInfo> getConPrjList(UserProjectInfo userProjectInfo);
	
	// 해당 프로젝트의 필요 스킬 조회
	@Select("select cd.dtlCodeNm as skill "
			+ "from info_project_skill ips "
			+ "inner join code_detail cd on ips.dtlcode = cd.dtlCode and cd.dCode = 'D060' "
			+ "where ips.prjSeq = #{prjSeq}")
	List<String> getPrjSkill(ProjectInfo projectInfo);
	
	// 프로젝트 참여
	@Insert("INSERT INTO INFO_USER_PROJECT (userSeq, prjSeq, upStartDate, upEndDate, roleCd) "
			+ "VALUES (#{userSeq}, #{prjSeq}, #{upStartDate}, #{upEndDate}, #{roleCd})")
	void prjAddPro(UserProjectInfo userProjectInfo);
}
