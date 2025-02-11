package com.chw.shopping.common.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Alias("commonVO")
public class CommonVO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 등록자, 등록일, 수정자, 수정일, 삭제여부, 첨부파일
    private String regId;
    private String regNm;
    private LocalDateTime regDt;
    private String modId;
    private LocalDateTime modDt;
    private String delYn;
    private String atchFileId;

    // 페이징
    private int page;
    private int firstPageNo;
    private int lastPageNo;

    // 파라미터
    private String search1;
    private String search2;
    private String search3;
    private String search4;
    private String search5;
    private String search6;
    private String search7;
    private String search8;
    private String search9;
    private String search10;
    private String searchOption;
    private String searchKeyword;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate searchStrtDt;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate searchEndDt;

    // 구분
    private String procType;
    private String loginId;

    public void setSearchVO(final CommonVO vo) {
        this.page = vo.getPage();
        this.searchOption = vo.getSearchOption();
        this.searchKeyword = vo.getSearchKeyword();
        this.searchStrtDt = vo.getSearchStrtDt();
        this.searchEndDt = vo.getSearchEndDt();
        this.search1 = vo.getSearch1();
        this.search2 = vo.getSearch2();
        this.search3 = vo.getSearch3();
        this.search4 = vo.getSearch4();
        this.search5 = vo.getSearch5();
        this.search6 = vo.getSearch6();
        this.search7 = vo.getSearch7();
        this.search8 = vo.getSearch8();
        this.search9 = vo.getSearch9();
        this.search10 = vo.getSearch10();
        this.firstPageNo = vo.firstPageNo;
        this.lastPageNo = vo.lastPageNo;
    }

    public String getLoginId() {
        if(SecurityContextHolder.getContext().getAuthentication() != null && !SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            return SecurityContextHolder.getContext().getAuthentication().getName();
        } else {
            return "";
        }
    }

}
