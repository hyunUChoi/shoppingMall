package com.chw.shopping.ma.code;

import com.chw.shopping.ma.code.service.MaCodeService;
import com.chw.shopping.ma.code.service.MaCodeVO;
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
@RequestMapping(value = "/ma/code/")
public class MaCodeController {

    private final MaCodeService maCodeService;
    private final MessageSource messageSource;

    @RequestMapping(value = "list")
    public String list(@ModelAttribute("searchVO") MaCodeVO searchVO, @PageableDefault(size = 10) Pageable page, Model model) throws Exception {
        model.addAttribute("resultList", maCodeService.selectList(searchVO, page));
        return "pages/ma/code/list";
    }

    @ResponseBody
    @RequestMapping(value = "countByCode")
    public ResponseEntity<MaCodeVO> countByMenuCd(@ModelAttribute("searchVO") MaCodeVO searchVO) throws Exception {
        HttpStatus status = maCodeService.countByCode(searchVO) > 0 ? HttpStatus.UNPROCESSABLE_ENTITY : HttpStatus.OK;
        return new ResponseEntity<>(searchVO, status);
    }

    @ResponseBody
    @GetMapping(value = "selectContents")
    public ResponseEntity<?> selectContents(@ModelAttribute("searchVO") MaCodeVO searchVO) throws Exception {
        MaCodeVO codeVO = maCodeService.selectContents(searchVO);
        return new ResponseEntity<>(codeVO, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping(value = "insertProc")
    public ResponseEntity<?> insertProc(@Validated(MaCodeVO.insert.class) @ModelAttribute("searchVO") MaCodeVO searchVO, BindingResult bindingResult) throws Exception {
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("return", "list");
        returnMap.put("message", messageSource.getMessage("insert", null, Locale.KOREA));
        maCodeService.insertContents(searchVO);
        return ResponseEntity.ok(returnMap);
    }

    @ResponseBody
    @PatchMapping(value = "updateProc")
    public ResponseEntity<?> updateProc(@Validated(MaCodeVO.insert.class) @ModelAttribute("searchVO") MaCodeVO searchVO,  BindingResult bindingResult) throws Exception {
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("return", "list");
        returnMap.put("message", messageSource.getMessage("update", null, Locale.KOREA));
        maCodeService.updateContents(searchVO);
        return ResponseEntity.ok(returnMap);
    }

    @ResponseBody
    @GetMapping(value = "selectCodeList")
    public ResponseEntity<?> selectCodeList(@ModelAttribute("searchVO") MaCodeVO searchVO) throws Exception {
        List<MaCodeVO> codeList = maCodeService.selectListByCode(searchVO);
        return ResponseEntity.ok(codeList);
    }

    @ResponseBody
    @DeleteMapping(value = "deleteProc")
    public ResponseEntity<?> deleteProc(@ModelAttribute("searchVO") MaCodeVO searchVO) throws Exception {
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("return", "list");
        returnMap.put("message", messageSource.getMessage("delete", null, Locale.KOREA));
        maCodeService.deleteContents(searchVO);
        return ResponseEntity.ok(returnMap);
    }

}
