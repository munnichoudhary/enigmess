package com.enigmess;

import java.io.File;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;


public class UpdateUserTask extends BasicTask
{
    Context context;
	String imagepath;
	FileBody bodys;
	private ProgressDialog progressDialog = null;
	public UploadListener uploadListener;
	String resp = "";
	
	String first_name,last_name,mail_id,passwordd;
	File photoFile;
	public UpdateUserTask(String imagepath,Context context)
	{
		this.imagepath = imagepath;
		this.context = context;
	}

	
	public UpdateUserTask(String fname, String lname, String email,
			   String password, File image1, Context context) {

			  this.first_name = fname;
			  this.last_name = lname;
			  this.mail_id = email;
			  this.passwordd = password;
			  
			  this.photoFile = image1;

			  this.context = context;

			 }
	public UpdateUserTask(Context context) 
	{
		this.context = context;
	}
	protected void onPreExecute() 
	{
		super.onPreExecute();
		progressDialog = new ProgressDialog(context);
		progressDialog.setTitle("Uploading data");
	    progressDialog.setMessage("Please Wait");
		progressDialog.show();
		progressDialog.setCancelable(false);
		}
	
	@Override
	protected Object doInBackground(String... params) 
	{
		Object retVal = null;

		try {

			HttpClient httpClient = new DefaultHttpClient();
			HttpContext localContext = new BasicHttpContext();
			HttpPost httpPost = new HttpPost(params[0]);
			MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);		

			if (!TextUtils.isEmpty(imagepath)) 
			{
				FileBody body = new FileBody(new File(imagepath));
				entity.addPart("file", body);
				
			}
			
			 if (!TextUtils.isEmpty(photoFile.toString()))

			   {
			    FileBody body = new FileBody(photoFile);
			    entity.addPart("file", body);
			    System.out.println("Params" + photoFile);
			   }

			httpPost.setEntity(entity);
			HttpResponse response = httpClient.execute(httpPost, localContext);
			HttpEntity resEntity = response.getEntity();
			resp = EntityUtils.toString(resEntity);
			if ((!TextUtils.isEmpty(resp)) && resp.contains("success")) 
			{
				retVal = Boolean.valueOf(true);
			}
			else 
			{
				
			}

		} 
		catch (Exception e) 
		{	
			System.out.println("error response:"+ e);
		}
		return retVal;
	}

	@Override
	protected void onPostExecute(Object result) {
		super.onPostExecute(result);
		if(progressDialog.isShowing())
    	{
    	   progressDialog.dismiss();
    	}
		uploadListener.processFinish(resp);
		

}
}
