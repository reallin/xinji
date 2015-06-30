package com.hust.xinli.business;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.hust.xinli.bean.User;
import com.hust.xinli.net.NetException;


/**
 * <p>TODO</p>
 * @author Tian
 * @version $Id: UserController.java 2014-4-10 下午8:23:48 Tian $
 */
public class UserController extends AbstractController {

	/**
	 * Creates a new instance of UserController.
	 * @param context
	 */
	
	public UserController() {
		super();
	}

	/**
	 * 用户登录函数.该请求返回一个302重定向，根据重定向地址判断是否登录成功： 重定向地址不含error的表示登陆成功；重定向地址包含error的
	 * （比如：http://csdc-20:8080/ccar/user/login.jsp?error=5）表示登录失败 <br/>
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws NetException 
	 */
	public boolean login(String username, String password)  {
		try {
			return netAssistant.login(username, password);
		} catch (NetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 注册用户. <br/>
	 * @param username
	 * @param password
	 * @return
	 * @throws NetException
	 */
	public Boolean register(String username, String password, String randomrandom,String nickname) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("password", password));
		params.add(new BasicNameValuePair("nickname", nickname));
		params.add(new BasicNameValuePair("rand", randomrandom));
		params.add(new BasicNameValuePair("accountType", "1"));
		String result="";
		try {
			result = netAssistant.getJsonStringByPost(BASE_URL + "/user/register", params);
		} catch (NetException e1) {
			e1.printStackTrace();
		}
		String msg = getResponseMsg(result);
		Log.e(TAG, "注册msg：" + msg);		
		
		JSONObject obj;
		try {
			obj = new JSONObject(result);
			boolean success=obj.getBoolean("isSuccess");
			return success;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return  false;
	}
	
	public boolean getInfo(){
		String result="";
		try {
			result = netAssistant.getJsonStringByPost(BASE_URL + "/user/getLoginerInfo", null);
		} catch (NetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String msg = getResponseMsg(result);		
		JSONObject obj;
		try {
			obj = new JSONObject(result);
			boolean success=obj.getBoolean("isSuccess");
			if(!success)
				return false;
			JSONObject account=obj.getJSONObject("data").getJSONObject("account");	
			User.nickname=obj.getString("nickname");
			User.birthday=obj.getString("birthday");
			User.signature=obj.getString("signature");
			User.isBoy=obj.getBoolean("sex");
			return true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return  false;
	}
	public boolean updateInfo(String nickname,String signature,String birthday,String isBoy){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("nickname", nickname));
		params.add(new BasicNameValuePair("signature", signature));
		params.add(new BasicNameValuePair("birthday", birthday));
		params.add(new BasicNameValuePair("sex", isBoy));
		String result="";
		try {
			result = netAssistant.getJsonStringByPost(BASE_URL + "/user/modify", params);
		} catch (NetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String msg = getResponseMsg(result);		
		JSONObject obj;
		try {
			obj = new JSONObject(result);
			boolean success=obj.getBoolean("isSuccess");
			return success;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return  false;
	}
	
	public boolean resetPassword(String oldPwd,String newPwd){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("oldPwd", oldPwd));
		params.add(new BasicNameValuePair("newPwd", newPwd));
		String result="";
		try {
			result = netAssistant.getJsonStringByPost(BASE_URL + "/user/resetPwd", params);
		} catch (NetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String msg = getResponseMsg(result);		
		JSONObject obj;
		try {
			obj = new JSONObject(result);
			boolean success=obj.getBoolean("isSuccess");
			return success;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return  false;
	}
	
	public boolean sendPwdRand(String username){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username", username));

		String result="";
		try {
			result = netAssistant.getJsonStringByPost(BASE_URL + "/user/sendPwdRand", params);
		} catch (NetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String msg = getResponseMsg(result);
		JSONObject obj;
		try {
			obj = new JSONObject(result);
			boolean success=obj.getBoolean("isSuccess");
			return success;
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return  false;
	}
	
	public boolean restrievePassword(String username,String newPwd,String randomrandom){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("newPwd", newPwd));
		params.add(new BasicNameValuePair("rand", randomrandom));
		String result="";
		try {
			//result = netAssistant.getJsonStringByPost(BASE_URL + "/user/retrievePwd", params);
			result = netAssistant.getJsonStringByPost(BASE_URL + "/user/resetPwdbyRand", params);
		} catch (NetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String msg = getResponseMsg(result);	
		Log.e("errormsg", msg);
		JSONObject obj;
		try {
			obj = new JSONObject(result);
			boolean success=obj.getBoolean("isSuccess");
			return success;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return  false;
	}
	/**
	 * 登出，通过cookie保存状态信息，木有返回值
	 * 这里需不需要返回success字段. <br/>
	 * @throws NetException
	 */
	public void logout() {
		try {
			String result =netAssistant.getJsonStringByPost(BASE_URL + "/logout", null);
			Log.d("logout", "logout:" + result);
		} catch (NetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 请求服务器发送注册短信验证码. <br/>
	 * @throws NetException
	 */
	public int sendRegisterRand(String phone) throws NetException{
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username", phone));
		String result = netAssistant.getJsonStringByPost(BASE_URL + "/user/sendRegisterRand", params);
		String msg = getResponseMsg(result);
		Log.e(TAG, "sendRegisterRand msg:" + msg);
		//Log.e("errormsg", "sendRegisterRand msg:" + msg);
		if (msg.equals("send successfully")){
			return 0;
		} else if (msg.contains("exist")){
			return 1;
		} else if(msg.contains("not")){
			return 2;
		} else {
			return 3;
		}
	}
	
	public boolean checkRegisterRand(String random) throws NetException{
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("random", random));
		String result = netAssistant.getJsonStringByPost(BASE_URL + "/user/checkRegisterRand", params);
		String msg = getResponseMsg(result);
		if (msg == "验证码正确"){
			
			return true;
		} else if (msg == "验证码错误"){
			
			return false;
		} else {
			
			return false;
		}
	}
}

