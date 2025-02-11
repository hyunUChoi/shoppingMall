package com.chw.shopping.dao.oracle;

import com.chw.shopping.ma.event.service.MaEventVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MaEventDAO {

    List<MaEventVO> selectList(MaEventVO vo);

    int selectCount(MaEventVO vo);

    MaEventVO selectContents(MaEventVO vo);

    void insertContents(MaEventVO vo);

    void updateContents(MaEventVO vo);

    void deleteContents(MaEventVO vo);

    List<MaEventVO> selectAllList();

}
