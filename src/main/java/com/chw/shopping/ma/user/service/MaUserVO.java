package com.chw.shopping.ma.user.service;

import com.chw.shopping.common.domain.CommonVO;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@Alias("maUserVO")
public class MaUserVO extends CommonVO implements Serializable {

    public interface insert{}
    public interface update{}

    private static final long serialVersionUID = 1L;

    private int seq;

    @NotBlank(groups = {insert.class, update.class}, message = "아이디를 입력해주세요.")
    @Pattern(groups = {insert.class, update.class}, regexp = "^[a-zA-Z0-9]*$", message = "영어 대/소문자, 숫자만 가능합니다.")
    private String userId;  // 아이디

    @NotBlank(groups = {insert.class}, message = "비밀번호를 입력해주세요.")
    @Pattern(groups = {insert.class}, regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[~!@#$%^&*()+|=])[a-zA-Z0-9~!@#$%^&*()+|=]+$", message = "영문 소문자, 숫자, 특수문자를 사용하세요.")
    private String userPw;  // 비밀번호

    private String userDi;  // DI

    @NotBlank(groups = {insert.class, update.class}, message = "이름을 입력해주세요.")
    private String userNm;  // 이름

    @NotBlank(groups = {insert.class, update.class}, message = "EMAIL 입력해주세요.")
    @Pattern(groups = {insert.class, update.class}, regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "EMAIL 형식을 확인해주세요.")
    private String userEmail;   // email

    @NotBlank(groups = {insert.class, update.class}, message = "휴대전화 번호를 입력해주세요.")
    @Size(groups = {insert.class, update.class}, min = 11, max = 11, message = "휴대전화 번호를 확인해주세요.")
    private String userTel; // 전화번호

    @NotBlank(groups = {insert.class, update.class}, message = "우편번호를 입력해주세요.")
    private String userPost;    // 우편번호

    @NotBlank(groups = {insert.class, update.class}, message = "주소를 입력해주세요.")
    private String userAddr;    // 주소

    @NotBlank(groups = {insert.class, update.class}, message = "상세주소를 입력해주세요.")
    private String userAddrDetail;  // 상세주소

    @NotBlank(groups = {insert.class, update.class}, message = "마케팅 수신여부를 선택해주세요.")
    private String mrkYn;   // 마케팅 수신여부

    @NotBlank(groups = {insert.class, update.class}, message = "권한을 선택해주세요.")
    private String auth;    // 권한

}
