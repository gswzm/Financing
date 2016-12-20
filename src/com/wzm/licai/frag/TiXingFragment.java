package com.wzm.licai.frag;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import com.wzm.licai.item.TixingFragmentItem;
import com.wzm.licai.ui.NaoZhongActivity;
import com.wzm.licai.ui.R;
import com.wzm.licai.ui.UserActivity;

public class TiXingFragment extends Fragment {
	private TixingFragmentItem xyk,jizhang,fangzu;
	private Calendar c;
	private AlarmManager a;
	private ImageView tixing_iv;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	          View view=inflater.inflate(R.layout.frag_tixing, container, false);
	          return view;
	}
    @Override
    public void onStart() {
    	super.onStart();
    	c=Calendar.getInstance();
    	a=(AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);
        xyk=(TixingFragmentItem) getView().findViewById(R.id.xinyongka);
        fangzu=(TixingFragmentItem) getView().findViewById(R.id.fangzu);
        jizhang=(TixingFragmentItem) getView().findViewById(R.id.jizhang);
        tixing_iv=(ImageView) getView().findViewById(R.id.tixing_iv);
        tixing_iv.setOnClickListener(new MyOnClickListener());
        xyk.setOnClickListener(new MyOnClickListener());
        fangzu.setOnClickListener(new MyOnClickListener());
        jizhang.setOnClickListener(new MyOnClickListener());
    }	
    private class MyOnClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			if(v==xyk){
				showDialogxyk();
			}else if(v==fangzu){
				showDialogxyk();
			}else if(v==jizhang){
				showDialogxyk();
			}else if(v==tixing_iv){
				startActivity(new Intent(getActivity(), UserActivity.class));
			}
		}
    	
    }
    private void showDialogxyk(){
    	DatePickerDialog dialog=new DatePickerDialog(getActivity(), new OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				c.set(year, monthOfYear,dayOfMonth);
				Toast.makeText(getActivity(), "到期时间"+DateFormat.format("yyy-MM-dd", c), Toast.LENGTH_SHORT).show();
				PendingIntent pi=PendingIntent.getActivity(getActivity(), 0, new Intent(getActivity(),NaoZhongActivity.class),
						PendingIntent.FLAG_UPDATE_CURRENT);
				a.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
			}
		}, 
    			c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
    	dialog.show();
    }
}
