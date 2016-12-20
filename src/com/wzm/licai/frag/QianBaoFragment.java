package com.wzm.licai.frag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wzm.licai.bean.Wallet;
import com.wzm.licai.db.WalletDao;
import com.wzm.licai.item.QianbaoFragmentItem;
import com.wzm.licai.ui.LoginActivity;
import com.wzm.licai.ui.R;
import com.wzm.licai.ui.UserActivity;

public class QianBaoFragment extends Fragment {
	private QianbaoFragmentItem qian,chuxu,xinyong,zhifu,qianbao;
	private LinearLayout ll_qianbao;
	private WalletDao dao;
	private Wallet wallet=null;
	private List<Wallet> wallets;
	private List<String> names;
	private Map<String,Double> map;
	private SharedPreferences sp;
	private ImageView toux;
	private TextView tv1,montv,tv2,tv3,tv4,tv11,tv12,tv13,tv14;
 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
          View view=inflater.inflate(R.layout.frag_qianbao, container, false);
          return view;
 }
 @Override
	public void onStart() {
		super.onStart();
		dao=new WalletDao(getActivity());
		sp=getActivity().getSharedPreferences(LoginActivity.fName, Context.MODE_APPEND);
		initView(getView());
	}
    private void initView(View view) {
    	toux=(ImageView) view.findViewById(R.id.toux);
    	toux.setOnClickListener(new MyOnclick());
    	ll_qianbao=(LinearLayout) view.findViewById(R.id.ll_qianbao);
    	wallets=dao.seletWallet(sp.getString("uid", ""));
    	names=new ArrayList<String>();
    	map=new HashMap<String, Double>();
    	for(Wallet w:wallets){
    		wallet=w;
    		names.add(wallet.getName());
    		map.put(wallet.getName(), wallet.getMoney());
    		//qianbao=new QianbaoFragmentItem(getActivity(), null);
    		
    	}
    	qian=(QianbaoFragmentItem) view.findViewById(R.id.xianjin);
    	chuxu=(QianbaoFragmentItem) view.findViewById(R.id.chuxu);
    	xinyong=(QianbaoFragmentItem) view.findViewById(R.id.xinyong);
    	zhifu=(QianbaoFragmentItem) view.findViewById(R.id.zhifu);
    	
    	tv1=(TextView)qian.findViewById(R.id.tv1);
    	tv2=(TextView)chuxu.findViewById(R.id.tv1);
    	tv3=(TextView)xinyong.findViewById(R.id.tv1);
    	tv4=(TextView)zhifu.findViewById(R.id.tv1);
    	tv1.setText(names.get(0)+"");
    	tv2.setText(names.get(1)+"");
    	tv3.setText(names.get(2)+"");
    	tv4.setText(names.get(3)+"");
    	tv11=(TextView)qian.findViewById(R.id.monTv);
    	tv12=(TextView)chuxu.findViewById(R.id.monTv);
    	tv13=(TextView)xinyong.findViewById(R.id.monTv);
    	tv14=(TextView)zhifu.findViewById(R.id.monTv);
    	tv11.setText(map.get(tv1.getText().toString())+"");
    	tv12.setText(map.get(tv2.getText().toString())+"");
    	tv13.setText(map.get(tv3.getText().toString())+"");
    	tv14.setText(map.get(tv4.getText().toString())+"");
    }
    private class MyOnclick implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(v==toux){
				startActivity(new Intent(getActivity(),UserActivity.class));
			}
		}
    	
    }
}
