package am2.utils;

import java.util.List;

import am1.items.Memo;
import am1.main.R;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MemoListAdapter extends ArrayAdapter<Memo> {

	// Inflater
	LayoutInflater inflater;

	//
	Activity actv;

	public MemoListAdapter(Context context, int resourceId, List<Memo> list) {
		super(context, resourceId, list);

		this.actv = (Activity) context;

		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		/*----------------------------
		 * Steps
		 * 1. Get view
		 * 2. Get item
		 * 3. Set data to view
			----------------------------*/
		/*----------------------------
		 * 1. Get view
			----------------------------*/
		View v;

    	if (convertView != null) {
			v = convertView;
		} else {//if (convertView != null)

			v = inflater.inflate(R.layout.list_row_memos, null);

		}//if (convertView != null)

    	/*----------------------------
		 * 2. Get item
			----------------------------*/
    	Memo m = (Memo) getItem(position);

    	/*----------------------------
		 * 3. Set data to view
			----------------------------*/
    	// Text
		TextView tv_text = (TextView) v.findViewById(R.id.list_row_memos_tv_activity_text);
    	
		tv_text.setText(m.getText());
		
    	// Created at
		TextView tv_created_at = 
				(TextView) v.findViewById(R.id.list_row_memos_tv_created_at);
		tv_created_at.setText(Methods.convert_millSec2digitsLabel(m.getCreated_at()));
		
		// Modified at
		TextView tv_modified_at = 
				(TextView) v.findViewById(R.id.list_row_memos_tv_modified_at);

		String modified_time = Methods.convert_millSec2digitsLabel(m.getModified_at());
			
		tv_modified_at.setText(modified_time);

		
		return v;

	}//public View getView(int position, View convertView, ViewGroup parent)

}
