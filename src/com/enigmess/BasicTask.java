package com.enigmess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.os.AsyncTask;
import android.util.Log;

public abstract class BasicTask extends AsyncTask<String,Void,Object>
{

	public String getJson(String url) throws ClientProtocolException, IOException
	{
		
		Log.d("BasicTask", url);
		
		String result = null;
		InputStream is = null;		
		DefaultHttpClient client = new DefaultHttpClient();
		
		HttpParams httpParameters = new BasicHttpParams();
		// Set the timeout in milliseconds until a connection is established.
		// The default value is zero, that means the timeout is not used. 
		int timeoutConnection = 30000;
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);

		// Set the default socket timeout (SO_TIMEOUT) 
		// in milliseconds which is the timeout for waiting for data.
		int timeoutSocket = 30000;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
		
		client.setParams(httpParameters);
		
		try {
			
			HttpResponse response = client.execute(new HttpGet(url));
			StatusLine statusLine = response.getStatusLine();
			if(statusLine.getStatusCode() == 200){
				
				StringBuilder builder = new StringBuilder();
				
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
			    	
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				String data;
				while((data = reader.readLine()) != null ){
					builder.append(data + '\n');
				}								
								
				result = builder.toString();
				
			}else{				
				Log.d("", "response code " + statusLine.getStatusCode());
			}
								
		}
		finally
		{
			try 
			{
				if(is != null)	is.close();
				client.getConnectionManager().shutdown();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}		

		return result;
	}

}
