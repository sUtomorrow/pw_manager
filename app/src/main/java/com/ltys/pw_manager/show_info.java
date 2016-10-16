package com.ltys.pw_manager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.show_info_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                Toast.makeText(getApplicationContext(), "edit", Toast.LENGTH_SHORT).show();
                Intent it = new Intent();
                it.setClassName(getApplicationContext(),"com.ltys.pw_manager.mod_item");
                it.putExtra("name",name);
                it.putExtra("account",account==null?"":account);
                it.putExtra("passwd",passwd==null?"":passwd);
                it.putExtra("note",note==null?"":note);
                it.putExtra("img",img==null?"":img);
                startActivity(it);
                finish();
                break;
            case R.id.del:
                int result = MainActivity.dwx.del_node_by_name(name);
                if(result == 0)
                    Toast.makeText(getApplicationContext(), "deleted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "delete failed", Toast.LENGTH_SHORT).show();
                finish();
                break;
            default:
                break;
        }
        return false;
    }
}
