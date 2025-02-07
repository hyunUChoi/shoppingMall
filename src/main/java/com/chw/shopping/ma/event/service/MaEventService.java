package com.chw.shopping.ma.event.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MaEventService {

    Page<MaEventVO> selectList(MaEventVO vo, Pageable page) throws Exception;

    MaEventVO selectContents(MaEventVO vo) throws Exception;

    void insertContents(MaEventVO vo) throws Exception;

    void updateContents(MaEventVO vo) throws Exception;

    void deleteContents(MaEventVO vo) throws Exception;

}
