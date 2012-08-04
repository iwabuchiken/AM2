package am2.main;

import am2.items.ActivityItem;
import am2.listeners.ButtonOnClickListener;
import am2.listeners.ButtonOnTouchListener;
import am2.utils.Methods;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class MemoActv extends Activity {

	private ActivityItem ai;

	private EditText et_content;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		/*----------------------------
		 * Steps
		 * 1. Super
		 * 2. Set content
		----------------------------*/
		super.onCreate(savedInstanceState);

		//
		setContentView(R.layout.actv_memo);

		this.setTitle("AM1/" + this.getClass().getName());
		
		initial_setup();
		
	}//public void onCreate(Bundle savedInstanceState)

	private void initial_setup() {
		
		et_content = (EditText) findViewById(R.id.actv_memo_et_content);
		
		get_ai();
		
		set_listeners();
		
	}//private void initial_setup()

	private void get_ai() {
		
		Intent i = this.getIntent();
		
		long ai_db_id = i.getLongExtra("ai_db_id", -1);
		
		if (ai_db_id == -1) {
			
			// Log
			Log.d("MemoActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "ai_db_id == -1");
			
			return;
			
		}//if (ai_db_id == -1)
		
		// Log
		Log.d("MemoActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "ai_db_id: " + ai_db_id);
		
		
		ai = Methods.get_AI_from_AI_dbId(this, ai_db_id);
		
	}//private void get_ai()

	private void set_listeners() {
		/*----------------------------
		 * 1. Add
			----------------------------*/
		
		
		Button bt_add = (Button) findViewById(R.id.actv_memo_bt_add);

		bt_add.setTag(Methods.ButtonTags.actv_memo_bt_add);
		
		bt_add.setOnTouchListener(new ButtonOnTouchListener(this));
//		bt_add.setOnClickListener(new ButtonOnClickListener(this, et_content));
		bt_add.setOnClickListener(new ButtonOnClickListener(this, ai.getDb_id(), et_content));

		// Log
		Log.d("RegisterGenreActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Listeners => Set");

	}//private void set_listeners()

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPause() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		super.onDestroy();
	}
	
}//public class MemoActv extends Activity
