package com.hust.xinli;



import hust.xinli.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.hust.xinli.bean.User;
import com.hust.xinli.business.UserController;
import com.hust.xinli.tool.NetworkConnectStatus;
import com.hust.xinli.user.LoginActivity;

public class StartActivity extends Activity {
	private static final int LOGIN_SUCCESS = 0;
	private static final int LOGIN_USERNAME_FAILURE = 1;
	private static final int LOGIN_PASSWORD_ERROR = 2;
	private static final int NET_ERROR = 3;
	private NetworkConnectStatus mNetworkStatus;
	private UserController userController;	
	private SharedPreferences settings;	
	String username,password;
	String TAG = "login";
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case LOGIN_SUCCESS:
				
				settings.edit().putString("username", username)
						.putString("password", password)
						.putBoolean("isLogin", true)
						.commit();		
				User.username=username;
				User.password=password;
				User.isLogin=true;
				Log.i("user", "login succss:"+User.username+" "+User.password);
				startActivity(new Intent(StartActivity.this, MainActivity.class));
				StartActivity.this.finish();
				break;
			case LOGIN_USERNAME_FAILURE:
				startActivity(new Intent(StartActivity.this, LoginActivity.class));
				StartActivity.this.finish();
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		// UmengUpdateAgent.update(this);
		settings=getSharedPreferences("setting",0);
		if(!mNetworkStatus.isConnectInternet())
			Toast.makeText(getApplicationContext(), "请检查网络", Toast.LENGTH_SHORT).show();
		handler.postDelayed(new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//鍒ゆ柇鏄惁鏄涓�娆″惎鍔�				
				if(settings.getBoolean("first_exec", true)==true)     //绗竴娆℃墦寮�
				{
					Toast.makeText(getApplicationContext(), "first exec", Toast.LENGTH_SHORT).show();
					SharedPreferences.Editor editor=settings.edit();
					editor.putBoolean("first_exec", false);           //鏍囨敞闈炵涓�娆℃墦寮�
					editor.commit();
					startActivity(new Intent(StartActivity.this,GuideActivity.class));//鍒欒烦杞埌寮曞椤甸潰
					StartActivity.this.finish();
				}
				else
				//涓嶆槸绗竴娆℃墦寮�锛�//鍒欒烦杞埌鐧诲綍椤甸潰
				{
					userController = new UserController();
					settings = getSharedPreferences("setting", Context.MODE_PRIVATE);	
					initLogin();
				}
			}			
		}, 1000);        //寤舵椂500ms		
	}
	private void initLogin(){		
		boolean isLogin = settings.getBoolean("isLogin", false);	
		if (isLogin){
			username = settings.getString("username", null);
			password = settings.getString("password", null);
			new Thread(new Runnable() {						
				@Override
				public void run() {
					Log.i("user", "auto login:"+username+" "+password);
						if(userController.login(username, password))
							handler.sendEmptyMessage(LOGIN_SUCCESS);
						else
							handler.sendEmptyMessage(LOGIN_USERNAME_FAILURE);
				}
			}).start();	
		} 
		else
			{
				startActivity(new Intent(StartActivity.this,LoginActivity.class));
				StartActivity.this.finish();
			}
	}
 	
}
