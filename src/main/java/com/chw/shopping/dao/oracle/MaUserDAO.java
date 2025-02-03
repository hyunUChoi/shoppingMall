package com.chw.shopping.dao.oracle;

import com.chw.shopping.ma.user.service.MaUserVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MaUserDAO {

    List<MaUserVO> selectList(MaUserVO vo);

    int selectCount(MaUserVO vo);

    int countByUserId(MaUserVO vo);

    MaUserVO selectContents(MaUserVO vo);

    void insertContents(MaUserVO vo);

    void updateContents(MaUserVO vo);

    void passwordUpdateContents(MaUserVO vo);

    void deleteContents(MaUserVO vo);

    MaUserVO selectByUserId(MaUserVO vo);
}
