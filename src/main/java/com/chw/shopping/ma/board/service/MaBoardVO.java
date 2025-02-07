package com.chw.shopping.ma.board.service;

import com.chw.shopping.common.domain.CommonVO;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@Getter
@Setter
@Alias("maBoardVO")
public class MaBoardVO extends CommonVO implements Serializable {

    public interface insert{}

    private static final long serialVersionUID = 1L;


}
