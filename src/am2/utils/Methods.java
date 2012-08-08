package am2.utils;

import am2.main.*;
import am2.items.*;
import am2.listeners.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

public class Methods {

	static int tempRecordNum = 20;
	
	public static enum DialogTags {
		// Generics
		dlg_generic_dismiss, dlg_generic_dismiss_second_dialog,
		
		// dlg_item_menu.xml
		dlg_item_menu,

		// dlg_add_memos.xml
		dlg_add_memos, dlg_add_memos_add, dlg_add_memos_gv,

		// dlg_register_patterns.xml
		dlg_register_patterns, dlg_register_patterns_register,
		
	}//public static enum DialogTags

	public static enum DialogButtonTags {
		// Generics
		dlg_generic_dismiss, dlg_generic_dismiss_second_dialog,

		// dlg_register.xml
		dlg_register_bt_cancel,

		// dlg_activity_list_menu.xml

		// dlg_confirm_delete_activity.xml
		dlg_confirm_delete_activity_bt_ok,

		// dlg_edit_memo.xml
		dlg_edit_memo_ok,

		// dlg_confirm_delete_memo.xml
		dlg_confirm_delete_memo_ok,
		
		
	}//public static enum DialogButtonTags
	
	public static enum ButtonTags {
		// main.xml
		main_bt_play, main_bt_pause, main_bt_rec,

		// internet_actv.xml
		internet_actv, internet_actv_bt_post, internet_actv_bt_post_json,
		internet_actv_bt_post_json_async,

		// actv_register_genre.xml
		actv_register_genre_bt_cancel, actv_register_genre_bt_register,

		// actv_register_group.xml
		actv_register_group_bt_cancel, actv_register_group_bt_register,
		
		// actv_register_activity.xml
		actv_register_activity_bt_cancel, actv_register_activity_bt_register,
		
		// actv_show_activity.xml
		actv_show_activity_bt_add,

		// actv_memo.xml
		actv_memo_bt_add, actv_memo_bt_clear, actv_memo_bt_back,
		
		
	}//public static enum ButtonTags
	
	public static enum ItemTags {
		
		
	}//public static enum ItemTags

	public static enum MoveMode {
		// TIListAdapter.java
		ON, OFF,
		
	}//public static enum MoveMode

	public static enum ButtonModes {
		// Play
		READY, FREEZE, PLAY,
		// Rec
		REC,
	}

//	public static enum ListLongClickTags {
	public static enum ListItemTags {
		// MainActivity.java
		main_activity_list,

		// ShowActivityActv.java
		show_actv_memo_list,
		
	}//public static enum LongClickTags

	public static enum DialogOnItemClickTags {
		
		// dlg_item_menu.xml
		dlg_item_menu,
		
		// dlg_register_patterns.xml
		dlg_register_patterns, dlg_register_patterns_gv,
		
	}//public static enum DialogOnItemClickListener
	
	public static enum DialogListTags {
		// dlg_register.xml
		dlg_register_lv,
		
		// dlg_activity_list_menu.xml
		dlg_activity_list_menu_lv,

		// dlg_main_actv_filter.xml
		dlg_main_actv_filter_lv,
		
		// dlg_main_actv_filter_group.xml
		dlg_main_actv_filter_group_lv,

		// dlg_menu_memo.xml
		dlg_menu_memo_lv,
		
	}//public static enum DialogListTags
	
	//
	public static final int vibLength_click = 35;

	/*----------------------------
	 * makeComparator(Comparator comparator)
	 * 
	 * => Used 
	 * 
	 * REF=> C:\WORKS\WORKSPACES_ANDROID\Sample\09_Matsuoka\プロジェクト
	 * 					\Step10\10_LiveWallpaper\src\sample\step10\livewallpaper\FilePicker.java
		----------------------------*/
	public static void makeComparator(Comparator comparator){
		
		comparator=new Comparator<Object>(){
			
			public int compare(Object object1, Object object2) {
			
				int pad1=0;
				int pad2=0;
				
				File file1=(File)object1;
				File file2=(File)object2;
				
				if(file1.isDirectory())pad1=-65536;
				if(file2.isDirectory())pad2=-65536;
				
				return pad1-pad2+file1.getName().compareToIgnoreCase(file2.getName());
			}
		};
	}

	public static void sortFileList(File[] files) {
		// REF=> http://android-coding.blogspot.jp/2011/10/sort-file-list-in-order-by-implementing.html
		Comparator<? super File> filecomparator = new Comparator<File>(){
			
			public int compare(File file1, File file2) {
				int pad1=0;
				int pad2=0;
				
				if(file1.isDirectory())pad1=-65536;
				if(file2.isDirectory())pad2=-65536;
				
				// Order => folders, files
//				return pad1-pad2+file1.getName().compareToIgnoreCase(file2.getName());
				
				// Order => files, folders
				return pad2-pad1+file1.getName().compareToIgnoreCase(file2.getName());
				
//				return String.valueOf(file1.getName()).compareTo(file2.getName());
			} 
		 };//Comparator<? super File> filecomparator = new Comparator<File>()
		 
		 //
		Arrays.sort(files, filecomparator);

	}//public static void sortFileList(File[] files)


	public static void sort_activitiesList_group(List<ActivityItem> aiList) {
		// REF=> http://android-coding.blogspot.jp/2011/10/sort-file-list-in-order-by-implementing.html
		Comparator<? super ActivityItem> ailist_comp = new Comparator<ActivityItem>(){
//		Comparator ailist_comp = new Comparator<Object>(){
			
			public int compare(ActivityItem ai1, ActivityItem ai2) {
//			public int compare(Object ai1, Object ai2) {
				
				if (ai1.getGroup_id() != ai2.getGroup_id()) {

					return (int)(ai1.getGroup_id() - ai2.getGroup_id());
					
				} else {//if (ai1.getGroup_id() != ai2.getGroup_id())
					
					// Log
					Log.d("Methods.java"
							+ "["
							+ Thread.currentThread().getStackTrace()[2]
									.getLineNumber() + "]", "ai1.getGroup_id() == ai2.getGroup_id()");
					Log.d("Methods.java"
							+ "["
							+ Thread.currentThread().getStackTrace()[2]
									.getLineNumber() + "]", 
								"ai1.getName(): " + ai1.getName() + "/" + "ai2.getName(): " + ai2.getName());
					// Log
					Log.d("Methods.java"
							+ "["
							+ Thread.currentThread().getStackTrace()[2]
									.getLineNumber() + "]", 
							"ai1.getName().compareToIgnoreCase(ai2.getName()): " + 
								ai1.getName().compareToIgnoreCase(ai2.getName()));
					
					return ai1.getName().compareToIgnoreCase(ai2.getName());
					
				}//if (ai1.getGroup_id() != ai2.getGroup_id())
				
//				return pad2-pad1+file1.getName().compareToIgnoreCase(file2.getName());
				
//				return String.valueOf(file1.getName()).compareTo(file2.getName());
			} 
		 };//Comparator<? super ActivityItem> ailist_comp = new Comparator<ActivityItem>()
		 
		 //
		Collections.sort(aiList, ailist_comp);

		// Notify
//		MainActv.ailAdapter.notifyDataSetChanged();
		
		// Log
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Exit: sort_activitiesList_group()");
		
		
	}//public static void sort_activitiesList_group(List<ActivityItem> aiList)

	/*----------------------------
	 * deleteDirectory(File target)()
	 * 
	 * 1. REF=> http://www.rgagnon.com/javadetails/java-0483.html
		----------------------------*/
	public static boolean deleteDirectory(File target) {
		
		if(target.exists()) {
			//
			File[] files = target.listFiles();
			
			//
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					
					deleteDirectory(files[i]);
					
				} else {//if (files[i].isDirectory())
					
					String path = files[i].getAbsolutePath();
					
					files[i].delete();
					
					// Log
					Log.d("Methods.java"
							+ "["
							+ Thread.currentThread().getStackTrace()[2]
									.getLineNumber() + "]", "Removed => " + path);
					
					
				}//if (files[i].isDirectory())
				
			}//for (int i = 0; i < files.length; i++)
			
		}//if(target.exists())
		
		return (target.delete());
	}

	public static void toastAndLog(Activity actv, String message, int duration) {
		// 
		// debug
		Toast.makeText(actv, message, duration)
				.show();
		
		// Log
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber() + "]", 
				message);
		
	}//public static void toastAndLog(Activity actv, String message)

	public static void toastAndLog(Activity actv, String fileName, String message, int duration) {
		// 
		// debug
		Toast.makeText(actv, message, duration)
				.show();
		
		// Log
		Log.d(fileName + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber() + "]", 
				message);
		
	}//public static void toastAndLog(Activity actv, String fileName, String message, int duration)

	public static void recordLog(Activity actv, String fileName, String message) {
		// Log
		Log.d(fileName + 
				"[" + Thread.currentThread().getStackTrace()[2].getLineNumber() + "]", 
				message);
		
	}//public static void toastAndLog(Activity actv, String message)

	public static long getMillSeconds(int year, int month, int date) {
		// Calendar
		Calendar cal = Calendar.getInstance();
		
		// Set time
		cal.set(year, month, date);
		
		// Date
		Date d = cal.getTime();
		
		return d.getTime();
		
	}//private long getMillSeconds(int year, int month, int date)

	/****************************************
	 *	getMillSeconds_now()
	 * 
	 * <Caller> 
	 * 1. ButtonOnClickListener # case main_bt_start
	 * 
	 * <Desc> 1. <Params> 1.
	 * 
	 * <Return> 1.
	 * 
	 * <Steps> 1.
	 ****************************************/
	public static long getMillSeconds_now() {
		
		Calendar cal = Calendar.getInstance();
		
		return cal.getTime().getTime();
		
	}//private long getMillSeconds_now(int year, int month, int date)

	public static List<String> getTableList(SQLiteDatabase wdb) {
		//=> source: http://stackoverflow.com/questions/4681744/android-get-list-of-tables : "Just had to do the same. This seems to work:"
		String q = "SELECT name FROM " + "sqlite_master"+
						" WHERE type = 'table' ORDER BY name";
		
		Cursor c = null;
		try {
			c = wdb.rawQuery(q, null);
		} catch (Exception e) {
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception => " + e.toString());
		}
		
		// Table names list
		List<String> tableList = new ArrayList<String>();
		
		// Log
		if (c != null) {
			c.moveToFirst();
			
			for (int i = 0; i < c.getCount(); i++) {
				// Log
				Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "c.getString(0) => " + c.getString(0));
				
				//
				tableList.add(c.getString(0));
				
				// Next
				c.moveToNext();
				
			}//for (int i = 0; i < c.getCount(); i++)

		} else {//if (c != null)
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "c => null");
		}//if (c != null)
		
		return tableList;
	}//public static List<String> getTableList()

//	public static Dialog dlg_template_okCancel(Activity actv, int layoutId, int titleStringId,
//									int okButtonId, int cancelButtonId, DialogTags okTag, DialogTags cancelTag) {
//		/*----------------------------
//		 * Steps
//		 * 1. Set up
//		 * 2. Add listeners => OnTouch
//		 * 3. Add listeners => OnClick
//			----------------------------*/
//		
//		// 
//		Dialog dlg = new Dialog(actv);
//		
//		//
//		dlg.setContentView(layoutId);
//		
//		// Title
//		dlg.setTitle(titleStringId);
//		
//		/*----------------------------
//		 * 2. Add listeners => OnTouch
//			----------------------------*/
//		//
//		Button btn_ok = (Button) dlg.findViewById(okButtonId);
//		Button btn_cancel = (Button) dlg.findViewById(cancelButtonId);
//		
//		//
//		btn_ok.setTag(okTag);
//		btn_cancel.setTag(cancelTag);
//		
//		//
//		btn_ok.setOnTouchListener(new DialogButtonOnTouchListener(actv, dlg));
//		btn_cancel.setOnTouchListener(new DialogButtonOnTouchListener(actv, dlg));
//		
//		/*----------------------------
//		 * 3. Add listeners => OnClick
//			----------------------------*/
//		//
//		btn_ok.setOnClickListener(new DialogButtonOnClickListener(actv, dlg));
//		btn_cancel.setOnClickListener(new DialogButtonOnClickListener(actv, dlg));
//		
//		//
////		dlg.show();
//		
//		return dlg;
//		
//	}//public static Dialog dlg_template_okCancel()

