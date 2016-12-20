package com.example.sheng.carpool.Data;

public interface StatusCode
{
	public static final String OK = "200";
	public static final String FAILED = "110";

	public static final String WRONG_PASSWORD = "112";
	public static final String FAILED_TO_EXCUTE_SQL = "113";
	public static final String FAILED_TO_SEARCH_USERNAME = "114";
	public static final String FAILED_TO_SEARCH_RESULT = "115";
	public static final String HAD_IN = "116";

	public static final String WROONG_TYPE_OF_REQUEST = "300";

	public static final String ACCOUNT_EXISTED = "400";

	public static final String FAILED_TOCONNECT_DATABASE = "999";
}
