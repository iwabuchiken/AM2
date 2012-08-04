package am2.listeners;
import java.io.File;

import am1.main.MemoActv;
import am2.utils.Methods;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ButtonOnClickListener implements OnClickListener {
	/*----------------------------
	 * Fields
		----------------------------*/
	//
	Activity actv;

	//
	Vibrator vib;
	
	//
	int position;
	ListView lv;
	long ai_db_id;
	EditText et_content;
	
	public ButtonOnClickListener(Activity actv) {
		//
		this.actv = actv;
		
		//
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
	}

	public ButtonOnClickListener(Activity actv, int position) {
		//
		this.actv = actv;
		this.position = position;
		
		//
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
		
		
		
	}//public ButtonOnClickListener(Activity actv, int position)

	public ButtonOnClickListener(Activity actv, ListView lv) {
		// 
		this.actv = actv;
		this.lv = lv;
		
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
	}

	public ButtonOnClickListener(Activity actv, long ai_db_id) {
		// 
		this.actv = actv;
		this.ai_db_id = ai_db_id;
		
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
		
	}

	public ButtonOnClickListener(Activity actv, EditText et_content) {

		this.actv = actv;
		this.et_content = et_content;
		
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
	}

	public ButtonOnClickListener(Activity actv, long ai_db_id, EditText et_content) {
		this.actv = actv;
		this.et_content = et_content;
		this.ai_db_id = ai_db_id;
		
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
	}

	@Override
	public void onClick(View v) {
		//
		Methods.ButtonTags tag_name = (Methods.ButtonTags) v.getTag();

		vib.vibrate(Methods.vibLength_click);
		
		//
		switch (tag_name) {
		case actv_register_genre_bt_cancel://--------------------------------------------
		case actv_register_group_bt_cancel:
		case actv_register_activity_bt_cancel:
			
			actv.finish();
			
			break;

		case actv_register_genre_bt_register://--------------------------------------------
			
			Methods.registerGenre(actv);
			
			break;// case actv_register_genre_bt_register
			
		case actv_register_group_bt_register://--------------------------------------------
			
			Methods.registerGroup(actv);
			
			break;// case actv_register_group_bt_register
			
		case actv_register_activity_bt_register://--------------------------------------------
			
			Methods.registerActivity(actv);
			
			break;// case actv_register_activity_bt_register

		case actv_show_activity_bt_add://--------------------------------------------
			
			Intent i = new Intent();
			
			i.setClass(actv, MemoActv.class);
			
			i.putExtra("ai_db_id", ai_db_id);
			
			// Log
			Log.d("ButtonOnClickListener.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "ai_db_id: " + ai_db_id);
			
			actv.startActivity(i);
			
			break;// case actv_show_activity_bt_add

		case actv_memo_bt_add://--------------------------------------------
			
			String content = et_content.getText().toString();
			
			if (content.equals("")) {
				
				Methods.dlg_inputEmpty(actv);
				
			} else {//if (content.equals(""))
				
				Methods.addMemo(actv, ai_db_id, content);
				
			}//if (content.equals(""))
			
			break;// case actv_memo_bt_add
			
		}//switch (tag_name)
		
	}//public void onClick(View v)

}
