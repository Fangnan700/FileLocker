package yvling.filelocker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import yvling.filelocker.service.GlobalInfoService;
import yvling.filelocker.service.UploadFileService;


@SpringBootTest
class FileLockerApplicationTests {
    @Autowired
    private UploadFileService uploadFileService;
    @Autowired
    private GlobalInfoService globalInfoService;

    @Test
    void contextLoads() {}


}
