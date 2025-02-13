package com.chw.shopping.ft.main;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/ft/main/")
public class FtMainController {

    @RequestMapping(value = "main")
    public String ftMain() {
        return "pages/ft/main/main";
    }

}
