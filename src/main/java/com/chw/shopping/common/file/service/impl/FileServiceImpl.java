package com.chw.shopping.common.file.service.impl;

import com.chw.shopping.common.file.service.FileService;
import com.chw.shopping.common.file.service.FileVO;
import com.chw.shopping.dao.oracle.FileDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileDAO fileDAO;

    @Transactional
    @Override
    public List<FileVO> selectList(FileVO vo) throws Exception {
        return fileDAO.selectList(vo);
    }

    @Transactional
    @Override
    public int selectCount(FileVO vo) throws Exception {
        return fileDAO.selectCount(vo);
    }

    @Transactional
    @Override
    public FileVO selectContents(FileVO vo) throws Exception {
        return fileDAO.selectContents(vo);
    }

    @Transactional
    @Override
    public void insertContents(FileVO vo) throws Exception {
        fileDAO.insertContents(vo);
    }

    @Transactional
    @Override
    public void deleteContents(FileVO vo) throws Exception {
        fileDAO.deleteContents(vo);
    }

}
