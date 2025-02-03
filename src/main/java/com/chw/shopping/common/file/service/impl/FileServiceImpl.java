package com.chw.shopping.common.file.service.impl;

import com.chw.shopping.common.file.service.FileService;
import com.chw.shopping.dao.oracle.FileDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileDAO fileDAO;

}
