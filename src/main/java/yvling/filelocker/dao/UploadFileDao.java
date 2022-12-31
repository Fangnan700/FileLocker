package yvling.filelocker.dao;

import org.apache.ibatis.annotations.*;
import yvling.filelocker.entity.UploadFile;

import java.util.List;

@Mapper
public interface UploadFileDao {

    // 获取所有文件信息
    @Select("SELECT * FROM upload_file")
    List<UploadFile> getAllFiles();

    // 通过文件编码获取文件信息
    @Results({
            @Result(property = "file_code", column = "file_code"),
            @Result(property = "file_name", column = "file_name"),
            @Result(property = "file_type", column = "file_type"),
            @Result(property = "file_size", column = "file_size"),
            @Result(property = "save_time", column = "save_time"),
            @Result(property = "upload_time", column = "upload_time"),
            @Result(property = "delete_time", column = "delete_time")
    })
    @Select("SELECT * FROM upload_file WHERE file_code = #{ file_code }")
    UploadFile getFileByCode(@Param("file_code") String file_code);

    // 插入文件信息
    @Insert("INSERT INTO upload_file (file_code, file_name, file_type, file_size, save_time, upload_time, delete_time)" +
            "VALUES (#{ file_code }, #{ file_name }, #{ file_type}, #{ file_size }, #{ save_time }, #{ upload_time }, #{ delete_time })")
    void insertFileInfo(
            @Param("file_code") String file_code,
            @Param("file_name") String file_name,
            @Param("file_type") String file_type,
            @Param("file_size") Long file_size,
            @Param("save_time") Long save_time,
            @Param("upload_time") Long upload_time,
            @Param("delete_time") Long delete_time
    );

    @Delete("DELETE FROM upload_file WHERE file_code = #{ file_code }")
    boolean deleteFileByCode(String file_code);
}
