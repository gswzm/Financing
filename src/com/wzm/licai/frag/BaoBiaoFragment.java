package com.wzm.licai.frag;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.wzm.licai.pie.PieChart;
import com.wzm.licai.pie.ZCPieChart;
import com.wzm.licai.ui.R;
import com.wzm.licai.ui.UserActivity;

public class BaoBiaoFragment extends Fragment {
	private LinearLayout ll;
	private Switch sw;
	private Button select;
	private ImageView baobiao_iv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
           View view=inflater.inflate(R.layout.frag_baobiao, container, false);
           return view;
    }
    @Override
    public void onStart() {
    	super.onStart();
    	initView(getView());
    }
    private void initView(View view){
    	ll=(LinearLayout) view.findViewById(R.id.ll);
    	final View pieChart = new PieChart().execute(getActivity());
    	final View zcPieChart = new ZCPieChart().execute(getActivity());
		sw=(Switch) view.findViewById(R.id.sw);
		if(sw.isChecked()==false){
			ll.addView(pieChart);
		}
		sw.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					ll.removeView(pieChart);
					ll.addView(zcPieChart);
				}else{
					ll.removeView(zcPieChart);
					ll.addView(pieChart);
				}
			}
		});
    	baobiao_iv=(ImageView) view.findViewById(R.id.baobiao_iv);
    	baobiao_iv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), UserActivity.class));
			}
		});
    }
}
