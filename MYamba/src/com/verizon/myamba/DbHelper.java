package com.verizon.myamba;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "yamba.db";
	private static final int DB_VERSION = 1;

	public DbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = String.format("create table %s ("
				+ "%s int primary key, %s text, %s text, %s int)",
				StatusContract.PATH, StatusContract.Columns._ID,
				StatusContract.Columns.USER, StatusContract.Columns.MESSAGE,
				StatusContract.Columns.CREATED_AT);
		Log.d("DbHelper", "sql: " + sql);
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Typically you do ALTER TABLE ...
		db.execSQL("drop table if exists " + StatusContract.PATH);
		onCreate(db);
	}

}
