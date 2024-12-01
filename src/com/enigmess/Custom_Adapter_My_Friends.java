package com.enigmess;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class Custom_Adapter_My_Friends extends BaseAdapter {

	LayoutInflater mInlfater;
	Context context;
	ArrayList<String>user_name=new ArrayList<String>();
	ArrayList<String>imageList=new ArrayList<String>();
	ArrayList<String>useridlist=new ArrayList<String>();
	ImageButton yes,no;
	int index;
	String message;
	private Cache mCache;
	Dialog   dialog;
	public Custom_Adapter_My_Friends(Context context, int resource, ArrayList<String> user_name,ArrayList<String> imageList,ArrayList<String>useridlist) 
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

			vi =  mInlfater.inflate(R.layout.item_myfrd, null);

			holder.name = (TextView) vi.findViewById(R.id.textView_caption);
			holder.Img = (ImageView) vi.findViewById(R.id.imageView_mockgalleryItem);
			//holder.btnAdd=(ImageView)vi.findViewById(R.id.ic_arrow);
			holder.delete_friend=(ImageView)vi.findViewById(R.id.delete_friend);
			

			dialog = new Dialog(((Activity)context));
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			dialog.setContentView(R.layout.popup_suredelete);

			
			
			vi.setTag(holder);
		}
		else 
		{
			holder = (ViewHolder) vi.getTag();
		}


		holder.name.setText(user_name.get(position));
		mCache.loadBitmap(context,imageList.get(position), holder.Img);  
		
		
		holder.name.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				System.out.println("Params " + position);
				
				
				 Walk_theme.friend_id=useridlist.get(position);
				 System.out.println("idddddddddd " +  Walk_theme.friend_id);
			
				 
				 Intent i = new Intent(context.getApplicationContext(), Send_Msg.class);
					i.putExtra("username",user_name.get(position));
					i.putExtra("pic",imageList.get(position));
					i.putExtra("id", useridlist.get(position));
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

					context.getApplicationContext().startActivity(i);
				 
				 
				 
			}
		});

		  
		holder.delete_friend.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				System.out.println("Params " + position);
				
				
				 Walk_theme.friend_id=useridlist.get(position);
				 System.out.println("idddddddddd " +  Walk_theme.friend_id);
			
				 
				 dialog.show();
					yes=(ImageButton)dialog.findViewById(R.id.yes);
					
					yes.setOnClickListener(new OnClickListener()
					{
						@Override
						public void onClick(View v)
						{ 
							
							if(v== yes)
							{
								
							
				 Uploade fetchData = new Uploade(context);
					fetchData.execute("http://gamestaxi.webfactional.com/gametaxi/api/delete_friend.php?friend_id="+ Walk_theme.friend_id+"&user_id="+Walk_theme.userid);
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
							
							if(TextUtils.equals(status, "success"))
							{	
								//reldecrypted_msg.setVisibility(View.INVISIBLE);
								Toast.makeText((Activity)context, "Friend Deleted", Toast.LENGTH_LONG).show();
								
								
				                Intent i = new Intent(context, My_Friends.class);
				                
				                
				            	((Activity)context).finish();
				               
				                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

				                context.getApplicationContext().startActivity(i);
								
						      // ((Activity)context).finish();
								
							}
							else
							{
								//reldecrypted_msg.setVisibility(View.VISIBLE);
							
								
							}
							

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
						}
					});
				 
					no=(ImageButton)dialog.findViewById(R.id.no);
					no.setOnClickListener(new OnClickListener()
					{
						@Override
						public void onClick(View v)
						{ 
							 dialog.dismiss();
							
						}
					});
				 
			}
		});

		
		
		//	holder.number.setText(user_number.get(position));
		index=position;

		System.out.println("positin"+position);


		return vi;

	}
	static class ViewHolder
	{
	ImageView delete_friend;
		TextView name;
		ImageView Img;
		ImageView btnAdd;
	}
}
