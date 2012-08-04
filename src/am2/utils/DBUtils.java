package am2.utils;




import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
//import android.view
import android.widget.LinearLayout.LayoutParams;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/****************************************
 * Copy & pasted from => C:\WORKS\WORKSPACES_ANDROID\ShoppingList\src\shoppinglist\main\DBManager.java
 ****************************************/
public class DBUtils extends SQLiteOpenHelper{

	/*****************************************************************
	 * Class fields
	 *****************************************************************/
	/*----------------------------
	 * 1. DB-related
	 * 2. Table names, folder names
	 * 3. Activity, context
	 * 4. Columns, types
		----------------------------*/
	/*----------------------------
	 * 1. DB-related
		----------------------------*/
	// Database
	SQLiteDatabase db = null;
	
	 // DB name
	public final static String dbName = "AM1.db";
	
	/*----------------------------
	 * 2. Table names, folder names
		----------------------------*/
	// Table names
	public final static String mainTableName = "main_table";
	
	public final static String tableName_genres = "genres";
	
	public final static String tableName_groups = "groups";
	
	public final static String tableName_activities = "activities";

	public final static String tableName_memos = "memos";

	public static String currentTableName = null;
	
	public static String baseDirName = "";
	
	/*----------------------------
	 * 3. Activity, context
		----------------------------*/
	// Activity
	Activity activity;
	
	//
	Context context;
	
	/*----------------------------
	 * 4. Columns, types
		----------------------------*/
	// 
	public static final String[] cols_genres = {
		"name", "created_at", "modified_at"
	};
	
	public static final String[] types_genres = {
		"TEXT", "INTEGER", "INTEGER"
	};
	
	public static final String[] cols_groups = {
		"name", "genre_id", "created_at", "modified_at"
	};
	
	public static final String[] types_groups = {
		"TEXT", "INTEGER", "INTEGER", "INTEGER"
	};
	
	public static final String[] cols_activities = {
		"name", "group_id", "created_at", "modified_at", "finished_at"
	};
	
	public static final String[] types_activities = {
		"TEXT", "INTEGER", "INTEGER", "INTEGER", "INTEGER"
	};

	public static final String[] cols_memos = {
		"text", "activity_id", "group_id", "created_at", "modified_at"
	};
	
	public static final String[] types_memos = {
		"TEXT", "INTEGER", "INTEGER", "INTEGER", "INTEGER"
	};

	/*****************************************************************
	 * Constructor
	 *****************************************************************/
	public DBUtils(Context context, String dbName) {
		super(context, dbName, null, 1);
		
		// Initialize activity
		this.activity = (Activity) context;
		
		this.context = context;
		
//		this.dbName = dbName;
		
	}//public DBUtils(Context context)

//	public DBUtils() {
//		// TODO �����������ꂽ�R���X�g���N�^�[�E�X�^�u
//	}

