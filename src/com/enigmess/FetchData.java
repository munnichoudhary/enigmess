package com.enigmess;

import java.io.IOException;

import android.app.ProgressDialog;
import android.content.Context;

public class FetchData extends BasicTask
{
	String json = null;
	Context context;
	public FetchListener response = null;
	ProgressDialog progressDialog;
	
	public FetchData(Context context)
	{
		this.context = context;
	}
	
	@Override
	protected void onPreExecute() 
	{
		super.onPreExecute();
		progressDialog = new ProgressDialog(context);
		progressDialog.setTitle("Fetching data");
	    progressDialog.setMessage("Please Wait");
		progressDialog.show();
		progressDialog.setCancelable(true);
	}
	
    @Override
	protected Object doInBackground(String... params)
	{
    	try
    	{
			 json = getJson(params[0]);
		}
    	catch (IOException e) 
    	{
			e.printStackTrace();
			json = new String("Could not connect to Internet");
		}
		return json;
		
	}
    protected void onPostExecute(Object result)
    {  	
    	if(progressDialog.isShowing())
    	{
    	   progressDialog.dismiss();
    	}
    	
    	response.fetchFinish(json);
    }

}