//	public static Dialog dlg_template_okCancel_2_dialogues(
//											Activity actv, int layoutId, int titleStringId,
//											int okButtonId, int cancelButtonId, 
//											DialogTags okTag, DialogTags cancelTag,
//											Dialog dlg1) {
//		/*----------------------------
//		* Steps
//		* 1. Set up
//		* 2. Add listeners => OnTouch
//		* 3. Add listeners => OnClick
//		----------------------------*/
//		
//		// 
//		Dialog dlg2 = new Dialog(actv);
//		
//		//
//		dlg2.setContentView(layoutId);
//		
//		// Title
//		dlg2.setTitle(titleStringId);
//		
//		/*----------------------------
//		* 2. Add listeners => OnTouch
//		----------------------------*/
//		//
//		Button btn_ok = (Button) dlg2.findViewById(okButtonId);
//		Button btn_cancel = (Button) dlg2.findViewById(cancelButtonId);
//		
//		//
//		btn_ok.setTag(okTag);
//		btn_cancel.setTag(cancelTag);
//		
//		//
//		btn_ok.setOnTouchListener(new DialogButtonOnTouchListener(actv, dlg2));
//		btn_cancel.setOnTouchListener(new DialogButtonOnTouchListener(actv, dlg2));
//		
//		/*----------------------------
//		* 3. Add listeners => OnClick
//		----------------------------*/
//		//
//		btn_ok.setOnClickListener(new DialogButtonOnClickListener(actv, dlg1, dlg2));
//		btn_cancel.setOnClickListener(new DialogButtonOnClickListener(actv, dlg1, dlg2));
//		
//		//
//		//dlg.show();
//		
//		return dlg2;
//		
//	}//public static Dialog dlg_template_okCancel_2_dialogues()

//	public static Dialog dlg_template_cancel(
//										// Activity, layout
//										Activity actv, int layoutId,
//										// Title
//										int titleStringId,
//										// Cancel button, DialogTags => Cancel
//										int cancelButtonId, DialogTags cancelTag) {
//		/*----------------------------
//		* Steps
//		* 1. Set up
//		* 2. Add listeners => OnTouch
//		* 3. Add listeners => OnClick
//		----------------------------*/
//		
//		// 
//		Dialog dlg = new Dialog(actv);
//		
//		//
//		dlg.setContentView(layoutId);
//		
//		// Title
//		dlg.setTitle(titleStringId);
//		
//		/*----------------------------
//		* 2. Add listeners => OnTouch
//		----------------------------*/
//		//
//		Button btn_cancel = (Button) dlg.findViewById(cancelButtonId);
//		
//		//
//		btn_cancel.setTag(cancelTag);
//		
//		//
//		btn_cancel.setOnTouchListener(new DialogButtonOnTouchListener(actv, dlg));
//		
//		/*----------------------------
//		* 3. Add listeners => OnClick
//		----------------------------*/
//		//
//		btn_cancel.setOnClickListener(new DialogButtonOnClickListener(actv, dlg));
//		
//		//
//		//dlg.show();
//		
//		return dlg;
//		
//	}//public static Dialog dlg_template_okCancel()

	/****************************************

	 * findIndexFromArray(String[] ary, String target)
	 * 
	 * <Caller> 
	 * 1. convertAbsolutePathIntoPath()
	 * 
	 * <Desc> 
	 * 1. 
	 * 
	 * <Params> 1.
	 * 
	 * <Return> 
	 * 		-1		=> Couldn't find
	 * 		
	 * 
	 * <Steps> 1.
	 ****************************************/
	public static int findIndexFromArray(String[] ary, String target) {
		
		int index = -1;
		
		for (int i = 0; i < ary.length; i++) {
			
			if (ary[i].equals(target)) {
				
				return i;
				
			}//if (ary[i].equals(tar))
			
		}//for (int i = 0; i < ary.length; i++)
		
		return index;
		
	}//public static int findIndexFromArray(String[] ary, String target)

	public static String  convert_millSeconds2digitsLabel(long millSeconds) {
		/*----------------------------
		 * Steps
		 * 1. Prepare variables
		 * 2. Build a string
		 * 3. Return
			----------------------------*/
		/*----------------------------
		 * 1. Prepare variables
			----------------------------*/
		int seconds = (int)(millSeconds / 1000);
		
		int minutes = seconds / 60;
		
		int digit_seconds = seconds % 60;
		
		/*----------------------------
		 * 2. Build a string
			----------------------------*/
		StringBuilder sb = new StringBuilder();
		
		if (String.valueOf(minutes).length() < 2) {
			
			sb.append("0");
			
		}//if (String.valueOf(minutes).length() < 2)
		
		sb.append(String.valueOf(minutes));
		sb.append(":");

		if (String.valueOf(digit_seconds).length() < 2) {
			
			sb.append("0");
			
		}//if (String.valueOf(digit_seconds).length() < 2)

		sb.append(String.valueOf(digit_seconds));
		
		/*----------------------------
		 * 3. Return
			----------------------------*/
		return sb.toString();
		
	}//public static void  convert_millSeconds2digitsLabel()

	public static String  convert_millSec2digitsLabel(long millSeconds) {
		/*----------------------------
		 * Steps
		 * 1. Prepare variables
		 * 2. Build a string
		 * 3. Return
			----------------------------*/
		// Format
		SimpleDateFormat form = new SimpleDateFormat("yyyy/MM/dd H:mm:ss");
		
		return form.format(millSeconds);
		
	}//public static void  convert_millSeconds2digitsLabel()

	
	private static long getDuration(String file_path) {
		/*----------------------------
		 * 2. Duration
		 * 		1. temp_mp
		 * 		2. Set source
		 * 		2-1. Prepare
		 * 		2-2. Get duration
		 * 		2-3. Release temp_mp
		 * 
		 * 		3. Prepare	=> NOP
		 * 		4. Start			=> NOP
			----------------------------*/
		MediaPlayer temp_mp = new MediaPlayer();
		
		try {
			
			temp_mp.setDataSource(file_path);
			
		} catch (IllegalArgumentException e) {
			
			// Log
			Log.d("Methods.java"
					+ "["
					+ Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + "]", "Exception: " + e.toString());
			
			return -1;
			
		} catch (IllegalStateException e) {
			// Log
			Log.d("Methods.java"
					+ "["
					+ Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + "]", "Exception: " + e.toString());
			
			return -1;
			
		} catch (IOException e) {
			// Log
			Log.d("Methods.java"
					+ "["
					+ Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + "]", "Exception: " + e.toString());
			
			return -1;
			
		}//try
		
		/*----------------------------
		 * 2.2-1. Prepare
			----------------------------*/
		try {
			
			temp_mp.prepare();
			
		} catch (IllegalStateException e) {
			// Log
			Log.d("Methods.java"
					+ "["
					+ Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + "]", "Exception: " + e.toString());
			
			return -1;
			
		} catch (IOException e) {
			// Log
			Log.d("Methods.java"
					+ "["
					+ Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + "]", "Exception: " + e.toString());
			
			return -1;
			
		}//try

		/*----------------------------
		 * 2.2-2. Get duration
			----------------------------*/
		long duration = temp_mp.getDuration();
		
		/*----------------------------
		 * 2.2-3. Release temp_mp
			----------------------------*/
		temp_mp.reset();
		temp_mp.release();
		temp_mp = null;
		
		return duration;
		
	}//private static long getDuration(String file_path)

	public static boolean dropTable(Activity actv, String tableName) {
		DBUtils dbu = new DBUtils(actv, DBUtils.dbName);
		
		//
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		boolean res = dbu.dropTable(actv, wdb, tableName);
		
		if (res == false) {

			Toast.makeText(actv, "Drop table => Failed", 2000)
			.show();
			
			wdb.close();
			
			return false;
			
		} else {//if (res == false)

			Toast.makeText(actv, "Table dropped: " + tableName, 2000)
			.show();
			
			wdb.close();
			
			return true;
			
		}//if (res == false)
		

	}//public static boolean dropTable(Activity actv, String tableName)

	private static boolean insertDataIntoDB(
			Activity actv, String tableName, String[] colNames, String[] values, String[] colTypes) {
		/*----------------------------
		* Steps
		* 1. Set up db
		* 1-2. Table exists?
		* 2. Insert data
		* 3. Show message
		* 4. Close db
		----------------------------*/
		/*----------------------------
		* 1. Set up db
		----------------------------*/
		DBUtils dbu = new DBUtils(actv, DBUtils.dbName);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		/*----------------------------
		 * 1-2. Table exists?
			----------------------------*/
		boolean result = dbu.tableExists(wdb, tableName);
		
		// If doesn't exist...
		if (result == false) {
			// Create one
			result = dbu.createTable(wdb, tableName, colNames, colTypes);
			
			if (result == true) {
				
				// Log
				Log.d("Methods.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "Table created: " + tableName);
				
				
			} else {//if (result == true)

				// Log
				Log.d("Methods.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "Create table => Failed: " + tableName);
				
				wdb.close();
				
				return false;
				
			}//if (result == true)
			
			
		}//if (result == false)
		
		/*----------------------------
		* 2. Insert data
		----------------------------*/
		result = false;
		
		try {
			
			result = dbu.insertData(wdb, tableName, colNames, values);
			
		} catch (Exception e) {
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());
			
		}
		
		/*----------------------------
		* 3. Show message
		----------------------------*/
		if (result == true) {
		
//			// debug
//			Toast.makeText(actv, "Data stored", 2000).show();
			// Log
			Log.d("Methods.java"
					+ "["
					+ Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + "]", "dbu.insertData => true");
			
			
			/*----------------------------
			* 4. Close db
			----------------------------*/
			wdb.close();
			
			return true;
		
		} else {//if (result == true)
		
//			// debug
//			Toast.makeText(actv, "Store data => Failed", 200).show();
			
			// Log
			Log.d("Methods.java"
					+ "["
					+ Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + "]", "dbu.insertData => false");
			/*----------------------------
			* 4. Close db
			----------------------------*/
			wdb.close();
			
			return false;
		
		}//if (result == true)
		
		/*----------------------------
		* 4. Close db
		----------------------------*/
		
	}//private static int insertDataIntoDB()

	private static boolean insertDataIntoDB(
			Activity actv, String tableName, String[] types, String[] values) {
		/*----------------------------
		* Steps
		* 1. Set up db
		* 2. Insert data
		* 3. Show message
		* 4. Close db
		----------------------------*/
		/*----------------------------
		* 1. Set up db
		----------------------------*/
		DBUtils dbu = new DBUtils(actv, DBUtils.dbName);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		/*----------------------------
		* 2. Insert data
		----------------------------*/
		boolean result = dbu.insertData(wdb, tableName, types, values);
		
		/*----------------------------
		* 3. Show message
		----------------------------*/
		if (result == true) {
			
			// debug
			Toast.makeText(actv, "Data stored", 2000).show();
			
			/*----------------------------
			* 4. Close db
			----------------------------*/
			wdb.close();
			
			return true;
			
		} else {//if (result == true)
		
			// debug
			Toast.makeText(actv, "Store data => Failed", 200).show();
			
			/*----------------------------
			* 4. Close db
			----------------------------*/
			wdb.close();
			
			return false;
		
		}//if (result == true)

	}//private static int insertDataIntoDB()

	public static void test_postDataToRemote(Activity actv) {
		/*----------------------------
		 * 1. url
			----------------------------*/
		
		// Log
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "test_postDataToRemote => Started");
		
		
		/*----------------------------
		 * 1. url
			----------------------------*/
//		String url = "http://cosmos-cm.herokuapp.com";
		String url = "http://cosmos-cm.herokuapp.com/test1_post";
		
		/*----------------------------
		 * 2. httpPost
			----------------------------*/
		HttpPost httpPost = new HttpPost(url);
		
		/*----------------------------
		 * 3. Parameters
			----------------------------*/
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		
//		paramList.add(new BasicNameValuePair("file_name", "1234.wav"));
		paramList.add(new BasicNameValuePair("test_data", "1234.wav"));
		
		try {
			
			httpPost.setEntity(new UrlEncodedFormEntity(paramList, HTTP.UTF_8));
			
		} catch (UnsupportedEncodingException e) {
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());
			
		}//try
		
		HttpUriRequest request = httpPost;
		
		/*----------------------------
		 * 4. Client object
			----------------------------*/
		DefaultHttpClient dhc = new DefaultHttpClient();
		
		/*----------------------------
		 * 5. Http response
			----------------------------*/
		HttpResponse hr = null;
		
		try {
			hr = dhc.execute(request);
		} catch (ClientProtocolException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		/*----------------------------
		 * 6. Response null?
			----------------------------*/
		if (hr == null) {
			
			// debug
			Toast.makeText(actv, "hr == null", 2000).show();
			
			return;
			
		}//if (hr == null)
		
		/*----------------------------
		 * 7. Status
			----------------------------*/
		int statusCode = hr.getStatusLine().getStatusCode();
		
		// debug
		Toast.makeText(actv, "statusCode: " + statusCode, 2000).show();
		
		// Log
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "statusCode: " + statusCode);
		
		
//		// debug
//		Toast.makeText(actv, "test_postDataToRemote", 2000).show();
		
	}//public static void test_postDataToRemote(Activity actv)

	/****************************************
	 * 	test_postDataToRemote_json(Activity actv)
	 * 
	 * <Caller> 
	 * 1. ButtonOnClickListener
	 * 
	 * <Desc> 
	 * 1. Instead of setting an entity to an http client, create an HttpParams object,
	 * 			set a name-value pair to it, then parse it to a DefaultHttpClient object.
	 * 
	 * <Params> 1.
	 * 
	 * <Return> 1.
	 * 
	 * <Steps> 1.
	 ****************************************/
	public static void test_postDataToRemote_json(Activity actv) {
		/*----------------------------
		 * 1. url
		 * 2. httpPost
		 * 3. Parameters
		 * 4. Client object
		 * 5. Http response
		 * 6. Response null?
		 * 7. Status
			----------------------------*/
		
		// Log
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "test_postDataToRemote => Started");
		
		
		/*----------------------------
		 * 1. url
			----------------------------*/
