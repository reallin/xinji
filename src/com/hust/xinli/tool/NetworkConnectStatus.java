package com.hust.xinli.tool;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 检查网络是否连接״̬
 *
 */
public class NetworkConnectStatus {
	
	private Activity activity;
	
	public NetworkConnectStatus(Activity activity) {
		this.activity = activity;
	}
	
	public boolean isConnectInternet() {
		boolean netSataus = false;
		ConnectivityManager conManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
		if(networkInfo != null) { //一定要判空
			netSataus = networkInfo.isAvailable();
		}
		return netSataus;
	}

}
