package com.enigmess;
import java.util.ArrayList;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Custom_Adaptoer_Send_Msg extends ArrayAdapter<String>
{
	ArrayList<String> name = new ArrayList<String>();
	ArrayList<String> messages = new ArrayList<String>();
	Context context;
	static LayoutInflater vi = null;
	ProgressDialog progressDialog;
	Bitmap bitmap ;
	private Cache mCache;
	public  Custom_Adaptoer_Send_Msg(Send_Msg context, int resource,ArrayList<String> Name,ArrayList<String> messages) 
	{
		super(context, resource);
		// TODO Auto-generated constructor stub
		
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
		this.name = Name;
	
		this.messages=messages;

	
		vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() 
	{
		return name.size();
	}

	public static class ViewHolder 
	{

		TextView Name, No,messages;
		ImageView pic;
		Button decrypte;
		
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		View vi = convertView;
		final ViewHolder holder;
		if (convertView == null) 
		{
			holder = new ViewHolder();
			vi = Custom_Adapter_Messages.vi.inflate(R.layout.item_send_msg, null);
        	holder.Name = (TextView) vi.findViewById(R.id.textView_msg);
        	
        	holder.messages=(TextView)vi.findViewById(R.id.datetime);
		
			vi.setTag(holder);
		}

		else
		{
			holder = (ViewHolder) convertView.getTag();
		}

		if (name.get(position) != null) 
		{
			holder.Name.setText(name.get(position));
		}
		
		
		if (messages.get(position) != null) 
		{
			holder.messages.setText(messages.get(position));
		}
		
		return vi;
	}
	
	
	
	
}
