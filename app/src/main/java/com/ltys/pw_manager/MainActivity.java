package com.ltys.pw_manager;

import android.Manifest;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
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
    private final String md_text = "liutengying";
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
        MainActivityPermissionsDispatcher.get_permissions_funcWithCheck(MainActivity.this);
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.INTERNET})
    void get_permissions_func() {
        show_signin();
    }

    private void show_signin(){

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.INTERNET})
    void no_permission_func() {
        Toast.makeText(this, "请设置读写内存卡和网络权限", Toast.LENGTH_LONG).show();
    }
}
