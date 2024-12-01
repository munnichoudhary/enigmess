package com.enigmess;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Session;

public class Edit_Profile extends Activity implements UploadListener,FetchListener
{
	UpdateUserTask update;
	TextView textUserName;
	ImageView edit_profile,logout;
	ActionBar actionBar;
	ImageView profilePic,submit_edit_pro,addfrds,my_friends,backbutton,user_name_edit_profile,user_image_edit_profile;
	EditText name_edit_pro,username_edit_pro,email_edit_pro,pin_edit_pro;

	String name_edit_pro1,username_edit_pro1,email_edit_pro1,pin_edit_pro1,selectedImagePath;
	private static int RESULT_LOAD_IMAGE = 1;
	SharedPreferences.Editor editit;
	SharedPreferences SP;
	public static String PREFS_NAME="settings";
	File photofile;
	private String resultString;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editprofile);


		actionBar = getActionBar();
		Inflate_Actionbar.customTitle(getLayoutInflater(), actionBar,"Edit Profile",1,this);

		edit_profile=(ImageView )findViewById(R.id.edit_profile);
		user_name_edit_profile=(ImageView )findViewById(R.id.user_name_edit_profile);
		user_image_edit_profile=(ImageView )findViewById(R.id.user_image_edit_profile);
		submit_edit_pro=(ImageView )findViewById(R.id.submit_edit_pro);

		name_edit_pro=(EditText)findViewById(R.id.name_edit_pro);
		username_edit_pro=(EditText)findViewById(R.id.username_edit_pro);
		email_edit_pro=(EditText)findViewById(R.id.email_edit_pro);
		pin_edit_pro=(EditText)findViewById(R.id.pin_edit_pro);

System.out.println("strname"+	Walk_theme.strname);

//Toast.makeText(Edit_Profile.this, "userid"+Walk_theme.userid, Toast.LENGTH_LONG).show();