//		String url = "http://cosmos-cm.herokuapp.com";
		String url = "http://cosmos-cm.herokuapp.com/test2_post_json";
		
		/*----------------------------
		 * 2. httpPost
		 * 		1. Instantiate
		 * 		2. Set header
			----------------------------*/
		HttpPost httpPost = new HttpPost(url);
		
		httpPost.setHeader("Content-type", "application/json");
		
		/*----------------------------
		 * 3. Parameters
			----------------------------*/
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		
		
		JSONObject jso = new JSONObject();
		JSONObject jso2 = new JSONObject();
		
		try {
			
//			jso.put("file_name", "4567.wav");
			
			jso.put("file_name", "8910.wav");
			jso.put("file_path", "/mnt/sdcard-extra/CM");
			
		} catch (JSONException e) {
			
			// debug
			Toast.makeText(actv, e.toString(), 2000).show();
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", e.toString());
			
		}
		
		try {
			
			jso2.put("test_json", jso);
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", jso2.toString());
			
			
		} catch (JSONException e) {
			// debug
			Toast.makeText(actv, "jso2: " + e.toString(), 2000).show();
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "jso2: " + e.toString());
		}
		
		StringEntity stringEntity = null;
		
		try {
			
			stringEntity = new StringEntity(jso2.toString());
			
		} catch (UnsupportedEncodingException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
		
//		paramList.add(new BasicNameValuePair("file_name", "1234.wav"));
//		paramList.add(new BasicNameValuePair("test_data", "1234.wav"));
//		paramList.add(new BasicNameValuePair("test_data", jso));
		
//		paramList.add(new BasicNameValuePair("test_data", "1234.wav"));
		
		
		
//		try {
//			
//			httpPost.setEntity(new UrlEncodedFormEntity(paramList, HTTP.UTF_8));
//			
//		} catch (UnsupportedEncodingException e) {
//			
//			// Log
//			Log.d("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Exception: " + e.toString());
//			
//		}//try

		if (stringEntity != null) {
			
			httpPost.setEntity(stringEntity);
			
		} else {//if (stringEntity != null)
			
			// debug
			Toast.makeText(actv, "stringEntity != null", 2000).show();
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "stringEntity != null");
			
			return;
			
		}//if (stringEntity != null)
		
		HttpUriRequest postRequest = httpPost;
		
		/*----------------------------
		 * 4. Client object
			----------------------------*/
//		HttpParams hp = new BasicHttpParams();
//		
//		hp.setParameter("test_json", jso);
		
		
		DefaultHttpClient dhc = new DefaultHttpClient();
//		DefaultHttpClient dhc = new DefaultHttpClient(hp);
		
		/*----------------------------
		 * 5. Http response
			----------------------------*/
		HttpResponse hr = null;
		
		try {
			
			hr = dhc.execute(postRequest);
			
		} catch (ClientProtocolException e) {
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", e.toString());
		} catch (IOException e) {
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", e.toString());
		}
		
		/*----------------------------
		 * 6. Response null?
			----------------------------*/
		if (hr == null) {
			
			// debug
			Toast.makeText(actv, "hr == null", 2000).show();
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "hr == null");
			
			return;
			
		}//if (hr == null)
		
		/*----------------------------
		 * 7. Status
			----------------------------*/
		int statusCode = hr.getStatusLine().getStatusCode();
		
		// debug
		Toast.makeText(actv, "statusCode: " + statusCode, 2000).show();
		
		// Log
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "statusCode: " + statusCode);
	}//public static void test_postDataToRemote_json(Activity actv)

	/****************************************
	 * 	test_postDataToRemote_json_with_params(Activity actv, String param)
	 * 
	 * <Caller> 
	 * 1. SendMusicDataTask
	 * 
	 * <Desc> 
	 * 1. Instead of setting an entity to an http client, create an HttpParams object,
	 * 			set a name-value pair to it, then parse it to a DefaultHttpClient object.
	 * 
	 * <Params> 1.
	 * 
	 * <Return> 1.
	 * 
	 * <Steps> 1.
	 ****************************************/
	public static boolean test_postDataToRemote_json_with_params(Activity actv, String param) {
		/*----------------------------
		 * 1. url
		 * 2. httpPost
		 * 3. Parameters
		 * 4. Client object
		 * 5. Http response
		 * 6. Response null?
		 * 7. Status
		 * 
		 * 8. Return
			----------------------------*/
		
		// Log
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "test_postDataToRemote => Started");
		
		
		/*----------------------------
		 * 1. url
			----------------------------*/
//		String url = "http://cosmos-cm.herokuapp.com";
		String url = "http://cosmos-cm.herokuapp.com/test2_post_json";
		
		/*----------------------------
		 * 2. httpPost
		 * 		1. Instantiate
		 * 		2. Set header
			----------------------------*/
		HttpPost httpPost = new HttpPost(url);
		
		httpPost.setHeader("Content-type", "application/json");
		
		/*----------------------------
		 * 3. Parameters
			----------------------------*/
		JSONObject jso = new JSONObject();
		JSONObject jso2 = new JSONObject();
		
		try {
			
//			jso.put("file_name", "8910.wav");
			jso.put("file_name", param);
			jso.put("file_path", "/mnt/sdcard-extra/CM");
			
		} catch (JSONException e) {
			
			// debug
			Toast.makeText(actv, e.toString(), 2000).show();
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", e.toString());
			
		}
		
		try {
			
			jso2.put("test_json", jso);
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", jso2.toString());
			
			
		} catch (JSONException e) {
			// debug
			Toast.makeText(actv, "jso2: " + e.toString(), 2000).show();
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "jso2: " + e.toString());
		}
		
		StringEntity stringEntity = null;
		
		try {
			
			stringEntity = new StringEntity(jso2.toString());
			
		} catch (UnsupportedEncodingException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}

		if (stringEntity != null) {
			
			httpPost.setEntity(stringEntity);
			
		} else {//if (stringEntity != null)
			
			// debug
			Toast.makeText(actv, "stringEntity != null", 2000).show();
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "stringEntity != null");
			
			return false;
			
		}//if (stringEntity != null)
		
		HttpUriRequest postRequest = httpPost;
		
		/*----------------------------
		 * 4. Client object
			----------------------------*/
		DefaultHttpClient dhc = new DefaultHttpClient();
		
		/*----------------------------
		 * 5. Http response
			----------------------------*/
		HttpResponse hr = null;
		
		try {
			
			hr = dhc.execute(postRequest);
			
		} catch (ClientProtocolException e) {
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", e.toString());
		} catch (IOException e) {
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", e.toString());
		}
		
		/*----------------------------
		 * 6. Response null?
			----------------------------*/
		if (hr == null) {
			
			// debug
			Toast.makeText(actv, "hr == null", 2000).show();
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "hr == null");
			
			return false;
			
		}//if (hr == null)
		
		/*----------------------------
		 * 7. Status
			----------------------------*/
		int statusCode = hr.getStatusLine().getStatusCode();
		
		// debug
