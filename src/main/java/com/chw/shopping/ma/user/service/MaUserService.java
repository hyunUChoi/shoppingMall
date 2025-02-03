package com.chw.shopping.ma.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MaUserService {

    Page<MaUserVO> selectList(MaUserVO vo, Pageable page) throws Exception;

    int countByUserId(MaUserVO vo) throws Exception;

    MaUserVO selectContents(MaUserVO vo) throws Exception;

    void insertContents(MaUserVO vo) throws Exception;

    void updateContents(MaUserVO vo) throws Exception;

    void passwordUpdateContents(MaUserVO vo) throws Exception;

    void deleteContents(MaUserVO vo) throws Exception;
}
