package com.wzm.licai.ui;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.wzm.licai.bean.AddMoney;
import com.wzm.licai.bean.Wallet;
import com.wzm.licai.db.AddMoneyDao;
import com.wzm.licai.db.WalletDao;
import com.wzm.licai.frag.MingXiFragment;

public class ShouRuActivity extends Activity implements OnClickListener {
    private EditText shouruEt,markS;
    private Spinner typeS,qianbaoS;
    private TextView timeS,dayS;
    private Button sure,cancel;
    private String[] types={"����","��ְ","����"};
    private List<String> typess;
    private String type,dayOf,hourOf,stringStr;
    int year1,month1,day1,hour1,minute1;
    //Ǯ��
    private List<Wallet> listW;
    private WalletDao wdao;
    private int wid;
    //����
    private AddMoneyDao adao;
    private SharedPreferences sp,sp1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_shouru);
	    adao=new AddMoneyDao(this);
	    sp=getSharedPreferences(LoginActivity.fName, Context.MODE_APPEND);
	    sp1=getSharedPreferences(MingXiFragment.fZhang, Context.MODE_APPEND);
		initView();
	}
	private void initView(){
		//���
		shouruEt=(EditText)findViewById(R.id.shouruS);
		//��ע
		markS=(EditText)findViewById(R.id.markS);
		//ʱ��
		timeS=(TextView) findViewById(R.id.timeS);
		dayS=(TextView) findViewById(R.id.dateS);
		dayS.setOnClickListener(this);
		timeS.setOnClickListener(this);
		final long time=System.currentTimeMillis();
		SimpleDateFormat sDay=new SimpleDateFormat("yyyy-MM-dd");
		String day=sDay.format(time);
		SimpleDateFormat sHour=new SimpleDateFormat("HH:mm");
		String hour=sHour.format(time);
		dayS.setText(day);
		timeS.setText(hour);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		stringStr=format.format(time);
		typeS=(Spinner) findViewById(R.id.typeS);
		qianbaoS=(Spinner) findViewById(R.id.walletS);
		//
		wdao=new WalletDao(this);
		listW=wdao.seletWallet(sp.getString("uid", ""));
	    final Map<String,Integer> map=new HashMap<String,Integer>();
		final List<String> names=new ArrayList<String>();
		Wallet wall=null;
		for(Wallet w: listW){
			wall=w;
			names.add(wall.getName());
			map.put(wall.getName(), wall.getId());
		}
		qianbaoS.setAdapter(new ArrayAdapter<String>(this, 
				android.R.layout.simple_spinner_item,names));
		qianbaoS.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				   wid=map.get(names.get(position));
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
		typess=new ArrayList<String>();
		typess.add("����");
		typess.add("��ְ");
		typess.add("����");
		typeS.setAdapter(new ArrayAdapter<String>(this, 
				android.R.layout.simple_spinner_item,typess));
		
		typeS.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				if(typess.get(position).equals("����")){
					 View view1=getLayoutInflater().inflate(
							 R.layout.zhichu_type, null);
					 final EditText et_type=(EditText)view1.findViewById(R.id.et_type);
					 
					new AlertDialog.Builder(ShouRuActivity.this)
					.setTitle("������")
					.setView(view1)
					.setCancelable(false)
					.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
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
					.setNegativeButton("ȡ��", null)
					.create().show();
					
				}else{
				     type=typess.get(position);
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
		sure=(Button) findViewById(R.id.bt_login);
		cancel=(Button) findViewById(R.id.bt_regist);
		sure.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String m=shouruEt.getText().toString();
				double money=Double.parseDouble(m);
				String mark=markS.getText().toString();
				String uid=sp.getString("uid", "");
				String zid=sp1.getString("zid", "");
				if(m.equals("")){
					 Toast.makeText(getApplicationContext(),
							   "��������", 0).show();
				}else{
				AddMoney amoney=new AddMoney(money, stringStr, type, mark,  wid+"",uid,zid);
				adao.insertMoney(amoney);
				wdao.updateWallet(money+"", wid+"");
				ShouRuActivity.this.finish();
				}
			}
		});
		cancel.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_regist:
			ShouRuActivity.this.finish();
			break;
		case R.id.dateS:
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
					if((month1+1)<10){
						m="0"+(month1+1);
					}else{
						m=""+(month1+1);
					}if(day1<10){
						d="0"+day1;
					}else{
						d=""+day1;
					}
					dayOf=year1+"-"+m+"-"+d;
					dayS.setText(year1+"��"+m+"��"+d+"��");
					stringStr=dayOf;
				}
			}, 
					year1, month1, day1);
			dialog.show();
			break;
		case R.id.timeS:
			Calendar c1=Calendar.getInstance();
			hour1=c1.get(Calendar.HOUR_OF_DAY);
			minute1=c1.get(Calendar.MINUTE);
			TimePickerDialog dialog1=new TimePickerDialog(this, new OnTimeSetListener() {
				@Override
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					hour1=hourOfDay;
					minute1=minute;
					String mi="";
					String ho="";
					if(minute1<10){
						mi="0"+minute1;
					}else{
						mi=""+minute1;
					}if(hour1<10){
						ho="0"+hour1;
					}else{
						ho=""+hour1;
					}
					hourOf=ho+":"+mi;
					timeS.setText(hour1+"ʱ"+mi+"��");
					stringStr=dayOf+" "+hourOf;
				}
			},
					hour1, minute1, true);
			dialog1.show();
			break;
		}
	}
}
