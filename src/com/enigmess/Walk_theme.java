package com.enigmess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
public class Walk_theme extends Application 
{
	static String msg = "Sorry cannot save contents without first entering required fields in ";
	static public String api = "http://69.195.124.251/~facharzt/admin/api/";
	static public String user_id, walk_id, profile_pic, fbprofile_pic;
	static String Login_from = null;
	static String My_walk_Poi_id = null,My_walk_Poi_name = null,video_url=null,first_name=null;
	Context context;
	
	protected static String friend_id,message_id;
	protected static String userid;
	//public static String UserName;
	public static String strname;
	public static String u_name;
	public static String email;
	public static String enterpin;
	public static String userid_sign_up;


	public static String userName;
	public static String password;
	//public static String userid;
	
	
	@Override
	public void onCreate() 
	{
		super.onCreate();
		context = getApplicationContext();
	}

	
	public static void  Click_Backbutton(Context context,Activity activity)
	{
		context.startActivity(new Intent(context,activity.getClass()));
		((Activity)context).finish();

	}
	
	static public void getSharedPrefValue(Context context)
	 {
	  SharedPreferences sharedPrefs = context.getSharedPreferences(Sign_In.PREFS_NAME,MODE_PRIVATE);
	  userName = sharedPrefs.getString("username", "");
	  password = sharedPrefs.getString("password", "");
	 userid=sharedPrefs.getString("userid", "");
	 }
	
	public static void showMessage(Context context, String message)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message).setPositiveButton("OK",
				new OnClickListener() 
		{
					@Override
					public void onClick(DialogInterface dialog, int which) 
					{

					}
		});
		builder.create().show();
	}

	static void checkNetwork(Context context) 
	{
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null)
		{
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
			{
				for (int i = 0; i < info.length; i++) 
				{
					if (info[i].getState() == NetworkInfo.State.CONNECTED) 
					{
						return;
					}
				}
			}
		}
		Walk_theme.showMessage(context, "Network not available");
	}

	public static void executeFetchUrl(FetchData fetchData, String url) 
	{
		
		if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB)
		{
			fetchData.execute(url);
		} 
		
		else
		{
			fetchData.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
		}
	}

	public static Bitmap getProfilePic(Context context) 
	{
		File cacheDir = context.getCacheDir();
		File f = new File(cacheDir, "pic");
		FileInputStream fis = null;
		try
		{
			fis = new FileInputStream(f);
		} 
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return BitmapFactory.decodeStream(fis);
	}

	public static Bitmap getCircleBitmap(Context context) 
	{
		Bitmap bitmap = getProfilePic(context);
		Bitmap output = null;
		if (bitmap != null) 
		{

			if (bitmap.getWidth() > bitmap.getHeight()) 
			{
				output = Bitmap.createBitmap(bitmap.getHeight(),
						bitmap.getHeight(), Config.ARGB_8888);
			} 
			else 
			{
				output = Bitmap.createBitmap(bitmap.getWidth(),
						bitmap.getWidth(), Config.ARGB_8888);
			}

			Canvas canvas = new Canvas(output);

			final int color = 0xff424242;
			final Paint paint = new Paint();
			final Rect rect = new Rect(0, 0, bitmap.getWidth(),
					bitmap.getHeight());

			float r = 0;

			if (bitmap.getWidth() > bitmap.getHeight()) 
			{
				r = bitmap.getHeight() / 2;
			} 
			
			else
			{
				r = bitmap.getWidth() / 2;
			}

			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawCircle(r, r, r, paint);
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(bitmap, rect, rect, paint);
			return output;

		}

		return output;
	}

	public static void deleteCache(Context context)
	{
		try
		{
			File dir = context.getCacheDir();
			if (dir != null && dir.isDirectory()) 
			{
				deleteDir(dir);
			}
		} 
		catch (Exception e)
		{
		}
	}

	public static boolean deleteDir(File dir) 
	{
		if (dir != null && dir.isDirectory()) 
		{
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) 
			{
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) 
				{
					return false;
				}
			}
		}
		return dir.delete();
	}

	
	
	

}
