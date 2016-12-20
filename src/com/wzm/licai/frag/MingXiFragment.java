package com.wzm.licai.frag;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wzm.licai.adapter.MingXiAdapter;
import com.wzm.licai.bean.AddMoney;
import com.wzm.licai.bean.ZhangBen;
import com.wzm.licai.db.AddMoneyDao;
import com.wzm.licai.db.WalletDao;
import com.wzm.licai.db.ZhangbenDao;
import com.wzm.licai.ui.LoginActivity;
import com.wzm.licai.ui.R;
import com.wzm.licai.ui.UserActivity;

public class MingXiFragment extends Fragment {
	private ListView lv;
	private ImageView touxiang,spup;
	private LinearLayout ll,zhangb_ll;
	private TextView zhangb,month_sr,month_zc,day_sr,day_zc,rili,tv_month,tv_day;
    private TextView addZB;
    private boolean flag=true;
    private int year1,month1,day1,hour,minute; 
    //明细列表
    private AddMoneyDao adao;
    private List<Double> listM,listDay,listZB;
    private String  month;
    //删除修改明细
    private WalletDao wdao;
    private TextView srId,zcId;
    private List<Integer> listId;
    private double qianItem;
    private int temp=0;
   
    //账本列表
    private List<ZhangBen> list;
    private Map<String,Integer> map;
    private List<TextView> tvs;
    private ZhangbenDao dao;
    private SharedPreferences sp,sp1;
    public static final String fZhang="zhangben.xml";
    private Editor editor;
    private ZhangBen zBen;
    private TextView tv2;
    private int zid;
   @Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_mingxi, container, false);
	   return view;
}
   @Override
