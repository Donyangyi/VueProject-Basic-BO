package kr.co.basic.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.basic.bean.Boards;
import kr.co.basic.bean.Comments;
import kr.co.basic.mapper.MainPageMapper;

@Repository
public class MainPageDao {
	
	@Autowired
	private MainPageMapper mainPageMapper;
	
	//전체 게시판 정보 출력
	public List<Boards> BoardAllInfo(String categoriCd, RowBounds rowBounds) {
		return  mainPageMapper.BoardAllInfo(categoriCd, rowBounds);
	}
	
	//각 게시판 별 게시글 갯수
	public int getBoardCnt(String categoriCd) {
		return mainPageMapper.getBoardCnt(categoriCd);
	}
	
	public Boards getBoardInfo(String boardSeq) {
		return mainPageMapper.getBoardInfo(boardSeq);
	}
	
	public List<Comments> getComments(String boardSeq) {
		return mainPageMapper.getComments(boardSeq);
	}
	
	// 대댓글 작성
	public void submitReplyPro(Comments comments) {
		mainPageMapper.submitReplyPro(comments);
	}
	
	public void submitPost(Boards boards){
		mainPageMapper.submitPost(boards);
	}
	
	public void updateBoardPro(Boards boards){
		mainPageMapper.updateBoardPro(boards);
	}
}
