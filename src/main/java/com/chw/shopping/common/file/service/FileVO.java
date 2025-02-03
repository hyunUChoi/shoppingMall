package com.chw.shopping.common.file.service;

import com.chw.shopping.common.domain.CommonVO;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@Getter
@Setter
@Alias("fileVO")
public class FileVO extends CommonVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String fileSavePath; // 파일저장경로
    private String orgFileNm; // 파일원본명
    private String saveFileNm; // 파일저장명
    private String fileType; // 파일타입
    private String fileSize; // 파일크기
    private String fileOrd; // 파일순번

}
