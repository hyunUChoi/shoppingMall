package com.chw.shopping.ma.board.service;

import com.chw.shopping.common.domain.CommonVO;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Alias("maBoardVO")
public class MaBoardVO extends CommonVO implements Serializable {

    public interface notice{}
    public interface inq{}
    public interface inqAnswer{}
    public interface faq{}

    private static final long serialVersionUID = 1L;

    private int seq;
    private String brdDivn; // 게시판 구분

    @NotBlank(groups = {notice.class, inq.class, faq.class}, message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(groups = {notice.class, inq.class, faq.class}, message = "내용을 입력해주세요.")
    private String cont;

    @NotNull(groups = {notice.class}, message = "공지 시작일을 입력해주세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate notiStrtDt; // 공지 시작일

    @NotNull(groups = {notice.class}, message = "공지 종료일을 입력해주세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate notiEndDt; // 공지 종료일

    @NotBlank(groups = {inq.class}, message = "문의유형을 선택해주세요.")
    private String qaType; // 질문유형

    @NotBlank(groups = {inqAnswer.class}, message = "답변을 입력해주세요.")
    private String answer; // 답변

    private String ansId; // 답변등록자 아이디
    private String ansNm; // 답변등록자 이름
    private LocalDateTime ansDt; // 답변등록일
    private String atchFileId; // 첨부파일

}
