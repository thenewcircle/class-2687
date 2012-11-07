package com.verizon.myamba;

import android.app.ListActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends ListActivity implements
		LoaderCallbacks<Cursor> {
	private static final String[] FROM = { StatusContract.Columns.USER,
			StatusContract.Columns.MESSAGE };
	private static final int[] TO = { android.R.id.text1, android.R.id.text2 };
	private static final int LOADER_ID = 47;
	private SimpleCursorAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_2, null, FROM, TO,
				CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		getListView().setAdapter(adapter);
		
		getLoaderManager().initLoader(LOADER_ID, null, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_update:
			Intent statucActivityIntent = new Intent(this, StatusActivity.class);
			startActivity(statucActivityIntent);
			return true;
		case R.id.menu_refresh:
			startService(new Intent(this, RefreshService.class));
			return true;
		default:
			return false;
		}
	}

	// --- LoaderCallbacks ---

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		if(id != LOADER_ID) return null;
		return new CursorLoader(this, StatusContract.CONTENT_URI, null, null,
				null, StatusContract.DEFAULT_SORT_ORDER);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		adapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		adapter.swapCursor(null);
	}

}