public void onStart() {
	super.onStart();
	sp=getActivity().getSharedPreferences(LoginActivity.fName, Context.MODE_APPEND);
    sp1=getActivity().getSharedPreferences(fZhang, Context.MODE_PRIVATE);
    editor=sp1.edit();
    dao=new ZhangbenDao(getActivity());
    //明细列表
    adao=new AddMoneyDao(getActivity());
    wdao=new WalletDao(getActivity());
    initView(getView());
    
}
   private void initView(View view){
	 //明细列表
	   lv=(ListView) view.findViewById(R.id.lv);
	   month_sr=(TextView) view.findViewById(R.id.month_sr);
	   month_zc=(TextView) view.findViewById(R.id.month_zc);
	   day_sr=(TextView) view.findViewById(R.id.day_sr);
	   day_zc=(TextView) view.findViewById(R.id.day_zc);
	   //显示日历
	   rili=(TextView)view.findViewById(R.id.rilitv);
	   rili.setOnClickListener(new MyOnClickListener());
	   tv_month=(TextView)view.findViewById(R.id.tv_month);
	   tv_day=(TextView)view.findViewById(R.id.tv_day);
	   SimpleDateFormat  sDateFormat=   new    SimpleDateFormat("dd");       
	   String  date = sDateFormat.format(new  java.util.Date());
	   SimpleDateFormat  sMonthFormat=   new    SimpleDateFormat("MM");       
	   month = sMonthFormat.format(new  java.util.Date());
	   rili.setText(date);
	   tv_day.setText(date);
	   tv_month.setText(month+"月");
	   //显示个人信息
	   touxiang=(ImageView) view.findViewById(R.id.touxiang);
	   touxiang.setOnClickListener(new MyOnClickListener());
	   //账本列表
	   map=new HashMap<String, Integer>();
	   tvs=new ArrayList<TextView>();
	   spup=(ImageView) view.findViewById(R.id.spup);
	   ll=(LinearLayout) view.findViewById(R.id.ll_zb);
	   zhangb_ll=(LinearLayout) view.findViewById(R.id.zhangb_ll);
	   addZB=(TextView) view.findViewById(R.id.addZB);
	   addZB.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			View view=getActivity().getLayoutInflater().inflate(
					 R.layout.zhichu_type, null);
			 final EditText et_type=(EditText)view.findViewById(R.id.et_type);
			 
			new AlertDialog.Builder(getActivity())
			.setTitle("请输入")
			.setView(view)
			.setCancelable(false)
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					String et=et_type.getText().toString();
					dao.insertZhangBen(et, sp.getString("uid", ""));
				}
			})
			.setNegativeButton("取消", null)
			.create().show();
			
		}
	});
	   zhangb=(TextView) view.findViewById(R.id.tv_zhangb);
	   list=dao.selectZhangBen(sp.getString("uid", ""));
	   for(ZhangBen zb:list){
		   zBen=zb;
		   tv2=new TextView(getActivity());
		   tv2.setText(zBen.getName());
		   tv2.setBackgroundResource(R.drawable.zhangb);
		   tv2.setTextColor(Color.WHITE);
		   tv2.setPadding(3, 14, 3, 0);
		   tv2.setTextSize(10);
		   tvs.add(tv2);
		   map.put(zBen.getName(),zBen.getId());
		   zhangb_ll.addView(tv2);
		   if(zBen.getName().equals("生意账本")){
			   zhangb.setText(zBen.getName());
			   editor.putString("zid", zBen.getId()+"");
			   editor.putString("zName", zBen.getName());
			   editor.commit();
			   zid=zBen.getId();
			   panduan(month,zid);
		   }else{
			   zhangb.setText("选择账本");
			   month_sr.setText("");
			   month_zc.setText("");
			   day_sr.setText("");
			   day_zc.setText("");
			   lv.setAdapter(null);
		   }
	   }
	   
	   for(final TextView t:tvs){
		        //切换账本
				t.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						zhangb.setText(t.getText().toString());
						zid=map.get(t.getText().toString());
						//保存账本id
						editor.putString("zid", map.get(t.getText().toString())+"");
						editor.putString("zName", t.getText().toString());
					    editor.commit();
					    //查询账本
					   panduan(month,zid);
					}
				});
				//删除zhangben
				t.setOnLongClickListener(new OnLongClickListener() {
					@Override
					public boolean onLongClick(View v) {
						new AlertDialog.Builder(getActivity())
						.setTitle("删除")
						.setMessage("是否确定删除")
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								zid=map.get(t.getText().toString());
								adao.deleteZZb(zid+"");
								dao.deleteZhangBen(zid+"");
							}
						})
						.setNegativeButton("取消", null)
						.create().show();
						
						return true;
					}
				});
	   }
	   lv.setOnItemClickListener(new MyOnItemClick());
	  
	   ll.setOnClickListener(new MyOnClickListener());
   }
   
   /**
    * 显示listview
    */
   private void panduan(String month,int zid){
	   listM= adao.selectMoneyByZhangBen(zid+"",month);
	   listId=adao.selectId(month,zid+"");
	   if((listM==null||listM.size()<=0)||(listId==null||listId.size()<=0)){
		   month_sr.setText("");
		   month_zc.setText("");
		   day_sr.setText("");
		   day_zc.setText("");
		   lv.setAdapter(null);
		   Toast.makeText(getActivity(), "当前账本没有记录",
		    		Toast.LENGTH_SHORT).show();
	   }else{
		   double moneyMs=0;
		   double moneyMz=0;
		   for(double m:listM){
			   if(m>0){
				   moneyMs+=m;
			   }else if(m<0){
				   moneyMz+=m;
			   }
		   }
		   month_sr.setText(moneyMs+" 收入");
		   if(moneyMz==0){
			   month_zc.setText("支出  "+moneyMz);
		   }else{
			   month_zc.setText("支出  "+moneyMz*(-1)); 
		   }
		   String yue=tv_month.getText().toString();
		   char[] c=yue.toCharArray();
		   String yues=String.valueOf(c, 0, 2);
		   String time=yues+tv_day.getText().toString();
		   listDay=adao.selectMoneyByDay(time,zid+"");
		   if(listDay==null||listDay.size()<=0){
			   day_sr.setText("");
			   day_zc.setText("");
			   lv.setAdapter(null);
		   }else{
			   double moneyDs=0;
			   double moneyDz=0;
			   for(double d:listDay){
				   if(d>0){
					   moneyDs+=d;
				   }else if(d<0){
					   moneyDz+=d;
				   }
			   }
			   day_sr.setText(moneyDs+" 收入");
			   if(moneyDz==0){
				   day_zc.setText("支出  "+moneyDz);
			   }else{
				   day_zc.setText("支出  "+moneyDz*(-1)); 
			   }
		   lv.setAdapter(new MingXiAdapter(getActivity(), listDay,listId)); 
		   }
	   }
		  
   }
   private class MyOnItemClick implements OnItemClickListener{

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		    ImageView delete=(ImageView) view.findViewById(R.id.delete);
		    ImageView update=(ImageView) view.findViewById(R.id.update);
		    if(flag){
		    	delete.setVisibility(View.VISIBLE);
			    update.setVisibility(View.VISIBLE);
			    flag=false;
		    }else{
		    	delete.setVisibility(View.GONE);
			    update.setVisibility(View.GONE);
			    flag=true;
		    }
		    delete.setOnClickListener(new MyOnClickListener());
		    update.setOnClickListener(new MyOnClickListener());
		    srId=(TextView) view.findViewById(R.id.tv_shouru_id);
			zcId=(TextView) view.findViewById(R.id.tv_zhichu_id);
			qianItem=listDay.get(position);
		    
	}
	   
   }
   private class MyOnClickListener implements OnClickListener{
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.ll_zb:
			if(flag){
			spup.setImageResource(R.drawable.sppiner1);
			zhangb_ll.setVisibility(View.GONE);
			addZB.setVisibility(View.GONE);
			flag=false;
			}else{
				spup.setImageResource(R.drawable.sppiner2);
				zhangb_ll.setVisibility(View.VISIBLE);
				addZB.setVisibility(View.VISIBLE);
				flag=true;
			}
			break;
		case R.id.touxiang:
			startActivity(new Intent(getActivity(), UserActivity.class));
			break;
		case R.id.rilitv:
			Calendar c=Calendar.getInstance();
			  year1=c.get(Calendar.YEAR);
			  month1=c.get(Calendar.MONTH);
			  day1=c.get(Calendar.DAY_OF_MONTH);
			DatePickerDialog dialog=new DatePickerDialog(getActivity(), new OnDateSetListener() {
				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear,
						int dayOfMonth) {
					    year1=year;
					    month1=monthOfYear;
					    day1=dayOfMonth;
					    String tv_m="";
					    String tv_d="";
					    if(day1<10){
					    	tv_d="0"+day1;
					    }else{
					    	tv_d=""+day1;
					    }
						if((month1+1)<10){
							tv_m="0"+(month1+1);
						}else{
							tv_m=""+(month1+1);
						}
						tv_day.setText(tv_d);
					    rili.setText(tv_d);
						tv_month.setText(tv_m+"月");
						System.out.println(tv_m+"ddddddddd");
						listM=adao.selectMoneyByZhangBen(zid+"", tv_m+"");
						if(listM==null||listM.size()<=0){
							month_sr.setText("");
							month_zc.setText("");
							day_sr.setText("");
							day_zc.setText("");
							lv.setAdapter(null);
						}else{
							double moneyMs=0;
							double moneyMz=0;
							   for(double m:listM){
								   if(m>0){
									   moneyMs+=m;
								   }else if(m<0){
									   moneyMz+=m;
								   }
							   }
							   month_sr.setText(moneyMs+" 收入");
							   if(moneyMz==0){
								   month_zc.setText("支出  "+moneyMz);
							   }else{
								   month_zc.setText("支出  "+moneyMz*(-1)); 
							   }
							   System.out.println(tv_m+"yueqqqs");
							   String time=tv_m+tv_d;
							   System.out.println("timeqq"+time);
							   listDay=adao.selectMoneyByDay(time,zid+"");
							   System.out.println(listDay);
							   if(listDay==null||listDay.size()<=0){
								   day_sr.setText("");
								   day_zc.setText("");
								   lv.setAdapter(null);
							   }else{
								   double moneyDs=0;
								   double moneyDz=0;
								   for(double d:listDay){
									   if(d>0){
										   moneyDs+=d;
									   }else if(d<0){
										   moneyDz+=d;
									   }
								   }
								   day_sr.setText(moneyDs+" 收入");
								   if(moneyDz==0){
									   day_zc.setText("支出  "+moneyDz);
								   }else{
									   day_zc.setText("支出  "+moneyDz*(-1)); 
								   }
							   lv.setAdapter(new MingXiAdapter(getActivity(),listDay,listId)); 
							   } 
						}
				}
			}, 
					c.get(Calendar.YEAR), c.get(Calendar.MONTH),
					c.get(Calendar.DAY_OF_MONTH));
			dialog.show();
			break;
		case R.id.delete:
			new AlertDialog.Builder(getActivity())
			.setTitle("删除")
			.setMessage("是否确定删除")
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if(qianItem>=0){
						int w=adao.selectWid(srId.getText().toString());
						wdao.updateWallet("-"+qianItem, w+"");
						adao.deleteItem(srId.getText().toString());
					}else{
						int w=adao.selectWid(zcId.getText().toString());
						wdao.updateWallet(""+qianItem*(-1), w+"");
						adao.deleteItem(zcId.getText().toString());
					}
					initView(getView());
				}
			})
			.setNegativeButton("取消", null)
			.create().show();
			
			break;
		case R.id.update:
			View view=getActivity().getLayoutInflater().inflate(R.layout.zhichu_type, null);
			final EditText et=(EditText) view.findViewById(R.id.et_type);
			if(qianItem<0){
				et.setText((qianItem*(-1))+"");
				et.setSelection(((qianItem*(-1))+"").length());
				temp=0;
			}else{
				et.setText(qianItem+"");
				et.setSelection((qianItem+"").length());
				temp=1;
			}
			
			new AlertDialog.Builder(getActivity())
			.setTitle("修改")
			.setView(view)
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					String xiugai="";
					if(temp==1){
						xiugai=et.getText().toString();
						double xx=Double.parseDouble(xiugai);
						adao.updateItem(xiugai, srId.getText().toString());
						int w=adao.selectWid(srId.getText().toString());
						wdao.updateWallet(""+(xx-qianItem), w+"");
					}if(temp==0){
						xiugai="-"+et.getText().toString();
						double xx=Double.parseDouble(xiugai);
						adao.updateItem(xiugai, zcId.getText().toString());
						int w=adao.selectWid(srId.getText().toString());
						wdao.updateWallet(""+((-qianItem)-xx), w+"");
					}
					initView(getView());
				}
			})
			.setNegativeButton("取消", null)
			.create().show();
			
			break;
		}
	}
	   
   }
}
