package com.chw.shopping.ma.board.service.impl;

import com.chw.shopping.dao.oracle.MaBoardDAO;
import com.chw.shopping.ma.board.service.MaBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaBoardServiceImpl implements MaBoardService {

    private final MaBoardDAO maBoardDAO;

}
