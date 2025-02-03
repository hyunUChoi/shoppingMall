package com.chw.shopping.ma.code.service;

import com.chw.shopping.common.domain.CommonVO;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@Alias("maCodeVO")
public class MaCodeVO extends CommonVO implements Serializable {

    public interface insert{}

    private static final long serialVersionUID = 1L;

    private int seq;

    @NotBlank(groups = {insert.class}, message = "코드를 입력해주세요.")
    private String code;

    @NotBlank(groups = {insert.class}, message = "코드명을 입력해주세요.")
    private String cdNm;

    private String upperCd;
    private String upperNm;

    @NotNull(groups = {insert.class}, message = "코드 순서를 입력해주세요.")
    @Range(groups = {insert.class}, min = 1, max = 99, message = "1~99까지만 가능합니다.")
    private int cdOrd;
    private int cdLevel;
    private String cdDscr;

}
