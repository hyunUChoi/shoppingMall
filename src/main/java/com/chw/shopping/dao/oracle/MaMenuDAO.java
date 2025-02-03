package com.chw.shopping.dao.oracle;

import com.chw.shopping.ma.menu.service.MaMenuVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MaMenuDAO {

    List<MaMenuVO> selectList(MaMenuVO vo);

    List<MaMenuVO> selectListByMenuCd(MaMenuVO vo);

    int selectCount(MaMenuVO vo);

    int countByMenuCd(MaMenuVO vo);

    MaMenuVO selectContents(MaMenuVO vo);

    void insertContents(MaMenuVO vo);

    void updateContents(MaMenuVO vo);

    void updateLowerContents(MaMenuVO vo);

    void deleteContents(MaMenuVO vo);

    List<MaMenuVO> selectMenuList(MaMenuVO vo);

}
