package am2.listeners;

import am1.items.ActivityItem;
import am1.utils.Methods;
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
		
//		// Log
//		if (tag != null) {
//			
//			Log.d("ListOnLongClickListener.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "tag.name(): " + tag.name());
//
//		} else {//if (tag != null)
//			Log.d("ListOnLongClickListener.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "tag => null");
//		}//if (tag != null)
//
//		if (tag2 != null) {
//			
//			Log.d("ListOnLongClickListener.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "tag2.name(): " + tag2.name());
//
//		} else {//if (tag2 != null)
//			Log.d("ListOnLongClickListener.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "tag2 => null");
//		}//if (tag2 != null)

		
//		Log.d("ListOnLongClickListener.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "tag2.name(): " + tag2.name());
		
		

//		// Log
//		Log.d("ListOnLongClickListener.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "v.getClass().getName(): " + v.getClass().getName());
		
		switch (tag) {
		
		/****************************************
		 * <Caller> 1. MainActv.set_listeners()
		 * 
		 ****************************************/
		case main_activity_list://-----------------------------------------------
			
			ActivityItem ai = (ActivityItem) parent.getItemAtPosition(position);
			
			Methods.dlg_activity_list(actv, ai);
			
			break;
		
		}//switch (tag)
		
		return false;
	}//public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id)

}
