package com.wzm.licai.item;

import com.wzm.licai.ui.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class QianbaoFragmentItem extends RelativeLayout {

	private RelativeLayout relativeLayout;
	private ImageView iv;
	private TextView tv_Title;
	private TextView tv_Desc;
	private String text_Title;
	private String text_Desc;
	private int qiaobaoColor;
	private Drawable src;

	public QianbaoFragmentItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub

		initView(context);

		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.item_QianbaoFragment);

		relativeLayout = (RelativeLayout) findViewById(R.id.rl);
		tv_Title = (TextView) findViewById(R.id.tv1);
		tv_Desc = (TextView) findViewById(R.id.tv2);
		iv = (ImageView) findViewById(R.id.iv);

		text_Title = ta.getString(R.styleable.item_QianbaoFragment_firsttext);
		text_Desc = ta.getString(R.styleable.item_QianbaoFragment_secondtext);
		src = ta.getDrawable(R.styleable.item_QianbaoFragment_qianbaosrc);
		qiaobaoColor = ta.getColor(R.styleable.item_QianbaoFragment_backgroud,
				0);

		tv_Title.setText(text_Title);
		tv_Desc.setText(text_Desc);
		iv.setImageDrawable(src);
		relativeLayout.setBackgroundColor(qiaobaoColor);

		// 回收
		ta.recycle();

	}

	/*
	 * 初始化界面
	 */
	public void initView(Context context) {
		View.inflate(context, R.layout.item_qianbaofragment, this);
	}
}
