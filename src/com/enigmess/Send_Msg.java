package com.enigmess;




import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.facebook.Session;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Send_Msg extends Activity
{
	TextView textUserName;
	EditText edit_text_type_msg1;
	ImageButton submit_button_msg;
	String edit_text_type_msg;
	ImageView profilePic, edit_profile,my_friends,backbutton,addfrds,logout;
	ArrayList<String> msgs,datetime;
	ListView msgList;
	ActionBar actionBar;
	
	  SharedPreferences.Editor editit;
		SharedPreferences SP;
		public static String PREFS_NAME="settings";
		
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_friend_list_sendmsg);

		actionBar = getActionBar();
		Inflate_Actionbar.customTitle(getLayoutInflater(), actionBar,"My Friend List",1,this);
		
		
		
  edit_profile=(ImageView )findViewById(R.id.edit_profile);
		  
  Walk_theme.getSharedPrefValue(Send_Msg.this);
		  edit_profile.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View arg0)
				{
					
					
					Walk_theme.Click_Backbutton(Send_Msg.this, new Edit_Profile());
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
					
					  Walk_theme.deleteCache(Send_Msg.this);
					    Session session = Session.getActiveSession();
					    System.out.println("session:" + session);

					    if (session != null) {

					     if (!session.isClosed()) {
					      System.out.println("SESSIONNOTCLOSED");
					      session.closeAndClearTokenInformation();
					      Walk_theme.user_id = "";
					      finish();
					    //startActivity(new (Enter_Pin.this, Sign_In.class));
					  	Walk_theme.Click_Backbutton(Send_Msg.this, new Sign_In());
					      
					      finish();
					     }
					    } else {
					     System.out.println("SESSIONCLOSED");
					

							Walk_theme.Click_Backbutton(Send_Msg.this, new Sign_In());
							
					     finish();

					    }
					 
					    
						Walk_theme.Click_Backbutton(Send_Msg.this, new Sign_In());
					    finish();
					
					
					    
						Walk_theme.Click_Backbutton(Send_Msg.this, new Sign_In());
				
				}
			});
		  
		  
		  
			 addfrds=(ImageView )findViewById(R.id.addfrds);
		  addfrds.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View arg0)
				{
					
					
					Walk_theme.Click_Backbutton(Send_Msg.this, new Get_All_User());
				}
			});
		  
			backbutton=(ImageView )findViewById(R.id.backbutton);


			backbutton.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View arg0)
				{


					Walk_theme.Click_Backbutton(Send_Msg.this, new Get_All_User());


				}
			});

		  
		  my_friends=(ImageView )findViewById(R.id.my_friends);
			my_friends.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View arg0)
				{

					Walk_theme.Click_Backbutton(Send_Msg.this, new My_Friends());

				}
			});
		  
		  
		final Intent intent= getIntent();
		setGUI();
		textUserName.setText(intent.getStringExtra("username"));
		FetchImage fetchData=new FetchImage(Send_Msg.this);
		
		
		
		fetchData.response = new FetchImageListener() 
		{

			@Override
			public void fetchFinish(Bitmap bitmap)
			{
				profilePic.setImageBitmap(getCircleBitmap(bitmap));

			}
		};
		fetchData.execute(intent.getStringExtra("pic"));
		FetchData fetchMsg = new FetchData(Send_Msg.this);
		fetchMsg.response = new FetchListener()
		{

			@Override
			public void fetchFinish(String output)
			{

				JSONObject json;
				try 
				{

					json = new JSONObject(output);
					JSONArray data = json.getJSONArray("data");
					System.out.println("Result1"+data);

					JSONObject c;

					System.out.println("Random"+data);
					msgs = new ArrayList<String>();
					datetime = new ArrayList<String>();
					
					for(int i=0;i<data.length();i++)
					{
						 c = data.getJSONObject(i);
						 msgs.add(c.getString("message"));
						datetime.add(c.getString("datetime"));
						
						 System.out.println("message"+c.getString("message"));
					}
				
					
					
					
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(Send_Msg.this, android.R.layout.simple_list_item_1);
					adapter = new Custom_Adaptoer_Send_Msg(Send_Msg.this,R.layout.item_send_msg,datetime,msgs);
					msgList.setAdapter(adapter);
				} 
				catch (JSONException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
					
					 System.out.println("message"+e);
				}

				//JSONArray data = new JSONArray(output);



			}
		};
		
		System.out.println("byuserid"+ intent.getStringExtra("id"));
		fetchMsg.execute("http://gamestaxi.webfactional.com/gametaxi/api/get_message_byuser.php?user_id="+intent.getStringExtra("id"));
		
		
		edit_text_type_msg1=(EditText)findViewById(R.id.edit_text_type_msg);

		submit_button_msg=(ImageButton)findViewById(R.id.submit_button_msg);


		edit_text_type_msg = edit_text_type_msg1.getText().toString();

		submit_button_msg.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				FetchData fetchData = new FetchData(Send_Msg.this);
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

								
								edit_text_type_msg1.setText("");
								
								System.out.println("edit_text_type_msg"+edit_text_type_msg);

								Toast.makeText(Send_Msg.this, "message send sucess", Toast.LENGTH_LONG).show();
							}
							else
							{
								Toast.makeText(Send_Msg.this, "not fetching data", Toast.LENGTH_LONG).show();

							}
						}
						catch(JSONException je)
						{

							Toast.makeText(Send_Msg.this, "Could not fetch data", Toast.LENGTH_LONG).show();
						}

					}
				};


				//	System.out.println("edit_text_type_msg"+fetchData.execute("http://gamestaxi.webfactional.com/gametaxi/api/send_message.php?sender_id=1&message="+edit_text_type_msg+"&receiver_id=2"));
				try 
				{
					fetchData.execute("http://gamestaxi.webfactional.com/gametaxi/api/send_message.php?sender_id="+Walk_theme.userid+"&message="+URLEncoder.encode(edit_text_type_msg1.getText().toString(),"UTF-8")+"&receiver_id="+intent.getStringExtra("id"));
				} 
				catch (UnsupportedEncodingException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}



		});

	}
	private void setGUI()
	{
		textUserName = (TextView)findViewById(R.id.user_name);
		profilePic = (ImageView)findViewById(R.id.user_image);
		msgList = (ListView)findViewById(R.id.list_view);
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
