package com.chw.shopping.ma.banner.service;

import com.chw.shopping.common.domain.CommonVO;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Alias("maBannerVO")
public class MaBannerVO extends CommonVO implements Serializable {

    public interface insert{}

    private static final long serialVersionUID = 1L;

    private int seq;

    @NotBlank(groups = {insert.class}, message = "제목을 입력해주세요.")
    private String title; // 프로모션 제목

    @NotBlank(groups = {insert.class}, message = "URL을 입력해주세요.")
    private String url; // URL

    @NotNull(groups = {insert.class}, message = "코드 순서를 입력해주세요.")
    @Range(groups = {insert.class}, min = 1, max = 99, message = "1~99까지만 가능합니다.")
    private int ord;

    @NotNull(groups = {insert.class}, message = "프로모션 시작일을 입력해주세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date strtDt; // 프로모션 시작일

    @NotNull(groups = {insert.class}, message = "프로모션 종료일을 입력해주세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date endDt; // 프로모션 종료일

    private int viewCnt; // 조회수

    @NotBlank(groups = {insert.class}, message = "첨부파일을 등록해주세요.")
    private String atchFileId;

}
