package com.wzm.licai.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wzm.licai.bean.AddMoney;
import com.wzm.licai.bean.Wallet;
import com.wzm.licai.db.AddMoneyDao;
import com.wzm.licai.db.WalletDao;
import com.wzm.licai.frag.MingXiFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class ZhiChuActivity extends Activity implements OnClickListener {
		private EditText zhichuEt,markZ;
	    private Spinner typeZ,qianbaoZ;
	    private TextView timeZ,dayZ;
	    private Button sure,cancel;
	    private List<String> typess;
	    private String type,dayOf,hourOf,stringStr;
	    int year1,month1,day1,hour1,minute1;
	    //钱包
	    private List<Wallet> listW;
	    private WalletDao wdao;
	    private int wid;
	    private double qian;
	    //保存
	    private AddMoneyDao adao;
	    private SharedPreferences sp,sp1;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.activity_zhichu);
		    adao=new AddMoneyDao(this);
		    sp=getSharedPreferences(LoginActivity.fName, Context.MODE_APPEND);
		    sp1=getSharedPreferences(MingXiFragment.fZhang, Context.MODE_APPEND);
			initView();
		}
		private void initView(){
			sure=(Button) findViewById(R.id.bt_login);
			cancel=(Button) findViewById(R.id.bt_regist);
			sure.setEnabled(false);
			//金额
			zhichuEt=(EditText)findViewById(R.id.zhichu);
			//备注
			markZ=(EditText)findViewById(R.id.markZ);
			//时间
			timeZ=(TextView) findViewById(R.id.timeZ);
			dayZ=(TextView) findViewById(R.id.dateZ);
			dayZ.setOnClickListener(this);
			timeZ.setOnClickListener(this);
			final long time=System.currentTimeMillis();
			SimpleDateFormat sDay=new SimpleDateFormat("yyyy-MM-dd");
			String day=sDay.format(time);
			SimpleDateFormat sHour=new SimpleDateFormat("HH:mm");
			String hour=sHour.format(time);
			dayZ.setText(day);
			timeZ.setText(hour);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			stringStr=format.format(time);
			typeZ=(Spinner) findViewById(R.id.typeZ);
			qianbaoZ=(Spinner) findViewById(R.id.walletZ);
			//
			wdao=new WalletDao(this);
			listW=wdao.seletWallet(sp.getString("uid", ""));
			final Map<String,Integer> map=new HashMap<String,Integer>();
			final Map<String,Double> mapM=new HashMap<String,Double>();
			final List<String> names=new ArrayList<String>();
			Wallet wall=null;
			for(Wallet w: listW){
				wall=w;
				names.add(wall.getName());
				map.put(wall.getName(), wall.getId());
				mapM.put(wall.getName(), wall.getMoney());
			}
			qianbaoZ.setAdapter(new ArrayAdapter<String>(this, 
					android.R.layout.simple_spinner_item,names));
			qianbaoZ.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					    qian=mapM.get(names.get(position));
					   if(qian==0){
						   Toast.makeText(getApplicationContext(),
								   "当前钱包木有钱，请重新选择", 0).show();
					   }else{
						   wid=map.get(names.get(position));
						   sure.setEnabled(true);
					   }
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					
				}
			});
			typess=new ArrayList<String>();
			typess.add("吃饭");
			typess.add("购物");
			typess.add("旅游");
			typess.add("其他");
			typeZ.setAdapter(new ArrayAdapter<String>(this, 
					android.R.layout.simple_spinner_item,typess));
			
			typeZ.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					if(typess.get(position).equals("其他")){
						 View view1=getLayoutInflater().inflate(
								 R.layout.zhichu_type, null);
						 final EditText et_type=(EditText)view1.findViewById(R.id.et_type);
						 
						new AlertDialog.Builder(ZhiChuActivity.this)
						.setTitle("请输入")
						.setView(view1)
						.setCancelable(false)
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								String et=et_type.getText().toString();
								if(et.equals("")){
									
								}else{
									typess.add(et);
									type=et;
								}
							}
						})
						.setNegativeButton("取消", null)
						.create().show();
						
					}else{
					     type=typess.get(position);
					}
				}
				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					
				}
			});
			
			sure.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String m=zhichuEt.getText().toString();
					double mon=Double.parseDouble(m);
					double money=mon*(-1);
					if(m.equals("")){
						 Toast.makeText(getApplicationContext(),
								   "请输入金额", 0).show();
					}
					if(qian<mon){
						   Toast.makeText(getApplicationContext(),
								   "余额不足，请重新选择", 0).show();
					 }else{
					String mark=markZ.getText().toString();
					String uid=sp.getString("uid", "");
					String zid=sp1.getString("zid", "");
					AddMoney amoney=new AddMoney(money, stringStr, type, mark,  wid+"",uid,zid);
					adao.insertMoney(amoney);
					wdao.updateWallet(money+"", wid+"");
					ZhiChuActivity.this.finish();
					 }
				}
			});
			cancel.setOnClickListener(this);
		}
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.bt_regist:
				ZhiChuActivity.this.finish();
				break;
			case R.id.dateZ:
				Calendar c=Calendar.getInstance();
				year1=c.get(Calendar.YEAR);
				month1=c.get(Calendar.MONTH);
				day1=c.get(Calendar.DAY_OF_MONTH);
				DatePickerDialog dialog=new DatePickerDialog(this, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear,
							int dayOfMonth) {
						year1=year;
						month1=monthOfYear;
						day1=dayOfMonth;
						String m="";
						String d="";
						if(month1<10){
							m="0"+(month1+1);
						}else{
							m=""+(month1+1);
						}if(day1<10){
							d="0"+day1;
						}else{
							d=""+day1;
						}
						dayOf=year1+"-"+m+"-"+d;
						dayZ.setText(year1+"年"+m+"月"+d+"日");
						stringStr=dayOf;
					}
				}, 
						year1, month1, day1);
				dialog.show();
				break;
			case R.id.timeZ:
				Calendar c1=Calendar.getInstance();
				hour1=c1.get(Calendar.HOUR_OF_DAY);
				minute1=c1.get(Calendar.MINUTE);
				TimePickerDialog dialog1=new TimePickerDialog(this, new OnTimeSetListener() {
					@Override
					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
						hour1=hourOfDay;
						minute1=minute;
						String mi="";
						if(minute1<10){
							mi="0"+minute1;
						}else{
							mi=""+minute1;
						}
						hourOf=hour1+":"+mi;
						timeZ.setText(hour1+"时"+mi+"分");
						stringStr=dayOf+" "+hourOf;
					}
				},
						hour1, minute1, true);
				dialog1.show();
				break;
			}
		}
	}