package com.dlwx.onedrop.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.activitys.MessageActivity;
import com.dlwx.onedrop.base.MainActivity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "wch";
//    private NotiFicaBean notiFicaBean;
	private Context ctx;
	private PendingIntent pi;

	@Override
	public void onReceive(Context context, Intent intent) {
		this.ctx = context;
		try {
			Bundle bundle = intent.getExtras();
//			Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

			if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
				String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
				Log.i(TAG, "[MyReceiver] 接收Registration Id : " + regId);
				//send the Registration Id to your server...

			} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
				Log.i(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
				processCustomMessage(context, bundle);

			} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
				Log.i(TAG, "[MyReceiver] 接收到推送下来的通知");
				int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
				Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
                processCustomMessage(context, bundle);

			} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
				Log.i("wch", "[MyReceiver] 用户点击打开了通知");
				Intent inten = null;
				inten = new Intent(context, MessageActivity.class);
                        inten.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                        context.startActivity(inten);
				Gson gson = new Gson();
			} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
				Log.i(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
				//在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

			} else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
				boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
				Log.i(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
			} else {
				Log.i(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
			}
		} catch (Exception e){
            Log.i("wch",e.getMessage());
		}

	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
				if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
					Log.i(TAG, "This message has no Extra data");
					continue;
				}

				try {
					JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
					Iterator<String> it =  json.keys();

					while (it.hasNext()) {
						String myKey = it.next().toString();
						sb.append("\nkey:" + key + ", value: [" +
								myKey + " - " +json.optString(myKey) + "]");
					}
				} catch (JSONException e) {
					Log.e(TAG, "Get message extra JSON error!");
				}

			} else {
                String string = bundle.getString(key);
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}
	
	//send msg to MainActivity
	private void processCustomMessage(Context context, Bundle bundle) {
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
        msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);

//        if (!ExampleUtil.isEmpty(extras)) {
//            try {
//                JSONObject extraJson = new JSONObject(extras);
//                if (extraJson.length() > 0) {
//                    msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
//                }
//
//
////				MyApplication.extraJson = extraJson.toString();
//                Log.i("wch",extraJson.toString());
//            } catch (JSONException e) {
//
//            }
//
//        }
			showNot(message);
        LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
//		if (MainActivity.isForeground) {
//			message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//			extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//			Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
//			msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
//			if (!ExampleUtil.isEmpty(extras)) {
//				try {
//					JSONObject extraJson = new JSONObject(extras);
//					if (extraJson.length() > 0) {
//						msgIntent.putExtra(MainActivity. KEY_EXTRAS, extras);
//					}
//					Log.i("wch",extraJson.toString());
//				} catch (JSONException e) {
//
//				}
//
//			}
//			LocalBroadcastManager.getInstance(context).sendBroacast(msgIntent);
//		}
	}

	private void showNot(String mess){



		Intent inten = null;
		inten = new Intent(ctx, MessageActivity.class);
		inten.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
		pi = PendingIntent.getActivity(ctx, 0, inten, 0);
		//获取NotificationManager实例
		NotificationManager notifyManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
		//实例化NotificationCompat.Builde并设置相关属性
		NotificationCompat.Builder builder = new NotificationCompat.Builder(   ctx)
				//设置小图标
				.setSmallIcon(R.mipmap.icon_logo)
				//设置通知标题

				.setContentTitle("木香茗")
				.setContentIntent(pi)
				//设置通知内容
				.setContentText(mess);
		builder.setDefaults(Notification.DEFAULT_ALL);
		//设置通知时间，默认为系统发出通知的时间，通常不用设置
		//.setWhen(System.currentTimeMillis());
		//通过builder.build()方法生成Notification对象,并发送通知,id=1
		notifyManager.notify(1, builder.build());

	}

}