//		Toast.makeText(actv, "statusCode: " + statusCode, 2000).show();
		
		// Log
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "statusCode: " + statusCode);
		
		/*----------------------------
		 * 8. Return
			----------------------------*/
		return true;
		
	}//public static boolean test_postDataToRemote_json_with_params(Activity actv)


	/****************************************
	 *
	 * 
	 * <Caller> 1. MainActv.onOptionsItemSelected()
	 * 
	 * <Desc> 1. <Params> 1.
	 * 
	 * <Return> 1.
	 * 
	 * <Steps> 1.
	 ****************************************/
	public static void dlg_register(Activity actv) {
		/*----------------------------
		 * 1. Prepare data
		 * 2. Prepare adapter
		 * 3. Build dialog
		 * 
		 * 4. Set to list view
		 * 5. Set listener => List view
		 * 
		 * 5-2. Button "Cancel"
		 * 
		 * 6. Show dialog
			----------------------------*/
		List<String> itemList = new ArrayList<String>();
		
		itemList.add(actv.getString(R.string.dlg_register_lv_activity));
		itemList.add(actv.getString(R.string.dlg_register_lv_group));
		itemList.add(actv.getString(R.string.dlg_register_lv_genre));
		
		ArrayAdapter<String> adp = new ArrayAdapter<String>(
				actv,
				android.R.layout.simple_list_item_1,
				itemList
				);
		
		/*----------------------------
		 * 3. Build dialog
			----------------------------*/
		// 
		Dialog dlg = new Dialog(actv);
		
		//
		dlg.setContentView(R.layout.dlg_register);
		
		// Title
		dlg.setTitle(actv.getString(R.string.generic_tv_register));
		
		/*----------------------------
		 * 4. Set to list view
			----------------------------*/
		ListView lv = (ListView) dlg.findViewById(R.id.dlg_register_lv);
		
		lv.setAdapter(adp);
		
		/*----------------------------
		 * 5. Set listener
			----------------------------*/
		lv.setTag(Methods.DialogListTags.dlg_register_lv);
		
		lv.setOnItemClickListener(new DialogOnItemClickListener(actv, dlg));
		
		/*----------------------------
		 * 5-2. Button "Cancel"
			----------------------------*/
		Button bt_cancel = (Button) dlg.findViewById(R.id.dlg_register_bt_cancel);
		
		bt_cancel.setTag(Methods.DialogButtonTags.dlg_generic_dismiss);
		
		bt_cancel.setOnTouchListener(new DialogButtonOnTouchListener(actv));
		bt_cancel.setOnClickListener(new DialogButtonOnClickListener(actv, dlg));
		
		/*----------------------------
		 * 6. Show dialog
			----------------------------*/
		dlg.show();
		
	}//public static void dlg_register(Activity actv)

	public static void registerGenre(Activity actv) {
		/*----------------------------
		 * 1. Get text
		 * 2. Is empty?
		 * 3. db setup
		 * 4. Insert data
			----------------------------*/
		EditText et = (EditText) actv.findViewById(R.id.actv_register_genre_et_name);
		
		String name = et.getText().toString();
		
		/*----------------------------
		 * 2. Is empty?
			----------------------------*/
		if (name.equals("")) {
			
			// debug
			Toast.makeText(actv, "入力がありません", 2000).show();
			
			return;
			
		}//if (name.equals(""))
		
		/*----------------------------
		 * 3. db setup
			----------------------------*/
		DBUtils dbu = new DBUtils(actv, DBUtils.dbName);
		
//		boolean res = dbu.tableExistsOrCreate(
		int i_res = dbu.tableExistsOrCreate(
												actv, 
												DBUtils.dbName, 
												DBUtils.tableName_genres, 
												DBUtils.cols_genres, DBUtils.types_genres);
		 
		/*----------------------------
		 * 4. Insert data
			----------------------------*/
		long created_at = Methods.getMillSeconds_now();
		long modified_at = Methods.getMillSeconds_now();
		
		Object[] values = {name, created_at, modified_at};
		
		boolean res = dbu.insertData(
									actv, 
									DBUtils.dbName, 
									DBUtils.tableName_genres, 
									DBUtils.cols_genres, values);
		
		if (res == true) {
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Data inserted: name=" + name);
			
			// debug
			Toast.makeText(actv, "データを登録しました：" + name, 2000).show();
			
			return;
			
		} else {//if (res == true)

			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Insert data: Failed");
			
			return;

		}//if (res == true)
		
	}//public static void registerGenre(Activity actv)

	
	public static void registerGroup(Activity actv) {
		/*----------------------------
		 * 1. Get text
		 * 1-1. Is empty?
		 * 1-2. Get spinner item
		 * 
		 * 2. Get genre id from genre_name
		 * 3. db setup
		 * 4. Insert data
			----------------------------*/
		EditText et = (EditText) actv.findViewById(R.id.actv_register_group_et_name);
		
		String group_name = et.getText().toString();
		
		/*----------------------------
		 * 1-1. Is empty?
			----------------------------*/
		if (group_name.equals("")) {
			
			// debug
			Toast.makeText(actv, "入力がありません", 2000).show();
			
			return;
			
		}//if (group_name.equals(""))
		
		/*----------------------------
		 * 1-2. Get spinner item
			----------------------------*/
		Spinner sp = (Spinner) actv.findViewById(R.id.actv_register_group_sp_group);
		
		String genre_name = (String) sp.getSelectedItem();
		
		// Log
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "group_name: " + group_name + "/" + "genre_name: " + genre_name);
		
		/*----------------------------
		 * 2. Get genre id from genre_name
			----------------------------*/
		long genre_id = Methods.getGenreId_FromGenreName(
										actv, 
										DBUtils.dbName, 
										DBUtils.tableName_genres,
										genre_name);
		
		// Log
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "genre_id: " + genre_id);
		
		
		/*----------------------------
		 * 3. db setup
			----------------------------*/
		DBUtils dbu = new DBUtils(actv, DBUtils.dbName);
		
		int i_res = dbu.tableExistsOrCreate(
												actv, 
												DBUtils.dbName, 
												DBUtils.tableName_groups, 
												DBUtils.cols_groups, DBUtils.types_groups);
		
		if (i_res == 1) {
			
			// Log
			Log.d("Methods.java"
					+ "["
					+ Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + "]", "Table created: " + DBUtils.tableName_groups);
			
			
		} else if (i_res == 0) {//if (i_result == true)
			// Log
			Log.d("Methods.java"
					+ "["
					+ Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + "]", "Table exists: " + DBUtils.tableName_groups);
			
		} else if (i_res == -1) {//if (i_result == true)

			// Log
			Log.d("Methods.java"
					+ "["
					+ Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + "]", "Create table => Failed: " + DBUtils.tableName_groups);
			
		}//if (i_result == true)
		
//		return;

		/*----------------------------
		 * 4. Insert data
			----------------------------*/
		long created_at = Methods.getMillSeconds_now();
		long modified_at = Methods.getMillSeconds_now();
		
		Object[] values = {group_name, genre_id, created_at, modified_at};
		
		boolean res = dbu.insertData_group(
									actv, 
									DBUtils.dbName, 
									DBUtils.tableName_groups, 
									DBUtils.cols_groups, values);
		
		if (res == true) {
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Data inserted: group_name=" + group_name);
			
			// debug
			Toast.makeText(actv, "データを登録しました：" + group_name, 2000).show();
			
			return;
			
		} else {//if (res == true)

			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Insert data => Failed: " + group_name);
			
			return;

		}//if (res == true)
	}//public static void registerGroup(Activity actv)

	/****************************************
	 *
	 * 
	 * <Caller> 1. <Desc> 1. <Params> 1.
	 * 
	 * <Return> 
	 * 1.	-1		=> The genre doesn't exist in the table
	 * 
	 * <Steps> 1.
	 ****************************************/
	private static long getGenreId_FromGenreName(Activity actv, String dbname,
			String tablenameGenres, String genre_name) {
		/*----------------------------
		 * 1. db setup
		 * 2. Is in the table?
		 * 3. Genre id
		 * 4. Close db
		 * 
		 * 5. Return
			----------------------------*/
		DBUtils dbu = new DBUtils(actv, DBUtils.dbName);
		
		SQLiteDatabase rdb = dbu.getReadableDatabase();
		
		/*----------------------------
		 * 2. Is in the table?
			----------------------------*/
		boolean res = dbu.isInTable(
										actv, DBUtils.dbName, 
										DBUtils.tableName_genres, 
										DBUtils.cols_groups[0], genre_name);

		if (res == false) {
			
			// debug
			Toast.makeText(actv, "このジャンルは、まだ登録されてません", 2000).show();
			
			return -1;
			
		}//if (res == false)
		
		/*----------------------------
		 * 3. Genre id
			----------------------------*/
		String sql = "SELECT * FROM " + tablenameGenres + " WHERE name='" + genre_name + "'";
		
		Cursor c = rdb.rawQuery(sql, null);
		
		c.moveToFirst();
		
		long genre_id = c.getLong(0);
		
		
		rdb.close();
		
		return genre_id;
		
		
//		return 0;
	}//private static long getGenreId_FromGenreName



	/****************************************
	 *
	 * 
	 * <Caller> 1. <Desc> 1. <Params> 1.
	 * 
	 * <Return> 
	 * 1.	-1		=> The group doesn't exist in the table
	 * 
	 * <Steps> 1.
	 ****************************************/
	private static long getGroupId_FromGroupName(Activity actv, String dbname,
			String tablenameGroups, String group_name) {
		/*----------------------------
		 * 1. db setup
		 * 2. Is in the table?
		 * 3. Group id
		 * 4. Close db
		 * 
		 * 5. Return
			----------------------------*/
		DBUtils dbu = new DBUtils(actv, DBUtils.dbName);
		
		SQLiteDatabase rdb = dbu.getReadableDatabase();
		
		/*----------------------------
		 * 2. Is in the table?
			----------------------------*/
		boolean res = dbu.isInTable(
										actv, DBUtils.dbName, 
										DBUtils.tableName_groups, 
										DBUtils.cols_groups[0], group_name);

		if (res == false) {
			
			// debug
			Toast.makeText(actv, "このグループは、まだ登録されてません", 2000).show();
			
			return -1;
			
		}//if (res == false)
		
		/*----------------------------
		 * 3. Group id
			----------------------------*/
		String sql = "SELECT * FROM " + tablenameGroups + " WHERE name='" + group_name + "'";
		
		Cursor c = rdb.rawQuery(sql, null);
		
		c.moveToFirst();
		
		long group_id = c.getLong(0);
		
		
		rdb.close();
		
		return group_id;
		
		
//		return 0;
	}//private static long getGroupId_FromGenreName

	/****************************************
	 *
	 * 
	 * <Caller> 1. <Desc> 1. <Params> 1.
	 * 
	 * <Return> 
	 * 1.	null		=> The activity doesn't exist in the table
	 * 
	 * <Steps> 1.
	 ****************************************/
	public static ActivityItem get_AI_from_AI_dbId(Activity actv, long ai_db_id) {
		/*----------------------------
		 * 1. db setup
		 * 2. Is in the table?
		 * 3. Group id
		 * 4. Close db
		 * 
		 * 5. Return
			----------------------------*/
		DBUtils dbu = new DBUtils(actv, DBUtils.dbName);
		SQLiteDatabase rdb = dbu.getReadableDatabase();
		
		/*----------------------------
		 * 2. Is in the table?
			----------------------------*/
		boolean res = dbu.isInTable(
										actv, DBUtils.dbName, 
										DBUtils.tableName_activities, 
										android.provider.BaseColumns._ID, String.valueOf(ai_db_id));

		if (res == false) {
			
			// debug
			Toast.makeText(actv, "この活動は、まだ登録されてません", 2000).show();
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "ai_db_id: " + ai_db_id);
			
			
			return null;
			
		}//if (res == false)
		
		/*----------------------------
		 * 3. Group id
			----------------------------*/
		String sql = "SELECT * FROM " + DBUtils.tableName_activities + 
								" WHERE " + android.provider.BaseColumns._ID + 
								"='" + String.valueOf(ai_db_id) + "'";
		
		Cursor c = rdb.rawQuery(sql, null);
		
		c.moveToFirst();
		
		ActivityItem ai = new ActivityItem(
				c.getString(1),
				c.getLong(2),
				c.getLong(3), 
				c.getLong(4)
				);
		
		// db_id
		ai.setDb_id(c.getLong(0));

		
//		long group_id = c.getLong(0);
		
		
		rdb.close();
		
		return ai;
		
		
//		return 0;
	}//public static ActivityItem AI_from_AI_dbId()


		
		/****************************************
		 *
		 * 
	
	 * <Caller> 1. ButtonOnClickListener
	 * <Desc> 1. <Params> 1.
	 * 
	 * <Return> 1.
	 * 
	 * <Steps> 1.
	 ****************************************/
	public static void registerActivity(Activity actv) {
		/*----------------------------
		 * 1. Get text
		 * 1-1. Is empty?
		 * 1-2. Get spinner item
		 * 
		 * 2. Get genre id from group_name
		 * 3. db setup
		 * 4. Insert data
			----------------------------*/
		EditText et = (EditText) actv.findViewById(R.id.actv_register_activity_et_name);
		
		String activity_name = et.getText().toString();
		
		/*----------------------------
		 * 1-1. Is empty?
			----------------------------*/
		if (activity_name.equals("")) {
			
			// debug
			Toast.makeText(actv, "入力がありません", 2000).show();
			
			return;
			
		}//if (activity_name.equals(""))
		
		/*----------------------------
		 * 1-2. Get spinner item
			----------------------------*/
		Spinner sp = (Spinner) actv.findViewById(R.id.actv_register_activity_sp_group);
		
		String group_name = (String) sp.getSelectedItem();
		
		// Log
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "activity_name: " + activity_name + "/" + "group_name: " + group_name);
		
		/*----------------------------
		 * 2. Get group id from group_name
			----------------------------*/
		long group_id = Methods.getGroupId_FromGroupName(
										actv, 
										DBUtils.dbName, 
										DBUtils.tableName_groups,
										group_name);
		
		// Log
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "group_id: " + group_id);
		
		
		/*----------------------------
		 * 3. db setup
			----------------------------*/
		DBUtils dbu = new DBUtils(actv, DBUtils.dbName);
		
		int i_res = dbu.tableExistsOrCreate(
												actv, 
												DBUtils.dbName, 
												DBUtils.tableName_groups, 
												DBUtils.cols_groups, DBUtils.types_groups);
		
		if (i_res == 1) {
			
			// Log
			Log.d("Methods.java"
					+ "["
					+ Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + "]", "Table created: " + DBUtils.tableName_groups);
			
			
		} else if (i_res == 0) {//if (i_result == true)
			// Log
			Log.d("Methods.java"
					+ "["
					+ Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + "]", "Table exists: " + DBUtils.tableName_groups);
			
		} else if (i_res == -1) {//if (i_result == true)

			// Log
			Log.d("Methods.java"
					+ "["
					+ Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + "]", "Create table => Failed: " + DBUtils.tableName_groups);
			
		}//if (i_result == true)
		
