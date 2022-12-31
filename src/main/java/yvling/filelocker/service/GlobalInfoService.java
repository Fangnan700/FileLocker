package yvling.filelocker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yvling.filelocker.dao.GlobalInfoDao;
import yvling.filelocker.entity.GlobalInfo;

@Service
public class GlobalInfoService {

    @Autowired
    private GlobalInfoDao globalInfoDao;

    // 通过取件码查询取件信息
    public GlobalInfo getFileInfoByPickupCode(String pickup_code) {
        return globalInfoDao.getFileInfoByPickupCode(pickup_code);
    }

    // 新增取件信息
    public boolean insertPickupCodeAndFileCode(String pickup_code, String file_code) {
        boolean status = false;
        try {
            globalInfoDao.insertPickupCodeAndFileCode(pickup_code, file_code);
            status = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }
}
