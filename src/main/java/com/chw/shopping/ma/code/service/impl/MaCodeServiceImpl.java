package com.chw.shopping.ma.code.service.impl;

import com.chw.shopping.dao.oracle.MaCodeDAO;
import com.chw.shopping.ma.code.service.MaCodeService;
import com.chw.shopping.ma.code.service.MaCodeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MaCodeServiceImpl implements MaCodeService {

    private final MaCodeDAO maCodeDAO;

    @Transactional
    @Override
    public Page<MaCodeVO> selectList(MaCodeVO vo, Pageable page) throws Exception {
        vo.setFirstPageNo(page.getPageNumber() * page.getPageSize() + 1);
        vo.setLastPageNo(vo.getFirstPageNo() + page.getPageSize() - 1);

        List<MaCodeVO> resultList = maCodeDAO.selectList(vo);
        int total = maCodeDAO.selectCount(vo);
        return new PageImpl<>(resultList, page, total);
    }

    @Transactional
    @Override
    public List<MaCodeVO> selectListByCode(MaCodeVO vo) throws Exception {
        return maCodeDAO.selectListByCode(vo);
    }

    @Transactional
    @Override
    public int countByCode(MaCodeVO vo) {
        return maCodeDAO.countByCode(vo);
    }

    @Transactional
    @Override
    public MaCodeVO selectContents(MaCodeVO vo) throws Exception {
        return maCodeDAO.selectContents(vo);
    }

    @Transactional
    @Override
    public void insertContents(MaCodeVO vo) throws Exception {
        maCodeDAO.insertContents(vo);
    }

    @Transactional
    @Override
    public void updateContents(MaCodeVO vo) throws Exception {
        MaCodeVO codeVO = maCodeDAO.selectContents(vo);
        maCodeDAO.updateContents(vo);
        vo.setUpperCd(codeVO.getCode());
        maCodeDAO.updateLowerContents(vo);
    }

    @Transactional
    @Override
    public void deleteContents(MaCodeVO vo) throws Exception {
        maCodeDAO.deleteContents(vo);
    }

    @Transactional
    @Override
    public LinkedHashMap<String, String> codeMap(String code) {
        List<MaCodeVO> codeList = maCodeDAO.codeMap(code);
        LinkedHashMap<String, String> map = new LinkedHashMap<>();

        for(MaCodeVO vo : codeList) {
            map.put(vo.getCode(), vo.getCdNm());
        }

        return map;
    }

}
