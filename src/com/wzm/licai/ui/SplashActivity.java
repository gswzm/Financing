package com.wzm.licai.ui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends Activity {
    private ImageView jia,ting,li,cai,tong,a2,enlish;
    private Animation anim1,anim2;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		initView();
	}
    
	private void initView() {
		jia=(ImageView) findViewById(R.id.iv_jia);
		ting=(ImageView) findViewById(R.id.iv_ting);
		li=(ImageView) findViewById(R.id.iv_li);
		cai=(ImageView) findViewById(R.id.iv_cai);
		tong=(ImageView) findViewById(R.id.iv_tong);
		a2=(ImageView) findViewById(R.id.iv_a2);
		enlish=(ImageView) findViewById(R.id.english);
		myPlay();
	}
	private void myPlay() {
		anim1=AnimationUtils.loadAnimation(this, R.anim.word);
		anim2=AnimationUtils.loadAnimation(this, R.anim.center);
		jia.startAnimation(anim1);
		ting.startAnimation(anim1);
		li.startAnimation(anim1);
		cai.startAnimation(anim1);
		tong.startAnimation(anim1);
		enlish.startAnimation(anim1);
		a2.startAnimation(anim2);
		anim1.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				startActivity(new Intent(SplashActivity.this, LoginActivity.class));
				SplashActivity.this.finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
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
