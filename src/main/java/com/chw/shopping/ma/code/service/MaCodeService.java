package com.chw.shopping.ma.code.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedHashMap;
import java.util.List;

public interface MaCodeService {

    Page<MaCodeVO> selectList(MaCodeVO vo, Pageable page) throws Exception;

    List<MaCodeVO> selectListByCode(MaCodeVO vo) throws Exception;

    int countByCode(MaCodeVO vo) throws Exception;

    MaCodeVO selectContents(MaCodeVO vo) throws Exception;

    void insertContents(MaCodeVO vo) throws Exception;

    void updateContents(MaCodeVO vo) throws Exception;

    void deleteContents(MaCodeVO vo) throws Exception;

    LinkedHashMap<String, String> codeMap(String code) throws Exception;

}
