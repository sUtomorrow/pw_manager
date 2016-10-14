package com.ltys.pw_manager;

import android.Manifest;
import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.sevenheaven.gesturelock.GestureLock;
import com.sevenheaven.gesturelock.GestureLockView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

import static java.lang.System.exit;

@RuntimePermissions
public class MainActivity extends AppCompatActivity {
    private String sDir = null;
    private String temp_sDir = null;
    private String note_sDir = null;
    private do_with_xml dwx_pass = null;
    private do_with_xml dwx_content = null;
    private GestureLock gestureView = null;

    //    private String SDCARD_DIR = "/mnt/sdcard/.wifi_trans/.pic/";
//    private String SDCARD_DIR_TEMP = "/mnt/sdcard/.wifi_trans/.temp/";
//    private String SDCARD_DIR_NOTE = "/mnt/sdcard/.wifi_trans/.note/";
//    private String NOSDCARD_DIR = "/data/data/.com.example.lenovo.wifi_trans/.pic/";
//    private String NOSDCARD_DIR_TEMP = "/data/data/.com.example.lenovo.wifi_trans/.temp/";
//    private String NOSDCARD_DIR_NOTE = "/data/data/.com.example.lenovo.wifi_trans/.note/";
    private final String md_text = "liutengying";
    private Button sign_in_button = null;
    private File file = null;
    private FileOutputStream fos = null;
    private OutputStreamWriter osw = null;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        String status = Environment.getExternalStorageState();
//        if (status.equals(Environment.MEDIA_MOUNTED)) {
//            sDir = SDCARD_DIR;
//            temp_sDir = SDCARD_DIR_TEMP;
//            note_sDir = SDCARD_DIR_NOTE;
//        } else {
//            sDir = NOSDCARD_DIR;
//            temp_sDir = NOSDCARD_DIR_TEMP;
//            note_sDir = NOSDCARD_DIR_NOTE;
//        }

//        sDir = new String("./");
//        file = new File(sDir + "pass.xml");
//        if (!file.exists()) {
//            try {
//                file.createNewFile();
//                fos = new FileOutputStream(file);
//                osw = new OutputStreamWriter(fos, "UTF-8");
//                osw.write("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\"?><root></root>");
//            } catch (IOException e) {
//                e.printStackTrace();
//                exit(0);
//            }
//        }
        MainActivityPermissionsDispatcher.get_permissions_funcWithCheck(MainActivity.this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET})
    void get_permissions_func() {
        show_lock();
    }

    private void show_lock(){
        setContentView(R.layout.lock);
        gestureView = (GestureLock) findViewById(R.id.gesture_lock);
        gestureView.setMode(1);
        gestureView.setAdapter(new GestureLock.GestureLockAdapter() {
            @Override
            public int getDepth() {
                return 4;
            }

            @Override
            public int[] getCorrectGestures() {
                return new int[]{0,1,2,3,4,5,6,12};
            }
            @Override
            public int getUnmatchedBoundary() {
                return 5;
            }
            @Override
            public int getBlockGapSize(){
                return 10;
            }
            @Override
            public GestureLockView getGestureLockViewInstance(Context context, int position) {
                if(position % 2 == 0){
                    return new MyStyleLockView(context);
                }else{
                    return new NexusStyleLockView(context);
                }
            }
        });
        gestureView.setOnGestureEventListener(new GestureLock.OnGestureEventListener(){
            @Override
            public void onGestureEvent(boolean matched) {
                Toast.makeText(MainActivity.this, "Match:" + matched, Toast.LENGTH_SHORT).show();
                gestureView.clear();
            }

            @Override
            public void onUnmatchedExceedBoundary() {
                Toast.makeText(MainActivity.this, "输入5次错误!30秒后才能输入", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBlockSelected(int position) {
                Log.d("position", position + "");
            }

        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET})
    void no_permission_func() {
        Toast.makeText(this, "请设置读写内存卡和网络权限", Toast.LENGTH_LONG).show();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
