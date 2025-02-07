package com.chw.shopping.ma.event.service;

import com.chw.shopping.common.domain.CommonVO;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Alias("maEventVO")
public class MaEventVO extends CommonVO implements Serializable {

    public interface insert{}

    private static final long serialVersionUID = 1L;

    private int seq;

    @NotNull(groups = {insert.class}, message = "이벤트 시작일을 입력해주세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date strtDt; // 이벤트 시작일

    @NotNull(groups = {insert.class}, message = "이벤트 종료일을 입력해주세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDt; // 이벤트 종료일

    @NotBlank(groups = {insert.class}, message = "이벤트 발송 시간을 선택해주세요.")
    private String alertTime; // 이벤트 발송 시간

    @NotBlank(groups = {insert.class}, message = "이벤트 내용을 입력해주세요.")
    private String cont; // 이벤트 내용


}