//		return;

		/*----------------------------
		 * 4. Insert data
			----------------------------*/
		long created_at = Methods.getMillSeconds_now();
		long modified_at = Methods.getMillSeconds_now();
		
		Object[] values = {activity_name, group_id, created_at, modified_at};
		
		boolean res = dbu.insertData_activity(
									actv, 
									DBUtils.dbName, 
									DBUtils.tableName_activities, 
									DBUtils.cols_activities, values);
		
		if (res == true) {
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Data inserted: activity_name=" + activity_name);
			
			// debug
			Toast.makeText(actv, "データを登録しました：" + activity_name, 2000).show();
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Activity registered: " + activity_name);
			
			
			return;
			
		} else {//if (res == true)

			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Insert data => Failed: " + activity_name);
			
			return;

		}//if (res == true)
	}//public static void registerActivity(Activity actv)

	public static List<ActivityItem> getAIList_fromDB(Activity actv) {
		/*----------------------------
		 * 1. db setup
		 * 1-1. Table exists?
		 * 2. Cursor
		 * 3. Build item objects
		 * 
		 * 9. Close db
			----------------------------*/
		/*----------------------------
		 * 1. db setup
			----------------------------*/
		DBUtils dbu = new DBUtils(actv, DBUtils.dbName);
		
		//
		SQLiteDatabase rdb = dbu.getReadableDatabase();

		/*----------------------------
		 * 1-1. Table exists?
			----------------------------*/
		boolean res = dbu.tableExists(rdb, DBUtils.tableName_activities);
		
		if (res == false) {
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table doesn't exist: " + DBUtils.tableName_activities);
			
			return null;
			
		}//if (res == false)
		
		/*----------------------------
		 * 2. Cursor
			----------------------------*/
		String sql = "SELECT * FROM " + DBUtils.tableName_activities;
		
		Cursor c = rdb.rawQuery(sql, null);
		
		actv.startManagingCursor(c);
		
		if (c.getCount() < 1) {
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "c.getCount() < 1");
			
			return null;
			
		}//if (c.getCount() < 1)
		
		/*----------------------------
		 * 3. Build item objects
			----------------------------*/
		List<ActivityItem> aiList = new ArrayList<ActivityItem>();
		
		c.moveToFirst();
		
		for (int i = 0; i < c.getCount(); i++) {
			
			ActivityItem ai = new ActivityItem(
								c.getString(1),
								c.getLong(2),
								c.getLong(3), 
								c.getLong(4)
								);
			
			// db_id
			ai.setDb_id(c.getLong(0));
			
			aiList.add(ai);
			
			c.moveToNext();
			
		}//for (int i = 0; i < c.getCount(); i++)
		
		/*----------------------------
		 * 9. Close db
			----------------------------*/
		rdb.close();
		
		return aiList;
	}//public static List<ActivityItem> getAIList_fromDB(Activity actv)

	/****************************************
	 *
	 * 
	 * <Caller> 1. 
	 * 
	 * <Desc> 1. <Params> 1.
	 * 
	 * <Return> 1.
	 * 
	 * <Steps> 1.
	 ****************************************/
	public static List<ActivityItem> find_AIs_by_group(Activity actv, String groupName) {
		/*----------------------------
		 * 1. db setup
		 * 2. Table exists?
		 * 3. Get group id by group name
		 * 
		 * 2. Cursor
		 * 3. Build item objects
		 * 
		 * 9. Close db
			----------------------------*/
		/*----------------------------
		 * 1. db setup
			----------------------------*/
		DBUtils dbu = new DBUtils(actv, DBUtils.dbName);
		
		//
		SQLiteDatabase rdb = dbu.getReadableDatabase();

		/*----------------------------
		 * 2. Table exists?
			----------------------------*/
		boolean res = dbu.tableExists(rdb, DBUtils.tableName_activities);
		
		if (res == false) {
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table doesn't exist: " + DBUtils.tableName_activities);
			
			return null;
			
		}//if (res == false)
		
		/*----------------------------
		 * 3. Get group id by group name
			----------------------------*/
		long group_id = Methods.get_groupId_by_groupName(actv, groupName);
		
		/*----------------------------
		 * 2. Cursor
			----------------------------*/
		String sql = "SELECT * FROM " + DBUtils.tableName_activities +
						" WHERE " + DBUtils.cols_activities[1] + "='" + group_id + "'";
		
		Cursor c = rdb.rawQuery(sql, null);
		
		actv.startManagingCursor(c);
		
		if (c.getCount() < 1) {
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "c.getCount() < 1");
			
			return null;
			
		}//if (c.getCount() < 1)
		
		/*----------------------------
		 * 3. Build item objects
			----------------------------*/
		List<ActivityItem> aiList = new ArrayList<ActivityItem>();
		
		c.moveToFirst();
		
		for (int i = 0; i < c.getCount(); i++) {
			
			ActivityItem ai = new ActivityItem(
								c.getString(1),
								c.getLong(2),
								c.getLong(3), 
								c.getLong(4)
								);
			
			// db_id
			ai.setDb_id(c.getLong(0));
			
			aiList.add(ai);
			
			c.moveToNext();
			
		}//for (int i = 0; i < c.getCount(); i++)
		
		/*----------------------------
		 * 9. Close db
			----------------------------*/
		rdb.close();
		
		return aiList;
	}//public static List<ActivityItem> find_AIs_by_group(Activity actv)


	/****************************************
	 *
	 * 
	 * <Caller> 1. find_AIs_by_group(Activity actv, String groupName)
	 * 
	 * <Desc> 1. <Params> 1.
	 * 
	 * <Return> 
	 * 1.	-1		=> Item not in the table
	 * 
	 * <Steps> 1.
	 ****************************************/
	private static long get_groupId_by_groupName(Activity actv, String groupName) {
		/*----------------------------
		 * 1. db
			----------------------------*/
		
		DBUtils dbu = new DBUtils(actv, DBUtils.dbName);
		
		SQLiteDatabase rdb = dbu.getReadableDatabase();
		
		/*----------------------------
		 * 2. Is in the table?
			----------------------------*/
		boolean res = dbu.isInTable(
										actv, DBUtils.dbName, 
										DBUtils.tableName_groups, 
										DBUtils.cols_groups[0],
										groupName);

		if (res == false) {
			
			// debug
			Toast.makeText(actv, "このグループは、まだ登録されてません", 2000).show();
			
			return -1;
			
		}//if (res == false)
		
		/*----------------------------
		 * 3. Group name
			----------------------------*/
		String sql = "SELECT * FROM " + 
							DBUtils.tableName_groups + 
							" WHERE " + 
							DBUtils.cols_groups[0] + 
							"='" + groupName + "'";
		
		Cursor c = rdb.rawQuery(sql, null);
		
		c.moveToFirst();
		
		long group_id_obtained = c.getLong(0);
		
		/*----------------------------
		 * 4. Close db
			----------------------------*/
		rdb.close();
		
		return group_id_obtained;
		
	}//private static long get_groupId_by_groupName(String groupName)
	

	/****************************************
	 *
	 * 
	 * <Caller> 1. <Desc> 1. <Params> 1.
	 * 
	 * <Return> 
	 * 1.	null		=> The group doesn't exist in the table
	 * 
	 * <Steps> 1.
	 ****************************************/
	public static String find_GroupName_by_GroupId(
							Activity actv, String dbname, long group_id) {
		/*----------------------------
		 * 1. db setup
		 * 2. Is in the table?
		 * 3. Group name
		 * 4. Close db
		 * 
		 * 5. Return
			----------------------------*/
		DBUtils dbu = new DBUtils(actv, DBUtils.dbName);
		
		SQLiteDatabase rdb = dbu.getReadableDatabase();
		
		/*----------------------------
		 * 2. Is in the table?
			----------------------------*/
		boolean res = dbu.isInTable(
										actv, DBUtils.dbName, 
										DBUtils.tableName_groups, 
										android.provider.BaseColumns._ID,
										String.valueOf(group_id));

		if (res == false) {
			
			// debug
			Toast.makeText(actv, "このグループは、まだ登録されてません", 2000).show();
			
			return null;
			
		}//if (res == false)
		
		/*----------------------------
		 * 3. Group name
			----------------------------*/
		String sql = "SELECT * FROM " + 
							DBUtils.tableName_groups + 
							" WHERE " + android.provider.BaseColumns._ID + 
							"='" + group_id + "'";
		
		Cursor c = rdb.rawQuery(sql, null);
		
		c.moveToFirst();
		
		String group_name_obtained = c.getString(1);
		
		/*----------------------------
		 * 4. Close db
			----------------------------*/
		rdb.close();
		
		/*----------------------------
		 * 5. Return
			----------------------------*/
		return group_name_obtained;
		
//		return 0;
	}//public static String find_GroupName_by_GroupId

	public static void dlg_activity_list(Activity actv, ActivityItem ai) {
		/*----------------------------
		 * 1. Prepare dialog
		 * 2. Prepare => List
		 * 3. Adapter
		 * 4. Set listener => List
			----------------------------*/
		
		Dialog dlg = dlg_template_cancel(
				// Activity, layout
				actv, R.layout.dlg_activity_list_menu,
				// Title
				R.string.dlg_activity_list_menu_title,
				// Cancel button, DialogTags => Cancel
				R.id.dlg_activity_list_btn_cancel, 
				Methods.DialogButtonTags.dlg_generic_dismiss);
		
		/*----------------------------
		 * 2. Prepare => List
			----------------------------*/
		List<String> itemList = new ArrayList<String>();
		
//		String[] items = {"削除"};
		int[] items = {R.string.dlg_activity_list_menu_delete};
		
//		for (int i = 0; i < 10; i++) {
//			for (String item : items) {
		for (int item : items) {
				
			itemList.add(actv.getString(item));
			
		}
//		}//for (int i = 0; i < 10; i++)
//			for (String item : items) {
//				
//				itemList.add(item);
//				
//			}
		
		/*----------------------------
		 * 3. Adapter
			----------------------------*/
		ArrayAdapter<String> adp = new ArrayAdapter<String>(
				actv,
				android.R.layout.simple_list_item_1,
				itemList
				);
		
		ListView lv = (ListView) dlg.findViewById(R.id.dlg_activity_list_lv);
		
		lv.setAdapter(adp);
		
		/*----------------------------
		 * 4. Set listener => List
			----------------------------*/
		lv.setTag(Methods.DialogListTags.dlg_activity_list_menu_lv);
		
		lv.setOnItemClickListener(new DialogOnItemClickListener(actv, dlg, ai));
		
		dlg.show();
		
	}//public static void dlg_activity_list(Activity actv)

	public static Dialog dlg_template_cancel(
				// Activity, layout
				Activity actv, int layoutId,
				// Title
				int titleStringId,
				// Cancel button, DialogTags => Cancel
				int cancelButtonId, Methods.DialogButtonTags cancelTag) {
		/*----------------------------
		* Steps
		* 1. Set up
		* 2. Add listeners => OnTouch
		* 3. Add listeners => OnClick
		----------------------------*/
		
		// 
		Dialog dlg = new Dialog(actv);
		
		//
		dlg.setContentView(layoutId);
		
		// Title
		dlg.setTitle(titleStringId);
		
		/*----------------------------
		* 2. Add listeners => OnTouch
		----------------------------*/
		//
		Button btn_cancel = (Button) dlg.findViewById(cancelButtonId);
		
		//
		btn_cancel.setTag(cancelTag);
		
		//
		btn_cancel.setOnTouchListener(new DialogButtonOnTouchListener(actv, dlg));
		
		/*----------------------------
		* 3. Add listeners => OnClick
		----------------------------*/
		//
		btn_cancel.setOnClickListener(new DialogButtonOnClickListener(actv, dlg));
		
		//
		//dlg.show();
		
		return dlg;
	
	}//public static Dialog dlg_template_okCancel()

	public static Dialog dlg_template_cancel_2_dialogues(
			// Activity, layout
			Activity actv, int layoutId,
			// Title
			int titleStringId,
			// Cancel button
			int cancelButtonId, 
			// DialogTags => Cancel
			Methods.DialogButtonTags cancelTag,
			// First dialog
			Dialog dlg
			) {
		/*----------------------------
		* Steps
		* 1. Set up
		* 2. Add listeners => OnTouch
		* 3. Add listeners => OnClick
		----------------------------*/
		
		// 
		Dialog dlg2 = new Dialog(actv);
		
		//
		dlg2.setContentView(layoutId);
		
		// Title
		dlg2.setTitle(titleStringId);
		
		/*----------------------------
		* 2. Add listeners => OnTouch
		----------------------------*/
		//
		Button btn_cancel = (Button) dlg2.findViewById(cancelButtonId);
		
		//
		btn_cancel.setTag(cancelTag);
		
		//
		btn_cancel.setOnTouchListener(new DialogButtonOnTouchListener(actv, dlg2));
		
		/*----------------------------
		* 3. Add listeners => OnClick
		----------------------------*/
		//
		btn_cancel.setOnClickListener(new DialogButtonOnClickListener(actv, dlg2));
		
		//
		//dlg.show();
		
		return dlg;

	}//public static Dialog dlg_template_cancel_2_dialogues()

	public static Dialog dlg_template_okCancel_2_dialogues(
						// Activity, layout, title
						Activity actv, int layoutId, int titleStringId,
						// Ok, cancel
						int okButtonId, int cancelButtonId,
						// Tags
						DialogButtonTags dlgButtonTagOk, DialogButtonTags dlgButtonTagCancel,
						// 1st dialog
						Dialog dlg1) {
		/*----------------------------
		* Steps
		* 1. Set up
		* 2. Add listeners => OnTouch
		* 3. Add listeners => OnClick
		----------------------------*/
		
		// 
		Dialog dlg2 = new Dialog(actv);
		
		//
		dlg2.setContentView(layoutId);
		
		// Title
		dlg2.setTitle(titleStringId);
		
		/*----------------------------
		* 2. Add listeners => OnTouch
		----------------------------*/
		//
		Button btn_ok = (Button) dlg2.findViewById(okButtonId);
		Button btn_cancel = (Button) dlg2.findViewById(cancelButtonId);
		
		//
		btn_ok.setTag(dlgButtonTagOk);
		btn_cancel.setTag(dlgButtonTagCancel);
		
		//
		btn_ok.setOnTouchListener(new DialogButtonOnTouchListener(actv, dlg2));
		btn_cancel.setOnTouchListener(new DialogButtonOnTouchListener(actv, dlg2));
		
		/*----------------------------
		* 3. Add listeners => OnClick
		----------------------------*/
		//
		btn_ok.setOnClickListener(new DialogButtonOnClickListener(actv, dlg1, dlg2));
		btn_cancel.setOnClickListener(new DialogButtonOnClickListener(actv, dlg1, dlg2));
		
		//
		//dlg.show();
		
		return dlg2;
	
	}//public static Dialog dlg_template_okCancel_2_dialogues()

	
	public static void dlg_confirm_delete_activity(Activity actv, Dialog dlg, ActivityItem ai) {
		/*----------------------------
		 * 1. Dialog prototype
		 * 2. Set text
		 * 2-2. "Ok" button
		 * 3. Show dialog
			----------------------------*/
//		Dialog dlg2 = Methods.dlg_template_okCancel_2_dialogues(
		Dialog dlg2 = Methods.dlg_template_cancel(
											actv, R.layout.dlg_confirm_delete_activity, 
											R.string.generic_tv_confirm,
											R.id.dlg_confirm_delete_activity_btn_cancel,
//											Methods.DialogButtonTags.dlg_generic_dismiss_second_dialog
											Methods.DialogButtonTags.dlg_generic_dismiss
											);
		
//		// Activity, layout
//		Activity actv, int layoutId,
//		// Title
//		int titleStringId,
//		// Cancel button, DialogTags => Cancel
//		int cancelButtonId, Methods.DialogButtonTags cancelTag
		
		/*----------------------------
		 * 2. Set text
			----------------------------*/
		TextView tv_activity_name = 
					(TextView) dlg2.findViewById(
							R.id.dlg_confirm_delete_activity_tv_activity_name);
		
		tv_activity_name.setText(ai.getName());
		
		/*----------------------------
		 * 2-2. "Ok" button
			----------------------------*/
		Button bt_ok = (Button) dlg2.findViewById(R.id.dlg_confirm_delete_activity_btn_ok);
		
		bt_ok.setTag(Methods.DialogButtonTags.dlg_confirm_delete_activity_bt_ok);
		
		bt_ok.setOnTouchListener(new DialogButtonOnTouchListener(actv, dlg));
		
		bt_ok.setOnClickListener(new DialogButtonOnClickListener(actv, dlg, dlg2));
		
		dlg2.show();
	}

	public static void deleteActivity(Activity actv, Dialog dlg, Dialog dlg2) {
		/*----------------------------
		 * 1. Get item name
		 * 2. Delete item from db
		 * 3. Delete item => from aiList in MainActivity
		 * 4. Notifiy to adapter
		 * 
		 * 9. Dismiss dialogues
		 * 9-2. If deletion unsuccessful
		 * 10. Show message
			----------------------------*/
		TextView tv_item_name = 
				(TextView) dlg2.findViewById(
						R.id.dlg_confirm_delete_activity_tv_activity_name);
		
		String item_name = tv_item_name.getText().toString();
		
		/*----------------------------
		 * 2. Delete item from db
			----------------------------*/
		boolean res = Methods.deleteItem_fromDB(
											actv,
											DBUtils.dbName, 
											DBUtils.tableName_activities,
											DBUtils.cols_activities[0],
											item_name
								);
		if(res == true) {
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Item removed from db: " + item_name);
			
			
			/*----------------------------
			 * 3. Delete item => from aiList in MainActivity
				----------------------------*/
			int i;
			boolean flag = false;
			
			for (i = 0; i < MainActv.aiList.size(); i++) {
				
				if (MainActv.aiList.get(i).getName() == item_name) {
					
					flag = true;
					
					break;
					
				}//if (MainActv.aiList.get(i).getName() == item_name);
				
			}//for (int i = 0; i < MainActv.aiList.size(); i++)

			if (flag == true) {
				
				MainActv.aiList.remove(i);
				
				// Log
				Log.d("Methods.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "Removed from aiList: " + item_name);
				
				
			} else {//if (flag == true)
				// Log
				Log.d("Methods.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "Iten is not in aiList: " + item_name);
				
			}//if (flag == true)
			
			
			
//			for (ActivityItem ai : MainActv.aiList) {
//				
//				if (ai.getName() == item_name) {
//					
//					MainActv.aiList.remove(ai);
//					
//				}//if (ai.getName() == item_name)
//				
//			}//for (ActivityItem ai : MainActv.aiList)
			
			
			/*----------------------------
			 * 4. Notifiy to adapter
				----------------------------*/
			MainActv.ailAdapter.notifyDataSetChanged();
				
			/*----------------------------
			 * 9. Dismiss dialogues
				----------------------------*/
			dlg.dismiss();
			dlg2.dismiss();
			
			/*----------------------------
			 * 10. Show message
				----------------------------*/
			// debug
			Toast.makeText(actv, "活動を削除しました", 2000).show();
		
		} else {//if(res == true)
			/*----------------------------
			 * 9-2. If deletion unsuccessful
				----------------------------*/
			// debug
			Toast.makeText(actv, "テーブルから削除できませんでした： " + item_name, 2000).show();
			
		}//if(res == true)
		
	}//public static void deleteActivity(Activity actv, Dialog dlg, Dialog dlg2)

	/****************************************
	 * 
	 * <Caller> 1. deleteActivity(Activity actv, Dialog dlg, Dialog dlg2)
	 * 
	 * <Desc> 1. <Params> 1.
	 * 
	 * <Return> 1.
	 * 
	 * <Steps> 1.
	 ****************************************/
	public static boolean deleteItem_fromDB(Activity actv, String dbName,
			String tableName, String colName, String item_name) {
		/*----------------------------
		 * 1. db setup
		 * 2. Is in table?
		 * 3. If not, delete from the table
			----------------------------*/
		/*----------------------------
		 * 1. db setup
			----------------------------*/
		DBUtils dbu = new DBUtils(actv, DBUtils.dbName);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		boolean res = dbu.isInTable(actv, dbName, tableName, colName, item_name);
		
		if (res == false) {
			
			// debug
			Toast.makeText(actv, "この活動は、テーブルにありません： " + item_name, 2000).show();
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "The item not in the table: " + item_name);
			
			return false;
			
		}//if (res == flase)
		
		/*----------------------------
		 * 3. If not, delete from the table
			----------------------------*/
		res = dbu.deleteData_actvity(actv, wdb, item_name);
		
		return res;
		
	}//public static boolean deleteItem_fromDB

	public static List<String> get_groupList_fromDB(Activity actv) {
		/*----------------------------
		 * 1. db setup
		 * 1-1. Table exists?
		 * 2. Cursor
		 * 3. Build item objects
		 * 
		 * 9. Close db
			----------------------------*/
		/*----------------------------
		 * 1. db setup
			----------------------------*/
		DBUtils dbu = new DBUtils(actv, DBUtils.dbName);
		SQLiteDatabase rdb = dbu.getReadableDatabase();

		/*----------------------------
		 * 1-1. Table exists?
			----------------------------*/
		boolean res = dbu.tableExists(rdb, DBUtils.tableName_groups);
		
		if (res == false) {
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table doesn't exist: " + DBUtils.tableName_groups);
			
			rdb.close();
			
			return null;
			
		}//if (res == false)
		
		/*----------------------------
		 * 2. Cursor
			----------------------------*/
		String sql = "SELECT * FROM " + DBUtils.tableName_groups;
		
		Cursor c = rdb.rawQuery(sql, null);
		
		actv.startManagingCursor(c);
		
		if (c.getCount() < 1) {
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "c.getCount() < 1");
			
			rdb.close();
			
			return null;
			
		}//if (c.getCount() < 1)
		
		/*----------------------------
		 * 3. Build item objects
			----------------------------*/
		List<String> groups = new ArrayList<String>();
		
		c.moveToFirst();
		
		for (int i = 0; i < c.getCount(); i++) {
			
			
			groups.add(c.getString(1));
			
			c.moveToNext();
			
		}//for (int i = 0; i < c.getCount(); i++)
		
		/*----------------------------
		 * 9. Close db
			----------------------------*/
		rdb.close();
		
		return groups;
		
	}//public static List<Memo> getMemoList_fromDB(Activity actv)

	public static List<Memo> getMemoList_fromDB(Activity actv) {
		/*----------------------------
		 * 1. db setup
		 * 1-1. Table exists?
		 * 2. Cursor
		 * 3. Build item objects
		 * 
		 * 9. Close db
			----------------------------*/
		/*----------------------------
		 * 1. db setup
			----------------------------*/
		DBUtils dbu = new DBUtils(actv, DBUtils.dbName);
		SQLiteDatabase rdb = dbu.getReadableDatabase();

		/*----------------------------
		 * 1-1. Table exists?
			----------------------------*/
		boolean res = dbu.tableExists(rdb, DBUtils.tableName_memos);
		
		if (res == false) {
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table doesn't exist: " + DBUtils.tableName_activities);
			
			rdb.close();
			
			return null;
			
		}//if (res == false)
		
		/*----------------------------
		 * 2. Cursor
			----------------------------*/
		String sql = "SELECT * FROM " + DBUtils.tableName_memos;
		
		Cursor c = rdb.rawQuery(sql, null);
		
		actv.startManagingCursor(c);
		
		if (c.getCount() < 1) {
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "c.getCount() < 1");
			
			rdb.close();
			
			return null;
			
		}//if (c.getCount() < 1)
		
		/*----------------------------
		 * 3. Build item objects
			----------------------------*/
		List<Memo> memoList = new ArrayList<Memo>();
		
		c.moveToFirst();
		
		for (int i = 0; i < c.getCount(); i++) {
			
			Memo m = new Memo(
								c.getString(1),		// text
								c.getLong(2),		// activity_id
								c.getLong(4),		// created_at
								c.getLong(5)		// modified_at
								);
			
			// db_id
			m.setDb_id(c.getLong(0));
			
			memoList.add(m);
			
			c.moveToNext();
			
		}//for (int i = 0; i < c.getCount(); i++)
		
		/*----------------------------
		 * 9. Close db
			----------------------------*/
		rdb.close();
		
		return memoList;
		
	}//public static List<Memo> getMemoList_fromDB(Activity actv)

	public static List<Memo> getMemoList_fromDB(Activity actv, long ai_db_id) {
		/*----------------------------
		 * 1. db setup
		 * 1-1. Table exists?
		 * 2. Cursor
		 * 3. Build item objects
		 * 
		 * 9. Close db
			----------------------------*/
		/*----------------------------
		 * 1. db setup
			----------------------------*/
		DBUtils dbu = new DBUtils(actv, DBUtils.dbName);
		SQLiteDatabase rdb = dbu.getReadableDatabase();

		/*----------------------------
		 * 1-1. Table exists?
			----------------------------*/
		boolean res = dbu.tableExists(rdb, DBUtils.tableName_memos);
		
		if (res == false) {
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table doesn't exist: " + DBUtils.tableName_activities);
			
			rdb.close();
			
			return null;
			
		}//if (res == false)
		
		/*----------------------------
		 * 2. Cursor
			----------------------------*/
		String sql = "SELECT * FROM " + DBUtils.tableName_memos +
//						" WHERE " + android.provider.BaseColumns._ID + "='" +
						" WHERE " + DBUtils.cols_memos[1] + "='" +
						String.valueOf(ai_db_id) + "'";
		
		Cursor c = rdb.rawQuery(sql, null);
		
		actv.startManagingCursor(c);
		
		if (c.getCount() < 1) {
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "c.getCount() < 1");
			
			rdb.close();
			
			return null;
			
		}//if (c.getCount() < 1)
		
		/*----------------------------
		 * 3. Build item objects
			----------------------------*/
		List<Memo> memoList = new ArrayList<Memo>();
		
		c.moveToFirst();
		
		for (int i = 0; i < c.getCount(); i++) {
			
			Memo m = new Memo(
								c.getString(1),		// text
								c.getLong(2),		// activity_id
								c.getLong(4),		// created_at
								c.getLong(5)		// modified_at
								);
			
			// db_id
			m.setDb_id(c.getLong(0));
			
			memoList.add(m);
			
			c.moveToNext();
			
		}//for (int i = 0; i < c.getCount(); i++)
		
		/*----------------------------
		 * 9. Close db
			----------------------------*/
		rdb.close();
		
		return memoList;
	}//public static List<Memo> getMemoList_fromDB


	public static void dlg_inputEmpty(Activity actv) {
		/*----------------------------
		 * memo
			----------------------------*/
		Dialog dlg = Methods.dlg_template_cancel(
										actv, 
//										R.layout.dlg_input_empty, 
										R.layout.dlg_input_empty_original,
										R.string.generic_tv_input_empty, 
										R.id.dlg_input_empty_bt_ok, 
										Methods.DialogButtonTags.dlg_generic_dismiss);
		
		dlg.show();
		
	}//public static void dlg_inputEmpty(Activity actv)

	public static void addMemo(Activity actv, long ai_db_id, String content) {
		/*----------------------------
		 * 1. Prepare data
		 * 2. Insert data
			----------------------------*/
		long created_at = Methods.getMillSeconds_now();
		long modified_at = Methods.getMillSeconds_now();
		
		ActivityItem ai = Methods.get_AI_from_AI_dbId(actv, ai_db_id);
		
		if (ai == null) {
			// debug
			Toast.makeText(actv, "ai == null", 2000)
					.show();
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber() + "]", 
					"ai == null : ai_db_id = " + ai_db_id);
			
			return;
		}//if (ai == null)
		
		long group_id = ai.getGroup_id();
		
