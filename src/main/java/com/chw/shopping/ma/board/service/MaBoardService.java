package com.chw.shopping.ma.board.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MaBoardService {

    Page<MaBoardVO> selectList(MaBoardVO vo, Pageable page) throws Exception;

    MaBoardVO selectContents(MaBoardVO vo) throws Exception;

    void insertContents(MaBoardVO vo) throws Exception;

    void updateContents(MaBoardVO vo) throws Exception;

    void updateAnswerContents(MaBoardVO vo) throws Exception;

    void deleteContents(MaBoardVO vo) throws Exception;

}
