package am2.listeners;

import am1.utils.Methods;

import android.app.Activity;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnLongClickListener;

public class ListOnLongClickListener implements OnLongClickListener {

	Activity actv;

	static Vibrator vib;
	
	public ListOnLongClickListener(Activity actv) {
		
		this.actv = actv;
	
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
	
		// Log
		Log.d("ListOnLongClickListener.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Instance created");
		
	}

	@Override
	public boolean onLongClick(View v) {
		
		Methods.ListItemTags tag = (Methods.ListItemTags) v.getTag();
		
		vib.vibrate(Methods.vibLength_click);
		
		// Log
		Log.d("ListOnLongClickListener.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "v.getClass().getName(): " + v.getClass().getName());
		
		switch (tag) {
		
		case main_activity_list:
			
			// Log
			Log.d("ListOnLongClickListener.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "v.getClass().getName(): " + v.getClass().getName());
			
			
			break;
		
		
		}//switch (tag)
		
		
		return false;
	}//public boolean onLongClick(View arg0)

}//public class ListOnLongClickListener implements OnLongClickListener
