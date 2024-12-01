package com.enigmess;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import com.facebook.Session;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

public class My_Friends extends Activity implements FetchListener
{

	ActionBar actionBar;
	ArrayList<String>imageList=new ArrayList<String>();
	ArrayList<String>captionList=new ArrayList<String>();
	ArrayList<String>id=new ArrayList<String>();
	PullToRefreshListView list;
	TextView textView_caption;
	ImageView my_friends,backbutton,addfrds,logout, edit_profile,search_img;
	SharedPreferences.Editor editit;
	SharedPreferences SP;
	public static String PREFS_NAME="settings";
	EditText search_edittext;
	String search_edittext_str;
	int page = 1;
	private Custom_Adapter_My_Friends adapter;
	 ArrayList<String> name_filter=new ArrayList<String>();
	  ArrayList<String> user_filter=new ArrayList<String>();
	  ArrayList<String> img_filter=new ArrayList<String>();
	  ListView list_view;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myfrd_list);

		actionBar = getActionBar();
		Inflate_Actionbar.customTitle(getLayoutInflater(), actionBar,"My Friend List",1,this);

		edit_profile=(ImageView )findViewById(R.id.edit_profile);
		search_img=(ImageView )findViewById(R.id.search_img);
		search_edittext=(EditText )findViewById(R.id.search_edittext);
		list_view=(ListView)findViewById(R.id.list_view);
		search_edittext_str= search_edittext.getText().toString();
		Walk_theme.getSharedPrefValue(My_Friends.this);
		getNextPage();
		edit_profile.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{

				Walk_theme.Click_Backbutton(My_Friends.this, new Edit_Profile());
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

				Walk_theme.deleteCache(My_Friends.this);
				Session session = Session.getActiveSession();
				System.out.println("session:" + session);

				if (session != null) {

					if (!session.isClosed()) {
						System.out.println("SESSIONNOTCLOSED");
						session.closeAndClearTokenInformation();
						Walk_theme.user_id = "";
						finish();

						Walk_theme.Click_Backbutton(My_Friends.this, new Sign_In());

						finish();
					}
				} else {
					System.out.println("SESSIONCLOSED");


					Walk_theme.Click_Backbutton(My_Friends.this, new Sign_In());

					finish();

				}

				Walk_theme.Click_Backbutton(My_Friends.this, new Sign_In());
				finish();

				Walk_theme.Click_Backbutton(My_Friends.this, new Sign_In());

			}
		});



		addfrds=(ImageView )findViewById(R.id.addfrds);

		addfrds.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{


				Walk_theme.Click_Backbutton(My_Friends.this, new Get_All_User());
			}
		});

		backbutton=(ImageView )findViewById(R.id.backbutton);


		backbutton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{


				Walk_theme.Click_Backbutton(My_Friends.this, new Get_All_User());


			}
		});


		my_friends=(ImageView )findViewById(R.id.my_friends);
		my_friends.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{

				Walk_theme.Click_Backbutton(My_Friends.this, new My_Friends());

			}
		});




		list = (PullToRefreshListView)findViewById(R.id.list);

		list.setMode(Mode.PULL_FROM_END);

		list.setOnRefreshListener(new OnRefreshListener<ListView>()
				{
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView)
			{
				getNextPage();

			}
		});

		list.setOnScrollListener(new OnScrollListener()
		{

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState)
			{

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) 
			{

			}
		});


		//System.out.println("UserName"+Walk_theme.UserName);
		search_img.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				
				
				name_filter.clear();
				user_filter.clear();
				img_filter.clear();
				
				
				String value=search_edittext.getText().toString();
//				String value1=value.substring(1, 1);
				String for_no="([a-zA-Z]*)";
				String for_no1="([0-9]*)";
				if(value.matches(for_no))
				{
					
					
					for(int i=0;i<id.size();i++)
					{
						
					//System.out.println("Params true " + name.get(i).contains(value.toLowerCase()));
					if(captionList.get(i).contains(value.toLowerCase()))
					{
						
						//Toast.makeText(My_Friends.this,"Value"+captionList.get(i), Toast.LENGTH_LONG).show();
						name_filter.add(captionList.get(i));
						user_filter.add(id.get(i));
						img_filter.add(imageList.get(i));
						list.setVisibility(View.INVISIBLE);
						
					
						list_view.setVisibility(View.VISIBLE);
						adapter = new Custom_Adapter_My_Friends(My_Friends.this, R.layout.item_mockgallery_one,name_filter, img_filter,user_filter);
						list_view.setAdapter(adapter);
					}
					
					}
				}
				
				
				else
				{
					Toast.makeText(My_Friends.this, "Not match any user", Toast.LENGTH_LONG).show();
				}
			}



		});
	}
	private void Fetch_Random_poi(int page)
	{
		FetchData fetchData = new FetchData(My_Friends.this);
		fetchData.response = My_Friends.this;
		String url = "http://gamestaxi.webfactional.com/gametaxi/api/get_my_friend.php?user_id="+Walk_theme.userid+"&refresh=0&page="+ page ++;
		
		System.out.println("myfrds link" + url );
		Walk_theme.executeFetchUrl(fetchData, url);
	}

	@Override
	public void fetchFinish(String output)
	{
		// TODO Auto-generated method stub

		try 
		{

			JSONObject json = new JSONObject(output);
			JSONArray data = json.getJSONArray("data");
			
			System.out.println("Result_frnd_gggg"+data);

			JSONObject c;

			System.out.println("Random"+data);
			for(int i=0;i<data.length();i++)
			{
				c = data.getJSONObject(i);

				String poi = c.getString("user_name");

				
				id.add(c.getString("user_id"));

				String imageurl=c.getString("profile_pic");

				System.out.println("Images"+poi+""+imageurl);
				captionList.add(poi);

				imageList.add(imageurl);
			}

			
			if(list.isRefreshing())
				   list.onRefreshComplete();
			System.out.println("ImageList"+imageList);

			
		
			adapter = new Custom_Adapter_My_Friends(My_Friends.this, R.layout.item_myfrd,captionList, imageList,id);
			list.setAdapter(adapter);
			
			
			
		    adapter.notifyDataSetChanged();

		} 
		catch (JSONException e) 
		{
			System.out.println("execption"+e);
			Walk_theme.showMessage(My_Friends.this, "Could not fetch data");

		}

	}
	
	private void getNextPage() 
	{	
		Fetch_Random_poi(page ++);
	}
	@Override
	protected void onDestroy()
	{
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
