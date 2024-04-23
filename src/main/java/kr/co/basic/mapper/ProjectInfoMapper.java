package kr.co.basic.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import kr.co.basic.bean.ProjectInfo;
import kr.co.basic.bean.ProjectSkill;
import kr.co.basic.bean.UserInfo;
import kr.co.basic.bean.UserProjectInfo;

public interface ProjectInfoMapper {

	// ============================================================PrjSearch================================================================================
	// 모든 프로젝트 조회
	@Select("select ip.prjSeq, ip.prjNm, ip.customerCd, "
			+ "        ip.prjStartDate, ip.prjEndDate, ip.prjDetail, "
			+ "        cd.dtlCodeNm as customerNm "
			+ "from info_project ip "
			+ "inner join code_detail cd on ip.customerCd = cd.dtlCode and cd.dCode = 'D050' "
			+ "order by ip.prjSeq")
	List<ProjectInfo> getAllPrj();
	
	// 조건에 맞는 프로젝트 조회(모든 값 null = 모든 프로젝트 조회)
	@Select("SELECT ip.prjSeq, ip.prjNm, ip.customerCd, "
			+ "       ip.prjStartDate, ip.prjEndDate, ip.prjDetail, "
			+ "       cd.dtlCodeNm as customerNm "
			+ "FROM info_project ip "
			+ "INNER JOIN code_detail cd ON ip.customerCd = cd.dtlCode AND cd.dCode = 'D050' "
			+ "WHERE (#{prjNm, jdbcType=VARCHAR} IS NULL OR ip.prjNm LIKE '%' || #{prjNm, jdbcType=VARCHAR} || '%') "
			+ "AND (#{customerNm, jdbcType=VARCHAR} IS NULL OR cd.dtlCodeNm LIKE '%' || #{customerNm, jdbcType=VARCHAR} || '%') "
			+ "AND (#{startFromDate, jdbcType=VARCHAR} IS NULL OR ip.prjStartDate >= #{startFromDate, jdbcType=VARCHAR}) "
			+ "AND (#{startToDate, jdbcType=VARCHAR} IS NULL OR ip.prjStartDate <= #{startToDate, jdbcType=VARCHAR}) "
			+ "AND (#{endFromDate, jdbcType=VARCHAR} IS NULL OR ip.prjEndDate >= #{endFromDate, jdbcType=VARCHAR}) "
			+ "AND (#{endToDate, jdbcType=VARCHAR} IS NULL OR ip.prjEndDate <= #{endToDate, jdbcType=VARCHAR}) "
			+ "ORDER BY ip.prjSeq")
	List<ProjectInfo> getPrjList(ProjectInfo projectInfo);
	
	// 해당하는 프로젝트 스킬
	@Select("select cd.dtlCodeNm as skill "
			+ "from info_project_skill ips "
			+ "inner join code_detail cd on ips.dtlcode = cd.dtlCode and cd.dCode = 'D060' "
			+ "where ips.prjSeq = #{prjSeq}")
	List<String> getPrjSkill(ProjectInfo projectInfo);
	
	// 선택 프로젝트 삭제
	@Delete("CALL Delete_Project(#{prjSeq})")
	void deleteProject(String prjSeq);
	
	// ============================================================PrjDetail================================================================================
	// 해당하는 프로젝트 정보 조회
	@Select("SELECT ip.prjSeq, ip.prjNm, ip.customerCd, "
			+ "        ip.prjStartDate, ip.prjEndDate, ip.prjDetail, "
			+ "        cd.dtlCodeNm AS customerNm "
			+ "FROM info_project ip "
			+ "INNER JOIN code_detail cd ON cd.dtlCode = ip.customerCd and cd.dCode = 'D050' "
			+ "where ip.prjSeq = #{prjSeq}")
	ProjectInfo getPrjInfo(String prjSeq);
	
	// 프로젝트에 참여하고 있는 인원 조회
	@Select("SELECT iup.userSeq, iup.prjSeq, iup.upStartDate, "
			+ "        iup.upEndDate, iup.roleCd, iu.userNm, "
			+ "        cd.dtlCodeNm AS roleNm "
			+ "FROM info_user_project iup "
			+ "INNER JOIN code_detail cd ON cd.dtlCode = iup.roleCd and cd.dCode = 'D020' "
			+ "INNER JOIN info_user iu ON iu.userSeq = iup.userSeq "
			+ "where prjSeq = #{prjSeq}")
	List<UserProjectInfo> getUserList(String prjSeq);
	
