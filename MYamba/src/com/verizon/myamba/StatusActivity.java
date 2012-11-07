package com.verizon.myamba;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

public class StatusActivity extends Activity {
	private Button buttonGo;
	private EditText input;
	private TextView count;
	private YambaClient client;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		client = new YambaClient("student", "password");
		getActionBar().setDisplayHomeAsUpEnabled(true);

		setContentView(R.layout.activity_status);

		count = (TextView) findViewById(R.id.count);
		count.setText("140");
		input = (EditText) findViewById(R.id.input);
		input.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				count.setText(Integer.toString(140 - s.length()));
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}
		});

		buttonGo = (Button) findViewById(R.id.button_go);
		buttonGo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final String status = input.getText().toString();
				Log.d("Yamba", "onClicked with input: " + status);
				// Post to the cloud
				new StatusUpdateTask().execute(status);
			}
		});
	}

	// Async task
	class StatusUpdateTask extends AsyncTask<String, Void, String> {
		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = ProgressDialog.show(StatusActivity.this, "Posting",
					"please wait...");
		}

		// Work happens on separate worker thread
		@Override
		protected String doInBackground(String... params) {
			try {
				client.postStatus(params[0]);
				return "Successfully posted";
			} catch (YambaClientException e) {
				e.printStackTrace();
				return "Failed to post";
			}
		}

		// Kicks in after doInBackground is done, and runs on main thread
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			dialog.dismiss();
			if (!"Failed to post".equals(result))
				input.setText("");
			Toast.makeText(StatusActivity.this, result, Toast.LENGTH_LONG)
					.show();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// app icon in action bar clicked; go home
			Intent intent = new Intent(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
