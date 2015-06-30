package com.hust.xinli.net;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.hust.xinli.bean.User;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

/**
 * <p>
 * TODO
 * </p>
 * 
 * @author Tian
 * @version $Id: NetAssistantImpl.java 2014-3-23 下午2:38:52 Tian $
 */
public class NetAssistant{


	public String urlResult;
	private HttpClient httpClient = null;
	private static final String TAG = "xinli";

	public NetAssistant() {
		httpClient = new DefaultHttpClient();
	}

	public String getViewInfo(){
		
		String url = "http://etotech.net:8080/psychology/mood/overview";
		//String url = AbstractController.BASE_URL+"/mood/overview";
	
		try {
			urlResult = getJsonStringByPost(url, null);
			return urlResult;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public String getJsonStringByPost(String url, List<NameValuePair> params)
			throws NetException {
		HttpPost httpPost = new HttpPost(url);
		int statusCode = 0;
		String result = null;
		
		try {
			if (params != null) {
				httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
				Log.d(TAG, "请求参数：" + params);
			}

			HttpParams httpParams = new BasicHttpParams();
			httpParams.setParameter("http.protocol.handle-redirects", false); // 默认不让重定向，为了拿到重定向地址，这里需要手动处理
			httpPost.setParams(httpParams);

			httpPost.setHeader("Cookie", User.cookie);
			Log.d(TAG, "requestHeaders:" + Arrays.toString(httpPost.getAllHeaders()));
			
			HttpResponse httpResponse = httpClient.execute(httpPost);
			statusCode = httpResponse.getStatusLine().getStatusCode();
			result = EntityUtils.toString(httpResponse.getEntity());
			
			Header cookie = httpResponse
					.getFirstHeader("Set-Cookie");
			if (cookie != null){
				// 如果响应头的Set-Cookie不为空，那么设置Cookie（说明之前失效）
				User.cookie = cookie.getValue();
				Log.i(TAG, "响应SetCookie：" + cookie.toString());
			}
			
			Log.d(TAG, "statusCode:" + statusCode);
			if (statusCode == 200) {
				// 请求成功
				Log.d(TAG, "getJsonStringByPost:" + result);
				return result;
				
			} else if (statusCode == 302) {
				// 如果重定向到302,
				Header locationHeader = httpResponse.getFirstHeader("Location");
				if (locationHeader != null) {
					// 如果location不为空（测试的时候发现如果请求不成功会为空然后直接抛出异常）
					String redirectUrl = locationHeader.getValue();
					if (redirectUrl.contains("login")) {
						// 如果重定向的地址是登陆，说明需要重新登陆
						Log.d(TAG,  "需要重新登录...");
						if(User.username.equals("")||User.password.equals(""))
							return null;
						login(User.username, User.password);
						// 重新执行一遍
						
						// 处理请求参数发生变化的情况，比如carId登录之后
						// java是值传递，因此需要更新
						dealParamChanged(params);
						return getJsonStringByPost(url, params);
					}
					return null;
				} else {
					throw new NetException("服务器响应错误，状态码：" + statusCode);
				}
			} else {
				throw new NetException("服务器响应错误，状态码：" + statusCode);
			}
		} catch (UnsupportedEncodingException e) {
			throw new NetException("URL编码异常：" + e.getMessage());
		} catch (ParseException e) {
			throw new NetException("返回结果解析异常：" + e.getMessage());
		} catch (IOException e) {
			throw new NetException("IO访问异常：" + e.getMessage());
		}

	}


	public boolean login(String username, String password) throws NetException {
		if(username.equals("")||password.equals(""))
			return false;
		Log.d(TAG, "登录...");
		//String url = "http://etotech.net:8080/psychology/login";         //登录用的url
		String url = "http://etotech.net:8080/psychology/login";
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("j_username", username));
		params.add(new BasicNameValuePair("j_password", password));

		try {
			httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			// 设置cooike
			HttpParams httpParams = new BasicHttpParams();

			httpParams.setParameter("http.protocol.handle-redirects", false); // 默认不让重定向，为了拿到重定向地址，这里需要手动处理
			httpPost.setParams(httpParams);

			HttpResponse httpResponse = httpClient.execute(httpPost);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			String result = EntityUtils.toString(httpResponse.getEntity());
			
			Log.d(TAG, "statusCode:" + statusCode);
			if (statusCode == 302) {
				// 如果重定向意味着登陆成功
					Log.d(TAG, "登录成功！");
					Header cookie = httpResponse.getFirstHeader("Set-Cookie");
					Log.i(TAG, "Cookie:" + cookie.getValue());

					// 保存用户名密码cookie
					User.username = username;
					User.password = password;
					User.cookie = cookie.getValue();
					User.isLogin=true;
					getLoginerInfo();					
					
					return true;
			} else if(statusCode == 200){
				JSONObject jsonObject = new JSONObject(result);
				boolean isSuccess = jsonObject.optBoolean("success");
				String msg = jsonObject.optString("msg");
				if (!isSuccess){
					Log.d(TAG, "登录失败");
					return false;
				} else {
					throw new NetException("未知错误");
				}
				
			} else {
				throw new NetException("网络错误");
			}
		} catch (UnsupportedEncodingException e) {
			throw new NetException("URL编码异常：" + e.getMessage());
		} catch (ParseException e) {
			throw new NetException("返回结果解析异常：" + e.getMessage());
		} catch (IOException e) {
			throw new NetException("IO访问异常：" + e.getMessage());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private void getLoginerInfo() throws NetException{
		String result="";
		try {
			result = getJsonStringByPost("http://etotech.net:8080/psychology" + "/user/getLoginerInfo", null);
			Log.i("record", result);
		} catch (NetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		JSONObject obj;
		try {
			obj = new JSONObject(result);
			boolean success=obj.getBoolean("isSuccess");
			if(!success)
				return ;
			JSONObject account=obj.getJSONObject("data").getJSONObject("account");	
			Log.i("account", "ceshi"+account.getInt("accountType"));
			User.category = account.getInt("accountType");
			Log.i("account", User.category+"");
			User.nickname=account.getString("nickname");
			User.birthday=account.getString("birthday");
			User.signature=account.getString("signature");
			User.isBoy=account.getBoolean("sex");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	
	/**
	 * TODO:Method. <br/>
	 * @param params
	 */
	private void dealParamChanged(List<NameValuePair> params) {
		if(params == null) {
			return;
		}
		int len = params.size();
		int carIdPos = -1; // 没找到之前置为-1
		for (int i = 0; i < len; i++){
			if (params.get(i).getName().equals("carId")){
				carIdPos = i;
				// 找到之后就可以退出了，不必再循环
				break;
			} 
		}
		if (carIdPos != -1){
			// 移除原来的内容，新增
			params.remove(carIdPos);
		}
	}
	
}
