package com.enigmess;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

public class Pass_Url extends Application
{
	static String msg = "Sorry cannot save contents without first entering required fields in ";
   // static public String api = "http://69.195.124.251/~facharzt/admin/api/";
    static public String api = "http://gamestaxi.webfactional.com/gametaxi/api/";
    Context context;
    @Override
    public void onCreate()
    {
    	super.onCreate();
    	context = getApplicationContext();
    }
    
	public static void showMessage(Sign_Up parsing, String response) 
	{
		// TODO Auto-generated method stub
		Toast.makeText(parsing, response, Toast.LENGTH_LONG).show();
	}

	public static void showEmptyMessage(Sign_Up parsing, String string)
	{
		// TODO Auto-generated method stub
		Toast.makeText(parsing, string, Toast.LENGTH_LONG).show();
	}

	public static void showMessage(Edit_Profile edit_Profile, String response)
	{
		// TODO Auto-generated method stub
		Toast.makeText(edit_Profile, response, Toast.LENGTH_LONG).show();
	}

	
}
