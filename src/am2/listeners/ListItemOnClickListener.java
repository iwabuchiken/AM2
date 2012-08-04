package am2.listeners;

import am1.utils.Methods;
import android.app.Activity;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class ListItemOnClickListener implements OnClickListener {

	Activity actv;
	
	static Vibrator vib;
	
	public ListItemOnClickListener(Activity actv) {

		this.actv = actv;
		
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
	
		// Log
		Log.d("ListOnLongClickListener.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Instance created");
		
	}//public ListItemOnClickListener(Activity actv)

	@Override
	public void onClick(View v) {
		
		vib.vibrate(Methods.vibLength_click);

	}//public void onClick(View v)

}//public class ListItemOnClickListener implements OnClickListener
