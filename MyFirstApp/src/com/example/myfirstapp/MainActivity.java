package com.example.myfirstapp;

import java.util.ArrayList;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.Window;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

@SuppressLint("SetJavaScriptEnabled")
public class MainActivity extends Activity implements OnTabChangeListener {
	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
	public final static String CAFETERIA_LIST_URL = "http://14.63.161.205:9000";
	public final static String MENU_LIST_URL = "http://14.63.161.205:9000/application/menu";
	
	private TabHost tabHost;
	private WebView wv;
	private WebView wv2;
	private int currentTab = 0;
	private String ids = "1111";

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);
		
		TextView tv = (TextView)this.findViewById(R.id.title);
		tv.setTypeface(Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL)); //"sans-serif", Typeface.NORMAL));
		
		makeIds();
		
		wv = (WebView) findViewById(R.id.clist);
		wv.getSettings().setJavaScriptEnabled(true);
		wv.loadUrl(CAFETERIA_LIST_URL + "?ids=" + ids);
		wv.setWebViewClient(new WebViewClient());

		wv2 = (WebView) findViewById(R.id.cmenu);
		wv2.getSettings().setJavaScriptEnabled(true);
		wv2.loadUrl(MENU_LIST_URL);
		wv2.setWebViewClient(new WebViewClient());
		
		ArrayList<String> al = new ArrayList<String>();
		al.add("Manage Cafeteria List");
		al.add("View Manual");
		al.add("Developer Information");

		ListView lv = (ListView) this.findViewById(R.id.listView1);
		CustomListAdaptor cla = new CustomListAdaptor(this, R.layout.custom_list, al);
		//ArrayAdapter<CharSequence> aa;
		//aa = ArrayAdapter.createFromResource(this, R.array.setting_select, android.R.layout.simple_list_item_1);
		lv.setAdapter(cla);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {
				// TODO Auto-generated method stub
				System.out.println(position);
				if (position == 0) {
					Intent i = new Intent(getApplicationContext(), ManageListActivity.class);
					startActivity(i);
				} else if(position == 1) {
					Intent i = new Intent(getApplicationContext(), ManageListActivity.class);
					startActivity(i);
				} else {
					Intent i = new Intent(getApplicationContext(), DeveloperInfoActivity.class);
					startActivity(i);
				}
			}
			
		});

		tabHost = (TabHost) findViewById(R.id.tabHost);
		tabHost.setup();

		TabHost.TabSpec spec = tabHost.newTabSpec("clist");
		spec.setContent(R.id.clist);
		spec.setIndicator("",
				this.getResources().getDrawable(R.drawable.icon_list));
		tabHost.addTab(spec);

		spec = tabHost.newTabSpec("cmenu");
		spec.setContent(R.id.cmenu);
		spec.setIndicator("",
				this.getResources().getDrawable(R.drawable.icon_menu));
		tabHost.addTab(spec);

		spec = tabHost.newTabSpec("settings");
		spec.setContent(R.id.settings);
		spec.setIndicator("",
				this.getResources().getDrawable(R.drawable.icon_setting));
		tabHost.addTab(spec);

		for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
			View cView = tabHost.getTabWidget().getChildAt(i);
			cView.setBackgroundDrawable(this.getResources().getDrawable(
					R.drawable.tapstrip));
		}

		tabHost.setOnTabChangedListener(this);
		/*tabHost.getTabWidget().getChildAt(0).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				wv.loadUrl(CAFETERIA_LIST_URL);
			}
		});
		tabHost.getTabWidget().getChildAt(1).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				wv2.loadUrl(MENU_LIST_URL);
			}
		});*/

		tabHost.setCurrentTab(0);
		this.currentTab = 0;
		tabHost.getTabWidget()
				.getChildAt(0)
				.setBackgroundDrawable(
						this.getResources().getDrawable(R.drawable.tapstrip2));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onTabChanged(String tabId) {
		for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
			View cView = tabHost.getTabWidget().getChildAt(i);
			cView.setBackgroundDrawable(this.getResources().getDrawable(
					R.drawable.tapstrip));
		}
		tabHost.getTabWidget()
		.getChildAt(tabHost.getCurrentTab())
		.setBackgroundDrawable(
				this.getResources().getDrawable(R.drawable.tapstrip2));
		
		TextView tv = (TextView) this.findViewById(R.id.title);

		if (tabId.equals("clist")) {
			this.currentTab = 0;
			tv.setText(R.string.title_cafeteria);
			makeIds();
			wv.loadUrl(CAFETERIA_LIST_URL + "?ids=" + ids);

		} else if (tabId.equals("cmenu")) {
			this.currentTab = 1;
			tv.setText(R.string.title_menu);
			wv2.loadUrl(MENU_LIST_URL);

		} else {
			this.currentTab = 2;
			tv.setText(R.string.title_settings);
		}
	}
	
	public void onBackPressed() {
		switch (this.currentTab) {
		case 0:
			if(wv.canGoBack()) wv.goBack();
			else super.onBackPressed();
			break;
		case 1:
			if(wv2.canGoBack()) wv2.goBack();
			else super.onBackPressed();
			break;
		default:
			super.onBackPressed();
			break;
		}
	}
	
	public void makeIds() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		this.ids = "";
		
		if (prefs.getBoolean("north_cafe_check", true)) ids += "1";
		else ids += "0";
		if (prefs.getBoolean("north_meilu_check", true)) ids += "1";
		else ids += "0";
		if (prefs.getBoolean("east_cafe_check", true)) ids += "1";
		else ids += "0";
		if (prefs.getBoolean("west_cafe_check", true)) ids += "1";
		else ids += "0";
	}
}
