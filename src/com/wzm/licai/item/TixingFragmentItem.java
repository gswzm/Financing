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


public class TixingFragmentItem extends RelativeLayout {

	private ImageView iv;
	private TextView tv;
	private String text;
	private Drawable src;

	public TixingFragmentItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.item_TixingFragment);
		text = ta.getString(R.styleable.item_TixingFragment_text);
		src = ta.getDrawable(R.styleable.item_TixingFragment_src);
		tv = (TextView) findViewById(R.id.tv);
		iv = (ImageView) findViewById(R.id.iv);
		tv.setText(text);
		iv.setBackground(src);

		// 回收
		ta.recycle();

	}

	/*
	 * 初始化界面
	 */
	public void initView(Context context) {
		View.inflate(context, R.layout.item_tixingfragment, this);
	}
}
