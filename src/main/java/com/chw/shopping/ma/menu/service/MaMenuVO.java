package com.chw.shopping.ma.menu.service;

import com.chw.shopping.common.domain.CommonVO;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Alias("maMenuVO")
public class MaMenuVO extends CommonVO implements Serializable {

    public interface insert{}

    private static final long serialVersionUID = 1L;

    private int seq; // 일련번호

    @NotBlank(groups = {insert.class}, message = "메뉴 구분을 선택해주세요.")
    private String menuDivn; // 메뉴 구분

    @NotBlank(groups = {insert.class}, message = "메뉴명을 입력해주세요.")
    private String menuNm; // 메뉴명

    @NotBlank(groups = {insert.class}, message = "URL을 입력해주세요.")
    private String menuUrl; // 메뉴 URL

    @NotBlank(groups = {insert.class}, message = "메뉴코드를 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "영어 대/소문자, 숫자만 가능합니다.", groups = {insert.class})
    private String menuCd; // 메뉴 코드

    private int menuLevel;  // 메뉴 레벨
    private String upperCd; // 상위코드
    private String upperNm; // 상위메뉴명

    @NotNull(groups = {insert.class}, message = "메뉴순서를 입력해주세요.")
    @Range(min = 1, max = 99, message = "1~99까지만 가능합니다.", groups = {insert.class})
    private int menuOrd; // 메뉴 순서

    private String menuDscr; // 메뉴 설명

    private List<MaMenuVO> menuList; // 메뉴정보 리스트
    private MaMenuVO maMenuVO;
}
