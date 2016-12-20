package com.wzm.licai.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.wzm.licai.frag.BaoBiaoFragment;
import com.wzm.licai.frag.MingXiFragment;
import com.wzm.licai.frag.QianBaoFragment;
import com.wzm.licai.frag.TiXingFragment;


public class MainActivity extends FragmentActivity{
    private ViewPager vp;
    private ImageView mingxi,qianbao,tianjia,baobiao,tixing;
    private List<Fragment> frags;
    private List<ImageView> images;
    private int[] image1={R.drawable.mingxi1,R.drawable.qianbao1,
    		R.drawable.baobiao1,R.drawable.tixing1};
    private int[] image2={R.drawable.mingxi2,R.drawable.qianbao2,
    		R.drawable.baobiao2,R.drawable.tixing2};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
	    initView();
	    initData();
	    initPager();
	    
	}
	@Override
	protected void onResume() {
		super.onResume();
		initView();
	    initData();
	    initPager();
	}
	private void initView() {
	      mingxi=(ImageView) findViewById(R.id.mingxi);
	      qianbao=(ImageView) findViewById(R.id.qianbao);
	      baobiao=(ImageView) findViewById(R.id.baobiao);
	      tixing=(ImageView) findViewById(R.id.tixing);
	      tianjia=(ImageView) findViewById(R.id.tianjia);
	      tianjia.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showAddDialog();
			}
		});
	      mingxi.setImageResource(R.drawable.mingxi2);
	      mingxi.setOnClickListener(new MyOnClickListener(0));
	      qianbao.setOnClickListener(new MyOnClickListener(1));
	      baobiao.setOnClickListener(new MyOnClickListener(2));
	      tixing.setOnClickListener(new MyOnClickListener(3));
	    		  
	}
    private class MyOnClickListener implements OnClickListener{
        private int index;
        public MyOnClickListener(int index){
        	this.index=index;
        }
		@Override
		public void onClick(View v) {
			vp.setCurrentItem(index);
		}
    	
    }
	private void initData() {
		frags=new ArrayList<Fragment>();
		frags.add(new MingXiFragment());
		frags.add(new QianBaoFragment());
		frags.add(new BaoBiaoFragment());
		frags.add(new TiXingFragment());
		images=new ArrayList<ImageView>();
		images.add(mingxi);
		images.add(qianbao);
		images.add(baobiao);
		images.add(tixing);
	}

	private void initPager() {
		vp=(ViewPager) findViewById(R.id.vp);
		vp.setAdapter(new MyAdapter(getSupportFragmentManager()));
		vp.setOnPageChangeListener(new MyOnPageListener());
	}
    private class MyAdapter extends FragmentPagerAdapter{

		public MyAdapter(FragmentManager fm) {
			super(fm);
		}
		@Override
		public Fragment getItem(int arg0) {
			return frags.get(arg0);
		}
		@Override
		public int getCount() {
			return frags.size();
		}
    }
	private class MyOnPageListener implements OnPageChangeListener{
		@Override
		public void onPageScrollStateChanged(int arg0) {
			
		}
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			
		}
		@Override
		public void onPageSelected(int arg0) {
			for(int i=0;i<images.size();i++){
				if(i==arg0){
					images.get(i).setImageResource(image2[i]);
				}else{
					images.get(i).setImageResource(image1[i]);
				}
			}
		}
		
	}
    private void showAddDialog(){
    	View view=getLayoutInflater().inflate(R.layout.frag_tianjia, null);
    	Button shouru=(Button) view.findViewById(R.id.shouru);
    	Button zhichu=(Button) view.findViewById(R.id.zhichu);
    	shouru.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
                 startActivity(new Intent(MainActivity.this,ShouRuActivity.class));				
			}
		});
    	zhichu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
                 startActivity(new Intent(MainActivity.this,ZhiChuActivity.class));				
			}
		});
    	new AlertDialog.Builder(this)
    	.setTitle("ÇëÑ¡Ôñ")
    	.setView(view)
    	.show();
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
}
