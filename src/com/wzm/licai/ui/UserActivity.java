package com.wzm.licai.ui;

import com.wzm.licai.db.UserDao;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class UserActivity extends Activity implements OnClickListener{
    private ActionBar bar;
    private RelativeLayout tou_user,name_user,pwd_user;
    private TextView cancel,nameTv;
    private SharedPreferences sp;
    private Editor editor;
    private UserDao dao;
    //名字和密码
    private String name,pwd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_user);
		bar=getActionBar();
		bar.setDisplayShowHomeEnabled(false);
	    bar.setDisplayShowCustomEnabled(true);
	    bar.setDisplayHomeAsUpEnabled(true);
	    bar.setTitle("我的个人资料");
	    dao=new UserDao(this);
	    sp=getSharedPreferences(LoginActivity.fName, Context.MODE_PRIVATE);
	    editor=sp.edit();
	    name=sp.getString("name", "");
	    pwd=sp.getString("pwd", "");
	    initView();
	}
    private void initView(){
    	tou_user=(RelativeLayout) findViewById(R.id.tou_user);
    	name_user=(RelativeLayout) findViewById(R.id.name_user);
    	pwd_user=(RelativeLayout) findViewById(R.id.pwd_user);
    	cancel=(TextView) findViewById(R.id.cancelTT);
    	nameTv=(TextView) findViewById(R.id.nameTv);
    	nameTv.setText(name);
    	tou_user.setOnClickListener(this);
    	name_user.setOnClickListener(this);
    	pwd_user.setOnClickListener(this);
    	cancel.setOnClickListener(this);
    }
    @Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tou_user:
			
			break;
		case R.id.name_user:
			showNameDialog();
			break;
		case R.id.pwd_user:
			showPwdDialog();
			break;
		case R.id.cancelTT:
			editor.clear();
			startActivity(new Intent(this, LoginActivity.class));
			this.finish();
			break;
		}
	}
	private void showPwdDialog() {
		View view=getLayoutInflater().inflate(R.layout.dialog_pwd, null);
		EditText oldP=(EditText)view.findViewById(R.id.oldPwd);
		final EditText newP=(EditText)view .findViewById(R.id.newPwd);
		oldP.setText(pwd);
		new AlertDialog.Builder(this)
		.setTitle("修改密码")
		.setView(view)
		.setPositiveButton("确定",new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			   dao.updatePwd(newP.getText().toString(), sp.getString("name", ""));
			   editor.putString("name", sp.getString("name", ""));
			   editor.putString("pwd", newP.getText().toString());
			   editor.putString("uid", ""+dao.selectId(sp.getString("name", "")));
			   editor.commit();
			}
		})
		.setNegativeButton("取消",null)
		.show();
	}
	private void showNameDialog() {
		View view=getLayoutInflater().inflate(R.layout.dialog_name, null);
		EditText oldN=(EditText)view.findViewById(R.id.oldName);
		final EditText newN=(EditText)view .findViewById(R.id.newName);
		oldN.setText(name);
		new AlertDialog.Builder(this)
		.setTitle("修改昵称")
		.setView(view)
		.setPositiveButton("确定",new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			   dao.updateName(newN.getText().toString(),name);
			   editor.putString("name", newN.getText().toString());
			   editor.putString("pwd", pwd);
			   editor.putString("uid", ""+dao.selectId(newN.getText().toString()));
			   editor.commit();
			}
		})
		.setNegativeButton("取消",null)
		.show();
	}
	@Override
	protected void onResume() {
		super.onResume();
		nameTv.setText(sp.getString("name", ""));
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == android.R.id.home) {
			UserActivity.this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
