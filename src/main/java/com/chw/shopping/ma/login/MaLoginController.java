package com.chw.shopping.ma.login;

import com.chw.shopping.ma.user.service.MaUserVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/ma/login/")
public class MaLoginController {

    @RequestMapping(value = "login")
    public String login(@ModelAttribute("searchVO") MaUserVO searchVO) {
        return "pages/common/login";
    }
}
