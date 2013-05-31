package com.example.myfirstapp;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomListAdaptor extends ArrayAdapter<String> {

	private Context mContext;
	private int id;
	private ArrayList<String> items;

	public CustomListAdaptor(Context context, int textViewResourceId,
			ArrayList<String> objects) {
		super(context, textViewResourceId, objects);
		this.mContext = context;
		this.id = textViewResourceId;
		this.items = objects;
	}

	public View getView(int position, View v, ViewGroup parent) {
		View mView = v;
		if (mView == null) {
			LayoutInflater vi = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			mView = vi.inflate(id, null);
		}

		TextView text = (TextView) mView.findViewById(R.id.setting_text);

		if (items.get(position) != null) {
			text.setTextColor(Color.parseColor("#FF000000"));
			text.setText(items.get(position));
			text.setBackgroundColor(Color.parseColor("#00000000"));
		}
		
		return mView;
	}
}