//		"text", "activity_id", "group_id", "created_at", "modified_at"
		Object[] memo_data = {content, ai_db_id, group_id, created_at, modified_at};
		
		/*----------------------------
		 * 2. Insert data
			----------------------------*/
		boolean res = DBUtils.insertData_memo(actv, memo_data);
		
		if (res == true) {
			
			// debug
			Toast.makeText(actv, "メモを保管しました", 2000)
					.show();
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber() + "]", 
					"Memo stored: Activity => " + ai_db_id);
			
		} else {//if (res == true)
			// debug
			Toast.makeText(actv, "メモを保管できませんでした", 2000)
					.show();
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber() + "]", 
					"Memo couldn't be stored: Activity => " + ai_db_id);
			
		}//if (res == true)
		
		
	}//public static void addMemo(Activity actv, long ai_db_id, String content)

	public static void dlg_main_opt_filter(Activity actv) {
		/*----------------------------
		 * 1. Setup dialog
		 * 2. ListView
		 * 3. Adapter
		 * 4. Set adapter
		 * 5. Set listener
		 * 
		 * 9. Show dialog
			----------------------------*/
		Dialog dlg = dlg_template_cancel(
				// Activity, layout
				actv, R.layout.dlg_main_actv_filter,
				// Title
				R.string.main_opt_menu_filter,
				// Cancel button, DialogTags => Cancel
				R.id.dlg_main_actv_filter_btn_cancel, 
				Methods.DialogButtonTags.dlg_generic_dismiss);
		
		/*----------------------------
		 * 2. ListView
			----------------------------*/
		ListView lv = (ListView) dlg.findViewById(R.id.dlg_main_actv_filter_lv);
		
		List<String> items = new ArrayList<String>();
		
		String[] itemNames = {
				
				actv.getString(R.string.dlg_main_actv_filter_list_item_group),
				actv.getString(R.string.dlg_main_actv_filter_list_item_genre),
				actv.getString(R.string.dlg_main_actv_filter_list_item_date),
				actv.getString(R.string.dlg_main_actv_filter_list_item_keyword),
				actv.getString(R.string.dlg_main_actv_filter_list_item_all)
		};//String[] itemNames
		
		for (String name : itemNames) {
			
			items.add(name);
			
		}
		
		/*----------------------------
		 * 3. Adapter
			----------------------------*/
		ArrayAdapter<String> adp = new ArrayAdapter<String>(
				actv,
				android.R.layout.simple_list_item_1,
				items
				);
		
		/*----------------------------
		 * 4. Set adapter
			----------------------------*/
		lv.setAdapter(adp);

		/*----------------------------
		 * 5. Set listener
			----------------------------*/
		lv.setTag(Methods.DialogListTags.dlg_main_actv_filter_lv);
		
		lv.setOnItemClickListener(new DialogOnItemClickListener(actv, dlg));
		
		/*----------------------------
		 * 9. Show dialog
			----------------------------*/
		dlg.show();
		
		
	}//public static void dlg_main_opt_filter(Activity actv)

	public static void mainOptFilter(Activity actv, Dialog dlg, String item) {
		/*----------------------------
		 * 0. "All"
		 * 1. Dialog
		 * 
		 * 9. Show dialog
			----------------------------*/
		/*----------------------------
		 * 0. "All"
			----------------------------*/
		if(item.equals(actv.getString(R.string.dlg_main_actv_filter_list_item_all))) {
			MainActv.aiList.clear();
			
			MainActv.aiList.addAll(Methods.getAIList_fromDB(actv));
			
			Methods.sort_activitiesList_group(MainActv.aiList);
			
			MainActv.ailAdapter.notifyDataSetChanged();
			
			dlg.dismiss();
			
			return;
		}//if(item.equals(actv.getString(R.string.dlg_main_actv_filter_list_item_all)))
		
		/*----------------------------
		 * 1. Dialog
			----------------------------*/
		Dialog dlg2 = new Dialog(actv);

		if (item.equals(actv.getString(R.string.dlg_main_actv_filter_list_item_group))) {
			/*----------------------------
			 * 1. Content view, title
			 * 2. Listeners
			 * 3. List view
				----------------------------*/
			
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "item: " + item + "/" +
					"actv.getString(R.string.dlg_main_actv_filter_list_item_group): " +
					actv.getString(R.string.dlg_main_actv_filter_list_item_group));
			
//					+ "actv.getString(R.string.dlg_main_actv_filter_list_item_group): " + 
//							actv.getString(R.string.dlg_main_actv_filter_list_item_group));
			
			
			//
			dlg2.setContentView(R.layout.dlg_main_actv_filter_group);
			
			// Title
			dlg2.setTitle(R.string.dlg_main_actv_filter_list_group_title);
			
			/*----------------------------
			* 2. Listeners
			----------------------------*/
			//
			Button btn_cancel = (Button) dlg2.findViewById(R.id.dlg_main_actv_filter_group_btn_cancel);
			
			//
			btn_cancel.setTag(Methods.DialogButtonTags.dlg_generic_dismiss_second_dialog);
			
			//
			btn_cancel.setOnTouchListener(new DialogButtonOnTouchListener(actv, dlg2));
			btn_cancel.setOnClickListener(new DialogButtonOnClickListener(actv, dlg, dlg2));

			/*----------------------------
			 * 3. List view
			 * 		1. Set up
			 * 		2. Listener
				----------------------------*/
			ListView lv = (ListView) dlg2.findViewById(R.id.dlg_main_actv_filter_group_lv);
			
			lv.setTag(Methods.DialogListTags.dlg_main_actv_filter_group_lv);
			
//			List<String> groups = new ArrayList<String>();
			List<String> groups = Methods.get_groupList_fromDB(actv);
			
			
			
			ArrayAdapter<String> adp = new ArrayAdapter<String>(
					actv,
					android.R.layout.simple_list_item_1,
					groups
					);

			lv.setAdapter(adp);
			
			/*----------------------------
			 * 3.2. Listener
				----------------------------*/
			lv.setOnItemClickListener(new DialogOnItemClickListener(actv, dlg, dlg2));
			
			dlg2.show();
			
		} else {//if (dlg2 == null)
			
			Toast.makeText(actv, "Not 'group'", 2000).show();

//			dlg2.show();

		}//if (dlg2 == null)
