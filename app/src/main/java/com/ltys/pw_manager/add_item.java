package com.ltys.pw_manager;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/10/16.
 */

public class add_item extends Activity {
    private ImageView ma_icon;
    private EditText ma_name=null,ma_account=null,ma_passwd=null,ma_note=null;
    private String new_name=null,new_account=null,new_passwd=null,new_note=null,new_img=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mod_add);
        ma_icon = (ImageView) findViewById(R.id.ma_img);
        ma_name = (EditText) findViewById(R.id.ma_name);
        ma_account = (EditText) findViewById(R.id.ma_account);
        ma_passwd = (EditText) findViewById(R.id.ma_passwd);
        ma_note = (EditText) findViewById(R.id.ma_note);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mod_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()){
            case R.id.save:
                if(!(new_name=ma_name.getText().toString()).equals("")) {
                    new_account = ma_account.getText().toString();
                    new_passwd = ma_passwd.getText().toString();
                    new_note = ma_note.getText().toString();
                    new_img = "";
                    int result = MainActivity.dwx.add_node(new_name,new_note,new_account,new_passwd,new_img);
                    if(result == 0){
                        Toast.makeText(getApplicationContext(), "save success", Toast.LENGTH_SHORT).show();
                    }
                    else if(result == 1){
                        Toast.makeText(getApplicationContext(), "已有同名项", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "save failed", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "名称不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.cancel:
                Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_SHORT).show();
                finish();
                break;
            default:
                break;
        }
        return false;
    }
}
