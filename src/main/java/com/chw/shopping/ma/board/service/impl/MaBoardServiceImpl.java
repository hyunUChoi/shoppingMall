package com.chw.shopping.ma.board.service.impl;

import com.chw.shopping.dao.oracle.MaBoardDAO;
import com.chw.shopping.ma.board.service.MaBoardService;
import com.chw.shopping.ma.board.service.MaBoardVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaBoardServiceImpl implements MaBoardService {

    private final MaBoardDAO maBoardDAO;

    @Transactional
    @Override
    public Page<MaBoardVO> selectList(MaBoardVO vo, Pageable page) throws Exception {
        vo.setFirstPageNo(page.getPageNumber() * page.getPageSize() + 1);
        vo.setLastPageNo(vo.getFirstPageNo() + page.getPageSize() - 1);

        List<MaBoardVO> resultList = maBoardDAO.selectList(vo);
        int total = maBoardDAO.selectCount(vo);
        return new PageImpl<>(resultList, page, total);
    }

    @Transactional
    @Override
    public MaBoardVO selectContents(MaBoardVO vo) throws Exception {
        return maBoardDAO.selectContents(vo);
    }

    @Transactional
    @Override
    public void insertContents(MaBoardVO vo) throws Exception {
        maBoardDAO.insertContents(vo);
    }

    @Transactional
    @Override
    public void updateContents(MaBoardVO vo) throws Exception {
        maBoardDAO.updateContents(vo);
    }

    @Transactional
    @Override
    public void updateAnswerContents(MaBoardVO vo) throws Exception {
        maBoardDAO.updateAnswerContents(vo);
    }

    @Transactional
    @Override
    public void deleteContents(MaBoardVO vo) throws Exception {
        maBoardDAO.deleteContents(vo);
    }

}
