package yvling.filelocker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import yvling.filelocker.entity.UploadFile;

import java.io.File;
import java.util.*;

@Service
public class TimerService implements ApplicationRunner {
    @Autowired
    private DeleteFileService deleteFileService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Date time = calendar.getTime();

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                deleteExpiredFiles();
            }
        };
        timer.scheduleAtFixedRate(timerTask, time, 24*60*60*1000);
    }

    private void deleteExpiredFiles() {
        Long cur_time = System.currentTimeMillis();
        List<UploadFile> expiredFilesList = deleteFileService.getExpiredFiles(cur_time);
        deleteFileService.deleteExpiredFile(cur_time);
        for(UploadFile expired_file : expiredFilesList) {
            String file_code = expired_file.getFile_code();
            File file = new File("upload" + File.separator + file_code);
            if(file.exists()) {
                file.delete();
            }
            deleteFileService.deleteExpiredFileInfo(file_code);
        }
    }
}
