package am2.listeners;

import am2.items.ActivityItem;
import am2.items.Memo;
import am2.utils.Methods;
import android.app.Activity;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;

public class ListOnItemLongClickListener implements OnItemLongClickListener {

	Activity actv;

	static Vibrator vib;

	public ListOnItemLongClickListener(Activity actv) {

		this.actv = actv;
		
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
	
		// Log
		Log.d("ListOnItemLongClickListener.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Instance created");
		
	}//public ListOnItemLongClickListener(Activity actv)

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
		
//		Methods.ListItemTags tag = (Methods.ListItemTags) v.getTag();
		Methods.ListItemTags tag = (Methods.ListItemTags) parent.getTag();
//		Methods.ListItemTags tag2 = (Methods.ListItemTags) v.getTag();
		
		vib.vibrate(Methods.vibLength_click);
		
		switch (tag) {
		
		/****************************************
		 * <Caller> 1. MainActv.set_listeners()
		 * 
		 ****************************************/
		case main_activity_list://-----------------------------------------------
			
			ActivityItem ai = (ActivityItem) parent.getItemAtPosition(position);
			
			Methods.dlg_activity_list(actv, ai);
			
			break;// case main_activity_list
		
		case show_actv_memo_list://-----------------------------------------------
			
			Memo m = (Memo) parent.getItemAtPosition(position);
			
			Methods.dlg_menu_Memo(actv, m);
			
//			// Log
//			Log.d("ListOnItemLongClickListener.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "memo: " + m.getText());
			
			break;// case show_actv_memo_list
			
		}//switch (tag)
		
		return false;
	}//public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id)

}
