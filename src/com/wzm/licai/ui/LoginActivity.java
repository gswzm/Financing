package com.wzm.licai.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wzm.licai.db.UserDao;

public class LoginActivity extends Activity implements OnClickListener{
    private EditText name,pwd;
    private Button login,regist;
    private UserDao dao;
    private SharedPreferences sp;
    public static final String fName="user.xml";
    private Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
	    initView();
	    dao=new UserDao(this);
	    sp=getSharedPreferences(fName, Context.MODE_PRIVATE);
	    editor=sp.edit();
	}

	private void initView() {
       name=(EditText) findViewById(R.id.et_name);
       pwd=(EditText) findViewById(R.id.et_pwd);
       login=(Button) findViewById(R.id.bt_login);
       regist=(Button) findViewById(R.id.bt_regist);
       login.setOnClickListener(this);
       regist.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
         switch (v.getId()) {
		case R.id.bt_login:
			String n=name.getText().toString();
			String p=pwd.getText().toString();
			if(n.equals("")){
				Toast.makeText(this, "用户名不能为空", 0).show();return;
			}else if(p.equals("")){
				Toast.makeText(this, "密码不能为空", 0).show();return;
			}
			if(!p.equals(dao.selectPwd(n))){
				Toast.makeText(this, "用户名与密码不符", 0).show();
			}else{
				
				editor.putString("name", n);
				editor.putString("pwd", p);
				editor.putString("uid", dao.selectId(n)+"");
				editor.commit();
				Intent intent=new Intent(LoginActivity.this,MainActivity.class);
				startActivity(intent);
				this.finish();
			}
			break;
		case R.id.bt_regist:
			startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
			break;
		}		
	}
	@Override
	protected void onResume() {
		super.onResume();
        String name1=sp.getString("name", "");
        String pwd1=sp.getString("pwd", "");
        name.setText(name1);
        pwd.setText(pwd1);
	}
}
