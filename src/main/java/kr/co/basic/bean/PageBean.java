package kr.co.basic.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PageBean {
	private int min; // 최소 페이지 번호
	private int max; // 최대 페이지 번호
	private int prevPage; // 이전 버튼의 페이지 번호
	private int nextPage; // 다음 버튼의 페이지 번호
	private int pageCnt; // 전체 페이지 개수
	private int currentPage; // 현재 페이지 번호

	//contentCnt : 전체글 갯수(오라클 table) | currentPage : 현재 페이지번호(param)
	//contentPageCnt : 페이지당 글의 갯수(property) | paginationCnt : 페이지 버튼의 갯수(property)
	public PageBean(int contentCnt, int currentPage, int contentPageCnt, int paginationCnt) {
		// 현재 페이지 번호
		this.currentPage = currentPage;

		// 전체 페이지 갯수
		pageCnt = contentCnt / contentPageCnt;
		if (contentCnt % contentPageCnt > 0) {
			pageCnt++;
		}
		
		min = ((currentPage - 1) / paginationCnt) * paginationCnt + 1;
		max = Math.min(min + paginationCnt - 1, pageCnt);
		
		if (max > pageCnt) {
			max = pageCnt;
		}
		
		prevPage = min - 1;
		nextPage = max + 1;
		
		if (nextPage > pageCnt) {
			nextPage = pageCnt;
		}
	}

}
