package com.chw.shopping.dao.mysql;

import com.chw.shopping.ft.wishlist.service.FtWishlistVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FtWishlistDAO {

    int selectList(FtWishlistVO ftWishlistVO);
}
