package com.enigmess;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

public class Splash extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		Walk_theme.getSharedPrefValue(Splash.this);
		Thread background = new Thread() 
		{
			public void run() 
			{
				try 
				{
					sleep(5*1000);
					Intent i;
					if(TextUtils.equals(Walk_theme.userName, ""))
					{
						i=new Intent(Splash.this,Sign_In.class);
					}
					else
					{
						i=new Intent(Splash.this,Messages.class);
					}
					startActivity(i);
					finish();
				} 
				catch (Exception e)
				{            

				}
			}
		};

		background.start();
	}

	@Override
	protected void onDestroy() 
	{
		super.onDestroy();      
	}

}
