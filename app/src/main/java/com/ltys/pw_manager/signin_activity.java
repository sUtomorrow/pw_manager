package com.ltys.pw_manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.sevenheaven.gesturelock.GestureLock;
import com.sevenheaven.gesturelock.GestureLockView;
import java.io.File;

import static java.lang.System.exit;

/**
 * Created by Administrator on 2016/10/14.
 */

public class signin_activity extends Activity {
    public static int width = 5;
    private int lock = 1;
    public static int[] container = new int[width*width+1];
    public static int[] input_container = new int[width*width+1];
    public static int[] sec_input_container = new int[width*width+1];
    public static int[] negativeGestures = new int[width*width+1];
    private GestureLock gestureView = null;
    public int curse = 0;
    private int mode = 0;
    private static String path=null;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Intent it = getIntent();
        Bundle bd = it.getExtras();
        mode = bd.getInt("mode");
        path = bd.getString("path");
        for(int i = 0; i < negativeGestures.length; i++) {
            negativeGestures[i] = -1;
        }
        input_container = negativeGestures.clone();
        sec_input_container = negativeGestures.clone();
        container = negativeGestures.clone();
        path = path+".src/ltys.txt";
        File path_f = new File(MainActivity.sDir+".src/");
        if(!path_f.exists()){
            path_f.mkdirs();
        }
        File passwd_file = new File(path);
        if(!passwd_file.exists()){
            ToolFunction.write_pass(path,new int[]{0,6,12,18,24});
        }
        try{
            curse = 0;
            container = negativeGestures.clone();
            for(int i:ToolFunction.read_pass(path)){
                if(i == -1){
//                    Log.i("aeed",""+i);
                    break;
                }
                else{
                    container[curse] = i;
//                    Log.i("aas",""+i);
                    curse++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
//            Log.i("accc","5");
            exit(0);
        }
        setContentView(R.layout.lock);
        show_set_lock();
    }

    private void show_set_lock(){
        gestureView = (GestureLock) findViewById(R.id.gesture_lock);
        if(mode==1){
            Toast.makeText(signin_activity.this, "请输入原手势密码", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(signin_activity.this, "请输入手势密码", Toast.LENGTH_SHORT).show();
        }
        gestureView.setMode(mode);
        gestureView.setAdapter(new GestureLock.GestureLockAdapter() {
            @Override
            public int getDepth(){
                return width;
            }

            @Override
            public int[] getCorrectGestures() {
//                return MainActivity.container;
                return container;
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
                if(mode==0&&matched){
                    //解锁成功,返回主界面
                    Toast.makeText(signin_activity.this, "Match:" + matched, Toast.LENGTH_SHORT).show();
                    gestureView.clear();
                    MainActivity.lock_val = 0;
                    Intent it = new Intent();
                    it.setClassName(signin_activity.this,"com.ltys.pw_manager.MainActivity");
                    startActivity(it);
                    finish();
                }
                else if(mode==1&&matched&&lock==1&&curse!=0){
                    input_container = negativeGestures.clone();
                    sec_input_container = negativeGestures.clone();
                    lock = 0;
                    curse = 0;
                    gestureView.clear();
                    Toast.makeText(signin_activity.this, "请输入新的手势密码", Toast.LENGTH_SHORT).show();
                }
                else if(mode == 1 && lock == 0&&curse!=0){
                    sec_input_container = negativeGestures.clone();
                    gestureView.clear();
                    Toast.makeText(signin_activity.this, "请确认新的手势密码", Toast.LENGTH_SHORT).show();
                    curse = 0;
                    mode = 2;
                }
                else if(mode == 2 && lock == 0&&curse!=0){
                    int suc = 1;
                    String passwd = "";
                    for(int z=0;z<curse;z++){
                        if(sec_input_container[z] == input_container[z]){
                            passwd += sec_input_container[z]+" ";
                        }
                        else{
                            Toast.makeText(signin_activity.this, "两次输入不一致", Toast.LENGTH_SHORT).show();
                            suc = 0;
                            finish();
                            break;
                        }
                    }
                    if(suc!=0){
                       ToolFunction.write_pass(path,input_container);
                        Toast.makeText(signin_activity.this, "修改密码成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                else{
                    Toast.makeText(signin_activity.this, "Match:" + matched, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onUnmatchedExceedBoundary() {
                Toast.makeText(signin_activity.this, "密码错误次数太多", Toast.LENGTH_SHORT).show();
                finish();
            }
            @Override
            public void onBlockSelected(int position) {
                if(lock==0 &&mode ==1){
                    input_container[curse] = position;
                    curse++;
                }
                if(lock==0 &&mode ==2){
                    sec_input_container[curse] = position;
                    curse++;
                }
//                Log.d("position", position + "");
            }
        });
    }
}
