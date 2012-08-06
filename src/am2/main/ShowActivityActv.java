package am2.main;

import java.util.List;

import am2.items.ActivityItem;
import am2.items.Memo;
import am2.listeners.ButtonOnClickListener;
import am2.listeners.ButtonOnTouchListener;
import am2.listeners.ListOnItemLongClickListener;
import am2.utils.ActivityItemListAdapter;
import am2.utils.DBUtils;
import am2.utils.MemoListAdapter;
import am2.utils.Methods;
import android.app.ListActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowActivityActv extends ListActivity {

	public static List<Memo> memoList;
	
	public static MemoListAdapter mlAdapter;

	ActivityItem ai;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		/*----------------------------
		 * Steps
		 * 1. Super
		 * 2. Set content
		----------------------------*/
		super.onCreate(savedInstanceState);

		//
		setContentView(R.layout.actv_show_activity);

		this.setTitle(this.getClass().getName());
		
	}//public void onCreate(Bundle savedInstanceState)

	private void set_list() {
		/*----------------------------
		 * 1. db setup
		 * 2. Query
		 * 3. Prepare => Memo list
		 * 4. Prepare => MemoListAdapter
		 * 5. Set adapter to list
		 * 
		 * 9. Close db
			----------------------------*/
		DBUtils dbu = new DBUtils(this, DBUtils.dbName);
		
		SQLiteDatabase rdb = dbu.getReadableDatabase();

		int i_res = dbu.tableExistsOrCreate(
				this, 
				DBUtils.dbName, 
				DBUtils.tableName_memos, 
				DBUtils.cols_memos, DBUtils.types_memos);

		if (i_res == -1) {
			
			// debug
			Toast.makeText(this, "Create table => Error", 2000).show();
			
			// Log
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Create table => Error");
			
			rdb.close();
			
			return;
			
		}//if (i_res = -1)
		
		/*----------------------------
		 * 3. Prepare => Memo list
			----------------------------*/
		memoList = Methods.getMemoList_fromDB(this, ai.getDb_id());
//		memoList = Methods.getMemoList_fromDB(this);
		
		if (memoList == null) {
			
			// Log
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "memoList == null");
			
		} else {//if (memoList == null)

			for (Memo memo : memoList) {
				
				// Log
				Log.d("ShowActivityActv.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "memo.getActivity_id()" + memo.getActivity_id());
				
				
			}
			
			
			// Log
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "memoList.size(): " + memoList.size());
			
			/*----------------------------
			 * 4. Prepare => MemoListAdapter
				----------------------------*/
			mlAdapter = new MemoListAdapter(
									this,
									R.layout.actv_show_activity,
									memoList
					);
			
			/*----------------------------
			 * 5. Set adapter to list
				----------------------------*/
			setListAdapter(mlAdapter);
			
		}//if (memoList == null)
		
		/*----------------------------
		 * 9. Close db
			----------------------------*/
		rdb.close();
		
		//debug
		// Log
		Log.d("ShowActivityActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "ai.getName(): " + ai.getName() + "/" + "ai.getDb_id(): " + ai.getDb_id());
		
		
	}//private void set_list()

	private void set_listeners() {
		/*----------------------------
		 * 1. Button "追加"
		 * 2. List view
			----------------------------*/
		Button bt_add = (Button) findViewById(R.id.actv_show_activity_bt_2);
//		Button bt_cancel = (Button) findViewById(R.id.actv_register_activity_bt_cancel);
//		Button bt_cancel = (Button) findViewById(R.id.);
//		
		bt_add.setTag(Methods.ButtonTags.actv_show_activity_bt_add);
		
		bt_add.setOnTouchListener(new ButtonOnTouchListener(this));
		bt_add.setOnClickListener(new ButtonOnClickListener(this, ai.getDb_id()));

		
		// Log
		Log.d("RegisterGenreActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Listeners => Set");

		/*----------------------------
		 * 2. List view
			----------------------------*/
		ListView lv = this.getListView();
		
		lv.setTag(Methods.ListItemTags.show_actv_memo_list);
		
		lv.setOnItemLongClickListener(new ListOnItemLongClickListener(this));
		
	}//private void set_listeners()
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO 自動生成されたメソッド・スタブ
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO 自動生成されたメソッド・スタブ
		return super.onOptionsItemSelected(item);
	}

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
		/*----------------------------
		 * 1. super
		 * 2. Get intent
			----------------------------*/
		
		super.onStart();
		
		/*----------------------------
		 * 2. Get intent
			----------------------------*/
		set_ai_item_values();

		set_list();
		
		set_listeners();
		
	}//protected void onStart()

	/****************************************
	 *
	 * 
	 * <Caller> 1. onStart()
	 * 
	 * <Desc> 1. <Params> 1.
	 * 
	 * <Return> 1.
	 * 
	 * <Steps> 1.
	 ****************************************/
	private void set_ai_item_values() {
		/*----------------------------
		 * 1. Get intent extra
		 * 2. Get ai
		 * 3. Set values to views
			----------------------------*/
		/*----------------------------
		 * 1. Get intent extra
			----------------------------*/
		Intent i = this.getIntent();
		
		int aiItemPosition = i.getIntExtra("aiItemPosition", -1);
		
		if (aiItemPosition == -1) {
			
			// debug
			Toast.makeText(ShowActivityActv.this, "Intet extra を取得できませんでした", 2000).show();
		
			return;
		}//if (aiItemPosition == -1)
		
		// 2
		ai = MainActv.aiList.get(aiItemPosition);
		
		// Log
		Log.d("ShowActivityActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "ai.getDb_id(): " + ai.getDb_id());
		
		/*----------------------------
		 * 3. Set values to views
			----------------------------*/
		TextView tv_activity_name = (TextView) findViewById(R.id.actv_show_activity_tv_activity_name);
		
		tv_activity_name.setText(ai.getName());
		
		//
		long group_id = ai.getGroup_id();
		
		String group_name = 
				Methods.find_GroupName_by_GroupId(this, DBUtils.dbName, group_id);
		
		TextView tv_activity_group = (TextView) findViewById(R.id.actv_show_activity_tv_group);
		tv_activity_group.setText(group_name);
		
		TextView tv_activity_created_at = (TextView) findViewById(R.id.actv_show_activity_tv_created_at);
		tv_activity_created_at.setText(Methods.convert_millSec2digitsLabel(ai.getCreated_at()));

		//
		TextView tv_finished_at = 
							(TextView) findViewById(R.id.actv_show_activity_tv_finished_at);

		String finished_time;
		
		if (ai.getFinished_at() == 0) {
			
			finished_time = "-";
			
		} else {//if (ti.getFinished_at() == null)
			
			finished_time = Methods.convert_millSec2digitsLabel(ai.getFinished_at());
			
		}//if (ti.getFinished_at() == null)

		tv_finished_at.setText(finished_time);
		
	}//private void set_ai_item_values()

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
}//public class ShowActivityActv extends ListActivity
