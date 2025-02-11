package com.chw.shopping.common.file;

import com.chw.shopping.common.domain.CommonVO;
import com.chw.shopping.common.file.service.FileService;
import com.chw.shopping.common.file.service.FileVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/file/")
public class FileController {

    private final FileService fileService;

    @RequestMapping(value = "singleFile")
    public String singleFile(@ModelAttribute("searchVO") CommonVO searchVO, Model model) throws Exception {
        FileVO fileVO = new FileVO();
        fileVO.setAtchFileId(searchVO.getAtchFileId());
        model.addAttribute("fileInfo", fileService.selectContents(fileVO));
        return "pages/common/file/singleFile";
    }

    @RequestMapping(value = "multiFile")
    public String multiFile(@ModelAttribute("searchVO") CommonVO searchVO, Model model) throws Exception {
        FileVO fileVO = new FileVO();
        fileVO.setAtchFileId(searchVO.getAtchFileId());
        model.addAttribute("fileList", fileService.selectList(fileVO));
        return "pages/common/file/multiFile";
    }

    @RequestMapping(value = "singleImg")
    public String singleImg(@ModelAttribute("searchVO") CommonVO searchVO, Model model) throws Exception {
        FileVO fileVO = new FileVO();
        fileVO.setAtchFileId(searchVO.getAtchFileId());
        FileVO fileInfo = fileService.selectContents(fileVO);

        String thumbnail = "";
        if(fileInfo != null) {
            String filePath = fileInfo.getFileSavePath() + fileInfo.getSaveFileNm();
            Path path = Paths.get(filePath);

            if (fileInfo.getFileType().startsWith("image/")) {
                byte[] fileBytes = Files.readAllBytes(path);
                String base64Image = Base64.getEncoder().encodeToString(fileBytes);
                thumbnail = "data:" + fileInfo.getFileType() + ";base64," + base64Image;
            }
        }

        model.addAttribute("fileInfo", fileInfo);
        model.addAttribute("thumbnail", thumbnail);
        return "pages/common/file/singleImg";
    }

    @RequestMapping(value = "multiImg")
    public String multiImg(@ModelAttribute("searchVO") CommonVO searchVO, Model model) throws Exception {
        FileVO fileVO = new FileVO();
        fileVO.setAtchFileId(searchVO.getAtchFileId());
        List<FileVO> fileList = fileService.selectList(fileVO);
        List<String> thumbnailList = new ArrayList<>();

        for (FileVO vo : fileList) {
            String filePath = vo.getFileSavePath() + vo.getSaveFileNm();
            Path path = Paths.get(filePath);

            if (vo.getFileType().startsWith("image/")) {
                byte[] fileBytes = Files.readAllBytes(path);
                String base64Image = Base64.getEncoder().encodeToString(fileBytes);
                thumbnailList.add("data:" + vo.getFileType() + ";base64," + base64Image);
            }
        }
        model.addAttribute("fileList", fileList);
        model.addAttribute("thumbnailList", thumbnailList);
        return "pages/common/file/multiImg";
    }

    @ResponseBody
    @RequestMapping(value = "upload")
    public String upload(@RequestParam MultipartFile[] files) throws Exception {
        String atchFileId = "FILE"+ UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        String folderPath = now.getYear() + File.separator + now.getMonthValue()  + File.separator + now.getDayOfMonth() + File.separator;
        String path = "C:" + File.separator + "shopping" + File.separator + "attach" + File.separator + "upload" + File.separator;

        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            String saveFileNm = atchFileId + "_" + now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + "_" + i;
            String filepath = path + folderPath + saveFileNm;
            File saveFolder = new File(filepath);

            if(!saveFolder.exists() || saveFolder.isFile()) {
                saveFolder.mkdirs();
            }
            file.transferTo(saveFolder);

            FileVO fileVO = new FileVO();
            fileVO.setAtchFileId(atchFileId);
            fileVO.setSaveFileNm(saveFileNm);
            fileVO.setOrgFileNm(file.getOriginalFilename());
            fileVO.setFileSavePath(path + folderPath);
            fileVO.setFileType(file.getContentType());
            fileVO.setFileSize(file.getSize());
            fileVO.setFileOrd(i);
            fileService.insertContents(fileVO);
        }
        return atchFileId;
    }

    @RequestMapping("download")
    public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String storePath = request.getParameter("fileStorePath") + request.getParameter("saveFileNm");
            File file = new File(storePath);
            FileInputStream in = new FileInputStream(storePath);

            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(request.getParameter("originalFileNm"), String.valueOf(StandardCharsets.UTF_8)).replaceAll("\\+", "%20"));
            OutputStream os = response.getOutputStream();

            try {
                int length;
                byte[] bytes = new byte[(int) file.length()];
                while ((length = in.read(bytes)) > 0) {
                    os.write(bytes, 0, length);
                }
                os.flush();
            } finally {
                os.close();
                in.close();
            }
        } catch(Exception e) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.println("<script type=\"text/javascript\">alert('파일을 찾을 수 없습니다.'); history.back();</script>");
            writer.flush();
            writer.close();
        }
    }

    @ResponseBody
    @DeleteMapping("delete")
    public int delete(@ModelAttribute("searchVO") FileVO searchVO) throws Exception {
        // 물리파일 제거
        //FileVO fileVO = fileService.selectContents(searchVO);
        //File file = new File(fileVO.getFileSavePath(), fileVO.getSaveFileNm());
        //file.delete();
        fileService.deleteContents(searchVO);
        return fileService.selectCount(searchVO);
    }

}
