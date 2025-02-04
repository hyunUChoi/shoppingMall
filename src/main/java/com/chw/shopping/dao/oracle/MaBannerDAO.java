package com.chw.shopping.dao.oracle;

import com.chw.shopping.ma.banner.service.MaBannerVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MaBannerDAO {
    
    List<MaBannerVO> selectList(MaBannerVO vo);

    int selectCount(MaBannerVO vo);

    MaBannerVO selectContents(MaBannerVO vo);

    void insertContents(MaBannerVO vo);

    void updateContents(MaBannerVO vo);

    void deleteContents(MaBannerVO vo);

}
