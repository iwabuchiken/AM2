package am2.main;

import java.util.List;

import am2.utils.*;
import am2.items.*;
import am2.listeners.*;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class MainActv extends ListActivity {
	public static Vibrator vib;

	public static List<ActivityItem> aiList;

	public static ActivityItemListAdapter ailAdapter;
	
	/** Called when the activity is first created. */
    
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
		setContentView(R.layout.main);
		
		this.setTitle("AM1/" + this.getClass().getName());

		vib = (Vibrator) this.getSystemService(this.VIBRATOR_SERVICE);
		
//		set_list();
		
	}//public void onCreate(Bundle savedInstanceState)

	private void set_list() {
		/*----------------------------
		 * 1. db setup
		 * 2. Query
		 * 3. Prepare => ActivityItem list
		 * 4. Prepare => ActivityItemListAdapter
		 * 5. Set adapter to list
		 * 
		 * 9. Close db
			----------------------------*/
		DBUtils dbu = new DBUtils(this, DBUtils.dbName);
		
		SQLiteDatabase rdb = dbu.getReadableDatabase();

		int i_res = dbu.tableExistsOrCreate(
				this, 
				DBUtils.dbName, 
				DBUtils.tableName_activities, 
				DBUtils.cols_activities, DBUtils.types_activities);

		if (i_res == -1) {
			
			// debug
			Toast.makeText(MainActv.this, "Create table => Error", 2000).show();
			
			// Log
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Create table => Error");
			
			rdb.close();
			
			return;
			
		}//if (i_res = -1)
		
//		/*----------------------------
//		 * 2. Query
//			----------------------------*/
////		String sql = "SELECT * FROM " + DBUtils.tableName_activities + "'";
//		String sql = "SELECT * FROM " + DBUtils.tableName_activities;
//		
//		Cursor c = rdb.rawQuery(sql, null);
//		
//		c.moveToFirst();
//
//		// Log
//		Log.d("MainActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "c.getCount(): " + c.getCount());
		
		/*----------------------------
		 * 3. Prepare => ActivityItem list
			----------------------------*/
		aiList = Methods.getAIList_fromDB(this);
		

		if (aiList == null) {
			// Log
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "aiList == null");
			
			// debug
			Toast.makeText(MainActv.this, "活動はまだ登録されていません", 2000).show();
			
			rdb.close();
			
			return;
			
		} else {//if (aiList == null)
			// Log
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "aiList.size(): " + aiList.size());
			
			/*----------------------------
			 * 4. Prepare => ActivityItemListAdapter
				----------------------------*/
			ailAdapter = new ActivityItemListAdapter(
									this,
									R.layout.main,
									aiList
					);
			
			/*----------------------------
			 * 5. Set adapter to list
				----------------------------*/
			setListAdapter(ailAdapter);
		}//if (aiList == null)
			
	
		//debug
		for (ActivityItem ai : aiList) {
			
			// Log
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "ai.getName(): " + ai.getName() + "/" + "ai.getDb_id(): " + ai.getDb_id());
			
			
		}
		
		/*----------------------------
		 * 9. Close db
			----------------------------*/
		rdb.close();
		
	}//private void set_list()

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		// 
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.menu_main, menu);
		
		return super.onCreateOptionsMenu(menu);
	}//public boolean onCreateOptionsMenu(Menu menu)

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
		/*----------------------------
		 * Steps
		 * 1. 
			----------------------------*/
		
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
            case R.id.main_opt_menu_register://--------------------------------
            	
            	Methods.dlg_register(this);
            	
            	break;//case 0
            	
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
		
		set_list();
		
	}

	@Override
	protected void onStart() {
		// TODO 自動生成されたメソッド・スタブ
		super.onStart();
		
		set_list();
		
		set_listeners();
		
		// B4
//		db_update();
		
	}//protected void onStart()

	private void set_listeners() {
		/*----------------------------
		 * memo
			----------------------------*/
		ListView lv = this.getListView();
		
		lv.setTag(Methods.ListItemTags.main_activity_list);
		
//		lv.setOnLongClickListener(new ListOnLongClickListener(this));
		lv.setOnItemLongClickListener(new ListOnItemLongClickListener(this));
		
//		lv.setOnClickListener(new ListItemOnClickListener(this));
		
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "listener set");
		
		
	}//private void set_listeners()

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		/*----------------------------
		 * 1. Vibrate
		 * 2. Intent => Put position to extra
		 * 
			----------------------------*/
		/*----------------------------
		 * 1. Vibrate
			----------------------------*/
		vib.vibrate(Methods.vibLength_click);
		
		/*----------------------------
		 * 2. Intent => Put position to extra
			----------------------------*/
		Intent i = new Intent();
		
		i.setClass(this, ShowActivityActv.class);
		
		i.putExtra("aiItemPosition", position);
		
		startActivity(i);
		
		
		super.onListItemClick(l, v, position, id);
	}//protected void onListItemClick(ListView l, View v, int position, long id)

	private void db_update() {
		/*----------------------------
		 * memo
			----------------------------*/
		// B4
		DBUtils dbu = new DBUtils(this, DBUtils.dbName);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();

		String sql = "ALTER TABLE " + DBUtils.tableName_activities  + 
							" ADD COLUMN " + "finished_at" + " INTEGER";
		
		try {
			
			wdb.execSQL(sql);
			
			// Log
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Column added: " + "finished_at");
			
			wdb.close();
			
			return;
			
		} catch (SQLException e) {
			
			// Log
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());
			
			wdb.close();
			
			return;
			
		}//try
		
	}//private void db_update()

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
}//public class MainActv extends Activity