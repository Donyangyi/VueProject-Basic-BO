package kr.co.basic.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.basic.bean.Boards;
import kr.co.basic.bean.Comments;
import kr.co.basic.bean.PageBean;
import kr.co.basic.bean.UserInfo;
import kr.co.basic.dao.MainPageDao;
import kr.co.basic.mapper.MainPageMapper;

@Service
@PropertySource("/WEB-INF/properties/page.properties")
public class MainPageService {
	
	@Autowired
	private MainPageDao mainPageDao;
	
	@Autowired
	private MainPageMapper mainPageMapper;
	
	@Value("${page.listcnt}")
	private int page_listcnt;
	
	@Value("${page.paginationcnt}")
	private int page_paginationcnt;
	
	@Resource(name = "loginUserBean")
	private UserInfo loginUserBean;
	
	//전체 게시판 정보 출력
	public List<Boards> BoardAllInfo(int page) {
		int start = (page - 1) * page_listcnt;
		RowBounds rowBounds = new RowBounds(start, page_listcnt);
		
		List<Boards> boardInfo = new ArrayList<Boards>();
		int boardCnt = mainPageMapper.BoardCnt(); //전체 게시판 종류 갯수
		for(int i = 1; i <= boardCnt; i++) {
			String categoriCd = i+"";
			List<Boards> tempBoardInfo = mainPageDao.BoardAllInfo(categoriCd, rowBounds);
			boardInfo.addAll(tempBoardInfo);
		}
		
		return boardInfo;
	}
	
	//선택 게시판 정보 출력
		public List<Boards> getBoardInfo(int page, String categoriCd) {
			int start = (page - 1) * page_listcnt;
			RowBounds rowBounds = new RowBounds(start, page_listcnt);
			
			return mainPageDao.BoardAllInfo(categoriCd, rowBounds);
		}
	
	//각 게시판 별 게시글 갯수
	public ArrayList<PageBean> getBoardCnt(int currentPage) {
		int boardCnt = mainPageMapper.BoardCnt();
		ArrayList<PageBean> pageBeanList = new ArrayList<PageBean>();
		for(int i = 1; i <= boardCnt; i++) {
			String categoriCd = i+"";
			int content_cnt = mainPageDao.getBoardCnt(categoriCd);
			PageBean pageBean = new PageBean(content_cnt, currentPage, page_listcnt, page_paginationcnt);
			pageBeanList.add(pageBean);
		}
		return pageBeanList;
	}
	
	// 대댓글 작성
	@Transactional
	public boolean submitReplyPro(Comments comments) {
		try {
			mainPageDao.submitReplyPro(comments);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Transactional
	public boolean submitPost(Boards boards) {
		try {
			boards.setUserSeq(loginUserBean.getUserSeq());
			mainPageDao.submitPost(boards);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public PageBean getPageBean(List<Boards> boards, int currentPage, String categoriCd) {
		int content_cnt = mainPageMapper.getBoardCnt(categoriCd);
		
		PageBean pageBean = new PageBean(content_cnt, currentPage, 5, page_paginationcnt);
		return pageBean;
	}
	
	@Transactional
	public boolean updateBoardPro(Boards boards) {
		try {
			mainPageDao.updateBoardPro(boards);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Transactional
	public boolean deleteBoardPro(List<String> boardSeqs) {
		try {
			for(String boardSeq: boardSeqs) {
				mainPageMapper.deleteBoardPro(boardSeq);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
