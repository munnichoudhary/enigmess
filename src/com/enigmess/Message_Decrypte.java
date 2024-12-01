package com.enigmess;

import org.json.JSONException;
import org.json.JSONObject;
import com.facebook.Session;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
public class Message_Decrypte extends Activity
{
	EditText edit_text_entercode;
	ActionBar actionBar;
	TextView name,notrecieve,time;
	ImageView imgmsg,backbutton,delete;
	RelativeLayout reldecrypted_msg;
	String ms;
	ImageView my_friends,addfrds,logout,edit_profile;
	
	SharedPreferences.Editor editit;
	SharedPreferences SP;
	public static String PREFS_NAME="settings";
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_decrypte);
		
		
		  
		  Intent intent = getIntent();
			final String   extras=intent.getStringExtra("msg1");
			 System.out.println("extras"+extras);
		
    edit_text_entercode=(EditText)findViewById(R.id.edit_text_pin);
		
		name=(TextView)findViewById(R.id.name);
		notrecieve=(TextView)findViewById(R.id.notrecieve);
		time=(TextView)findViewById(R.id.time);
		imgmsg=(ImageView)findViewById(R.id.imgmsg);
		
		
		  actionBar = getActionBar();
		  Inflate_Actionbar.customTitle(getLayoutInflater(), actionBar,"Enter Pin",0,Message_Decrypte.this);
		  
		  
		  edit_profile=(ImageView )findViewById(R.id.edit_profile);
		  
		  Walk_theme.getSharedPrefValue(Message_Decrypte.this);
		  edit_profile.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View arg0)
				{
					//Intent myIntent = new Intent(Messages.this, Get_All_User.class);       
					//startActivity(myIntent);
					
					Walk_theme.Click_Backbutton(Message_Decrypte.this, new Edit_Profile());
				}
			});
	
		  
	
		 
		   backbutton=(ImageView )findViewById(R.id.backbutton);

		delete=(ImageView )findViewById(R.id.delete);
		reldecrypted_msg=(RelativeLayout)findViewById(R.id.reldecrypted_msg);
		  
		
		
		
		
		
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
					
					  Walk_theme.deleteCache(Message_Decrypte.this);
					    Session session = Session.getActiveSession();
					    System.out.println("session:" + session);

					    if (session != null) {

					     if (!session.isClosed()) {
					      System.out.println("SESSIONNOTCLOSED");
					      session.closeAndClearTokenInformation();
					      Walk_theme.user_id = "";
					      finish();
					    //startActivity(new Intent(Enter_Pin.this, Sign_In.class));
					      Walk_theme.Click_Backbutton(Message_Decrypte.this, new Sign_In());
					      
					      finish();
					     }
					    } else {
					     System.out.println("SESSIONCLOSED");
					

					     Walk_theme.Click_Backbutton(Message_Decrypte.this, new Sign_In());
							
					     finish();

					    }
			
					    
					    Walk_theme.Click_Backbutton(Message_Decrypte.this, new Sign_In());
					    finish();	    
					    Walk_theme.Click_Backbutton(Message_Decrypte.this, new Sign_In());
				
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
					
					Walk_theme.Click_Backbutton(Message_Decrypte.this, new Get_All_User());
				}
			});
		my_friends.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				
				Walk_theme.Click_Backbutton(Message_Decrypte.this, new My_Friends());
				
			}
		});
	  
		
		
		
		
		  backbutton.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View arg0)
				{
					
					
					Walk_theme.Click_Backbutton(Message_Decrypte.this, new Enter_Pin());
					
					//Intent myIntent = new Intent(Message_Decrypte.this, Enter_Pin.class);       
					//startActivity(myIntent);
				}
			});
		  
		  FetchData fetchData = new FetchData(Message_Decrypte.this);
		  
		

						fetchData.execute("http://gamestaxi.webfactional.com/gametaxi/api/get_message_byid.php?message_id="+extras);
						fetchData.response = new FetchListener() 
						{

							@Override
							public void fetchFinish(String output)
							{
								System.out.println("fetchfinish" + output);

								try 
								{
									  JSONObject json = new JSONObject(output);
									  JSONObject data = json.getJSONObject("data");
									System.out.println("Result1"+data);
									
									System.out.println("data111"+data);   
										    String message_id = data.getString("message_id");
										    String message= data.getString("message");
                                           System.out.println("messages11"+message);
                                           notrecieve.setText(message);
                                           
										    String poi = data.getString("user_name");
										    name.setText(poi);
										       
										    String datetime = data.getString("datetime");
										    time.setText(datetime);
										    
										    String imageurl=data.getString("profile_pic");   
										    
										    FetchImage fetchImage = new FetchImage(Message_Decrypte.this);
											fetchImage.execute(imageurl);  
											fetchImage.response = new FetchImageListener() 
											{
											
											@Override
											public void fetchFinish(Bitmap bitmap) 
											{
												
												imgmsg.setImageBitmap(getCircleBitmap(bitmap));
											    
												//imgmsg.setImageBitmap(bitmap);	
												
											}
										};
										    
										   
								}
										
						
								catch(JSONException je)
								{

									Toast.makeText(Message_Decrypte.this, "Could not fetch data", Toast.LENGTH_LONG).show();
								} 

                          } 
		
						};
	
	
						  delete.setOnClickListener(new OnClickListener()
							{

								@Override
								public void onClick(View arg0)
								{
									// TODO Auto-generated method stub

										FetchData fetchData = new FetchData(Message_Decrypte.this);
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
													ms=  obj.getString("message");	
														if(TextUtils.equals(status, "success"))
														{	
															//reldecrypted_msg.setVisibility(View.INVISIBLE);
															Toast.makeText(Message_Decrypte.this, "message deleted", Toast.LENGTH_LONG).show();
															Message_Decrypte.this.finish();
															Intent intent = new Intent(Message_Decrypte.this,new Messages().getClass());
															
															startActivity(intent);
															
														}
														else
														{
															//reldecrypted_msg.setVisibility(View.VISIBLE);
															Toast.makeText(Message_Decrypte.this, "message not deleted", Toast.LENGTH_LONG).show();
															
														}
													}
												catch(JSONException je)
												{

													Toast.makeText(Message_Decrypte.this, "Could not fetch data", Toast.LENGTH_LONG).show();
												}

											}
										};
										fetchData.execute("http://gamestaxi.webfactional.com/gametaxi/api/delete_message.php?message_id="+extras);
									}
								

							});
						  
	
	}
	
	
	private Bitmap getCircleBitmap(Bitmap bitmap) {
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

				
	
