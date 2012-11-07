package com.verizon.myamba;

import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClient.Status;
import com.marakana.android.yamba.clientlib.YambaClientException;

public class RefreshService extends IntentService {
	private static final String TAG = "RefreshService";
	private static final int MAX_POSTS = 20;
	private YambaClient client;

	public RefreshService() {
		super(TAG);
		client = new YambaClient("student", "password");
	}

	// Runs on a worker thread
	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d(TAG, "onHandleIntent");

		// Get the data
		try {
			List<Status> statuses = client.getTimeline(MAX_POSTS);
			for (Status status : statuses) {
				Log.d(TAG,
						String.format("%s: %s", status.getUser(),
								status.getMessage() ));
			}
		} catch (YambaClientException e) {
			e.printStackTrace();
		}
	}

}
