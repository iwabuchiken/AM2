package am2.utils;

import java.util.List;

import am2.main.*;
import am2.items.*;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ActivityItemListAdapter extends ArrayAdapter<ActivityItem> {

	// Inflater
	LayoutInflater inflater;

	//
	Activity actv;

	public ActivityItemListAdapter(Context context, int resourceId, List<ActivityItem> list) {
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

			v = inflater.inflate(R.layout.list_row_activity_item, null);

		}//if (convertView != null)

    	/*----------------------------
		 * 2. Get item
			----------------------------*/
    	ActivityItem ti = getItem(position);

    	/*----------------------------
		 * 3. Set data to view
		 * 		1. name
		 * 		2. Group name
		 * 		3. created_at
		 * 		4. finished_at
			----------------------------*/
    	/*----------------------------
		 * 3.1. name
			----------------------------*/
		TextView tv_name = (TextView) v.findViewById(R.id.list_row_activity_item_tv_activity_name);
    	
		tv_name.setText(ti.getName());
		
		/*----------------------------
		 * 2. Group name
			----------------------------*/
		TextView tv_group_name = 
					(TextView) v.findViewById(R.id.list_row_activity_item_tv_group);
		
		long group_id = ti.getGroup_id();
		
		String group_name = 
						Methods.find_GroupName_by_GroupId(actv, DBUtils.dbName, group_id);
		
		if (group_name != null) {
			
			tv_group_name.setText(group_name);
			
		} else {//if (group_name != null)
			
			tv_group_name.setText("-");
			
		}//if (group_name != null)
		
		/*----------------------------
		 * 3.3. created_at
			----------------------------*/
		TextView tv_created_at = 
						(TextView) v.findViewById(R.id.list_row_activity_item_tv_created_at);
    	
//		tv_created_at.setText(Methods.convert_millSeconds2digitsLabel(ti.getCreated_at()));
		tv_created_at.setText(Methods.convert_millSec2digitsLabel(ti.getCreated_at()));
		
//		// Log
//		Log.d("ActivityItemListAdapter.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "ti.getCreated_at(): " + ti.getCreated_at());
		
		/*----------------------------
		 * 3.3. finished_at
			----------------------------*/
		TextView tv_finished_at = 
						(TextView) v.findViewById(R.id.list_row_activity_item_tv_finished_at);
    	
		String finished_time;
		
		if (ti.getFinished_at() == 0) {
			
			finished_time = "-";
			
		} else {//if (ti.getFinished_at() == null)
			
			finished_time = Methods.convert_millSec2digitsLabel(ti.getFinished_at());
			
		}//if (ti.getFinished_at() == null)
		
//		// Log
//		Log.d("ActivityItemListAdapter.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "ti.getFinished_at(): " + ti.getFinished_at());
		
		tv_finished_at.setText(finished_time);
		
		return v;

	}//public View getView(int position, View convertView, ViewGroup parent)

}
