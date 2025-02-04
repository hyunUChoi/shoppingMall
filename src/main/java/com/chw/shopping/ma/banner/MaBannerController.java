package com.chw.shopping.ma.banner;

import com.chw.shopping.ma.banner.service.MaBannerService;
import com.chw.shopping.ma.banner.service.MaBannerVO;
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
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/ma/banner/")
public class MaBannerController {

    private final MaBannerService maBannerService;
    private final MessageSource messageSource;

    @RequestMapping(value = "list")
    public String list(@ModelAttribute("searchVO") MaBannerVO searchVO) {
        return "pages/ma/banner/list";
    }

    @RequestMapping(value = "addList")
    public String addList(@ModelAttribute("searchVO") MaBannerVO searchVO, Model model, @PageableDefault(value = 10) Pageable page) throws Exception {
        model.addAttribute("resultList", maBannerService.selectList(searchVO, page));
        return "pages/ma/banner/addList";
    }

    @RequestMapping(value = "{procType:insert|update}Form")
    public String form(@ModelAttribute("searchVO") MaBannerVO searchVO, @PathVariable String procType, Model model) throws Exception {
        MaBannerVO maBannerVO = new MaBannerVO();
        if(procType.equalsIgnoreCase("update")) {
            maBannerVO = maBannerService.selectContents(searchVO);
        }
        maBannerVO.setProcType(procType);
        maBannerVO.setSearchVO(searchVO);
        model.addAttribute("maBannerVO", maBannerVO);
        return "pages/ma/banner/form";
    }

    @ResponseBody
    @PostMapping(value = "insertProc")
    public ResponseEntity<?> insertProc(@Validated(MaBannerVO.insert.class) @ModelAttribute("searchVO") MaBannerVO searchVO, BindingResult bindingResult) throws Exception {
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("return", "list");
        returnMap.put("message", messageSource.getMessage("insert", null, Locale.KOREA));
        maBannerService.insertContents(searchVO);
        return ResponseEntity.ok(returnMap);
    }

    @ResponseBody
    @PutMapping(value = "updateProc")
    public ResponseEntity<?> updateProc(@Validated(MaBannerVO.insert.class) @ModelAttribute("searchVO") MaBannerVO searchVO, BindingResult bindingResult) throws Exception {
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("return", "view?seq" + searchVO.getSeq());
        returnMap.put("message", messageSource.getMessage("update", null, Locale.KOREA));
        maBannerService.updateContents(searchVO);
        return ResponseEntity.ok(returnMap);
    }

    @ResponseBody
    @DeleteMapping(value = "deleteProc")
    public ResponseEntity<?> deleteProc(@ModelAttribute("searchVO") MaBannerVO searchVO) throws Exception {
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("return", "list");
        returnMap.put("message", messageSource.getMessage("delete", null, Locale.KOREA));
        maBannerService.deleteContents(searchVO);
        return ResponseEntity.ok(returnMap);
    }

    @RequestMapping(value = "view")
    public String view(@ModelAttribute("searchVO") MaBannerVO searchVO, Model model) throws Exception {
        MaBannerVO maBannerVO = maBannerService.selectContents(searchVO);
        maBannerVO.setSearchVO(searchVO);
        model.addAttribute("maBannerVO", maBannerVO);
        return "pages/ma/banner/view";
    }
    
}
