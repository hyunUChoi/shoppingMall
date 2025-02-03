package com.chw.shopping.common.file;

import com.chw.shopping.common.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/file/")
public class FileController {

    private final FileService fileService;


}
