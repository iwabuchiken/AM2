package am2.main;

import am2.listeners.ButtonOnClickListener;
import am2.listeners.ButtonOnTouchListener;
import am2.utils.Methods;

import android.app.Activity;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class RegisterGenreActv extends Activity {

	public static Vibrator vib;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		/*----------------------------
		 * Steps
		 * 1. Super
		 * 2. Set content
		 * 3. Vib => Initialize
		----------------------------*/
		super.onCreate(savedInstanceState);

		//
		setContentView(R.layout.actv_register_genre);
		
		vib = (Vibrator) this.getSystemService(this.VIBRATOR_SERVICE);

		initial_setup();
		
	}//public void onCreate(Bundle savedInstanceState)

	private void initial_setup() {
		/*----------------------------
		 * 1. Set listener
			----------------------------*/
		set_listeners();
		
		
	}//private void initial_setup()

	private void set_listeners() {
		/*----------------------------
		 * 1. Cancel
			----------------------------*/
		Button bt_cancel = (Button) findViewById(R.id.actv_register_genre_bt_cancel);
		
		bt_cancel.setTag(Methods.ButtonTags.actv_register_genre_bt_cancel);
		
		bt_cancel.setOnTouchListener(new ButtonOnTouchListener(this));
		bt_cancel.setOnClickListener(new ButtonOnClickListener(this));

		// Log
		Log.d("RegisterGenreActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Listeners => Set");
		
		/*----------------------------
		 * 2. Register
			----------------------------*/
		Button bt_register = (Button) findViewById(R.id.actv_register_genre_bt_register);
		
		bt_register.setTag(Methods.ButtonTags.actv_register_genre_bt_register);
		
		bt_register.setOnTouchListener(new ButtonOnTouchListener(this));
		bt_register.setOnClickListener(new ButtonOnClickListener(this));

		// Log
		Log.d("RegisterGenreActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Listeners => Set");
		
		
	}//private void set_listeners()

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO 自動生成されたメソッド・スタブ
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		vib.vibrate(Methods.vibLength_click);
		
        switch (item.getItemId()) {
			/*----------------------------
			 * Steps
			 * 1. 
			 * 9. Default
				----------------------------*/
        	/*----------------------------
			 * 1. case 0	=> 
				----------------------------*/
//            case R.id.main_opt_menu_register://--------------------------------
//            	
//            	Methods.dlg_register(this);
//            	
//            	break;//case 0
            	
        }//switch (item.getItemId())

        return true;
	}//public boolean onOptionsItemSelected(MenuItem item)

	@Override
	protected void onPause() {
		// TODO 自動生成されたメソッド・スタブ
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO 自動生成されたメソッド・スタブ
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO 自動生成されたメソッド・スタブ
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO 自動生成されたメソッド・スタブ
		super.onDestroy();
	}
	
}//public class RegisterActvityActv extends Activity
