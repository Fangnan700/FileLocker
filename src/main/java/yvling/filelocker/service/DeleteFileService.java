package yvling.filelocker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yvling.filelocker.entity.UploadFile;
import yvling.filelocker.dao.DeleteFileDao;

import java.util.List;


@Service
public class DeleteFileService {
    @Autowired
    private DeleteFileDao deleteFileDao;

    List<UploadFile> getExpiredFiles(Long cur_time) {
        return deleteFileDao.getExpiredFiles(cur_time);
    }

    void deleteExpiredFile(Long cur_time) {
        deleteFileDao.deleteExpiredFile(cur_time);
    }

    void deleteExpiredFileInfo(String file_code) {
        deleteFileDao.deleteExpiredFileInfo(file_code);
    }

}
