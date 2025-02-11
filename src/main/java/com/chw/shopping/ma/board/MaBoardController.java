package com.chw.shopping.ma.board;

import com.chw.shopping.ma.board.service.MaBoardService;
import com.chw.shopping.ma.board.service.MaBoardVO;
import com.chw.shopping.ma.code.service.MaCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Controller
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
@RequestMapping(value = "/ma/board/{divn:notice|sysInq|faq}/")
public class MaBoardController {

    private final MaBoardService maBoardService;
    private final MaCodeService maCodeService;
    private final MessageSource messageSource;
    private final SmartValidator validator;

    @RequestMapping(value = "list")
    public String list(@ModelAttribute("searchVO") MaBoardVO searchVO, @PathVariable String divn, Model model) throws Exception {
        if(divn.equalsIgnoreCase("sysInq")) {
            model.addAttribute("codeMap", maCodeService.codeMap("INQ"));
        }
        return "pages/ma/board/" + divn + "/list";
    }

    @RequestMapping(value = "addList")
    public String addList(@ModelAttribute("searchVO") MaBoardVO searchVO, Model model, @PathVariable String divn, @PageableDefault(value = 10) Pageable page) throws Exception {
        searchVO.setBrdDivn(divn);
        model.addAttribute("resultList", maBoardService.selectList(searchVO, page));
        if(divn.equalsIgnoreCase("sysInq")) {
            model.addAttribute("codeMap", maCodeService.codeMap("INQ"));
        }
        return "pages/ma/board/" + divn + "/addList";
    }

    @RequestMapping(value = "{procType:insert|update}Form")
    public String form(@ModelAttribute("searchVO") MaBoardVO searchVO, @PathVariable String divn, @PathVariable String procType, Model model) throws Exception {
        MaBoardVO maBoardVO = new MaBoardVO();
        maBoardVO.setBrdDivn(divn);
        if(procType.equalsIgnoreCase("update")) {
            maBoardVO = maBoardService.selectContents(searchVO);
        }
        maBoardVO.setProcType(procType);
        maBoardVO.setSearchVO(searchVO);
        model.addAttribute("maBoardVO", maBoardVO);
        if(divn.equalsIgnoreCase("sysInq")) {
            model.addAttribute("codeMap", maCodeService.codeMap("INQ"));
        }
        return "pages/ma/board/" + divn + "/form";
    }

    @ResponseBody
    @PostMapping(value = "insertProc")
    public ResponseEntity<?> boardInsertProc(@ModelAttribute("searchVO") MaBoardVO searchVO, BindingResult bindingResult, @PathVariable String divn) throws Exception {
        ResponseEntity<?> errorBinding = errorBinding(bindingResult, searchVO, divn);
        if(errorBinding != null) {
            return errorBinding;
        }

        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("return", "list");
        returnMap.put("message", messageSource.getMessage("insert", null, Locale.KOREA));
        maBoardService.insertContents(searchVO);
        return ResponseEntity.ok(returnMap);
    }

    @ResponseBody
    @PatchMapping(value = "updateProc")
    public ResponseEntity<?> boardUpdateProc(@ModelAttribute("searchVO") MaBoardVO searchVO, BindingResult bindingResult, @PathVariable String divn) throws Exception {
        ResponseEntity<?> errorBinding = errorBinding(bindingResult, searchVO, divn);
        if(errorBinding != null) {
            return errorBinding;
        }

        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("return", "view?seq=" + searchVO.getSeq());
        returnMap.put("message", messageSource.getMessage("update", null, Locale.KOREA));
        maBoardService.updateContents(searchVO);
        return ResponseEntity.ok(returnMap);
    }

    @ResponseBody
    @PatchMapping(value = "inqUpdateProc")
    public ResponseEntity<?> updateProc(@Validated(MaBoardVO.inqAnswer.class) @ModelAttribute("searchVO") MaBoardVO searchVO, BindingResult bindingResult, @PathVariable String divn) throws Exception {
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("return", "view?seq=" + searchVO.getSeq());
        returnMap.put("message", messageSource.getMessage("update", null, Locale.KOREA));
        maBoardService.updateAnswerContents(searchVO);
        return ResponseEntity.ok(returnMap);
    }

    @ResponseBody
    @DeleteMapping(value = "deleteProc")
    public ResponseEntity<?> deleteProc(@ModelAttribute("searchVO") MaBoardVO searchVO, @PathVariable String divn) throws Exception {
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("return", "list");
        returnMap.put("message", messageSource.getMessage("delete", null, Locale.KOREA));
        maBoardService.deleteContents(searchVO);
        return ResponseEntity.ok(returnMap);
    }

    @RequestMapping(value = "view")
    public String view(@ModelAttribute("searchVO") MaBoardVO searchVO, @PathVariable String divn, Model model) throws Exception {
        MaBoardVO maBoardVO = maBoardService.selectContents(searchVO);
        maBoardVO.setSearchVO(searchVO);
        model.addAttribute("maBoardVO", maBoardVO);
        if(divn.equalsIgnoreCase("sysInq")) {
            model.addAttribute("codeMap", maCodeService.codeMap("INQ"));
        }
        return "pages/ma/board/" + divn + "/view";
    }

    private ResponseEntity<?> errorBinding(BindingResult bindingResult, MaBoardVO searchVO, String divn) {
        switch (divn) {
            case "notice":
                validator.validate(searchVO, bindingResult, MaBoardVO.notice.class);
                break;
            case "sysInq":
                validator.validate(searchVO, bindingResult, MaBoardVO.inq.class);
                break;
            case "faq":
                validator.validate(searchVO, bindingResult, MaBoardVO.faq.class);
                break;
        }

        if(bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), error.getDefaultMessage());
            });
            return ResponseEntity.unprocessableEntity().body(errors);
        }
        return null;
    }

}
