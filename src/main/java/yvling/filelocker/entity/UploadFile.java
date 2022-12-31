package yvling.filelocker.entity;

public class UploadFile {
    private String file_code;
    private String file_name;
    private String file_type;
    private Long file_size;
    private Long save_time;
    private Long upload_time;
    private Long delete_time;

    public UploadFile(String file_code, String file_name, String file_type, Long file_size, Long save_time, Long upload_time, Long delete_time) {
        this.file_code = file_code;
        this.file_name = file_name;
        this.file_type = file_type;
        this.file_size = file_size;
        this.save_time = save_time;
        this.upload_time = upload_time;
        this.delete_time = delete_time;
    }

    public String getFile_code() {
        return file_code;
    }

    public String getFile_name() {
        return file_name;
    }

    public String getFile_type() {
        return file_type;
    }

    public Long getFile_size() {
        return file_size;
    }

    public Long getSave_time() {
        return save_time;
    }

    public Long getUpload_time() {
        return upload_time;
    }

    public Long getDelete_time() {
        return delete_time;
    }

    @Override
    public String toString() {
        return "UploadFile{" +
                "file_code='" + file_code + '\'' +
                ", file_name='" + file_name + '\'' +
                ", file_type='" + file_type + '\'' +
                ", file_size=" + file_size +
                ", save_time=" + save_time +
                ", upload_time=" + upload_time +
                ", delete_time=" + delete_time +
                '}';
    }
}
