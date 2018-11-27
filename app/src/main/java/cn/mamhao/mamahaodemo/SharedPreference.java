package cn.mamhao.mamahaodemo;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

public class SharedPreference {
	public static final String birthday = "birthday";
	public static final String login = "login";
	public static final String mam_state = "mam_state";
	public static final String mam_pre_date = "mam_pre_date";
	public static final String boby_birthday = "boby_birthday";
	public static final String boby_sex = "boby_sex";
	public static final String boby_name = "boby_name";
	public static final String city = "city";
	public static final String street = "street";
	public static final String areaId = "areaId";
	public static final String isFirstInsnall = "isFirstInsnall";
	public static final String lng = "lng";
	public static final String lat = "lat";
	public static final String PUSH_TOKEN = "push_token";
	public static final String PUSH_COUNT = "push_count";
	public static final String phoneNum = "phoneNum";
	public static final String cartId = "cartId";
	public static final String memberId = "memberId";
	public static final String openId = "openId";
	public static final String unionid = "unionid";
	public static final String wenickname = "wenickname";
	public static final String headPic = "headPic";
	public static final String isVip = "isVip";
	public static final String appVersionCode = "appVersionCode";
	//定位是否成功 定位成功刷新H5界面 设置定位信息cookie
	public static final String locationSuccess = "locationSuccess";
	public static final String memberName = "memberName";
	public static final String token = "token";
	public static final String memberNickName = "memberNickName";
	public static final String promoterType = "promoterType";
	public static final String oPenPromotionCenter = "oPenPromotionCenter";
	public static final String oPenCloudCenter = "oPenCloudCenter";
	public static final String isWeixin = "isWeixin";
	public static final String shareRoleType = "shareRoleType";

	public static void saveToSP(Context context, String key, Object value) {
		if(null == context) return;
		SharedPreferences sp = context.getSharedPreferences("config", 0);
		if (value instanceof String) {
			sp.edit().putString(key, (String) value).commit();
		} else if (value instanceof Boolean) {
			sp.edit().putBoolean(key, (Boolean) value).commit();
		} else if (value instanceof Float) {
			sp.edit().putFloat(key, (Float) value).commit();
		} else if (value instanceof Integer) {
			sp.edit().putInt(key, (Integer) value).commit();
		} else if (value instanceof Long) {
			sp.edit().putLong(key, (Long) value).commit();
		}
	}

	public static void saveToSP(String config, Context context, String key,
                                Object value) {
		if(null == context) return;
		SharedPreferences sp = context.getSharedPreferences(config, 0);
		if (value instanceof String) {
			sp.edit().putString(key, (String) value).commit();
		} else if (value instanceof Boolean) {
			sp.edit().putBoolean(key, (Boolean) value).commit();
		} else if (value instanceof Float) {
			sp.edit().putFloat(key, (Float) value).commit();
		} else if (value instanceof Integer) {
			sp.edit().putInt(key, (Integer) value).commit();
		} else if (value instanceof Long) {
			sp.edit().putLong(key, (Long) value).commit();
		}

	}

	public static void save2Sp(Context context, Map<String,Object> map){
		if(null == context) return;
		save2Sp(context,"config",map);
	}

	public static void save2Sp(Context context, String name, Map<String,Object> map) {
		if(null == context) return;
		if(map == null || map.size() == 0)
			return;
		SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		for(Map.Entry<String,Object> entry:map.entrySet()){
			if(entry.getKey() == null)
				continue;
			putValue2Editor(editor,entry.getKey(),entry.getValue());
		}
		editor.commit();
	}

	private static void putValue2Editor(SharedPreferences.Editor editor, String key, Object value){
		if (value instanceof String) {
			editor.putString(key, (String) value);
		} else if (value instanceof Boolean) {
			editor.putBoolean(key, (Boolean) value);
		} else if (value instanceof Float) {
			editor.putFloat(key, (Float) value);
		} else if (value instanceof Integer) {
			editor.putInt(key, (Integer) value);
		} else if (value instanceof Long) {
			editor.putLong(key, (Long) value);
		}
	}

	public static String getString(String config, Context context, String key) {
		if (null == context)return "";

		SharedPreferences sp = context.getSharedPreferences(config, 0);
		return sp.getString(key, "");
	}

	public static String getString(Context context, String key) {
		if (null == context)return "";

		SharedPreferences sp = context.getSharedPreferences("config", 0);
		return sp.getString(key, "");
	}

	public static int getInt(Context context, String key) {
		if (null == context)return 0;
		SharedPreferences sp = context.getSharedPreferences("config", 0);
		return sp.getInt(key, 0);
	}

	public static int getInt(Context context, String key, int dedaultI) {
		if (null == context)return dedaultI;
		SharedPreferences sp = context.getSharedPreferences("config", 0);
		return sp.getInt(key, dedaultI);
	}

	public static long getLong(Context context, String key) {
		if (null == context)return 0;
		SharedPreferences sp = context.getSharedPreferences("config", 0);
		return sp.getLong(key, 0);
	}

	public static float getFloat(Context context, String key) {
		if (null == context)return 0.0f;
		SharedPreferences sp = context.getSharedPreferences("config", 0);
		return sp.getFloat(key, 0.0f);
	}

	public static float getDouble(Context context, String key) {
		if (null == context)return 0;
		SharedPreferences sp = context.getSharedPreferences("config", 0);
		return sp.getFloat(key, 0);
	}

	public static boolean getBoolean(Context context, String key) {
		if (null == context)return false;
		SharedPreferences sp = context.getSharedPreferences("config", 0);
		return sp.getBoolean(key, false);
	}

	public static boolean getBoolean(Context context, String key, boolean defaultB) {
		if (null == context)return defaultB;
		SharedPreferences sp = context.getSharedPreferences("config", 0);
		return sp.getBoolean(key, defaultB);
	}

	public static boolean getBoolean(String config, Context context, String key) {
		if (null == context)return false;
		SharedPreferences sp = context.getSharedPreferences(config, 0);
		return sp.getBoolean(key, false);
	}
	public static void cleanData(Context context) {
		if (null == context)return;
		SharedPreferences sp = context.getSharedPreferences("config", 0);
		sp.edit().clear();
	}
}
