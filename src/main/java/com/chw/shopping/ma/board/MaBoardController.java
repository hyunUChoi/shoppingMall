package com.chw.shopping.ma.board;

import com.chw.shopping.ma.board.service.MaBoardService;
import com.chw.shopping.ma.board.service.MaBoardVO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
@RequestMapping(value = "/ma/{divn:notice|sysInq|faq}/")
public class MaBoardController {

    private final MaBoardService maBoardService;
    private final MessageSource messageSource;

    @RequestMapping(value = "list")
    public String list(@ModelAttribute("searchVO") MaBoardVO searchVO, @PathVariable String divn) {
        return "pages/ma/board/" + divn + "/list";
    }

}
