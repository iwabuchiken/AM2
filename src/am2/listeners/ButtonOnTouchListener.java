package am2.listeners;

import am2.utils.Methods;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;

public class ButtonOnTouchListener implements OnTouchListener {

	/*----------------------------
	 * Fields
		----------------------------*/
	//
	Activity actv;

	//
	Vibrator vib;
	
	public ButtonOnTouchListener(Activity actv) {
		//
		this.actv = actv;
		
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO 自動生成されたメソッド・スタブ
		Methods.ButtonTags tag_name = (Methods.ButtonTags) v.getTag();
		
		switch (event.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
			
			switch (tag_name) {
			
			case actv_register_genre_bt_cancel:
			case actv_register_genre_bt_register:
				
			case actv_register_group_bt_cancel:
			case actv_register_group_bt_register:

			case actv_register_activity_bt_cancel:
			case actv_register_activity_bt_register:

			case actv_show_activity_bt_add:
				
			case actv_memo_bt_add:
				
				v.setBackgroundColor(Color.GRAY);
				
				break;
			}//switch (tag_name)
			
			break;//case MotionEvent.ACTION_DOWN:
			
		case MotionEvent.ACTION_UP:
			switch (tag_name) {
			case actv_register_genre_bt_cancel:
			case actv_register_genre_bt_register:

			case actv_register_group_bt_cancel:
			case actv_register_group_bt_register:
				
			case actv_register_activity_bt_cancel:
			case actv_register_activity_bt_register:
				
			case actv_show_activity_bt_add:
			
			case actv_memo_bt_add:
				
				v.setBackgroundColor(Color.WHITE);
				
				break;
			}//switch (tag_name)
			
			break;//case MotionEvent.ACTION_UP:
		}//switch (event.getActionMasked())
		return false;
	}

}
