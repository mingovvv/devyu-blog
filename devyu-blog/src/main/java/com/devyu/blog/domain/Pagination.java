package com.devyu.blog.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Pagination {

	/** 한 페이지당 게시글 수 **/
	private int pageSize = 5;

	/** 한 블럭(range)당 페이지 수 **/
	private int rangeSize = 5;

	/** 현재 페이지 **/
	private int curPage = 1;

	/** 현재 블럭(range) **/
	private int curRange = 1;

	/** 총 게시글 수 **/
	private int listCnt;

	/** 총 페이지 수 **/
	private int pageCnt;

	/** 총 블럭(range) 수 **/
	private int rangeCnt;

	/** 시작 페이지 **/
	private int startPage = 1;

	/** 끝 페이지 **/
	private int endPage = 1;

	/** 시작 index **/
	private int startIndex = 0;

	/** 이전 페이지 **/
	private int prevPage;

	/** 다음 페이지 **/
	private int nextPage;

	public Pagination(int listCnt, int curPage) {

		// 총 게시물 수와 현재 페이지를 Controller로 부터 받아온다.
		/** 현재페이지 **/
		setCurPage(curPage);
		/** 총 게시물 수 **/
		setListCnt(listCnt);

		/** 1. 총 페이지 수 **/
		setPageCnt(listCnt);
		/** 2. 총 블럭(range)수 **/
		setRangeCnt(pageCnt);
		/** 3. 블럭(range) setting **/
		rangeSetting(curPage);

		/** DB 질의를 위한 startIndex 설정 **/
		setStartIndex(curPage);
	}
	
	public void setPageCnt(int listCnt) {
		this.pageCnt = (int) ((Math.ceil(listCnt*1.0/pageSize) == 0 ) ? 1 : Math.ceil(listCnt*1.0/pageSize)); 
    }
    public void setRangeCnt(int pageCnt) {
        this.rangeCnt = (int) ((Math.ceil(pageCnt*1.0/rangeSize) == 0 ) ? 1 : Math.ceil(pageCnt*1.0/rangeSize));
    }
    public void rangeSetting(int curPage){
        
        setCurRange(curPage);        
        this.startPage = (curRange - 1) * rangeSize + 1;
        this.endPage = startPage + rangeSize - 1;
        
        if(endPage > pageCnt){
            this.endPage = pageCnt;
        }
        
        this.prevPage = (curRange * rangeSize) - rangeSize;
        
        if(prevPage < 1) {
        	this.prevPage = 1;
        }
        this.nextPage = (curRange * rangeSize) + 1;
        
        if(nextPage > pageCnt) {
        	nextPage = pageCnt;
    	}
    }
    public void setCurRange(int curPage) {
        this.curRange = (int)((curPage-1)/rangeSize) + 1;
    }
    public void setStartIndex(int curPage) {
        this.startIndex = (curPage-1) * pageSize;
    }

	@Override
	public String toString() {
		return "Pagination [pageSize=" + pageSize + ", rangeSize=" + rangeSize + ", curPage=" + curPage + ", curRange="
				+ curRange + ", listCnt=" + listCnt + ", pageCnt=" + pageCnt + ", rangeCnt=" + rangeCnt + ", startPage="
				+ startPage + ", endPage=" + endPage + ", startIndex=" + startIndex + ", prevPage=" + prevPage
				+ ", nextPage=" + nextPage + "]";
	}

    
}
