package yvling.filelocker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yvling.filelocker.dao.UploadFileDao;
import yvling.filelocker.entity.UploadFile;

import java.io.File;
import java.util.List;

@Service
public class UploadFileService {

    @Autowired
    private UploadFileDao uploadFileDao;


    // 获取所有文件信息
    public List<UploadFile> getAllFiles() {
        return uploadFileDao.getAllFiles();
    }

    // 通过文件编码获取文件信息
    public UploadFile getFileByCode(String file_code) {
        return uploadFileDao.getFileByCode(file_code);
    }

    // 插入文件信息
    public boolean insertFileInfo(UploadFile file) {
        boolean status = false;
        try {
            uploadFileDao.insertFileInfo(
                    file.getFile_code(),
                    file.getFile_name(),
                    file.getFile_type(),
                    file.getFile_size(),
                    file.getSave_time(),
                    file.getUpload_time(),
                    file.getDelete_time()
            );
            status = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    // 根据文件编码删除文件信息
    public boolean deleteFileByCode(String file_code) {
        boolean status = false;
        try {
            uploadFileDao.deleteFileByCode(file_code);
            status = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }


    // 保存文件
    public boolean saveFile(MultipartFile file, String file_code_t) {
        boolean status = false;

        try {
            File dir = new File("upload");
            if(!dir.exists()) {
                dir.mkdir();
            }
            String file_code = file_code_t;
            String path = dir.getAbsolutePath() + File.separator + file_code;
            file.transferTo(new File(path));

            status = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }
}
