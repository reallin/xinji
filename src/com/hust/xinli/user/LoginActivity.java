package com.hust.xinli.user;

import hust.xinli.R;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import com.hust.xinli.business.UserController;

public class LoginActivity extends Activity {
	private static final int LOGIN_SUCCESS = 0;
	private static final int LOGIN_USERNAME_FAILURE = 1;
	private static final int LOGIN_PASSWORD_ERROR = 2;
	private static final int NET_ERROR = 3;
	private UserController userController;	
	private SharedPreferences setting;	
	private EditText name;
	private EditText pwd;
	String TAG = "user";
	/*private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case LOGIN_SUCCESS:		
				setting.edit().remove("username").remove("password").remove("isLogin").commit();
				setting.edit().putString("username", name.getText().toString())
						.putString("password", pwd.getText().toString())
						.putBoolean("isLogin", true)
						.commit();	
				
				User.isLogin=true;
				Log.i("new", "login succss:"+User.username+" "+User.password);
				if(User.category == 2){
					startActivity(new Intent(LoginActivity.this, PostGraduateGuide.class));
				}else {
					startActivity(new Intent(LoginActivity.this, MainActivity.class));
				}
				LoginActivity.this.finish();
				break;
			case LOGIN_USERNAME_FAILURE:
				Toast.makeText(getApplicationContext(), "用户名或密码错误", Toast.LENGTH_SHORT).show();	
				pwd.setText("");
				break;
			default:
				break;
			}
			super.handleMessage(msg);		}

	};*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);	
		//UmengUpdateAgent.update(this);
		userController = new UserController();
		setting = getSharedPreferences("setting", Context.MODE_PRIVATE);		
		/*initHead();
		initViews();*/
	}

	
	/*private void initViews() {		
		name = (EditText) findViewById(R.id.login_name_edt);
		pwd = (EditText) findViewById(R.id.login_pwd_edt);		
		Button login = (Button) findViewById(R.id.login_login_btn);
		Button register = (Button) findViewById(R.id.login_register_btn);
		Button justtry = (Button) findViewById(R.id.login_try_btn);
		// 让9patch居中！！
		login.setPadding(0, 0, 0, 0);
		register.setPadding(0, 0, 0, 0);
		justtry.setPadding(0, 0, 0, 0);
		OnClickListener clickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.login_login_btn:					
					final String username = name.getText().toString();
					final String password = pwd.getText().toString();
					Log.d(TAG, "name:" + username + "\npwd:" + password);					
					if (username == null || "".equals(username.trim())){
						Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
						return;
					}
					new Thread(new Runnable() {						
						@Override
						public void run() {
								if(userController.login(username, password))
									handler.sendEmptyMessage(LOGIN_SUCCESS);
								else
									handler.sendEmptyMessage(LOGIN_USERNAME_FAILURE);
						}
					}).start();					
					break;
				case R.id.login_register_btn:
					startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
					break;
				case R.id.login_try_btn:
					startActivity(new Intent(LoginActivity.this, MainActivity.class));
					LoginActivity.this.finish();
					break;
				case R.id.login_forget_btn:
					startActivity(new Intent(LoginActivity.this,ForgetPasswordActivity.class));
					break;
				default:
					break;
				}
			}
		};
		login.setOnClickListener(clickListener);
		register.setOnClickListener(clickListener);
		justtry.setOnClickListener(clickListener);
		findViewById(R.id.login_forget_btn).setOnClickListener(clickListener);
	}

	private void initHead() {
		((TextView) findViewById(R.id.top_title_text)).setText("登录");
		((TextView)findViewById(R.id.top_left_btn)).setVisibility(View.INVISIBLE);
		((TextView)findViewById(R.id.top_right_btn)).setVisibility(View.INVISIBLE);
	}*/
	
}
