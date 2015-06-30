package com.hust.xinli.adapter;

import hust.xinli.R;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.hust.xinli.user.LoginActivity;

public class MyPagerAdapter extends PagerAdapter {
	private ArrayList<View> views;
	private Context mContext;
	
	public MyPagerAdapter(ArrayList<View> views,Context context){
		this.views=views;
		mContext=context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.views.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0==arg1;
	}

	public void destroyItem(View container, int position, Object object){
		((ViewPager)container).removeView(views.get(position));
	}
	
	public Object instantiateItem(View container, int position){
		switch (position) {
		case 3:
			if (mContext != null) {
				ImageButton firstShowImageButton = (ImageButton)views.get(position).findViewById(R.id.guide_start);
				if (firstShowImageButton != null) {
					firstShowImageButton.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub							
							mContext.startActivity(new Intent(mContext,
									LoginActivity.class));
							Activity guideActivity = (Activity) mContext;
							guideActivity.finish();							
						}
					});
				}				
			}		

			break;

		default:
			break;
		}
		((ViewPager)container).addView(views.get(position));
		return views.get(position);
	}

}
