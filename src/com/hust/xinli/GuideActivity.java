package com.hust.xinli;

import hust.xinli.R;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.hust.xinli.adapter.MyPagerAdapter;

public class GuideActivity extends Activity {
	private ViewPager mViewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		mViewPager=(ViewPager)findViewById(R.id.guide_viewpager);
		
		LayoutInflater mLayoutInflater=LayoutInflater.from(this);
		View view1=mLayoutInflater.inflate(R.layout.guide_one, null);
		View view2=mLayoutInflater.inflate(R.layout.guide_two, null);
		View view3=mLayoutInflater.inflate(R.layout.guide_end, null);
		
		final ArrayList<View> views =new ArrayList<View>();
		views.add(view1);
		views.add(view2);
		views.add(view3);
		
		MyPagerAdapter myPagerAdapter=new MyPagerAdapter(views,this);
		mViewPager.setAdapter(myPagerAdapter);	
		
	}
	
//	@Override    
//	public boolean onKeyDown(int keyCode, KeyEvent event) {  
//		if(keyCode == KeyEvent.KEYCODE_BACK){      
//			return  true;
//		}  
//		return  super.onKeyDown(keyCode, event);     
//	} 
}
