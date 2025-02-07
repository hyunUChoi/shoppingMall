package com.chw.shopping.ma.event;

import com.chw.shopping.ma.event.service.MaEventService;
import com.chw.shopping.ma.event.service.MaEventVO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Controller
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
@RequestMapping(value = "/ma/event/")
public class MaEventController {

    private final MaEventService maEventService;
    private final MessageSource messageSource;

    @RequestMapping(value = "list")
    public String list(@ModelAttribute("searchVO") MaEventVO searchVO) {
        return "pages/ma/event/list";
    }

    @RequestMapping(value = "addList")
    public String addList(@ModelAttribute("searchVO") MaEventVO searchVO, Model model, @PageableDefault(value = 10) Pageable page) throws Exception {
        model.addAttribute("resultList", maEventService.selectList(searchVO, page));
        return "pages/ma/event/addList";
    }

    @RequestMapping(value = "{procType:insert|update}Form")
    public String form(@ModelAttribute("searchVO") MaEventVO searchVO, @PathVariable String procType, Model model) throws Exception {
        MaEventVO maEventVO = new MaEventVO();
        if(procType.equalsIgnoreCase("update")) {
            maEventVO = maEventService.selectContents(searchVO);
        }
        maEventVO.setProcType(procType);
        maEventVO.setSearchVO(searchVO);
        model.addAttribute("maEventVO", maEventVO);
        return "pages/ma/event/form";
    }

    @ResponseBody
    @PostMapping(value = "insertProc")
    public ResponseEntity<?> insertProc(@Validated(MaEventVO.insert.class) @ModelAttribute("searchVO") MaEventVO searchVO, BindingResult bindingResult) throws Exception {
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("return", "list");
        returnMap.put("message", messageSource.getMessage("insert", null, Locale.KOREA));
        maEventService.insertContents(searchVO);
        return ResponseEntity.ok(returnMap);
    }

    @ResponseBody
    @PutMapping(value = "updateProc")
    public ResponseEntity<?> updateProc(@Validated(MaEventVO.insert.class) @ModelAttribute("searchVO") MaEventVO searchVO, BindingResult bindingResult) throws Exception {
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("return", "view?seq" + searchVO.getSeq());
        returnMap.put("message", messageSource.getMessage("update", null, Locale.KOREA));
        maEventService.updateContents(searchVO);
        return ResponseEntity.ok(returnMap);
    }

    @ResponseBody
    @DeleteMapping(value = "deleteProc")
    public ResponseEntity<?> deleteProc(@ModelAttribute("searchVO") MaEventVO searchVO) throws Exception {
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("return", "list");
        returnMap.put("message", messageSource.getMessage("delete", null, Locale.KOREA));
        maEventService.deleteContents(searchVO);
        return ResponseEntity.ok(returnMap);
    }

    @RequestMapping(value = "view")
    public String view(@ModelAttribute("searchVO") MaEventVO searchVO, Model model) throws Exception {
        MaEventVO maEventVO = maEventService.selectContents(searchVO);
        maEventVO.setSearchVO(searchVO);
        model.addAttribute("maEventVO", maEventVO);
        return "pages/ma/event/view";
    }

}
