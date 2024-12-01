package com.enigmess;



import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;
import com.facebook.Session;

import android.app.ActionBar;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class Enter_Pin  extends Activity
{
	EditText edit_text_pin ,edit_text_email,edit_email;
	TextView forgot;
	ActionBar actionBar;
	Context context;
	ImageButton imgbtn_submit,imgbtn_submit2;
	String text_pin;
	ImageView  logout,backbutton,addfrds;
	Dialog   dialog;
	  PopupWindow popupAlert;
	  ImageView my_friends,edit_profile;
	  
	  SharedPreferences.Editor editit;
		SharedPreferences SP;
		public static String PREFS_NAME="settings";
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.enter_pin);
		actionBar = getActionBar();
		  Inflate_Actionbar.customTitle(getLayoutInflater(), actionBar,"Enter Pin",0, Enter_Pin.this);
		
		   Intent intent = getIntent();
		   
		   dialog = new Dialog(Enter_Pin.this);
		   dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		   dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		   dialog.setContentView(R.layout.forget_pin_popup);

		 
		   
		   edit_text_pin=(EditText)findViewById(R.id.edit_text_pin);
			edit_text_email=(EditText)findViewById(R.id.edit_text_email);
			imgbtn_submit=(ImageButton)findViewById(R.id.imgbtn_submit);

			

		      System.out.println("ms"+Walk_theme.message_id);
					
					final String msg=Walk_theme.message_id;
				System.out.println("msg1"+msg);
				Walk_theme.getSharedPrefValue(Enter_Pin.this);
				
				 edit_profile=(ImageView )findViewById(R.id.edit_profile);
				  
				  
				  edit_profile.setOnClickListener(new OnClickListener()
					{

						@Override
						public void onClick(View arg0)
						{
							//Intent myIntent = new Intent(Messages.this, Get_All_User.class);       
							//startActivity(myIntent);
							
							Walk_theme.Click_Backbutton(Enter_Pin.this, new Edit_Profile());
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
						
						
						  Walk_theme.deleteCache(Enter_Pin.this);
						    Session session = Session.getActiveSession();
						    System.out.println("session:" + session);

						    if (session != null) {

						     if (!session.isClosed()) {
						      System.out.println("SESSIONNOTCLOSED");
						      session.closeAndClearTokenInformation();
						      Walk_theme.user_id = "";
						      finish();
						    //startActivity(new Intent(Enter_Pin.this, Sign_In.class));
						      Walk_theme.Click_Backbutton(Enter_Pin.this, new Sign_In());
						      
						      finish();
						     }
						    } else {
						     System.out.println("SESSIONCLOSED");
						/*
						     session = new Session(Dashboard.this);
						     Session.setActiveSession(session);
						     session.closeAndClearTokenInformation();*/
						   //  startActivity(new Intent(Messages.this, Sign_In.class));
						     

						     Walk_theme.Click_Backbutton(Enter_Pin.this, new Sign_In());
								
						     finish();

						    }
						   // startActivity(new Intent(Messages.this, Sign_In.class));
						    
						    Walk_theme.Click_Backbutton(Enter_Pin.this, new Sign_In());
						    finish();
						
						
						
						//Intent myIntent = new Intent(Messages.this, Sign_In.class);  
						    
						    Walk_theme.Click_Backbutton(Enter_Pin.this, new Sign_In());
						//startActivity(myIntent);
					}
				});

			 addfrds=(ImageView )findViewById(R.id.addfrds);
			  my_friends=(ImageView )findViewById(R.id.my_friends);
			  
			  addfrds.setOnClickListener(new OnClickListener()
				{

					@Override
					public void onClick(View arg0)
					{
						//Intent myIntent = new Intent(Messages.this, Get_All_User.class);       
						//startActivity(myIntent);
						
						Walk_theme.Click_Backbutton(Enter_Pin.this, new Get_All_User());
					}
				});
				my_friends.setOnClickListener(new OnClickListener()
				{

					@Override
					public void onClick(View arg0)
					{
						
						

						Walk_theme.Click_Backbutton(Enter_Pin.this, new My_Friends());
						
					}
				});
			
			
			
		   forgot=(TextView)findViewById(R.id.forgot_pin);
		   
			
			  
		   forgot.setOnClickListener(new OnClickListener()
				{

			   @Override
		  	   public void onClick(View arg0)
		  	   {
		  	   
			  dialog.show();
				 
				 
				    edit_email = (EditText) dialog.findViewById(R.id.edit_email);
					imgbtn_submit2=(ImageButton)dialog.findViewById(R.id.imgbtn_submit2);
		          	  	imgbtn_submit2.setOnClickListener(new OnClickListener()
		  	             {
		  	                      @Override
		  	            	   public void onClick(View v)
		  	                    { 
		  	                      
		  	                    	
		  	            	 
		  	                    	 if(v==imgbtn_submit2)
		  	          				   {
		  	          				
		  	                    		if((TextUtils.isEmpty(edit_email.getText().toString())))
		  	          				{
		  	          					
		  	          					Toast.makeText(Enter_Pin.this, "Fill in your login details", Toast.LENGTH_LONG).show();
		  	          				}

		  	          				else
		  	          				{

		  	          					FetchData fetchData = new FetchData(Enter_Pin.this);
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

		  	          								System.out.println("Response"+status);

		  	          								if(TextUtils.equals(status, "success"))
		  	          								{	
		  	          								
		  	          									
		  	          								Toast.makeText(Enter_Pin.this, "check new pin on mail id", Toast.LENGTH_LONG).show();
		  	          								
		  	          								}
		  	          								else
		  	          								{
		  	          									Toast.makeText(Enter_Pin.this, "wrong email id", Toast.LENGTH_LONG).show();

		  	          								}
		  	          							}
		  	          							catch(JSONException je)
		  	          							{

		  	          								Toast.makeText(Enter_Pin.this, "Could not fetch data", Toast.LENGTH_LONG).show();
		  	          							}

		  	          						}
		  	          					};
		  	          					try
		  	          					{
		  	          						fetchData.execute("http://gamestaxi.webfactional.com/gametaxi/api/forgot_pin.php?email="+URLEncoder.encode(edit_email.getText().toString(),"UTF-8"));


		  	          					}
		  	          					catch(UnsupportedEncodingException je)
		  	          					{

		  	          						Toast.makeText(Enter_Pin.this, "Could not check your details", Toast.LENGTH_LONG).show();

		  	          					}
		  	          				}
		  	              				}

		  	          				  
		  	            	   }
		  	            	   });
		          	   
		 
		          	 
		         	  
		  	   }
				});
			  
		  
		   
		   

		   
		   
		   
		   addfrds=(ImageView )findViewById(R.id.addfrds);
		   addfrds.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View arg0)
				{
					Intent myIntent = new Intent(Enter_Pin.this, Get_All_User.class);       
					startActivity(myIntent);
				}
			});
		  
		   logout=(ImageView )findViewById(R.id.logout);
		   
		
			  
			  logout.setOnClickListener(new OnClickListener()
				{

					@Override
					public void onClick(View arg0)
					{
						Intent myIntent = new Intent(Enter_Pin.this, Sign_In.class);       
						startActivity(myIntent);
					}
				});
			  
			  
			  
			  backbutton=(ImageView )findViewById(R.id.backbutton);
			  

			  backbutton.setOnClickListener(new OnClickListener()
				{

					@Override
					public void onClick(View arg0)
					{
						
						
						Walk_theme.Click_Backbutton(Enter_Pin.this, new Messages());
						
					
					}
				});
			  
		
		
	
		
		
		
		imgbtn_submit.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub

				if((TextUtils.isEmpty(edit_text_pin.getText().toString())))
				{
					
					Toast.makeText(Enter_Pin.this, "Fill in your login details", Toast.LENGTH_LONG).show();
				}

				else
				{

					FetchData fetchData = new FetchData(Enter_Pin.this);
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

								System.out.println("Response"+status);

								if(TextUtils.equals(status, "success"))
								{	

					        
									Intent intent = new Intent(Enter_Pin.this,new Message_Decrypte().getClass());
									Enter_Pin.this.finish();
									intent.putExtra("msg1",msg);
									startActivity(intent);
								}
								else
								{
									Toast.makeText(Enter_Pin.this, "Wrong Pin", Toast.LENGTH_LONG).show();

								}
							}
							catch(JSONException je)
							{

								Toast.makeText(Enter_Pin.this, "Could not fetch data", Toast.LENGTH_LONG).show();
							}

						}
					};
				
					System.out.println("error"+Walk_theme.userid);
					
					
					text_pin= edit_text_pin.getText().toString();
					System.out.println("textpin"+text_pin);
					System.out.println("textpin1"+edit_text_pin.getText().toString());
					
						fetchData.execute("http://gamestaxi.webfactional.com/gametaxi/api/check_pin.php?user_id="+Walk_theme.userid+"&pin="+text_pin);
					
				}
			}


		});
	
		/* String fontPath ="fonts/Lato-Light.ttf";
		  Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
		  
		  edit_text_pin.setTypeface(tf);
		  edit_text_email.setTypeface(tf);
		  forgot.setTypeface(tf);*/
	}
	@Override
	protected void onDestroy()
	{
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
