package com.chw.shopping.common.file.service;

import java.util.List;

public interface FileService {

    List<FileVO> selectList(FileVO vo) throws Exception;

    int selectCount(FileVO vo) throws Exception;

    FileVO selectContents(FileVO vo) throws Exception;

    void insertContents(FileVO vo) throws Exception;

    void deleteContents(FileVO vo) throws Exception;

}
