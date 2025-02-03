package com.chw.shopping.ft.wishlist;

import com.chw.shopping.ft.wishlist.service.FtWishlistService;
import com.chw.shopping.ft.wishlist.service.FtWishlistVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/ft/wishlist/")
public class FtWishlistController {

    @Autowired
    private FtWishlistService ftWishlistService;

    @RequestMapping(value = "list")
    public String list(@ModelAttribute("searchVO") FtWishlistVO searchVO, Model model) throws Exception {
        int a = ftWishlistService.selectList(searchVO);
        System.out.println(a);
        return "pages/ft/wishlist/list";
    }

}