//		dlg2.show();
		

		
//		Dialog dlg2 = null;
		
//		if (item.equals(actv.getString(R.string.dlg_main_actv_filter_list_item_group))) {
//			
//			dlg2 = Methods.dlg_template_cancel_2_dialogues(
//					actv, 
//					R.layout.dlg_main_actv_filter_group, 
//					R.string.dlg_main_actv_filter_list_group_title,
//					R.id.dlg_main_actv_filter_group_btn_cancel,
//					Methods.DialogButtonTags.dlg_generic_dismiss_second_dialog, 
//					dlg);
//			
//		}//if (item.equals(actv.getString(R.string.dlg_main_actv_filter_list_item_group)))
		
		// debug
//		Toast.makeText(actv, 
//				"R.string: " + actv.getString(R.string.dlg_main_actv_filter_list_item_group) + "/" +
//				"item: " + item, 
//				2000).show();
//		Toast.makeText(actv, 
//				String.valueOf(item.equals(actv.getString(R.string.dlg_main_actv_filter_list_item_group))), 
//		2000).show();
//		if (dlg2 == null) {
//			
//			Toast.makeText(actv, "dlg2 == null", 2000).show();
//			
//		} else {//if (dlg2 == null)
//			
//			Toast.makeText(actv, "dlg2 != null", 2000).show();
//			
//		}//if (dlg2 == null)
//		Toast.makeText(actv, 
//			String.valueOf(dlg2 == null), 
//			2000).show();
		
