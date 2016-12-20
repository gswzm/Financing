package com.wzm.licai.adapter;


import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wzm.licai.db.AddMoneyDao;
import com.wzm.licai.ui.R;

public class MingXiAdapter extends BaseAdapter{
	private Context context;
	private LayoutInflater inflater;
    private List<Double> listMoney;
    private List<Integer> aids;
    private ViewHolder holder;
    
    public MingXiAdapter(Context context ,List<Double> listMoney,List<Integer> aid) {
        this.context=context;
        this.listMoney=listMoney;
        this.aids=aid;
        inflater=(LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  
    }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listMoney.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		
		if(view==null){
			holder=new ViewHolder();
			view=inflater.inflate(R.layout.mingxi_item, null);
			holder.shouruS=(TextView) view.findViewById(R.id.tv_shouru);
			holder.zhichuZ=(TextView) view.findViewById(R.id.tv_zhichu);
			holder.srId=(TextView) view.findViewById(R.id.tv_shouru_id);
			holder.zcId=(TextView) view.findViewById(R.id.tv_zhichu_id);
			view.setTag(holder);
			
		}else{
			holder=(ViewHolder) view.getTag();
		}
		if(listMoney.get(position)>0){
		  holder.shouruS.setText(listMoney.get(position)+" 收入");
		  holder.zhichuZ.setText("");
		  holder.srId.setText(aids.get(position)+"");
		  holder.zcId.setText("");
		}else if(listMoney.get(position)<0){
			holder.zhichuZ.setText("支出  "+listMoney.get(position)*(-1));
			holder.shouruS.setText("");
			holder.zcId.setText(aids.get(position)+"");
			holder.srId.setText("");
		}
		return view;
	}
	class ViewHolder{
		public ImageView shanchu;
        public ImageView update;
        public TextView shouruS,srId;
        public TextView zhichuZ,zcId;
        public ImageView center;
	}
}
