package com.wzm.licai.pie;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;

import com.wzm.licai.bean.AddMoney;
import com.wzm.licai.db.AddMoneyDao;
import com.wzm.licai.ui.LoginActivity;

public class PieChart {
	
	private DefaultRenderer renderer;
	CategorySeries categorySeries;
	private AddMoneyDao dao;
	private SharedPreferences sp;
	private List<AddMoney> listTy;
	private Set<String> types;
	private List<Double> moneys;
	private Map<String,Double> map;
	private double gongzi,jianzhi,qita;
	public View execute(Context context) {
		sp=context.getSharedPreferences(LoginActivity.fName, Context.MODE_APPEND);
		dao=new AddMoneyDao(context);
		categorySeries = new CategorySeries("dasdas");
		setdate();
		int[] colors = new int[] { Color.RED, Color.YELLOW, Color.BLUE };
		renderer = buildCategoryRenderer(colors);
		
		
		return ChartFactory.getPieChartView(context, categorySeries, renderer);
	}

	private void setdate() {
		String[] typs={"����","��ְ","����"};
		types=new HashSet<String>();
		map=new HashMap<String, Double>();
		listTy=dao.selectAll(sp.getString("uid", ""));
		System.out.println(listTy+"uid"+sp.getString("uid", ""));
		if(listTy==null||listTy.size()<=0){
			categorySeries.add(typs[0], 0);
			categorySeries.add(typs[1], 0);
			categorySeries.add(typs[2], 0);
		}else{
			for(AddMoney am: listTy){
				if(am.getMoney()>=0){
			    types.add(am.getType());
				map.put(am.getType(), am.getMoney());
				}
			}
			for(String str:types){
				if(str.equals(typs[0])){
					moneys=dao.selectByType(typs[0]);
					for(double dm: moneys){
						gongzi+=dm;
					}
					
				}if(str.equals(typs[1])){
					moneys=dao.selectByType(typs[1]);
					for(double dm: moneys){
						jianzhi+=dm;
					}
				}else{
					map.remove(typs[0]);
					map.remove(typs[1]);
				}
				
			}
			Iterator it = map.entrySet().iterator();
			double v=0;
			  while (it.hasNext()) {
			   Map.Entry entry = (Map.Entry) it.next();
			   Object value = entry.getValue();
			   v=(double) value;
			   qita+=v;
			  }
			  
			categorySeries.add(typs[0], gongzi);
			categorySeries.add(typs[1], jianzhi);
			categorySeries.add(typs[2], qita);
		}
	}
	protected DefaultRenderer buildCategoryRenderer(int[] colors) {
		DefaultRenderer renderer = new DefaultRenderer();
		for (int color : colors) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(color);
			renderer.addSeriesRenderer(r);
		}
		// ��ʾ��ǩ
		if(types==null||types.size()<=0){
			renderer.setShowLabels(false);
		}else{
		renderer.setShowLabels(true);
		}
		// ����ʾ�ײ�˵��
		renderer.setShowLegend(false);
		// ���ñ�ǩ�����С
		renderer.setLabelsTextSize(20);
		// ���ñ�ǩ�������ɫ
		renderer.setLabelsColor(Color.BLUE);
		renderer.setZoomEnabled(true);
		// ����ͼ����ͼ�Ƿ�����ƶ�
		renderer.setPanEnabled(false);

		return renderer;
	}
}
