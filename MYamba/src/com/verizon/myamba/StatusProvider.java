package com.verizon.myamba;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class StatusProvider extends ContentProvider {
	private static final String TAG = "StatusProvider";

	private static final int STATUS_ITEM = 1;
	private static final int STATUS_DIR = 2;
	private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	static {
		uriMatcher.addURI(StatusContract.AUTHORITY, StatusContract.PATH,
				STATUS_DIR);
		uriMatcher.addURI(StatusContract.AUTHORITY, StatusContract.PATH + "/#",
				STATUS_ITEM);
	}

	private DbHelper dbHelper;
	private SQLiteDatabase db;

	@Override
	public boolean onCreate() {
		dbHelper = new DbHelper(getContext());
		return (dbHelper == null) ? false : true;
	}

	// uri: content://com.verizon.yamba.provider/status
	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
		case STATUS_DIR:
			return StatusContract.CONTENT_TYPE;
		case STATUS_ITEM:
			return StatusContract.CONTENT_ITEM_TYPE;
		default:
			return null;
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// Assert
		if (uriMatcher.match(uri) != STATUS_DIR) {
			throw new IllegalArgumentException("Unsupported uri: " + uri);
		}

		// Insert into the database
		db = dbHelper.getWritableDatabase();
		long id = db.insertWithOnConflict(StatusContract.PATH, null, values,
				SQLiteDatabase.CONFLICT_IGNORE);

		// Return value
		if (id > 0) {
			Uri ret = ContentUris.withAppendedId(uri, id);
			getContext().getContentResolver().notifyChange(ret, null);
			return ret;
		} else {
			return null;
		}
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		db = dbHelper.getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(StatusContract.PATH);

		// Figure out type of query and possibly id or the record
		switch (uriMatcher.match(uri)) {
		case STATUS_DIR:
			// Do nothing
			break;
		case STATUS_ITEM:
			long id = ContentUris.parseId(uri);
			qb.appendWhere(StatusContract.Columns._ID + "=" + id);
		default:
			throw new IllegalArgumentException("Unsupported uri: " + uri);
		}

		// Do the query
		Cursor cursor = qb.query(db, projection, selection, selectionArgs,
				null, null, sortOrder);

		cursor.setNotificationUri(getContext().getContentResolver(), uri);

		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}
}
