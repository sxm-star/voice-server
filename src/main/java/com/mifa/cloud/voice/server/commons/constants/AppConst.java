package com.mifa.cloud.voice.server.commons.constants;

public interface AppConst {
    String SERVICE_NAME = "voice-cloud";
    String BASE_PATH = "/" + SERVICE_NAME + "/api/";
    String BASE_AUTH_PATH = "/" + SERVICE_NAME + "/auth/" +  "/api/";
    /**
     * 逗号常量
     */
    String COMMA = ",";
    String SAMPLE_TOKEN = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2MjQxMzYzOTcyMzAwODAwIiwibmJmIjoxNDk1MDIxNjQ4LCJpc3MiOiJYSU5CQU5HIiwic2VjVmVyIjoiMSIsInR5cGUiOiJURU5BTlQiLCJleHAiOjE0OTc2MTM2NDgsImlhdCI6MTQ5NTAyMTY0OCwianRpIjoiMGQzZGI5OGY5YmRmNGJkNDkzMTNjYmMzYWM0NTFjN2UifQ.zNryjqJR8q7ynP-lQYIU7WkmvRxFYnJoHm9P6sY4jKc";
    String DOWNLOAD_PATH = "/home/dpwnload/";
    String DOWNLOAD_FILE_NAME = "xls";

    /**
     * 语音模板的meta
     */
    String[] VOICE_TEMPLATE_METAS = new String[]{"userName","userPhone","orgName","userJob","userSex","userAddress"};


}