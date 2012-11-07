package com.verizon.myamba;

import android.net.Uri;
import android.provider.BaseColumns;

public final class StatusContract {

	public static final String AUTHORITY = "com.verizon.yamba.provider";
	public static final String PATH = "status";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + PATH);
	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.verizon.status";
	public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.verizon.status";

	public static final class Columns implements BaseColumns {
		public static final String CREATED_AT = "yamba_created_at";
		public static final String USER = "yamba_user";
		public static final String MESSAGE = "yamba_message";
	};

	public static final String DEFAULT_SORT_ORDER = Columns.CREATED_AT
			+ " DESC";
}
