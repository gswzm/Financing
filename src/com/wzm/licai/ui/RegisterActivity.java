package com.wzm.licai.ui;

import com.wzm.licai.bean.Wallet;
import com.wzm.licai.db.UserDao;
import com.wzm.licai.db.WalletDao;
import com.wzm.licai.db.ZhangbenDao;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener{
    private EditText name,pwd;
    private Button cancel,regist;
    private UserDao dao;
    private ZhangbenDao zdao;
    private WalletDao wdao;
    private String[] zbName={"默认账本","旅游账本","生意账本"};
    private String[] qbName={"现金","储蓄卡","信用卡","支付宝"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);
	    initView();
	    dao=new UserDao(this);
	    zdao=new ZhangbenDao(this);
	    wdao=new WalletDao(this);
	}

	private void initView() {
       name=(EditText) findViewById(R.id.et_name);
       pwd=(EditText) findViewById(R.id.et_pwd);
       cancel=(Button) findViewById(R.id.bt_cancel);
       regist=(Button) findViewById(R.id.bt_regist);
       cancel.setOnClickListener(this);
       regist.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
		case R.id.bt_cancel:
			this.finish();
			break;
		case R.id.bt_regist:
			//数据库插入语句
			String n=name.getText().toString().trim();
			String p=pwd.getText().toString().trim();
			if(n.equals("")){
				Toast.makeText(this, "名字不能为空", 0).show();
			}else if(p.equals("")){
				Toast.makeText(this, "密码不能为空", 0).show();
			}else{
				dao.insert(n, p);
				int uid=dao.selectId(n);
				for(int i=0;i<zbName.length;i++){
				zdao.insertZhangBen(zbName[i], uid+"");
				}
				for(int j=0;j<qbName.length;j++){
					Wallet wa=new Wallet(0, qbName[j], 0);
					wdao.insertWallet(wa,uid+"");
					}
				Toast.makeText(this, "注册成功", 0).show();
				 this.finish();	
			}
           		
			break;
		}		
	}
}
