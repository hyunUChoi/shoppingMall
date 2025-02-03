package com.chw.shopping.ma.user;

import com.chw.shopping.ma.code.service.MaCodeService;
import com.chw.shopping.ma.user.service.MaUserService;
import com.chw.shopping.ma.user.service.MaUserVO;
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
import java.util.Locale;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/ma/user/")
public class MaUserController {

    private final MaUserService maUserService;
    private final MaCodeService maCodeService;
    private final MessageSource messageSource;

    @RequestMapping(value = "list")
    public String list(@ModelAttribute("searchVO") MaUserVO searchVO, Model model) throws Exception {
        model.addAttribute("codeMap", maCodeService.codeMap("ROLE"));
        return "pages/ma/user/list";
    }

    @RequestMapping(value = "addList")
    public String addList(@ModelAttribute("searchVO") MaUserVO searchVO, Model model, @PageableDefault(value = 10) Pageable page) throws Exception {
        model.addAttribute("codeMap", maCodeService.codeMap("ROLE"));
        model.addAttribute("resultList", maUserService.selectList(searchVO, page));
        return "pages/ma/user/addList";
    }

    @RequestMapping(value = "{procType:insert|update}Form")
    public String form(@ModelAttribute("searchVO") MaUserVO searchVO, @PathVariable String procType, Model model) throws Exception {
        MaUserVO maUserVO = new MaUserVO();
        if(procType.equalsIgnoreCase("update")) {
            maUserVO = maUserService.selectContents(searchVO);
        }
        maUserVO.setProcType(procType);
        maUserVO.setSearchVO(searchVO);
        model.addAttribute("codeMap", maCodeService.codeMap("ROLE"));
        model.addAttribute("maUserVO", maUserVO);
        return "pages/ma/user/form";
    }

    @ResponseBody
    @GetMapping(value = "countByUserId")
    public ResponseEntity<?> countByUserId(@ModelAttribute("searchVO") MaUserVO searchVO) throws Exception {
        HttpStatus status = maUserService.countByUserId(searchVO) > 0 ? HttpStatus.UNPROCESSABLE_ENTITY : HttpStatus.OK;
        return new ResponseEntity<>(searchVO, status);
    }

    @ResponseBody
    @PostMapping(value = "insertProc")
    public ResponseEntity<?> insertProc(@Validated(MaUserVO.insert.class) @ModelAttribute("searchVO") MaUserVO searchVO, BindingResult bindingResult) throws Exception {
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("return", "list");
        returnMap.put("message", messageSource.getMessage("insert", null, Locale.KOREA));
        maUserService.insertContents(searchVO);
        return ResponseEntity.ok(returnMap);
    }

    @ResponseBody
    @PutMapping(value = "updateProc")
    public ResponseEntity<?> updateProc(@Validated(MaUserVO.update.class) @ModelAttribute("searchVO") MaUserVO searchVO, BindingResult bindingResult) throws Exception {
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("return", "view?seq" + searchVO.getSeq());
        returnMap.put("message", messageSource.getMessage("update", null, Locale.KOREA));
        maUserService.updateContents(searchVO);
        return ResponseEntity.ok(returnMap);
    }

    @ResponseBody
    @PutMapping(value = "passwordUpdateProc")
    public ResponseEntity<?> passwordUpdateProc(@Validated(MaUserVO.insert.class) @ModelAttribute("searchVO") MaUserVO searchVO, BindingResult bindingResult) throws Exception {
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("return", "view?seq" + searchVO.getSeq());
        returnMap.put("message", messageSource.getMessage("update", null, Locale.KOREA));
        maUserService.passwordUpdateContents(searchVO);
        return ResponseEntity.ok(returnMap);
    }

    @ResponseBody
    @DeleteMapping(value = "deleteProc")
    public ResponseEntity<?> deleteProc(@ModelAttribute("searchVO") MaUserVO searchVO) throws Exception {
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("return", "list");
        returnMap.put("message", messageSource.getMessage("delete", null, Locale.KOREA));
        maUserService.deleteContents(searchVO);
        return ResponseEntity.ok(returnMap);
    }

    @RequestMapping(value = "view")
    public String view(@ModelAttribute("searchVO") MaUserVO searchVO, Model model) throws Exception {
        MaUserVO maUserVO = maUserService.selectContents(searchVO);
        maUserVO.setSearchVO(searchVO);
        model.addAttribute("maUserVO", maUserVO);
        model.addAttribute("codeMap", maCodeService.codeMap("ROLE"));
        return "pages/ma/user/view";
    }

}
