package com.chw.shopping.biz.login;

import com.chw.shopping.common.mail.MailService;
import com.chw.shopping.ma.user.service.MaUserService;
import com.chw.shopping.ma.user.service.MaUserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/biz/login/")
public class BizLoginController {

    private final MaUserService userService;
    private final MailService mailService;

    @RequestMapping(value = "login")
    public String login(@ModelAttribute("searchVO") MaUserVO searchVO) {
        return "pages/common/login/login";
    }

}
