package com.chw.shopping.dao.oracle;

import com.chw.shopping.common.file.service.FileVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileDAO {

    List<FileVO> selectList(FileVO vo);

    int selectCount(FileVO vo);

    FileVO selectContents(FileVO vo);

    void insertContents(FileVO vo);

    void deleteContents(FileVO vo);
}
