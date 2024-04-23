package kr.co.basic.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import kr.co.basic.bean.Boards;
import kr.co.basic.bean.Comments;

public interface MainPageMapper {
	
	// 전체 게시판 정보 조회
	@Select("select cd.dtlCodeNm as detailNm, iu.userNm, b.boardSeq, b.boardTitle, b.boardContent, b.boardRegiDate, b.categoriCd "
			+ "from BOARDS b join INFO_USER iu on b.userSeq = iu.userSeq "
			+ "join CODE_DETAIL cd on cd.dCode = 'D090' and cd.dtlCode = b.categoriCd "
			+ "where cd.dCode = 'D090' and b.categoriCd = #{categoriCd} order by boardSeq desc")
	List<Boards> BoardAllInfo(String categoriCd, RowBounds rowBounds);
	
	// 전체 게시판 종류 갯수
	@Select("select count(distinct categoriCd) from BOARDS")
	int BoardCnt();
	
	// 각 게시판 별 게시글 갯수
	@Select("select count(boardSeq) from BOARDS where categoricd = #{categoriCd}")
	int getBoardCnt(String categoriCd);
	
	// 해당 게시판 정보 조회
	@Select("SELECT b.categoriCd, "
			+ "    b.userSeq, "
			+ "    b.boardSeq, "
			+ "    b.boardTitle, "
			+ "    b.boardContent, "
			+ "    b.boardRegiDate, "
			+ "    cd.dtlCodeNm as categoriNm, "
			+ "    iu.userNm "
			+ "FROM boards b "
			+ "INNER JOIN code_detail cd on b.categoriCd = cd.dtlCode and cd.dCode = 'D090' "
			+ "INNER JOIN info_user iu on b.userSeq = iu.userSeq "
			+ "WHERE b.boardSeq = #{boardSeq}")
	Boards getBoardInfo(String boardSeq);
	
	// 해당 게시판 댓글 조회
	@Select("SELECT boardSeq, "
			+ "    commentSeq, "
			+ "    parentCommentSeq, "
			+ "    commentText, "
			+ "    commentRegiDate "
			+ "FROM comments "
			+ "WHERE boardSeq = #{boardSeq}")
	List<Comments> getComments(String boardSeq);
	
	// 대댓글 작성
	@SelectKey(statement = "select 'C'||lpad(commentSeq_inc.nextval, 3, 0) from dual", keyProperty = "commentSeq", before = true, resultType = String.class)
	@Insert("INSERT INTO comments (boardSeq, commentSeq, parentCommentSeq, commentText, commentRegiDate) "
			+ "VALUES (#{boardSeq}, #{commentSeq}, #{parentCommentSeq, jdbcType=VARCHAR}, #{commentText}, SYSDATE)")
	void submitReplyPro(Comments comments);
	
	// 게시글 작성
	@SelectKey(statement = "select 'BO'||lpad(boardSeq_inc.nextval, 3, 0) from dual", keyProperty = "boardSeq", before = true, resultType = String.class)
	@Insert("INSERT INTO BOARDS (userSeq, boardSeq, categoriCd, boardTitle, boardContent, boardRegiDate) "
	        + "VALUES (#{userSeq}, #{boardSeq}, #{categoriCd}, #{boardTitle}, #{boardContent}, SYSDATE)")
	void submitPost(Boards boards);
	
	// 게시글 수
	@Select("select count(*) from boards "
			+ "where categoriCd = #{categoriCd}")
	int getCountBoards(String categoriCd);
	
	// 게시글 업데이트
	@Update("UPDATE boards "
			+ "SET boardTitle = #{boardTitle}, "
			+ "    boardContent = #{boardContent} "
			+ "WHERE boardSeq = #{boardSeq}")
	void updateBoardPro(Boards boards);
	
	// 게시글 삭제
	@Delete("DELETE FROM boards "
			+ "WHERE boardSeq = #{boardSeq}")
	void deleteBoardPro(String boardSeq);
	
	
}
