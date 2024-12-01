package com.enigmess;

import java.io.IOException;

import android.app.ProgressDialog;
import android.content.Context;

public class Uploade extends Send
{
String json=null;
Context context;
public UploadListener response=null;
ProgressDialog ProgressDialog;
public Uploade(Context context)
{
	this.context=context;
}
@Override
  protected void onPreExecute()
   {
	super.onPreExecute();
	ProgressDialog=new ProgressDialog(context);
	ProgressDialog.setTitle("uploading data");
	ProgressDialog.setMessage("Please wait");
	ProgressDialog.show();
	ProgressDialog.setCancelable(false);
	
  }
@Override
protected Object doInBackground(String... params)
{
	try
	{
	  System.out.println("params"+params[0]);
	  json=getJson(params[0]);
	  System.out.println(json +"json");
	}
	catch(IOException e)
	{
		e.printStackTrace();
		json=new String("could not connect to the internet");
	}
	return json;
}
@Override
protected void onPostExecute(Object result)
{
	if(ProgressDialog.isShowing())
	{
		ProgressDialog.dismiss();
	}
	response.processFinish(json);
}
}
