package com.enigmess;

import java.util.ArrayList;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class Custom_Adapter_Get_All_User extends BaseAdapter {

	LayoutInflater mInlfater;
	Context context;
	ArrayList<String>user_name=new ArrayList<String>();
	ArrayList<String>imageList=new ArrayList<String>();
	ArrayList<String>useridlist=new ArrayList<String>();
	int index;
	String message;
	private Cache mCache;
	public Custom_Adapter_Get_All_User(Context context, int resource, ArrayList<String> user_name,ArrayList<String> imageList,ArrayList<String>useridlist) 
	{
		mInlfater = LayoutInflater.from(context);
		
		final int memClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
        final int size = 1024 * 1024 * memClass / 8;     
        RetainCache c = RetainCache.getOrCreateRetainableCache();
        mCache = c.mRetainedCache;
        
        if (mCache == null)
        {
        	
        	final int MAX_PIXELS_WIDTH = 100;
        	final int MAX_PIXELS_HEIGHT = 100;
            mCache = new Cache(size, MAX_PIXELS_WIDTH, MAX_PIXELS_HEIGHT);
            c.mRetainedCache = mCache;
        }
		
		
		
		this.context = context;
		this.user_name = user_name;
		this.imageList = imageList;
		this.useridlist = useridlist;
		mInlfater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		System.out.println("userid1"+useridlist);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return useridlist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return useridlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		View vi = convertView;
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();

			vi =  mInlfater.inflate(R.layout.item_mockgallery_one, null);

			holder.name = (TextView) vi.findViewById(R.id.textView_caption);
			holder.Img = (ImageView) vi.findViewById(R.id.imageView_mockgalleryItem);
			holder.btnAdd=(Button)vi.findViewById(R.id.addfrd);
			vi.setTag(holder);
		}
		else 
		{
			holder = (ViewHolder) vi.getTag();
		}


		holder.name.setText(user_name.get(position));
		
		mCache.loadBitmap(context,imageList.get(position), holder.Img);  

	
	    
	//	holder.Img .setImageBitmap(imageList.get(position));
		holder.btnAdd.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				System.out.println("Params " + position);
				
				
				 Walk_theme.friend_id=useridlist.get(position);
				 System.out.println("idddddddddd " +  Walk_theme.friend_id);
			
				 
				 Uploade fetchData = new Uploade(context);
					fetchData.execute("http://gamestaxi.webfactional.com/gametaxi/api/add_friend.php?user_id="+Walk_theme.userid+"&friend_id="+Walk_theme.friend_id);
					fetchData.response = new UploadListener() 
					{
						
						@Override
						public void processFinish(String output)
						{
							System.out.println("fetchfinish" + output);

							try 
							{
								JSONObject obj = new JSONObject(output);
							String status = obj.getString("response");

							System.out.println("Response"+status);

							 message=  obj.getString("message");	
							}
							
									    
							catch(JSONException je)
							{

								Toast.makeText(context.getApplicationContext(), "Could not fetch data", Toast.LENGTH_LONG).show();
							}
							
						}
					};
				 
			
			}
		});

		
		
		
		//	holder.number.setText(user_number.get(position));
		index=position;

		System.out.println("positin"+position);


		return vi;

	}
	static class ViewHolder
	{
		TextView name;
		ImageView Img;
		Button btnAdd;
	}
	
	
	
}
