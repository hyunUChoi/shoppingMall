package com.chw.shopping.ma.menu.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MaMenuService {

    Page<MaMenuVO> selectList(MaMenuVO vo, Pageable page) throws Exception;

    List<MaMenuVO> selectListByMenuCd(MaMenuVO vo) throws Exception;

    int countByMenuCd(MaMenuVO vo) throws Exception;

    MaMenuVO selectContents(MaMenuVO vo) throws Exception;

    void insertContents(MaMenuVO vo) throws Exception;

    void updateContents(MaMenuVO vo) throws Exception;

    void deleteContents(MaMenuVO vo) throws Exception;
}
