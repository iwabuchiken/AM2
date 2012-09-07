package am2.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;

import org.apache.commons.lang.StringUtils;

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
		
		this.setTitle(this.getClass().getName());

		vib = (Vibrator) this.getSystemService(this.VIBRATOR_SERVICE);
		
//		set_list();
		
		//debug
		backup_db();
		
		
	}//public void onCreate(Bundle savedInstanceState)

	private void backup_db() {
		String dirName_ExternalStorage = "/mnt/sdcard-ext";
		
		String dirPath_db_backup = dirName_ExternalStorage + "/AM2_backup";

		String dirPath_db = "/data/data/am2.main/databases";
		
		String fileName_db_backup_trunk = "am2_backup";
		String fileName_db_backup_ext = ".bk";

		
		
		String time_label = Methods.get_TimeLabel(Methods.getMillSeconds_now());
		
		String db_src = StringUtils.join(new String[]{dirPath_db, DBUtils.dbName}, File.separator);
		
		String db_dst = StringUtils.join(new String[]{dirPath_db_backup, fileName_db_backup_trunk}, File.separator);
		db_dst = db_dst + "_" + time_label + fileName_db_backup_ext;
		
		// Log
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", 
				"db_src: " + db_src + " * " + "db_dst: " + db_dst);
//				"db_dst: " + db_dst);
		
		/*----------------------------
		 * 2. Prep => Files
			----------------------------*/
		File src = new File(db_src);
		File dst = new File(db_dst);
		
		/*----------------------------
		 * 2-2. Folder exists?
			----------------------------*/
		File db_backup = new File(dirPath_db_backup);
		
		if (!db_backup.exists()) {
			
			try {
				db_backup.mkdir();
				
				// Log
				Log.d("Methods.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", "Folder created: " + db_backup.getAbsolutePath());
			} catch (Exception e) {
				
				// Log
				Log.d("Methods.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "Create folder => Failed");
				
				return;
				
			}
			
		} else {//if (!db_backup.exists())
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Folder exists: ");
			
		}//if (!db_backup.exists())
		
		/*----------------------------
		 * 3. Copy
			----------------------------*/
		try {
			FileChannel iChannel = new FileInputStream(src).getChannel();
			FileChannel oChannel = new FileOutputStream(dst).getChannel();
			iChannel.transferTo(0, iChannel.size(), oChannel);
			iChannel.close();
			oChannel.close();
			
			// Log
			Log.d("ThumbnailActivity.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "File copied");
			
			// debug
			Toast.makeText(this, "DB backup => Done", 3000).show();

		} catch (FileNotFoundException e) {
			// Log
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());
			
		} catch (IOException e) {
			// Log
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());
		}//try

		
		
	}

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
		
		/*----------------------------
		 * 3. Prepare => ActivityItem list
			----------------------------*/
		if(aiList == null) {
			aiList = Methods.getAIList_fromDB(this);
		}

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
			
			// Sort list
			Methods.sort_activitiesList_group(aiList);
			
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

		//debug
		for (ActivityItem ai : aiList) {
			
			// Log
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", 
					"ai.getGroup_id(): " + ai.getGroup_id());
			
			
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
            	
            case R.id.main_opt_menu_filter://--------------------------------
        
            	// Log
				Log.d("MainActv.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "Calling: Methods.dlg_main_opt_filter()");
				
            	
            	Methods.dlg_main_opt_filter(this);
            	
            	break;// R.id.main_opt_menu_filter
            	
            case R.id.main_opt_menu_sort://--------------------------------
            	
            	Methods.sort_activitiesList_group(aiList);
            	
            	ailAdapter.notifyDataSetChanged();
            	
            	break;
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