package com.chw.shopping.ft.wishlist.service.impl;

import com.chw.shopping.dao.mysql.FtWishlistDAO;
import com.chw.shopping.ft.wishlist.service.FtWishlistService;
import com.chw.shopping.ft.wishlist.service.FtWishlistVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FtWishlistServiceImpl implements FtWishlistService {

    @Autowired
    private FtWishlistDAO frWishlistDAO;

    @Override
    public int selectList(FtWishlistVO ftWishlistVO) throws Exception {
        return frWishlistDAO.selectList(ftWishlistVO);
    }
}
