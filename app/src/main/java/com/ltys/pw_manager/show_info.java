package com.ltys.pw_manager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/10/16.
 */

public class show_info extends Activity {
    private String name=null,account=null,passwd=null,note=null,img=null;
    private TextView show_name=null,show_account=null,show_passwd=null,show_note=null;
    private ImageView show_icon=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_info);
        show_icon = (ImageView) findViewById(R.id.show_img);
        show_name = (TextView) findViewById(R.id.show_name);
        show_account = (TextView) findViewById(R.id.show_account);
        show_passwd = (TextView) findViewById(R.id.show_passwd);
        show_note = (TextView) findViewById(R.id.show_note);
        Intent it = getIntent();
        Bundle bd = it.getExtras();
        name = bd.getString("name");
        account = bd.getString("account");
        passwd = bd.getString("passwd");
        note = bd.getString("note");
        img = bd.getString("img");
        show_name.setText(name);
        if(show_account!=null)
            show_account.setText(account);
        if(show_passwd!=null)
            show_passwd.setText(passwd);
        if(show_note!=null)
            show_note.setText(note);
//  show_icon.setImageBitmap();
    }
}
