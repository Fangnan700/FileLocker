package yvling.filelocker.dao;

import org.apache.ibatis.annotations.*;
import yvling.filelocker.entity.GlobalInfo;

@Mapper
public interface GlobalInfoDao {

    // 通过取件码获取文件信息
    @Results({
            @Result(property = "pickup_code", column = "pickup_code"),
            @Result(property = "file_code", column = "file_code")
    })
    @Select("SELECT * FROM global_info WHERE pickup_code = #{ pickup_code }")
    public GlobalInfo getFileInfoByPickupCode(@Param("pickup_code") String pickup_code);

    // 新增取件信息
    @Insert("INSERT INTO global_info(pickup_code, file_code) VALUES (#{ pickup_code }, #{ file_code })")
    public void insertPickupCodeAndFileCode(@Param("pickup_code") String pickup_code, @Param("file_code") String file_code);
}
