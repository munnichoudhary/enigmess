package com.enigmess;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.Session;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Messages extends Activity implements FetchListener
{

	ActionBar actionBar;

	ArrayList<String>message_ids=new ArrayList<String>();
	ArrayList<String>messages=new ArrayList<String>();

	ArrayList<String>imageList=new ArrayList<String>();
	ArrayList<String>captionList=new ArrayList<String>();

	ImageView backbutton;
	SharedPreferences.Editor editit;
	SharedPreferences SP;
	public static String PREFS_NAME="settings";
	ListView list;
	TextView textView_caption;
	ImageView  addfrds,logout,my_friends,edit_profile;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.messages);

		actionBar = getActionBar();
		Inflate_Actionbar.customTitle(getLayoutInflater(), actionBar,"Messages",1,this);

		backbutton=(ImageView)findViewById(R.id.backbutton);

		System.out.println("Inside messages user id"+Walk_theme.userid);
		backbutton.setVisibility(View.INVISIBLE);

		addfrds=(ImageView )findViewById(R.id.addfrds);
		my_friends=(ImageView )findViewById(R.id.my_friends);

		edit_profile=(ImageView )findViewById(R.id.edit_profile);

		Walk_theme.getSharedPrefValue(Messages.this);
		System.out.println("message userid"+Walk_theme.userid);
		edit_profile.setOnClickListener(new OnClickListener()
		{
            @Override
			public void onClick(View arg0)
			{
				Walk_theme.Click_Backbutton(Messages.this, new Edit_Profile());
			}
		});

   addfrds.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				Walk_theme.Click_Backbutton(Messages.this, new Get_All_User());
			}
		});

     my_friends.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
                Walk_theme.Click_Backbutton(Messages.this, new My_Friends());
			}
		});


		logout=(ImageView )findViewById(R.id.logout);


		list=(ListView)findViewById(R.id.list);

		Fetch_Random_poi();

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

				Walk_theme.deleteCache(Messages.this);
				Session session = Session.getActiveSession();
				System.out.println("session:" + session);

				if (session != null)
				{
					if (!session.isClosed())
					{
						System.out.println("SESSIONNOTCLOSED");
						session.closeAndClearTokenInformation();
						Walk_theme.user_id = "";
						finish();
						startActivity(new Intent(Messages.this, Sign_In.class));
						finish();
					}
				} 
				else 
				{
					System.out.println("SESSIONCLOSED");
					Walk_theme.Click_Backbutton(Messages.this, new Sign_In());

					finish();

				}


				Walk_theme.Click_Backbutton(Messages.this, new Sign_In());
				finish();


				Walk_theme.Click_Backbutton(Messages.this, new Sign_In());

			}
		});

	}
	private void Fetch_Random_poi()
	{
		FetchData fetchData = new FetchData(Messages.this);
		fetchData.response = Messages.this;
		System.out.println("useridLLMSG"+Walk_theme.userid);
		String url = "http://gamestaxi.webfactional.com/gametaxi/api/get_all_message.php?user_id="+Walk_theme.userid;
		Walk_theme.executeFetchUrl(fetchData, url);
	}

	@Override
	public void fetchFinish(String output)
	{
		// TODO Auto-generated method stub

		try 
		{
			JSONObject json = new JSONObject(output);
			if(json.getString("response").equals("success"))
			{
				JSONArray data = json.getJSONArray("data");
				System.out.println("Result1"+data);
				JSONObject c;
				System.out.println("Random"+data);
				for(int i=0;i<data.length();i++)
				{
					c = data.getJSONObject(i);
					String message_id = c.getString("message_id");
                	System.out.println("message_id"+message_id);
                   String message= c.getString("message");
					String poi = c.getString("user_name");
					String imageurl=c.getString("profile_pic");
					System.out.println("mmm"+imageurl);
					System.out.println("Images"+poi+""+imageurl);
					message_ids.add(message_id);
					messages.add(message);
					captionList.add(poi);
					imageList.add(imageurl);
				}

				System.out.println("ImageList"+message_ids);
				System.out.println("ImageList1"+messages);

				ArrayAdapter<String> adapter = new ArrayAdapter<String>(Messages.this, android.R.layout.simple_list_item_1);
				adapter = new Custom_Adapter_Messages(Messages.this, R.layout.item_mockgallery,captionList, imageList,messages,message_ids);
				list.setAdapter(adapter);
			}

		} 
		catch (JSONException e) 
		{
			System.out.println("execption"+e);
			Walk_theme.showMessage(Messages.this, "Could not fetch data");

		}

	}

	@Override
	protected void onDestroy()
	{
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
