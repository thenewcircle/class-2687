package com.verizon.myamba;

import java.util.List;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.util.Log;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClient.Status;
import com.marakana.android.yamba.clientlib.YambaClientException;

public class RefreshService extends IntentService {
	private static final String TAG = "RefreshService";
	private static final int MAX_POSTS = 20;
	private YambaClient client;
	private ContentValues values;

	public RefreshService() {
		super(TAG);
		client = new YambaClient("student", "password");
		values = new ContentValues();
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
				
				// Insert into db
				values.clear();
				values.put( StatusContract.Columns._ID, status.getId() );
				values.put( StatusContract.Columns.USER, status.getUser() );
				values.put( StatusContract.Columns.MESSAGE, status.getMessage() );
				values.put( StatusContract.Columns.CREATED_AT, status.getCreatedAt().getTime() );
				getContentResolver().insert( StatusContract.CONTENT_URI, values);
			}
		} catch (YambaClientException e) {
			e.printStackTrace();
		}
	}

}
