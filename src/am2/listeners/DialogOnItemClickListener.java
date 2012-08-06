package am2.listeners;

import am2.items.ActivityItem;
import am2.items.Memo;
import am2.main.*;
import am2.utils.Methods;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class DialogOnItemClickListener implements OnItemClickListener {

	//
	Activity actv;
	Dialog dlg;
	Dialog dlg2;
	
	ActivityItem ai;

	Memo m;
	//
	Vibrator vib;
	
	//
//	Methods.DialogTags dlgTag = null;

	public DialogOnItemClickListener(Activity actv, Dialog dlg) {
		// 
		this.actv = actv;
		this.dlg = dlg;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
		
	}//public DialogOnItemClickListener(Activity actv, Dialog dlg)

	public DialogOnItemClickListener(Activity actv, Dialog dlg, ActivityItem ai) {
		// 
		this.actv = actv;
		this.dlg = dlg;
		
		this.ai = ai;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
		
		
		
	}//public DialogOnItemClickListener(Activity actv, Dialog dlg, ActivityItem ai)

	public DialogOnItemClickListener(Activity actv, Dialog dlg, Dialog dlg2) {
		
		this.actv = actv;
		this.dlg = dlg;
		this.dlg2 = dlg2;

		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);

		
	}

	public DialogOnItemClickListener(Activity actv, Dialog dlg, Memo m) {
		
		this.actv = actv;
		this.dlg = dlg;
		this.m = m;

		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
		
	}//public DialogOnItemClickListener(Activity actv, Dialog dlg, Memo m)

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		/*----------------------------
		 * Steps
		 * 1. Get tag
		 * 2. Vibrate
		 * 3. Switching
			----------------------------*/
		
		Methods.DialogListTags tag = (Methods.DialogListTags) parent.getTag();
//		
		vib.vibrate(Methods.vibLength_click);
		
		/*----------------------------
		 * 3. Switching
			----------------------------*/
		switch (tag) {
		
		case dlg_register_lv://----------------------------------------------------
			
			String item = (String) parent.getItemAtPosition(position);
			
			if (item.equals(actv.getString(R.string.dlg_register_lv_activity))) {
				
				Intent i = new Intent();
				
//				i.setClass(actv, RegisterActivityActv.class);
				i.setClass(actv, RegisterActvityActv.class);
				
				actv.startActivity(i);
				
			} else if (item.equals(actv.getString(R.string.dlg_register_lv_group))) {
				
				Intent i = new Intent();
				
//				i.setClass(actv, RegisterActivityActv.class);
				i.setClass(actv, RegisterGroupActv.class);
				
				actv.startActivity(i);
				
			} else if (item.equals(actv.getString(R.string.dlg_register_lv_genre))) {
				
				Intent i = new Intent();
				
//				i.setClass(actv, RegisterActivityActv.class);
				i.setClass(actv, RegisterGenreActv.class);
				
				actv.startActivity(i);
				
			}//if (item.equals(actv.getString(R.string.ac)))
			
			
			break;// case dlg_register_lv
			
		case dlg_activity_list_menu_lv://----------------------------------------------------
			
			item = (String) parent.getItemAtPosition(position);
			
			if (item.equals(actv.getString(R.string.dlg_activity_list_menu_delete))) {
				
				Methods.dlg_confirm_delete_activity(actv, dlg, ai);
				
//				// debug
//				Toast.makeText(actv, "Delete", 2000).show();
				
			} else {//if (item.equals(object))
				
			}//if (item.equals(object))
			
			
			break;

		case dlg_main_actv_filter_lv://----------------------------------------------------
			
			item = (String) parent.getItemAtPosition(position);
			
//			// debug
//			Toast.makeText(actv, item, 2000).show();
			
			
			Methods.mainOptFilter(actv, dlg, item);
			
			break;// case dlg_main_actv_filter_lv

		case dlg_main_actv_filter_group_lv://----------------------------------------------------
			
			item = (String) parent.getItemAtPosition(position);
			
			// Log
			Log.d("DialogOnItemClickListener.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "item: " + item);
			
			
			Methods.mainOptFilter_group(actv, dlg, dlg2, item);
			
			break;// case dlg_main_actv_filter_group_lv

		case dlg_menu_memo_lv://----------------------------------------------------
			/*----------------------------
			 * 1. Get item
			 * 2. Switching
				----------------------------*/
			
			item = (String) parent.getItemAtPosition(position);

			/*----------------------------
			 * 2. Switching
				----------------------------*/
			if (item.equals(actv.getString(R.string.generic_tv_edit))) {
				
				Methods.dlg_editMemo(actv, dlg, m);
				
			} else {//if (item.equals(actv.getString(R.string.generic_tv_edit)))
				
			}//if (item.equals(actv.getString(R.string.generic_tv_edit)))
			
			
			break;// case dlg_menu_memo_lv
			
		}//switch (tag)
		
	}//public void onItemClick(AdapterView<?> parent, View v, int position, long id)
	
}//public class DialogOnItemClickListener implements OnItemClickListener