	// ============================================================Modal================================================================================
	// 해당 프로젝트에 참여하고 있지 않은 인원 조회
	@Select("SELECT u.userSeq, "
			+ "       u.userNm, "
			+ "       u.userId, "
			+ "       u.userPw, "
			+ "       u.genderCd, "
			+ "       gd.dtlCodeNm AS genderNm, "
			+ "       u.phoneNumber, "
			+ "       u.regiDate, "
			+ "       u.posCd, "
			+ "       pd.dtlCodeNm AS posNm, "
			+ "       u.skillRankCd, "
			+ "       sd.dtlCodeNm AS skillRankNm, "
			+ "       u.email, "
			+ "       u.address, "
			+ "       u.workStateCd, "
			+ "       wd.dtlCodeNm AS workStateNm, "
			+ "       u.userStateCd, "
			+ "       usd.dtlCodeNm AS userStateNm, "
			+ "       u.userRegiDate, "
			+ "       u.userImage "
			+ "FROM INFO_USER u "
			+ "LEFT JOIN CODE_DETAIL gd ON u.genderCd = gd.dtlCode AND gd.dCode = 'D010' "
			+ "LEFT JOIN CODE_DETAIL pd ON u.posCd = pd.dtlCode AND pd.dCode = 'D020' "
			+ "LEFT JOIN CODE_DETAIL sd ON u.skillRankCd = sd.dtlCode AND sd.dCode = 'D030' "
			+ "LEFT JOIN CODE_DETAIL wd ON u.workStateCd = wd.dtlCode AND wd.dCode = 'D040' "
			+ "LEFT JOIN CODE_DETAIL usd ON u.userStateCd = usd.dtlCode AND usd.dCode = 'D070' "
			+ "WHERE NOT EXISTS ( "
			+ "    SELECT 1 "
			+ "    FROM INFO_USER_PROJECT up "
			+ "    WHERE up.userSeq = u.userSeq AND up.prjSeq = #{prjSeq} "
			+ ") "
			+ "AND (#{userNm, jdbcType=VARCHAR} IS NULL OR u.userNm LIKE '%' || #{userNm, jdbcType=VARCHAR} || '%') "
			+ "AND (#{skillRankCd, jdbcType=VARCHAR} IS NULL OR u.skillRankCd = #{skillRankCd, jdbcType=VARCHAR}) "
			+ "AND u.userStateCd = '2'"
			+ "ORDER BY SUBSTR(userSeq, 1, 1), "
			+ "TO_NUMBER(SUBSTR(userSeq, 2)) DESC")
	List<UserInfo> getConUserList(UserProjectInfo userProjectInfo);
	
	// ============================================================Project Register================================================================================
	// 프로젝트 등록
	@SelectKey(statement = "select 'P'||lpad(prjSeq_inc.nextval, 3, 0) from dual", keyProperty = "prjSeq", before = true, resultType = String.class)
	@Insert("INSERT INTO INFO_PROJECT (prjSeq, prjNm, customerCd, prjStartDate, prjEndDate, prjDetail) "
			+ "VALUES (#{prjSeq}, #{prjNm}, #{customerCd}, #{prjStartDate}, #{prjEndDate}, #{prjDetail, jdbcType=VARCHAR})")
	void addProject(ProjectInfo projectInfo);
	
	// 프로젝트 요구 스킬 등록
	@Insert("INSERT INTO INFO_PROJECT_SKILL (prjSeq, dtlCode) VALUES (#{prjSeq}, #{dtlCode})")
	void addPrjSkill(@Param(value = "prjSeq") String prjSeq, @Param(value = "dtlCode") String dtlCode);
	
	// ============================================================Project Edit================================================================================
	// 해당 프로젝트의 요구 스킬
	@Select("select ips.dtlCode, cd.dtlCodeNm "
			+ "from info_project_skill ips "
			+ "left join code_detail cd on cd.dtlCode = ips.dtlCode and cd.dCode = 'D060' "
			+ "where prjSeq = #{prjSeq}")
	List<ProjectSkill> getSelectedPrjSkill(String prjSeq);
	
	// 해당 프로젝트의 모든 스킬 삭제
	@Delete("DELETE FROM info_project_skill WHERE prjSeq = #{prjSeq}")
	void deletePrjSkill(String prjSeq);
	
	// 해당 프로젝트 정보 업데이트
	@Update("UPDATE info_project "
			+ "SET "
			+ "    prjNm = #{prjNm}, "
			+ "    customerCd = #{customerCd}, "
			+ "    prjStartDate = #{prjStartDate}, "
			+ "    prjEndDate = #{prjEndDate}, "
			+ "    prjDetail = #{prjDetail, jdbcType=VARCHAR} "
			+ "WHERE prjSeq = #{prjSeq}")
	void modifyPrjPro(ProjectInfo projectInfo);
	
}
