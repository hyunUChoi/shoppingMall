package com.chw.shopping.ft.wishlist.service;

import com.chw.shopping.common.domain.CommonVO;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@Getter
@Setter
@Alias("ftWishlistVO")
public class FtWishlistVO extends CommonVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private int seq;
    private int prdSeq;
    private int amt;
    private String buyYn;

}