	/*******************************************************
	 * Methods
	 *******************************************************/
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		
	}//public void onCreate(SQLiteDatabase db)

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		
	}

	/****************************************
	 * createTable_generic()
	 * 
	 * <Caller> 
	 * 1. 
	 * 
	 * <Desc> 1. <Params> 1.
	 * 
	 * <Return> 1.
	 * 
	 * <Steps> 1.
	 ****************************************/
	public boolean createTable(
					SQLiteDatabase db, String tableName, String[] columns, String[] types) {
		/*----------------------------
		 * Steps
		 * 1. Table exists?
		 * 2. Build sql
		 * 3. Exec sql
			----------------------------*/
		
		//
//		if (!tableExists(db, tableName)) {
		if (tableExists(db, tableName)) {
			// Log
			Log.d("DBManager.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table exists => " + tableName);
			
			return false;
		}//if (!tableExists(SQLiteDatabase db, String tableName))
		
		/*----------------------------
		 * 2. Build sql
			----------------------------*/
		//
		StringBuilder sb = new StringBuilder();
		
		sb.append("CREATE TABLE " + tableName + " (");
		sb.append(android.provider.BaseColumns._ID +
							" INTEGER PRIMARY KEY AUTOINCREMENT, ");
		
		int i = 0;
		for (i = 0; i < columns.length - 1; i++) {
			sb.append(columns[i] + " " + types[i] + ", ");
		}//for (int i = 0; i < columns.length - 1; i++)
		
		sb.append(columns[i] + " " + types[i]);
		
		sb.append(");");
		
		// Log
		Log.d("DBManager.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "sql => " + sb.toString());
		
		/*----------------------------
		 * 3. Exec sql
			----------------------------*/
		//
		try {
//			db.execSQL(sql);
			db.execSQL(sb.toString());
			
			// Log
			Log.d(this.getClass().getName() + 
					"["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table created => " + tableName);
			
			
			return true;
		} catch (SQLException e) {
			// Log
			Log.d(this.getClass().getName() + 
					"[" + Thread.currentThread().getStackTrace()[2].getLineNumber() + "]", 
					"Exception => " + e.toString());
			
			return false;
		}//try
		
	}//public boolean createTable(SQLiteDatabase db, String tableName)

	public boolean tableExists(SQLiteDatabase db, String tableName) {
		// The table exists?
		Cursor cursor = db.rawQuery(
									"SELECT * FROM sqlite_master WHERE tbl_name = '" + 
									tableName + "'", null);
		
		((Activity) context).startManagingCursor(cursor);
//		actv.startManagingCursor(cursor);
		
		// Judge
		if (cursor.getCount() > 0) {
			return true;
		} else {//if (cursor.getCount() > 0)
			return false;
		}//if (cursor.getCount() > 0)
	}//public boolean tableExists(String tableName)

	/****************************************
	 *
	 * 
	 * <Caller> 1. <Desc> 1. <Params> 1.
	 * 
	 * <Return>
	 *  1. 1		=> Table created
	 *  2. 0		=> Table exists
	 *  3. -1		=> Exception
	 * 
	 * <Steps> 1.
	 ****************************************/
	public int tableExistsOrCreate(Activity actv, String dbName, 
									String tableName, String[] columns, String[] types) {
		/*----------------------------
		 * 1. db setup
		 * 2. Table exists?
		 * 2-1. If exists, close db and return
		 * 2-2. If not, create one and return
			----------------------------*/
		
		DBUtils dbu = new DBUtils(actv, DBUtils.dbName);
		
		//
		SQLiteDatabase rdb = dbu.getReadableDatabase();
		
		
		/*----------------------------
		 * // The table exists?
			----------------------------*/
		Cursor cursor = rdb.rawQuery(
									"SELECT * FROM sqlite_master WHERE tbl_name = '" + 
									tableName + "'", null);
		
//		((Activity) context).startManagingCursor(cursor);
		actv.startManagingCursor(cursor);
		
		// Judge
		if (cursor.getCount() > 0) {
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table exists: " + tableName);
			
			/*----------------------------
			 * 2-1. If exists, close db and return
				----------------------------*/
			rdb.close();
			
			return 0;
			
		} else {//if (cursor.getCount() > 0)
			
			/*----------------------------
			 * 2-2. If not, create one and return
				----------------------------*/
			rdb.close();
			
			SQLiteDatabase wdb = dbu.getWritableDatabase();
			
			boolean res = dbu.createTable(wdb, tableName, columns, types);
			
			if (res == true) {
				
				// Log
				Log.d("DBUtils.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "Table created: " + tableName);
				
				wdb.close();
				
				return 1;
				
			}//if (res == true)
			
			wdb.close();
			
			return -1;
			
		}//if (cursor.getCount() > 0)
		
	}//public int tableExists(String tableName)

	public boolean dropTable(Activity actv, SQLiteDatabase db, String tableName) {
		/*------------------------------
		 * The table exists?
		 *------------------------------*/
		// The table exists?
		boolean tempBool = tableExists(db, tableName);
		
		if (tempBool == true) {
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table exists: " + tableName);
			
//			Methods.recordLog(actv, 
//					"DBUtils.java", 
//					"Table exists: " + tableName);
			
		} else {//if (tempBool == true)
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table doesn't exist: " + tableName);

//			Methods.recordLog(actv, 
//					"DBUtils.java", 
//					"Table doesn't exist: " + tableName);

			return false;
		}

		/*------------------------------
		 * Drop the table
		 *------------------------------*/
		// Define the sql
        String sql 
             = "DROP TABLE " + tableName;
        
        // Execute
        try {
			db.execSQL(sql);
			
			// Vacuum
			db.execSQL("VACUUM");
			
			// Log
			Log.d("DBManager.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "The table dropped => " + tableName);
			
			// Return
			return true;
			
		} catch (SQLException e) {
			// TODO �����������ꂽ catch �u���b�N
			// Log
			Log.e("DBManager.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "DROP TABLE => failed (table=" + tableName + "): " + e.toString());
			
			// debug
			Toast.makeText(actv, 
						"DROP TABLE => failed(table=" + tableName, 
						3000).show();
			
			// Return
			return false;
		}//try

	}//public boolean dropTable(String tableName) 

	public boolean insertData(SQLiteDatabase db, String tableName, 
												String[] columnNames, String[] values) {
		
////		String sql = "SELECT * FROM TABLE " + DBUtils.table_name_memo_patterns;
//		String sql = "SELECT * FROM " + DBUtils.table_name_memo_patterns;
//		
//		Cursor c = db.rawQuery(sql, null);
//		
//		
//		
//		// Log
//		Log.d("DBUtils.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "c.getCount() => " + c.getCount() + " / " +
//				"c.getColumnCount() => " + c.getColumnCount());
//		
//		c.close();
		
		
		/*----------------------------
		* 1. Insert data
		----------------------------*/
		try {
			// Start transaction
			db.beginTransaction();
			
			// ContentValues
			ContentValues val = new ContentValues();
			
			// Put values
			for (int i = 0; i < columnNames.length; i++) {
				val.put(columnNames[i], values[i]);
			}//for (int i = 0; i < columnNames.length; i++)
			
			// Insert data
			db.insert(tableName, null, val);
			
			// Set as successful
			db.setTransactionSuccessful();
			
			// End transaction
			db.endTransaction();
			
			// Log
//			Log.d("DBUtils.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "Data inserted => " + "(" + columnNames[0] + " => " + values[0] + 
//				" / " + columnNames[3] + " => " + values[3] + ")");
			
			return true;
		} catch (Exception e) {
			// Log
			Log.e("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Exception! => " + e.toString());
			
			return false;
		}//try
		
//		//debug
//		return false;
		
	}//public insertData(String tableName, String[] columnNames, String[] values)

	public boolean insertData(SQLiteDatabase db, String tableName, 
											String[] columnNames, long[] values) {
		/*----------------------------
		* 1. Insert data
		----------------------------*/
		try {
			// Start transaction
			db.beginTransaction();
			
			// ContentValues
			ContentValues val = new ContentValues();
			
			// Put values
			for (int i = 0; i < columnNames.length; i++) {
				val.put(columnNames[i], values[i]);
			}//for (int i = 0; i < columnNames.length; i++)
			
			// Insert data
			db.insert(tableName, null, val);
			
			// Set as successful
			db.setTransactionSuccessful();
			
			// End transaction
			db.endTransaction();
			
			// Log
			Log.d("DBUtils.java" + "["
			+ Thread.currentThread().getStackTrace()[2].getLineNumber()
			+ "]", "Data inserted => " + "(" + columnNames[0] + " => " + values[0] + "), and others");
			
			return true;
		} catch (Exception e) {
			// Log
			Log.e("DBUtils.java" + "["
			+ Thread.currentThread().getStackTrace()[2].getLineNumber()
			+ "]", "Exception! => " + e.toString());
			
			return false;
		}//try
	}//public insertData(String tableName, String[] columnNames, String[] values)

	public boolean insertData(SQLiteDatabase db, String tableName, 
			String[] columnNames, Object[] values) {
		/*----------------------------
		* 1. Insert data
		----------------------------*/
		try {
			// Start transaction
			db.beginTransaction();
			
			// ContentValues
			ContentValues val = new ContentValues();
			
			// Put values
			val.put(columnNames[0], (String) values[0]);		// file_name
			val.put(columnNames[1], (String) values[1]);		// file_path
			
			val.put(columnNames[2], (Long) values[2]);		// duration
			
			val.put(columnNames[3], (Long) values[3]);		// date_added
			val.put(columnNames[4], (Long) values[4]);		// date_modified
			
			val.put(columnNames[5], (String) values[5]);		// file_info
			val.put(columnNames[6], (String) values[6]);		// memos
			
			val.put(columnNames[7], (String) values[7]);		// located_at
			
//			for (int i = 0; i < columnNames.length; i++) {
//			val.put(columnNames[i], values[i]);
//			}//for (int i = 0; i < columnNames.length; i++)
			
			// Insert data
			db.insert(tableName, null, val);
			
			// Set as successful
			db.setTransactionSuccessful();
			
			// End transaction
			db.endTransaction();
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Transaction => Ends");
			
			
			return true;
		
		} catch (Exception e) {
			// Log
			Log.e("DBUtils.java" + "["
			+ Thread.currentThread().getStackTrace()[2].getLineNumber()
			+ "]", "Exception! => " + e.toString());
			
			return false;
		}//try
		
		////debug
		//return false;
		
	}//public insertData(String tableName, String[] columnNames, String[] values)

	public boolean insertData(Activity actv, String dbName, 
						String tableName, String[] columnNames, Object[] values) {
		/*----------------------------
		* 1. Insert data
		----------------------------*/
		DBUtils dbu = new DBUtils(actv, dbName);
		
		//
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		try {
			// Start transaction
			wdb.beginTransaction();
			
			// ContentValues
			ContentValues val = new ContentValues();
			
//			"name", "created_at", "modified_at"
			
			// Put values
			val.put(columnNames[0], (String) values[0]);		// name
			val.put(columnNames[1], (Long) values[1]);		// created_at
			
			val.put(columnNames[2], (Long) values[2]);		// modified_at
			
			// Insert data
			wdb.insert(tableName, null, val);
			
			// Set as successful
			wdb.setTransactionSuccessful();
			
			// End transaction
			wdb.endTransaction();
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Transaction => Ends");
			
			wdb.close();
			
			return true;
		
		} catch (Exception e) {
			// Log
			Log.e("DBUtils.java" + "["
			+ Thread.currentThread().getStackTrace()[2].getLineNumber()
			+ "]", "Exception! => " + e.toString());
			
			wdb.close();
			
			return false;
		}//try
		
		////debug
		//return false;
		
	}//public insertData(String tableName, String[] columnNames, String[] values)

	public boolean insertData_group(Activity actv, String dbName, 
			String tableName, String[] columnNames, Object[] values) {
		/*----------------------------
		* 1. Insert data
		----------------------------*/
		DBUtils dbu = new DBUtils(actv, dbName);
		
		//
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		try {
			// Start transaction
			wdb.beginTransaction();
			
			// ContentValues
			ContentValues val = new ContentValues();
			
			//"name", "created_at", "modified_at"
			
			// Put values
			val.put(columnNames[0], (String) values[0]);		// name
			val.put(columnNames[1], (Long) values[1]);		// genre_id
			val.put(columnNames[2], (Long) values[2]);		// created_at
			val.put(columnNames[3], (Long) values[3]);		// modified_at
			
			// Insert data
			wdb.insert(tableName, null, val);
			
			// Set as successful
			wdb.setTransactionSuccessful();
			
			// End transaction
			wdb.endTransaction();
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Transaction => Ends");
			
			wdb.close();
			
			return true;
			
		} catch (Exception e) {
			// Log
			Log.e("DBUtils.java" + "["
			+ Thread.currentThread().getStackTrace()[2].getLineNumber()
			+ "]", "Exception! => " + e.toString());
			
			wdb.close();
			
			return false;
		}//try
		
		////debug
		//return false;
	
	}//public insertData(String tableName, String[] columnNames, String[] values)

	/****************************************
	 *
	 * 
	 * <Caller> 1. Methods.addMemo()
	 * 
	 * <Desc> 1. <Params> 1.
	 * 
	 * <Return> 1.
	 * 
	 * <Steps> 1.
	 ****************************************/
	public static boolean insertData_memo(Activity actv, Object[] values) {
		/*----------------------------
		* 1. Insert data
		----------------------------*/
		DBUtils dbu = new DBUtils(actv, dbName);
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		try {
			// Start transaction
			wdb.beginTransaction();
			
			// ContentValues
			ContentValues val = new ContentValues();
			
			//content, ai_db_id, group_id, created_at, modified_at
			
			// Put values
			val.put(DBUtils.cols_memos[0], (String) values[0]);		// name
			val.put(DBUtils.cols_memos[1], (Long) values[1]);		// ai_db_id
			val.put(DBUtils.cols_memos[2], (Long) values[2]);		// group_id
			val.put(DBUtils.cols_memos[3], (Long) values[3]);		// created_at
			val.put(DBUtils.cols_memos[4], (Long) values[4]);		// modified_at
			
			// Insert data
			wdb.insert(DBUtils.tableName_memos, null, val);
			
			// Set as successful
			wdb.setTransactionSuccessful();
			
			// End transaction
			wdb.endTransaction();
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Transaction => Ends");
			
			wdb.close();
			
			return true;
			
		} catch (Exception e) {
			// Log
			Log.e("DBUtils.java" + "["
			+ Thread.currentThread().getStackTrace()[2].getLineNumber()
			+ "]", "Exception! => " + e.toString());
			
			wdb.close();
			
			return false;
		}//try
		
		////debug
		//return false;
	
	}//public insertData_memo(String tableName, String[] columnNames, String[] values)


	/****************************************
	 *
	 * 
	 * <Caller> 1. <Desc> 1. <Params> 1.
	 * 
	 * <Return>
	 *  1. false		=> The data already in the table, or, insertion failed
	 *  2. true		=> The data inserted
	 * 
	 * <Steps> 1.
	 ****************************************/
	public boolean insertData_activity(Activity actv, String dbName, 
			String tableName, String[] columnNames, Object[] values) {
		/*----------------------------
		 * 0. Is in table?
			----------------------------*/
		boolean res = isInTable(actv, dbName, tableName, columnNames[0], (String) values[0]);
		
//		if (res == false) {
		if (res == true) {
			
			// debug
			Toast.makeText(actv, "���̃A�C�e���́A�����o�^���Ă���܂�", 2000).show();
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "The item already in the table");
			
			return false;
			
		}//if (res == false)
		
		/*----------------------------
		* 1. Insert data
		----------------------------*/
		DBUtils dbu = new DBUtils(actv, dbName);
		
		//
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		try {
			// Start transaction
			wdb.beginTransaction();
			
			// ContentValues
			ContentValues val = new ContentValues();
			
			//"name", "created_at", "modified_at"
			
			// Put values
			val.put(columnNames[0], (String) values[0]);		// name
			val.put(columnNames[1], (Long) values[1]);		// group_id
			val.put(columnNames[2], (Long) values[2]);		// created_at
			val.put(columnNames[3], (Long) values[3]);		// modified_at
			
			// Insert data
			wdb.insert(tableName, null, val);
			
			// Set as successful
			wdb.setTransactionSuccessful();
			
			// End transaction
			wdb.endTransaction();
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Transaction => Ends");
			
			wdb.close();
			
			return true;
			
		} catch (Exception e) {
			// Log
			Log.e("DBUtils.java" + "["
			+ Thread.currentThread().getStackTrace()[2].getLineNumber()
			+ "]", "Exception! => " + e.toString());
			
			wdb.close();
			
			return false;
		}//try
		
		////debug
		//return false;
	
	}//public insertData(String tableName, String[] columnNames, String[] values)

	public boolean updateData(SQLiteDatabase wdb, String tableName) {
		/*----------------------------
		* 1. Insert data
		----------------------------*/
		try {
			// Start transaction
//			wdb.beginTransaction();
			
			// ContentValues
			ContentValues val = new ContentValues();
			
			// Put values
//			val.put(DBUtils.cols_main_table[0], fi.getFile_name());		// file_name
			
//			for (int i = 0; i < columnNames.length; i++) {
//			val.put(columnNames[i], values[i]);
//			}//for (int i = 0; i < columnNames.length; i++)
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Update => Done");
			
			return true;
		
		} catch (Exception e) {
			// Log
			Log.e("DBUtils.java" + "["
			+ Thread.currentThread().getStackTrace()[2].getLineNumber()
			+ "]", "Exception! => " + e.toString());
			
			return false;
		}//try
		
		////debug
		//return false;
		
	}//public insertData(String tableName, String[] columnNames, String[] values)

	public boolean deleteData_actvity(
						Activity actv, SQLiteDatabase db, 
						String activityName) {
		/*----------------------------
		 * Steps
		 * 1. delete item
			----------------------------*/
		
		String sql = 
						"DELETE FROM " + DBUtils.tableName_activities + 
						" WHERE " + DBUtils.cols_activities[0] + "='" + activityName + "'";
		
		try {
			db.execSQL(sql);
			
			return true;
			
		} catch (SQLException e) {
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception => " + e.toString());
			
			return false;
			
		}//try
		
	}//public boolean deleteData(SQLiteDatabase db, String tableName, long file_id)

	/****************************************
	 *
	 * 
	 * <Caller> 1. Methods.deleteItem_fromDB()
	 * 
	 * <Desc> 1. <Params> 1.
	 * 
	 * <Return> 1.
	 * 
	 * <Steps> 1.
	 ****************************************/
	public boolean deleteData(Activity actv, SQLiteDatabase db, String tableName, long file_id) {
		/*----------------------------
		 * Steps
		 * 1. Item exists in db?
		 * 2. If yes, delete it
			----------------------------*/
		/*----------------------------
		 * 1. Item exists in db?
			----------------------------*/
		boolean result = isInDB_long(db, tableName, file_id);
		
		if (result == false) {		// Result is false ==> Meaning the target data doesn't exist
											//							in db; Hence, not executing delete op
			
			// debug
			Toast.makeText(actv, 
					"Data doesn't exist in db: " + String.valueOf(file_id), 
					2000).show();
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", 
					"Data doesn't exist in db => Delete the data (file_id = " + String.valueOf(file_id) + ")");
			
			return false;
			
		}//if (result == false)
		
		
		String sql = 
						"DELETE FROM " + tableName + 
						" WHERE file_id = '" + String.valueOf(file_id) + "'";
		
		try {
			db.execSQL(sql);
			
//			// Log
//			Log.d("DBUtils.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Data deleted => file id = " + String.valueOf(file_id));
			
			
			return true;
			
		} catch (SQLException e) {
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception => " + e.toString());
			
			return false;
			
		}//try
		
	}//public boolean deleteData(SQLiteDatabase db, String tableName, long file_id)

	
	/****************************************
	 *
	 * 
	 * <Caller> 
	 * 1. deleteData(Activity actv, SQLiteDatabase db, String tableName, long file_id)
	 * 
	 * <Desc> 
	 * 1. REF=> http://stackoverflow.com/questions/3369888/android-sqlite-insert-unique
	 * 
	 * <Params> 1.
	 * 
	 * <Return> 1.
	 * 
	 * <Steps> 1.
	 ****************************************/
	public boolean isInDB_long(SQLiteDatabase db, String tableName, long file_id) {
		
		String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE file_id = '" +
						String.valueOf(file_id) + "'";
		
		long result = DatabaseUtils.longForQuery(db, sql, null);
		
		// Log
		Log.d("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "result => " + String.valueOf(result));
		
		if (result > 0) {

			return true;
			
		} else {//if (result > 0)
			
			return false;
			
		}//if (result > 0)
		
//		return false;
		
	}//public boolean isInDB_long(SQLiteDatabase db, String tableName, long file_id)

	public boolean isInTable(Activity actv, String dbName, 
											String tableName, String colName, String value) {
		
		DBUtils dbu = new DBUtils(actv, DBUtils.dbName);
		
		SQLiteDatabase rdb = dbu.getReadableDatabase();

		
		String sql = "SELECT * FROM " + tableName + " WHERE " + colName + " = '" + value + "'";
		
		Cursor c = rdb.rawQuery(sql, null);
		
		actv.startManagingCursor(c);
		
		int res = c.getCount();
		
		rdb.close();
		
		return res > 0 ? true : false;
		
	}//public boolean isInDB_genre(SQLiteDatabase db, String tableName, String genreName)

	/****************************************
	 *
	 * 
	 * <Caller> 
	 * 1. Methods.updateData(Activity actv, String tableName, FileItem fi)
	 * 
	 * 
	 * <Desc> 1. <Params> 1.
	 * 
	 * <Return> 1.
	 * 
	 * <Steps> 1.
	 ****************************************/
	public boolean updateData_memos(SQLiteDatabase wdb, 
//																String tableName, FileItem fi) {
																String tableName) {
		/*----------------------------
		* Steps
		* 1. 
		----------------------------*/
//		String sql = "UPDATE " + tableName + " SET " + 
//			
//			"file_name='" + fi.getFile_name() + "', " +
//			"file_path='" + fi.getFile_path() + "', " +
//			
//			
//			" WHERE file_name = '" + fi.getFile_name() + "'";

		try {
		
//			wdb.execSQL(sql);
			
//			// Log
//			Log.d("DBUtils.java" + "["
//			+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//			+ "]", "sql => Done: " + sql);
			
//			Methods.toastAndLog(actv, "Data updated", 2000);
			
			return true;
		
		
		} catch (SQLException e) {
			// Log
//			Log.d("DBUtils.java" + "["
//			+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//			+ "]", "Exception => " + e.toString() + " / " + "sql: " + sql);
			
			return false;
		}
		
	}//public void updateData_memos

}//public class DBUtils

