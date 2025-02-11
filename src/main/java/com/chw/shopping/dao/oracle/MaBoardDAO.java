package com.chw.shopping.dao.oracle;

import com.chw.shopping.ma.board.service.MaBoardVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MaBoardDAO {

    List<MaBoardVO> selectList(MaBoardVO vo);

    int selectCount(MaBoardVO vo);

    MaBoardVO selectContents(MaBoardVO vo);

    void insertContents(MaBoardVO vo);

    void updateContents(MaBoardVO vo);

    void updateAnswerContents(MaBoardVO vo);

    void deleteContents(MaBoardVO vo);

}
