package yvling.filelocker.entity;

public class GlobalInfo {
    private String pickup_code;
    private String file_code;

    public GlobalInfo(String pickup_code, String file_code) {
        this.pickup_code = pickup_code;
        this.file_code = file_code;
    }

    public String getPickup_code() {
        return pickup_code;
    }

    public String getFile_code() {
        return file_code;
    }
}
