package yvling.filelocker.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import yvling.filelocker.entity.UploadFile;

import java.util.List;

@Mapper
public interface DeleteFileDao {

    @Delete("DELETE FROM upload_file WHERE delete_time < #{ cur_time }")
    void deleteExpiredFile(@Param("cur_time") Long cur_time);

    @Delete("DELETE FROM global_info WHERE global_info.file_code = #{ file_code }")
    void deleteExpiredFileInfo(@Param("file_code") String file_code);

    @Select("SELECT * FROM upload_file WHERE delete_time < #{ cur_time }")
    List<UploadFile> getExpiredFiles(@Param("cur_time") Long cur_time);
}
