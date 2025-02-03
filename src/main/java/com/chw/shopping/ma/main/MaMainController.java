package com.chw.shopping.ma.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/ma/main/")
public class MaMainController {

    @RequestMapping(value = "main")
    public void main() {
        System.out.println("Hello World");
    }
}
