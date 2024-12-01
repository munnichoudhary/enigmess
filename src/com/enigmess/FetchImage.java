package com.enigmess;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;

public class FetchImage extends BasicTask
{
	String json = null;
	Context context;
	public FetchImageListener response = null;
	
	Bitmap bmp = null;
	Image photo = null;
	
	public FetchImage(Context context)
	{
		this.context = context;
	}
	
	@Override
	protected void onPreExecute() 
	{
		super.onPreExecute();
		
	}
	
    @Override
	protected Object doInBackground(String... params)
	{
    	try
    	{
    		InputStream in = new URL(params[0]).openStream();
            bmp = BitmapFactory.decodeStream(in);         
            
		}
    	catch (IOException e) 
    	{
			e.printStackTrace();
			json = new String("Could not connect to Internet");
		}
		return bmp;
		
	}
    protected void onPostExecute(Object result)
    {  	
    	

        response.fetchFinish(bmp);
    }


}
