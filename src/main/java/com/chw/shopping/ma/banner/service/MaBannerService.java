package com.chw.shopping.ma.banner.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MaBannerService {

    Page<MaBannerVO> selectList(MaBannerVO vo, Pageable page) throws Exception;

    MaBannerVO selectContents(MaBannerVO vo) throws Exception;

    void insertContents(MaBannerVO vo) throws Exception;

    void updateContents(MaBannerVO vo) throws Exception;

    void deleteContents(MaBannerVO vo) throws Exception;

}
