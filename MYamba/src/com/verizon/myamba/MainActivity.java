package com.verizon.myamba;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

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

}