//		item.equals(actv.getString(R.string.dlg_main_actv_filter_list_item_group))
		/*----------------------------
		 * 9. Show dialog
			----------------------------*/
//		if (dlg2 != null) {
//			
//			dlg2.show();
//			
//			Toast.makeText(actv, "dlg2 => Shown", 2000).show();
//			
//		} else {//if (dlg2 != null)
//			
//			// debug
//			Toast.makeText(actv, "ダイアローグを作れませんでした", 2000).show();
//			
//		}//if (dlg2 != null)
		
//		// debug
//		Toast.makeText(actv, item, 2000).show();
		
	}//public static void mainOptFilter(Activity actv, Dialog dlg)

	/****************************************
	 * 
	 * 
	 * <Caller> 1. DialogOnItemClickListener.onItemClick() # case dlg_main_actv_filter_group_lv
	 * 
	 * <Desc> 1. <Params> 1.
	 * 
	 * <Return> 1.
	 * 
	 * <Steps> 1.
	 ****************************************/
	public static void mainOptFilter_group(Activity actv, Dialog dlg,
			Dialog dlg2, String item) {
		/*----------------------------
		 * 1. Update aiList
		 * 2. Notify adapter
		 * 3. Dismiss dialogues
			----------------------------*/
		MainActv.aiList.clear();
		
//		MainActv.aiList = Methods.find_AIs_by_group(actv, item);
		MainActv.aiList.addAll(Methods.find_AIs_by_group(actv, item));
		
		// Log
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "MainActv.aiList.size(): " + MainActv.aiList.size());
		
		
		/*----------------------------
		 * 2. Notify adapter
			----------------------------*/
		MainActv.ailAdapter.notifyDataSetChanged();
		
		// Log
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "MainActv.ailAdapter => Notified");
		
		/*----------------------------
		 * 3. Dismiss dialogues
			----------------------------*/
		dlg2.dismiss();
		dlg.dismiss();
		
		// debug
		Toast.makeText(actv, "フィルターしました", 2000).show();
		
	}//public static void mainOptFilter_group()

	
	public static void dlg_menu_Memo(Activity actv, Memo m) {
		/*----------------------------
		 * 1. Dialog
		 * 2. Prep => List
		 * 3. Adapter
		 * 4. Set adapter
		 * 5. Set listener
		 * 
		 * 9. Show
			----------------------------*/
		Dialog dlg = dlg_template_cancel(
				// Activity, layout
				actv, R.layout.dlg_menu_memo,
				// Title
				R.string.dlg_menu_memo_title,
				// Cancel button, DialogTags => Cancel
				R.id.dlg_menu_memo_btn_cancel, 
				Methods.DialogButtonTags.dlg_generic_dismiss);
//		
		/*----------------------------
		 * 2. Prep => List
			----------------------------*/
		List<String> menuItems = new ArrayList<String>();
		
		menuItems.add(actv.getString(R.string.generic_tv_edit));
		menuItems.add(actv.getString(R.string.generic_tv_delete));
		menuItems.add(actv.getString(R.string.generic_tv_copy));
		
		/*----------------------------
		 * 3. Adapter
			----------------------------*/
		ArrayAdapter<String> adp = new ArrayAdapter<String>(
				actv,
				android.R.layout.simple_list_item_1,
				menuItems
				);
		
		/*----------------------------
		 * 4. Set adapter
			----------------------------*/
		ListView lv = (ListView) dlg.findViewById(R.id.dlg_menu_memo_lv);
		
		lv.setAdapter(adp);
		
		/*----------------------------
		 * 5. Set listener
			----------------------------*/
		lv.setTag(Methods.DialogListTags.dlg_menu_memo_lv);
		
		lv.setOnItemClickListener(new DialogOnItemClickListener(actv, dlg, m));
		
		/*----------------------------
		 * 9. Show
			----------------------------*/
		dlg.show();
		
	}//public static void dlg_menu_Memo(Activity actv, Memo m)

	public static void dlg_editMemo(Activity actv, Dialog dlg, Memo m) {
		/*----------------------------
		 * 1. Buil dialog
		 * 2. Set text
		 * 3. Listeners
		 * 4. Show dialog
			----------------------------*/
		Dialog dlg2 = new Dialog(actv);
		
		//
		dlg2.setContentView(R.layout.dlg_edit_memo);
		
		// Title
		dlg2.setTitle(actv.getString(R.string.generic_tv_register));
		
		/*----------------------------
		 * 2. Set text
			----------------------------*/
		String text = m.getText();
		
		EditText et = (EditText) dlg2.findViewById(R.id.dlg_edit_memo_et_content);
		
		et.setText(text);
		
		et.setSelection(text.length());
		
		/*----------------------------
		 * 3. Listeners
			----------------------------*/
		/*----------------------------
		 * OnTouch
			----------------------------*/
		//
		Button btn_ok = (Button) dlg2.findViewById(R.id.dlg_edit_memo_bt_ok);
		Button btn_cancel = (Button) dlg2.findViewById(R.id.dlg_edit_memo_cancel);
		
		//
		btn_ok.setTag(Methods.DialogButtonTags.dlg_edit_memo_ok);
		btn_cancel.setTag(Methods.DialogButtonTags.dlg_generic_dismiss_second_dialog);
		
		//
		btn_ok.setOnTouchListener(new DialogButtonOnTouchListener(actv, dlg2));
		btn_cancel.setOnTouchListener(new DialogButtonOnTouchListener(actv, dlg2));
		
		/*----------------------------
		 * OnClick
			----------------------------*/
		//
		btn_ok.setOnClickListener(new DialogButtonOnClickListener(actv, dlg, dlg2, m));
		btn_cancel.setOnClickListener(new DialogButtonOnClickListener(actv, dlg, dlg2));
		
		/*----------------------------
		 * 4. Show dialog
			----------------------------*/
		dlg2.show();
		
	}//public static void dlg_editMemo(Activity actv, Dialog dlg, Memo m)

	public static void editMemo(Activity actv, Dialog dlg, Dialog dlg2, Memo m) {
		/*----------------------------
		 * 1. Input empty?
		 * 2. Update data
		 * 3. Show message
		 * 4. Dismiss dialogues
			----------------------------*/
		EditText et = (EditText) dlg2.findViewById(R.id.dlg_edit_memo_et_content);
		
		String content = et.getText().toString();
		
		if (content.length() < 1) {
			
			// debug
			Toast.makeText(actv, "入力がありません", 2000).show();
			
			return;
			
		}//if (content.length() < 1)
		
		/*----------------------------
		 * 2. Update data
			----------------------------*/
		m.setText(content);
		
		boolean res = DBUtils.updateData_memos(actv, m);
		
		/*----------------------------
		 * 3. Show message
			----------------------------*/
		if (res == true) {
			
			// debug
			Toast.makeText(actv, "メモを更新しました", 2000).show();
			
			dlg2.dismiss();
			dlg.dismiss();
			
			// Update list
			ShowActivityActv.memoList.clear();
			ShowActivityActv.memoList.addAll(Methods.getMemoList_fromDB(actv, m.getActivity_id()));
			
			ShowActivityActv.mlAdapter.notifyDataSetChanged();
			
		} else {//if (res == true)

			// debug
			Toast.makeText(actv, "メモを更新できませんでした", 2000).show();
		
		}//if (res == true)
		
		
	}//public static void editMemo(Activity actv, Dialog dlg, Dialog dlg2)

	public static void dlg_confirm_deleteMemo(Activity actv, Dialog dlg, Memo m) {
		/*----------------------------
		 * 1. Buil dialog
		 * 2. Set text
		 * 3. Listeners
		 * 4. Show dialog
			----------------------------*/
		Dialog dlg2 = new Dialog(actv);
		
		//
		dlg2.setContentView(R.layout.dlg_confirm_delete_memo);
		
		// Title
		dlg2.setTitle(actv.getString(R.string.generic_tv_confirm));
		
		/*----------------------------
		 * 2. Set text
			----------------------------*/
		String text = m.getText();
		
		if (text.length() > 10) {
			
			text = text.substring(0, 10);
			
			text += "...";
			
		}//if (text.length() > 10)
		
		
		TextView tv = (TextView) dlg2.findViewById(R.id.dlg_confirm_delete_memo_tv_memo_content);
		
		tv.setText(text);
		
		/*----------------------------
		 * 3. Listeners
			----------------------------*/
		/*----------------------------
		 * OnTouch
			----------------------------*/
		//
		Button btn_ok = (Button) dlg2.findViewById(R.id.dlg_confirm_delete_memo_btn_ok);
		Button btn_cancel = (Button) dlg2.findViewById(R.id.dlg_confirm_delete_memo_btn_cancel);
		
		//
		btn_ok.setTag(Methods.DialogButtonTags.dlg_confirm_delete_memo_ok);
		btn_cancel.setTag(Methods.DialogButtonTags.dlg_generic_dismiss_second_dialog);
		
		//
		btn_ok.setOnTouchListener(new DialogButtonOnTouchListener(actv, dlg2));
		btn_cancel.setOnTouchListener(new DialogButtonOnTouchListener(actv, dlg2));
		
		/*----------------------------
		 * OnClick
			----------------------------*/
		//
		btn_ok.setOnClickListener(new DialogButtonOnClickListener(actv, dlg, dlg2, m));
		btn_cancel.setOnClickListener(new DialogButtonOnClickListener(actv, dlg, dlg2));
		
		/*----------------------------
		 * 4. Show dialog
			----------------------------*/
		dlg2.show();
		
	}//public static void dlg_confirm_deleteMemo(Activity actv, Dialog dlg, Memo m)

	/****************************************
	 *
	 * 
	 * <Caller> 1. DialogButtonOnClickListener # dlg_confirm_delete_memo_ok
	 * 
	 * <Desc> 1. <Params> 1.
	 * 
	 * <Return> 1.
	 * 
	 * <Steps> 1.
	 ****************************************/
	public static void deleteMemo(Activity actv, Dialog dlg, Dialog dlg2, Memo m) {
		/*----------------------------
		 * 1. Delete memo
		 * 2. Message
		 * 3. Dismiss dlg's
		 * 4. Update memo list
			----------------------------*/
		long activity_id = m.getActivity_id();
		
		boolean res = DBUtils.deleteData_memo(actv, m);
		
		if (res == true) {
			
			//debug
			Toast.makeText(actv, "メモを削除しました", 2000).show();
			
			dlg2.dismiss(); dlg.dismiss();
			
		} else {//if (res == true)

			//debug
			Toast.makeText(actv, "メモを削除できませんでした", 2000).show();
			
		}//if (res == true)
		
		/*----------------------------
		 * 4. Update memo list
			----------------------------*/
		Methods.updateMemoList(actv, activity_id);
		
	}//public static void deleteMemo(Activity actv, Dialog dlg, Dialog dlg2, Memo m)

	public static void updateMemoList(Activity actv, long activity_id) {
		
		ShowActivityActv.memoList.clear();
		ShowActivityActv.memoList.addAll(Methods.getMemoList_fromDB(actv, activity_id));
		
		ShowActivityActv.mlAdapter.notifyDataSetChanged();

	}//public static void updateMemoList(Activity actv, Memo m)

}//public class Methods

////debug
//		Toast.makeText(actv, item, 2000).show();
		
