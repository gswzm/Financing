package com.wzm.licai.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public class NaoZhongActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_naozhong);
		//播放音乐
		//添加震动
		//弹出对话框
		final Vibrator vibrator=(Vibrator) getSystemService(VIBRATOR_SERVICE);
		//发出间断震动
		vibrator.vibrate(new long[]{1000,2000,1000,3000},0);
	  final MediaPlayer mp=MediaPlayer.create(this, R.raw.need);
	        mp.start();
		 new AlertDialog.Builder(this)
		 .setTitle("帐户提醒")
		 .setMessage("你有一项财务到期了，请查看！")
		 .setPositiveButton("确定", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				mp.stop();
				mp.release();
				vibrator.cancel();//关闭震动
				NaoZhongActivity.this.finish();
			}
		}).create().show();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nao_zhong, menu);
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
}
