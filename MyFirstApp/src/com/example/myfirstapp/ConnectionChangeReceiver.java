package com.example.myfirstapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

public class ConnectionChangeReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {

		/*
		 * ConnectivityManager connectivityManager = (ConnectivityManager)
		 * context .getSystemService(Context.CONNECTIVITY_SERVICE); NetworkInfo
		 * activeNetInfo = connectivityManager.getActiveNetworkInfo();
		 * NetworkInfo mobNetInfo = connectivityManager
		 * .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		 * 
		 * if ( activeNetInfo == null ) {
		 * 
		 * //Toast.makeText( context, // "Active Network Type : " +
		 * activeNetInfo.getTypeName(), // Toast.LENGTH_LONG ).show(); }
		 * Toast.makeText(context, "wifi disconnected",
		 * Toast.LENGTH_LONG).show();
		 * 
		 * /* if (mobNetInfo != null) { Toast.makeText(context,
		 * "Mobile Network Type : " + mobNetInfo.getTypeName(),
		 * Toast.LENGTH_SHORT).show();
		 * 
		 * }
		 * 
		 * }
		 */

		if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
			NetworkInfo networkInfo = intent
					.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
			if (networkInfo.isConnected()) {
				// Wifi is connected
				Toast.makeText(context, "wifi connected",
						  Toast.LENGTH_LONG).show();
			}
		} else if (intent.getAction().equals(
				ConnectivityManager.CONNECTIVITY_ACTION)) {
			NetworkInfo networkInfo = intent
					.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
			if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI
					&& !networkInfo.isConnected()) {
				// Wifi is disconnected
				Toast.makeText(context, "wifi disconnected",
						  Toast.LENGTH_LONG).show();
			}

		}
	}
}