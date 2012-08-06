package am2.listeners;

import am2.items.Memo;
import am2.utils.Methods;
import android.app.Activity;
import android.app.Dialog;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class DialogButtonOnClickListener implements OnClickListener {
	/*----------------------------
	 * Fields
		----------------------------*/
	//
	Activity actv;
	Dialog dlg;
	Dialog dlg2;		//=> Used in dlg_input_empty_btn_XXX

	//
	Vibrator vib;
	
	// Used in => Methods.dlg_addMemo(Activity actv, long file_id, String tableName)
	long file_id;
	String tableName;
	Memo m;		// Used in => dlg_edit_memo_ok
	
	public DialogButtonOnClickListener(Activity actv, Dialog dlg) {
		//
		this.actv = actv;
		this.dlg = dlg;
		
		//
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
	}

	public DialogButtonOnClickListener(Activity actv, Dialog dlg1,
			Dialog dlg2) {
		//
		this.actv = actv;
		this.dlg = dlg1;
		this.dlg2 = dlg2;
		
		//
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
	}

	public DialogButtonOnClickListener(Activity actv, Dialog dlg, long file_id, String tableName) {
		// 
		this.actv = actv;
		this.dlg = dlg;
		
		this.tableName = tableName;
		
		this.file_id = file_id;
		
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
		
	}//public DialogButtonOnClickListener(Activity actv, Dialog dlg, long file_id, String tableName)

	public DialogButtonOnClickListener(Activity actv, Dialog dlg, Dialog dlg2, Memo m) {
		this.actv = actv;
		this.dlg = dlg;
		this.dlg2 = dlg2;
		
		this.m = m;
		//
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
		
	}

	@Override
	public void onClick(View v) {
		//
		Methods.DialogButtonTags tag_name = (Methods.DialogButtonTags) v.getTag();

		vib.vibrate(Methods.vibLength_click);
		
		//
		switch (tag_name) {
		
		case dlg_generic_dismiss://------------------------------------------------
			
//			vib.vibrate(Methods.vibLength_click);
			
			dlg.dismiss();
			
			break;
			
		case dlg_generic_dismiss_second_dialog://---------------------------
			
			dlg2.dismiss();
			
			break;

		case dlg_confirm_delete_activity_bt_ok://---------------------------
			
			Methods.deleteActivity(actv, dlg, dlg2);
			
			break;// case dlg_confirm_delete_activity_bt_ok

		case dlg_edit_memo_ok:// ----------------------------------------------------
			
			Methods.editMemo(actv, dlg, dlg2, m);
			
			break;// case dlg_edit_memo_ok

		case dlg_confirm_delete_memo_ok:// ---------------------------------------
			
			Methods.deleteMemo(actv, dlg, dlg2, m);
			
			break;
			
		default: // ----------------------------------------------------
			break;
		}//switch (tag_name)
	}

}//public class DialogButtonOnClickListener implements OnClickListener
