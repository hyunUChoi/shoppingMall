package com.chw.shopping.ma.banner.service.impl;

import com.chw.shopping.dao.oracle.MaBannerDAO;
import com.chw.shopping.ma.banner.service.MaBannerService;
import com.chw.shopping.ma.banner.service.MaBannerVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaBannerServiceImpl implements MaBannerService {

    private final MaBannerDAO maBannerDAO;

    @Transactional(readOnly = true)
    @Override
    public Page<MaBannerVO> selectList(MaBannerVO vo, Pageable page) throws Exception {
        vo.setFirstPageNo(page.getPageNumber() * page.getPageSize() + 1);
        vo.setLastPageNo(vo.getFirstPageNo() + page.getPageSize() - 1);

        List<MaBannerVO> resultList = maBannerDAO.selectList(vo);
        int total = maBannerDAO.selectCount(vo);
        return new PageImpl<>(resultList, page, total);
    }

    @Transactional(readOnly = true)
    @Override
    public MaBannerVO selectContents(MaBannerVO vo) throws Exception {
        return maBannerDAO.selectContents(vo);
    }

    @Transactional
    @Override
    public void insertContents(MaBannerVO vo) throws Exception {
        maBannerDAO.insertContents(vo);
    }

    @Transactional
    @Override
    public void updateContents(MaBannerVO vo) throws Exception {
        maBannerDAO.updateContents(vo);
    }

    @Transactional
    @Override
    public void deleteContents(MaBannerVO vo) throws Exception {
        maBannerDAO.deleteContents(vo);
    }

}
