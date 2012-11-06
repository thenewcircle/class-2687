package com.verizon.myamba;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

public class StatusActivity extends Activity {
	private Button buttonGo;
	private EditText input;
	private TextView count;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
				final YambaClient client = new YambaClient("student",
						"password");

				new Thread() {
					public void run() {
						try {
							client.postStatus(status);
						} catch (YambaClientException e) {
							e.printStackTrace();
						}
					}
				}.start();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_status, menu);
		return true;
	}
}
