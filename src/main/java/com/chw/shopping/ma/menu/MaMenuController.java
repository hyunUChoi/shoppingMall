package com.chw.shopping.ma.menu;

import com.chw.shopping.ma.code.service.MaCodeService;
import com.chw.shopping.ma.menu.service.MaMenuService;
import com.chw.shopping.ma.menu.service.MaMenuVO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/ma/menu/")
public class MaMenuController {

    private final MaMenuService maMenuService;
    private final MaCodeService maCodeService;
    private final MessageSource messageSource;

    @RequestMapping(value = "list")
    public String list(@ModelAttribute("searchVO") MaMenuVO searchVO, Model model, @PageableDefault(value = 10) Pageable page) throws Exception {
        model.addAttribute("resultList", maMenuService.selectList(searchVO, page));
        model.addAttribute("codeMap", maCodeService.codeMap("MENU"));
        return "pages/ma/menu/list";
    }

    @ResponseBody
    @GetMapping(value = "selectContents")
    public ResponseEntity<?> selectContents(@ModelAttribute("searchVO") MaMenuVO searchVO) throws Exception {
        MaMenuVO menuVO = maMenuService.selectContents(searchVO);
        return new ResponseEntity<>(menuVO, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "countByMenuCd")
    public ResponseEntity<MaMenuVO> countByMenuCd(@ModelAttribute("searchVO") MaMenuVO searchVO) throws Exception {
        HttpStatus status = maMenuService.countByMenuCd(searchVO) > 0 ? HttpStatus.UNPROCESSABLE_ENTITY : HttpStatus.OK;
        return new ResponseEntity<>(searchVO, status);
    }

    @ResponseBody
    @PostMapping(value = "insertProc")
    public ResponseEntity<?> insertProc(@Validated(MaMenuVO.insert.class) @ModelAttribute("searchVO") MaMenuVO searchVO, BindingResult bindingResult) throws Exception {
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("return", "list");
        returnMap.put("message", messageSource.getMessage("insert", null, Locale.KOREA));
        maMenuService.insertContents(searchVO);
        return ResponseEntity.ok(returnMap);
    }

    @ResponseBody
    @PutMapping(value = "updateProc")
    public ResponseEntity<?> updateProc(@Validated(MaMenuVO.insert.class) @ModelAttribute("searchVO") MaMenuVO searchVO, BindingResult bindingResult) throws Exception {
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("return", "list");
        returnMap.put("message", messageSource.getMessage("update", null, Locale.KOREA));
        maMenuService.updateContents(searchVO);
        return ResponseEntity.ok(returnMap);
    }

    @ResponseBody
    @GetMapping(value = "selectCodeList")
    public ResponseEntity<?> selectCodeList(@ModelAttribute("searchVO") MaMenuVO searchVO) throws Exception {
        List<MaMenuVO> codeList = maMenuService.selectListByMenuCd(searchVO);
        return ResponseEntity.ok(codeList);
    }

    @ResponseBody
    @DeleteMapping(value = "deleteProc")
    public ResponseEntity<?> deleteProc(@ModelAttribute("searchVO") MaMenuVO searchVO) throws Exception {
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("return", "list");
        returnMap.put("message", messageSource.getMessage("delete", null, Locale.KOREA));
        maMenuService.deleteContents(searchVO);
        return ResponseEntity.ok(returnMap);
    }

}
