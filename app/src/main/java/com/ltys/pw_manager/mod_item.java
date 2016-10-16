package com.ltys.pw_manager;

import android.app.Activity;
import android.content.Intent;
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

public class mod_item extends Activity{
    private String name=null,account=null,passwd=null,note=null,img=null;
    private String new_name=null,new_account=null,new_passwd=null,new_note=null,new_img=null;
    private ImageView ma_icon;
    private EditText ma_name=null,ma_account=null,ma_passwd=null,ma_note=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mod_add);
        ma_icon = (ImageView) findViewById(R.id.ma_img);
        ma_name = (EditText) findViewById(R.id.ma_name);
        ma_account = (EditText) findViewById(R.id.ma_account);
        ma_passwd = (EditText) findViewById(R.id.ma_passwd);
        ma_note = (EditText) findViewById(R.id.ma_note);
        Intent it = getIntent();
        Bundle bd = it.getExtras();
        name = bd.getString("name");
        account = bd.getString("account");
        passwd = bd.getString("passwd");
        note = bd.getString("note");
        img = bd.getString("img");
        ma_name.setText(name);
        if(account!=null)
            ma_account.setText(account);
        if(passwd!=null)
            ma_passwd.setText(passwd);
        if(note!=null)
            ma_note.setText(note);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mod_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                if(!(new_name=ma_name.getText().toString()).equals("")) {
                    new_account = ma_account.getText().toString();
                    new_passwd = ma_passwd.getText().toString();
                    new_note = ma_note.getText().toString();
                    new_img = img;
                    if(MainActivity.dwx.modify_node(name,new_name,new_note,new_img,new_account,new_passwd)==0){
                        Toast.makeText(getApplicationContext(), "save success", Toast.LENGTH_SHORT).show();
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
