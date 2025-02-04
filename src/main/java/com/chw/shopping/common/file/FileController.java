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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/file/")
public class FileController {

    private final FileService fileService;

    @RequestMapping(value = "form")
    public String form(@ModelAttribute("searchVO") CommonVO searchVO, Model model) throws Exception {
        FileVO fileVO = new FileVO();
        fileVO.setAtchFileId(searchVO.getAtchFileId());
        model.addAttribute("fileList", fileService.selectList(fileVO));
        return "pages/common/fileUpload";
    }

    @ResponseBody
    @RequestMapping(value = "upload")
    public String upload(@RequestParam MultipartFile file) throws Exception {
        String atchFileId = "FILE"+ UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        String saveFileNm = atchFileId + "_" + now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String folderPath = now.getYear() + File.separator + now.getMonthValue()  + File.separator + now.getDayOfMonth() + File.separator;

        String path = "C:" + File.separator + "shopping" + File.separator + "attach" + File.separator + "upload" + File.separator;
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
        fileVO.setFileOrd(0);
        fileService.insertContents(fileVO);

        return atchFileId;
    }

    @RequestMapping("download")
    public void download(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        FileVO fileVO = fileService.selectContents(searchVO);
        File file = new File(fileVO.getFileSavePath(), fileVO.getSaveFileNm());
        file.delete();
        fileService.deleteContents(searchVO);
        return fileService.selectCount(searchVO);
    }

}
