package yvling.filelocker.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yvling.filelocker.entity.UploadFile;
import yvling.filelocker.service.GlobalInfoService;
import yvling.filelocker.service.UploadFileService;

import java.io.File;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Random;

@RestController
public class UploadFileController {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private UploadFileService uploadFileService;
    @Autowired
    private GlobalInfoService globalInfoService;


    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public JSONObject uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("save_time") Long save_time) throws Exception{

        // 初始化信息
        Long upload_time = System.currentTimeMillis();
        Long delete_time = upload_time + (save_time * 24 * 60 * 60 * 1000);
        InetAddress ia = InetAddress.getLocalHost();

        // 检测是否有同名文件
        String file_name = file.getOriginalFilename();
        UploadFile whether_exists = uploadFileService.getFileByCode(Base64.getEncoder().encodeToString(file_name.getBytes()));
        if(whether_exists != null) {
            file_name = "(1)" + file.getOriginalFilename();
        }

        // 创建文件信息对象
        UploadFile uploadFile = new UploadFile(
                Base64.getEncoder().encodeToString(file_name.getBytes()),
                file_name,
                file.getContentType(),
                file.getSize(),
                save_time,
                upload_time,
                delete_time
        );


        // 生成取件码
        Random random = new Random();
        int min = 100000;
        int max = 999999;
        int randomInt = random.nextInt(max - min + 1) + min;
        String pickup_code = Integer.toString(randomInt);



        // 生成响应信息
        JSONObject response_json = new JSONObject();

        Long file_size = uploadFile.getFile_size();
        Double size;
        String fileSizeStr;
        if(file_size < 1024) {
            size = Double.valueOf(file_size);
            fileSizeStr = size + "bit";
        } else if(file_size < 1048576) {
            size = (double) (file_size / 1024);
            fileSizeStr = size + "K";
        } else if(file_size < 1073741824) {
            size = (double) (file_size / 1048576);
            fileSizeStr = size + "M";
        } else {
            size = (double) (file_size / 1073741824);
            fileSizeStr = size + "G";
        }


        if(
                globalInfoService.insertPickupCodeAndFileCode(pickup_code, uploadFile.getFile_code()) &&
                uploadFileService.insertFileInfo(uploadFile) &&
                uploadFileService.saveFile(file, uploadFile.getFile_code())) {
            response_json.put("status", true);
            response_json.put("file_code", Base64.getEncoder().encodeToString(file.getOriginalFilename().getBytes()));
            response_json.put("file_name", file.getOriginalFilename());
            response_json.put("file_type", file.getContentType());
            response_json.put("file_size", fileSizeStr);
            response_json.put("save_time", save_time + "天");
            response_json.put("upload_time", dateFormat.format(new Date(upload_time)));
            response_json.put("delete_time", dateFormat.format(new Date(delete_time)));
            response_json.put("file_path", ia.getHostAddress() + File.separator + "upload" + uploadFile.getFile_code());
            response_json.put("pickup_code", pickup_code);
        } else {
            response_json.put("status", false);
        }

        return response_json;
    }


}
