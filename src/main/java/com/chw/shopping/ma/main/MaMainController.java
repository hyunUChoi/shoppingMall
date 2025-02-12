package com.chw.shopping.ma.main;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/ma/main/")
public class MaMainController {

    @RequestMapping(value = "main")
    public String maMain() {
        return "pages/ma/main/main";
    }
}