FetchData fetchData = new FetchData(Edit_Profile.this);
fetchData.execute("http://gamestaxi.webfactional.com/gametaxi/api/get_profile.php?user_id="+Walk_theme.userid);	
	fetchData.response = new FetchListener() 
	{

		@Override
		public void fetchFinish(String output)
		{
			System.out.println("fetchfinish" + output);

			try 
			{
				JSONObject obj = new JSONObject(output);
				
				String status = obj.getString("response");
				

				JSONObject universityObject =  obj.getJSONObject("data");
				String user_id = universityObject.getString("user_id"); 
				
				String user_name = universityObject.getString("user_name");
					username_edit_pro.setText(user_name);
					
					
					Walk_theme.getSharedPrefValue(Edit_Profile.this);
					String email = universityObject.getString("email");
					email_edit_pro.setText(email);
					
				String name = universityObject.getString("name");
			   name_edit_pro.setText(name);
			   
			   
				String profile_pic = universityObject.getString("profile_pic");
				
				  FetchImage fetchImage = new FetchImage(Edit_Profile.this);
					fetchImage.execute(profile_pic);  
					fetchImage.response = new FetchImageListener() 
					{
					
					@Override
					public void fetchFinish(Bitmap bitmap) 
					{
						
						user_image_edit_profile.setImageBitmap(getCircleBitmap(bitmap));
					    
						//imgmsg.setImageBitmap(bitmap);	
						
					}
				};
				    
				
				
				String pin = universityObject.getString("pin");
				pin_edit_pro.setText(pin);
				
				System.out.println("pin"+pin);

				
			}
			catch(JSONException je)
			{

				Toast.makeText(Edit_Profile.this, "Could not fetch data", Toast.LENGTH_LONG).show();
			}

		}
	};

	
		edit_profile.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
			
				Walk_theme.Click_Backbutton(Edit_Profile.this, new Edit_Profile());
			}
		});



		addfrds=(ImageView )findViewById(R.id.addfrds);
		my_friends=(ImageView )findViewById(R.id.my_friends);

		addfrds.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				Walk_theme.Click_Backbutton(Edit_Profile.this, new Get_All_User());
			}
		});
		my_friends.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{



				Walk_theme.Click_Backbutton(Edit_Profile.this, new My_Friends());

			}
		});

		logout=(ImageView )findViewById(R.id.logout);

		logout.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{

				SP = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
				editit = SP.edit();
				editit.putString("username", "");                    
				editit.putString("password","");
				editit.putString("userid","");
				editit.commit();


				Walk_theme.deleteCache(Edit_Profile.this);
				Session session = Session.getActiveSession();
				System.out.println("session:" + session);

				if (session != null) {

					if (!session.isClosed()) {
						System.out.println("SESSIONNOTCLOSED");
						session.closeAndClearTokenInformation();
						Walk_theme.user_id = "";
						finish();
						//startActivity(new Intent(Enter_Pin.this, Sign_In.class));
						Walk_theme.Click_Backbutton(Edit_Profile.this, new Sign_In());

						finish();
					}
				} else {
					System.out.println("SESSIONCLOSED");
					

					Walk_theme.Click_Backbutton(Edit_Profile.this, new Sign_In());

					finish();
		}
			
				Walk_theme.Click_Backbutton(Edit_Profile.this, new Sign_In());
				finish();


				Walk_theme.Click_Backbutton(Edit_Profile.this, new Sign_In());
				
			}
		});

		backbutton=(ImageView )findViewById(R.id.backbutton);


		backbutton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{

				Walk_theme.Click_Backbutton(Edit_Profile.this, new Messages());

			}
		});
		submit_edit_pro.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				

				try {

				name_edit_pro1=URLEncoder.encode(name_edit_pro.getText().toString(), "UTF-8");
				username_edit_pro1=URLEncoder.encode(username_edit_pro.getText().toString(), "UTF-8");
				email_edit_pro1=URLEncoder.encode(email_edit_pro.getText().toString(), "UTF-8");
				pin_edit_pro1=URLEncoder.encode(pin_edit_pro.getText().toString(), "UTF-8");
				
		
				if(photofile!=null)
				{
										
				String send_imagepath=URLEncoder.encode(photofile.toString(),"UTF-8");
			//	Toast.makeText(Edit_Profile.this, "InsideImage"+send_imagepath, Toast.LENGTH_LONG).show();
				
				update = new UpdateUserTask(name_edit_pro1, username_edit_pro1, pin_edit_pro1,email_edit_pro1,photofile, Edit_Profile.this);				
				System.out.println("Photo file " +photofile);					
				update.uploadListener = Edit_Profile.this;
				
				System.out.println("params edit pro http://gamestaxi.webfactional.com/gametaxi/api/edit_profile.php?user_id="+Walk_theme.userid+"&name="+name_edit_pro1 +"&user_name="+username_edit_pro1+"&pin="+pin_edit_pro1+"&email="+email_edit_pro1+"&file="+send_imagepath);
				update.execute("http://gamestaxi.webfactional.com/gametaxi/api/edit_profile.php?user_id="+Walk_theme.userid+"&name="+name_edit_pro1 +"&user_name="+username_edit_pro1+"&pin="+pin_edit_pro1+"&email="+email_edit_pro1+"&file="+send_imagepath);
				}
				else
				{
					
				System.out.println("Create Else");
				//Toast.makeText(Edit_Profile.this, "outsideImage", Toast.LENGTH_LONG).show();
					FetchData fetchData = new FetchData(Edit_Profile.this);
					fetchData.response = Edit_Profile.this;
					
					System.out.println("param edit profile http://gamestaxi.webfactional.com/gametaxi/api/edit_profile.php?user_id="+Walk_theme.userid+"&name="+name_edit_pro1 +"&user_name="+username_edit_pro1+"&pin="+pin_edit_pro1+"&email="+email_edit_pro1);
					String url = "http://gamestaxi.webfactional.com/gametaxi/api/edit_profile.php?user_id="+Walk_theme.userid+"&name="+name_edit_pro1 +"&user_name="+username_edit_pro1+"&pin="+pin_edit_pro1+"&email="+email_edit_pro1;
				
					Walk_theme.executeFetchUrl(fetchData, url);
					
					
				}
			}	
				catch (UnsupportedEncodingException e)
				{
			
					e.printStackTrace();
				}

	}
		});
		phptopic();
	}
	public void phptopic()
	{

		user_name_edit_profile.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				startActivityForResult(i, RESULT_LOAD_IMAGE);


			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		if (resultCode == RESULT_OK)
		{
			if (requestCode == RESULT_LOAD_IMAGE)
			    {
				Uri uri = data.getData();

				selectedImagePath = getPath(uri);

				// Toast.makeText(Sign_Up.this, image_path.getText(), Toast.LENGTH_LONG).show();
				System.out.println("URL"+uri+"selectedImagePath"+selectedImagePath);
			
				photofile = new File(selectedImagePath);
				
				System.out.println("filepath"+photofile.getPath());

				InputStream in;
				try
				{


					System.out.println("Inside Try");
				
					in = getContentResolver().openInputStream(uri);
					// Toast.makeText(Sign_Up.this," Inside Try"+in, Toast.LENGTH_LONG).show();
					Bitmap bmp = BitmapFactory.decodeStream(in);
					System.out.println("BITMAPFILE0"+bmp);
					user_image_edit_profile.setImageBitmap((bmp));

				} 
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		else
		{

			Toast.makeText(Edit_Profile.this, "User Cancelled picture Selection", Toast.LENGTH_LONG).show();
		}

	}

	public String getPath(Uri uri)
	{
		
		if( uri == null ) 
		{
			// TODO perform some logging or show user feedback
			return null;

		}
	
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		if( cursor != null )
		{
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		}
	
		return uri.getPath();
	}

	@Override
	public void processFinish(String output)
	{
		// TODO Auto-generated method stub
		
		JSONObject resultObject=null;
		try{

		 resultObject = new JSONObject(output);
		 resultString = resultObject.getString("response");
		 System.out.println("resultString"+output);
		
		Walk_theme.showMessage(Edit_Profile.this, resultString);
		if(TextUtils.equals(resultString, "success"))
	    {
	    }
		else
		{
			
		}
		}
		catch(JSONException e)
		{
			e.printStackTrace();
		}
		
	}

	@Override
	public void fetchFinish(String output)
	{
		// TODO Auto-generated method stub
		JSONObject resultObject=null;
		try
		{

		 resultObject = new JSONObject(output);
		 resultString = resultObject.getString("response");
		 System.out.println("resultString"+output);
		
		Walk_theme.showMessage(Edit_Profile.this, resultString);
		if(TextUtils.equals(resultString, "success"))
	    {
	    }
		else
		{
			
		}
		}
		catch(JSONException e)
		{
			e.printStackTrace();
		}
	}


private Bitmap getCircleBitmap(Bitmap bitmap)
{
	  Bitmap output = null;
	  
	  if(bitmap != null)
	  {
	   output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getWidth(), Config.ARGB_8888);

	       Canvas canvas = new Canvas(output);

	       final int color = 0xff424242;
	       final Paint paint = new Paint();
	       final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

	       float r = 0;

	       if (bitmap.getWidth() > bitmap.getHeight()) {
	           r = bitmap.getHeight() / 2;
	       } else {
	           r = bitmap.getWidth() / 2;
	       }

	       paint.setAntiAlias(true);
	       canvas.drawARGB(0, 0, 0, 0);
	       paint.setColor(color);
	       canvas.drawCircle(r, r, r, paint);
	       paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
	       canvas.drawBitmap(bitmap, rect, rect, paint);
	       
	  }
	  return output;
	 }
@Override
protected void onDestroy()
{
	// TODO Auto-generated method stub
	super.onDestroy();
}
					
}
