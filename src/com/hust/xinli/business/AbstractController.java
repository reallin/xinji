package com.hust.xinli.business;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

import com.hust.xinli.net.NetAssistant;

public class AbstractController {
	protected static String BASE_URL = "http://etotech.net:8080/psychology";
	//public static String BASE_URL = "http://115.29.142.1:8080/psychology";
	protected static String BASE = "http://etotech.net:8080";
	//public static String BASE = "http://115.29.142.1:8080";
	public NetAssistant netAssistant;
	
	String TAG = "NET";
	
	public AbstractController() {
		netAssistant = new NetAssistant();
	}

	/**
	 * 閺嶈宓佹潻鏂挎礀JSON鐎涙顑佹稉鎻掑灲閺傤厽妲搁崥锔藉灇閸旓拷 <br/>
	 * 
	 * @param json
	 * @return
	 */
	protected boolean isRequestSucess(String json) {
		try {
			JSONObject jsonObject = new JSONObject(json);
			return jsonObject.optBoolean("success", false);
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}

	}
	
	/**
	 * 閼惧嘲褰囬惄绋跨安娴ｆ挾娈戝☉鍫熶紖. <br/>
	 * @param json
	 * @return
	 */
	protected String getResponseMsg(String json){
		try {
			JSONObject jsonObject = new JSONObject(json);
			return jsonObject.optString("msg");
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 閼惧嘲褰囬惄绋跨安娴ｆ挾娈戦弫鐗堝祦閸愬懎顔� <br/>
	 * @param json
	 * @return
	 */
	protected String getResponseData(String json){
		try {
			JSONObject jsonObject = new JSONObject(json);
			return jsonObject.optString("data");
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	protected String dateFormatter(Date date){
		return new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(date);
	}
}
