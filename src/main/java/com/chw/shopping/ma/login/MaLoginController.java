package com.chw.shopping.ma.login;

import com.chw.shopping.common.mail.MailService;
import com.chw.shopping.ma.user.service.MaUserService;
import com.chw.shopping.ma.user.service.MaUserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/ma/login/")
public class MaLoginController {

    private final MaUserService userService;
    private final MailService mailService;

    @RequestMapping(value = "login")
    public String login(@ModelAttribute("searchVO") MaUserVO searchVO) {
        return "pages/common/login/login";
    }

    @RequestMapping(value = "checkId")
    public String checkId(@ModelAttribute("searchVO") MaUserVO searchVO, @ModelAttribute("error") String error, Model model) {
        if(error != null) {
            model.addAttribute("error", error);
        }
        return "pages/common/login/checkId";
    }

    @RequestMapping(value = "verify")
    public String verify(@ModelAttribute("searchVO") MaUserVO searchVO, RedirectAttributes redirectAttributes, Model model) throws Exception {
        MaUserVO maUserVO = userService.selectByUserId(searchVO.getUserId());

        if(maUserVO == null || !maUserVO.getAuth().equalsIgnoreCase("ADMIN")) {
            redirectAttributes.addFlashAttribute("error", "입력하신 아이디를 찾을 수 없습니다.");
            return "redirect:/ma/login/checkId";
        } else {
            model.addAttribute("maUserVO", maUserVO);
            return "pages/common/login/verify";
        }
    }

    @ResponseBody
    @GetMapping(value = "sendCode")
    public ResponseEntity<?> sendCode(@ModelAttribute("searchVO") MaUserVO searchVO) {
        Map<String, String> returnMap = new HashMap<>();

        // 인증번호
        StringBuilder certNum = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            certNum.append(random.nextInt(10));
        }
        returnMap.put("certNum", certNum.toString());

        // 메일발송
        try {
            mailService.sendEmail(searchVO.getUserEmail(), "[1.단 담아봐] 비밀번호 변경 인증번호 발송", certNum.toString());
            return ResponseEntity.ok().body(returnMap);

        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(returnMap);
        }
    }

    @RequestMapping(value = "newPw")
    public String newPw(@ModelAttribute("searchVO") MaUserVO searchVO, RedirectAttributes redirectAttributes) {
        if(searchVO.getUserId() == null) {
            redirectAttributes.addFlashAttribute("error", "비정상적인 접근입니다.");
            return "redirect:/ma/login/checkId";
        }
        return "pages/common/login/newPw";
    }

    @ResponseBody
    @PatchMapping(value = "changePassword")
    public ResponseEntity<?> changePassword(@Validated(MaUserVO.changePassword.class) @ModelAttribute("searchVO") MaUserVO searchVO, BindingResult bindingResult) throws Exception {
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("return", "login");
        returnMap.put("message", "비밀번호가 변경되었습니다.");
        userService.changePassword(searchVO);
        return ResponseEntity.ok(returnMap);
    }

}
