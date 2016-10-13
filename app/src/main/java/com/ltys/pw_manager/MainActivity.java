package com.ltys.pw_manager;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private String sDir = null;
    private String temp_sDir = null;
    private String note_sDir = null;
    private do_with_xml dwx = null;
    private String SDCARD_DIR = "/mnt/sdcard/.wifi_trans/.pic/";
    private String SDCARD_DIR_TEMP = "/mnt/sdcard/.wifi_trans/.temp/";
    private String SDCARD_DIR_NOTE = "/mnt/sdcard/.wifi_trans/.note/";
    private String NOSDCARD_DIR = "/data/data/.com.example.lenovo.wifi_trans/.pic/";
    private String NOSDCARD_DIR_TEMP = "/data/data/.com.example.lenovo.wifi_trans/.temp/";
    private String NOSDCARD_DIR_NOTE = "/data/data/.com.example.lenovo.wifi_trans/.note/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            sDir = SDCARD_DIR;
            temp_sDir = SDCARD_DIR_TEMP;
            note_sDir = SDCARD_DIR_NOTE;
        } else {
            sDir = NOSDCARD_DIR;
            temp_sDir = NOSDCARD_DIR_TEMP;
            note_sDir = NOSDCARD_DIR_NOTE;
        }
        
    }
}
