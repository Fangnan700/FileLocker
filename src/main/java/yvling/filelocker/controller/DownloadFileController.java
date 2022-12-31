package yvling.filelocker.controller;

import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yvling.filelocker.entity.GlobalInfo;
import yvling.filelocker.entity.UploadFile;
import yvling.filelocker.service.GlobalInfoService;
import yvling.filelocker.service.UploadFileService;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

@RestController
public class DownloadFileController {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private GlobalInfoService globalInfoService;

    @Autowired
    private UploadFileService uploadFileService;


    @RequestMapping(value = "/query_download", method = RequestMethod.POST)
    public JSONObject queryDownload(@RequestParam("pickup_code") String pickup_code) {
        JSONObject response_json = new JSONObject();

        GlobalInfo download_file_info = globalInfoService.getFileInfoByPickupCode(pickup_code);

        if(download_file_info != null) {
            String file_code = download_file_info.getFile_code();
            UploadFile uploadFile = uploadFileService.getFileByCode(file_code);
            Long file_size = uploadFile.getFile_size();
            String size;
            if(file_size < 1024) {
                size = file_size + "bit";
            } else if(file_size < 1048576) {
                size = (file_size / 1024) + "K";
            } else if(file_size < 1073741824) {
                size = (file_size / 1048576) + "M";
            } else {
                size = (file_size / 1073741824) + "G";
            }

            response_json.put("file_code", uploadFile.getFile_code());
            response_json.put("file_name", uploadFile.getFile_name());
            response_json.put("file_type", uploadFile.getFile_type());
            response_json.put("file_size", size);
            response_json.put("save_time", uploadFile.getSave_time() + "天");
            response_json.put("upload_time", dateFormat.format(new Date(uploadFile.getUpload_time())));
            response_json.put("delete_time", dateFormat.format(new Date(uploadFile.getDelete_time())));
            response_json.put("status", 1);
        } else {
            response_json.put("status", -1);
        }
        return response_json;
    }


    @GetMapping(value = "/download")
    public void downloadFile(@RequestParam("pickup_code") String pickup_code, HttpServletResponse response) throws Exception {
        GlobalInfo download_file_info = globalInfoService.getFileInfoByPickupCode(pickup_code);

        if(download_file_info != null) {
            String download_file_code = download_file_info.getFile_code();
            String download_file_name = new String(Base64.getDecoder().decode(download_file_code));

            File download_file = new File("upload" + File.separator + download_file_code);
            if(download_file.exists()) {
                FileInputStream fileInputStream = new FileInputStream(download_file);
                ServletOutputStream outputStream = response.getOutputStream();
                response.setContentType("application/octet-stream");
                // 如果文件名为中文需要设置编码
                response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(download_file_name, "utf8"));
                // 返回前端文件名需要添加
                response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
                byte[] bytes = new byte[1024];
                int len;
                while ((len = fileInputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, len);
                }
            }
        }
    }

}
