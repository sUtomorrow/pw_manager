package com.ltys.pw_manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.sevenheaven.gesturelock.GestureLock;
import com.sevenheaven.gesturelock.GestureLockView;

import static java.lang.System.exit;

/**
 * Created by Administrator on 2016/10/14.
 */

public class signin_activity extends Activity {
    private GestureLock gestureView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lock);
        show_set_lock();
    }

    private void show_set_lock(){
        gestureView = (GestureLock) findViewById(R.id.gesture_lock);
        gestureView.setMode(0);
        gestureView.setAdapter(new GestureLock.GestureLockAdapter() {
            @Override
            public int getDepth(){
                return MainActivity.width;
            }

            @Override
            public int[] getCorrectGestures() {
//                return MainActivity.container;
                return new int[]{0,6,12,18,24};
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
                if(gestureView.mode==0&&matched){
                    //密码成功,返回主界面
                    Toast.makeText(signin_activity.this, "Match:" + matched, Toast.LENGTH_SHORT).show();
                    gestureView.clear();
                    MainActivity.lock_val = 0;
                    Intent it = new Intent();
                    it.setClassName(signin_activity.this,"com.ltys.pw_manager.MainActivity");
                    startActivity(it);
                    finish();
                }else{
                    Toast.makeText(signin_activity.this, "Match:" + matched, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onUnmatchedExceedBoundary() {
                Toast.makeText(signin_activity.this, "密码错误次数太多", Toast.LENGTH_SHORT).show();
                exit(0);
            }
            @Override
            public void onBlockSelected(int position) {
                Log.d("position", position + "");
            }
        });
    }
}
