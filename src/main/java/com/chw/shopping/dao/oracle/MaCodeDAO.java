package com.chw.shopping.dao.oracle;

import com.chw.shopping.ma.code.service.MaCodeVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MaCodeDAO {

    List<MaCodeVO> selectList(MaCodeVO vo);

    List<MaCodeVO> selectListByCode(MaCodeVO vo);

    int selectCount(MaCodeVO vo);

    int countByCode(MaCodeVO vo);

    MaCodeVO selectContents(MaCodeVO vo);

    void insertContents(MaCodeVO vo);

    void updateContents(MaCodeVO vo);

    void updateLowerContents(MaCodeVO vo);

    void deleteContents(MaCodeVO vo);

    List<MaCodeVO> codeMap(String code);

}
