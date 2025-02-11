package com.chw.shopping.ma.event.service.impl;

import com.chw.shopping.dao.oracle.MaEventDAO;
import com.chw.shopping.ma.event.service.MaEventService;
import com.chw.shopping.ma.event.service.MaEventVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaEventServiceImpl implements MaEventService {

    private final MaEventDAO maEventDAO;

    @Transactional
    @Override
    public Page<MaEventVO> selectList(MaEventVO vo, Pageable page) {
        vo.setFirstPageNo(page.getPageNumber() * page.getPageSize() + 1);
        vo.setLastPageNo(vo.getFirstPageNo() + page.getPageSize() - 1);

        List<MaEventVO> resultList = maEventDAO.selectList(vo);
        int total = maEventDAO.selectCount(vo);
        return new PageImpl<>(resultList, page, total);
    }

    @Transactional
    @Override
    public MaEventVO selectContents(MaEventVO vo) throws Exception {
        return maEventDAO.selectContents(vo);
    }

    @Transactional
    @Override
    public void insertContents(MaEventVO vo) throws Exception {
        maEventDAO.insertContents(vo);
    }

    @Transactional
    @Override
    public void updateContents(MaEventVO vo) throws Exception {
        maEventDAO.updateContents(vo);
    }

    @Transactional
    @Override
    public void deleteContents(MaEventVO vo) throws Exception {
        maEventDAO.deleteContents(vo);
    }

    @Override
    public List<MaEventVO> selectAllList() {
        return maEventDAO.selectAllList();
    }

}
