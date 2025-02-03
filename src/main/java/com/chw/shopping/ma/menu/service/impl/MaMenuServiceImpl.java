package com.chw.shopping.ma.menu.service.impl;

import com.chw.shopping.dao.oracle.MaMenuDAO;
import com.chw.shopping.ma.menu.service.MaMenuService;
import com.chw.shopping.ma.menu.service.MaMenuVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaMenuServiceImpl implements MaMenuService {

    private final MaMenuDAO maMenuDAO;

    @Transactional(readOnly = true)
    @Override
    public Page<MaMenuVO> selectList(MaMenuVO vo, Pageable page) throws Exception {
        vo.setFirstPageNo(page.getPageNumber() * page.getPageSize() + 1);
        vo.setLastPageNo(vo.getFirstPageNo() + page.getPageSize() - 1);

        List<MaMenuVO> resultList = maMenuDAO.selectList(vo);
        int total = maMenuDAO.selectCount(vo);
        return new PageImpl<>(resultList, page, total);
    }

    @Transactional
    @Override
    public List<MaMenuVO> selectListByMenuCd(MaMenuVO vo) {
        return maMenuDAO.selectListByMenuCd(vo);
    }

    @Transactional(readOnly = true)
    @Override
    public int countByMenuCd(MaMenuVO vo) {
        return maMenuDAO.countByMenuCd(vo);
    }

    @Transactional(readOnly = true)
    @Override
    public MaMenuVO selectContents(MaMenuVO vo) {
        return maMenuDAO.selectContents(vo);
    }

    @Transactional
    @Override
    public void insertContents(MaMenuVO vo) {
        maMenuDAO.insertContents(vo);
    }

    @Transactional
    @Override
    public void updateContents(MaMenuVO vo) {
        MaMenuVO menuVO = maMenuDAO.selectContents(vo);
        maMenuDAO.updateContents(vo);
        vo.setUpperCd(menuVO.getMenuCd());
        maMenuDAO.updateLowerContents(vo);
    }

    @Transactional
    @Override
    public void deleteContents(MaMenuVO vo) {
        maMenuDAO.deleteContents(vo);
    }
}
