package com.enigmess;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Custom_Adapter_Messages extends ArrayAdapter<String>
{
	ArrayList<String> name = new ArrayList<String>();
	ArrayList<String> no = new ArrayList<String>();
	ArrayList<String> photo = new ArrayList<String>();
	ArrayList<String> messages = new ArrayList<String>();
	ArrayList<String> message_ids = new ArrayList<String>();
	Context context;
	static LayoutInflater vi = null;
	ProgressDialog progressDialog;
	Bitmap bitmap ;
	private Cache mCache;
	public Custom_Adapter_Messages(Messages context, int resource,ArrayList<String> Name,ArrayList<String> picture,ArrayList<String> messages,ArrayList<String> msgidlist) 
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
		this.photo = picture;
		this.messages=messages;

		this.message_ids=msgidlist;
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
			vi = Custom_Adapter_Messages.vi.inflate(R.layout.item_mockgallery, null);
        	holder.Name = (TextView) vi.findViewById(R.id.textView_caption);
        	
        	holder.messages=(TextView)vi.findViewById(R.id.messages);
        	holder.messages.getPaint().setMaskFilter(new BlurMaskFilter(4, Blur.NORMAL));
        	
        	
        	 String fontPath ="fonts/LEDLIGHT.ttf";
   		  Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
   		  
   		holder.messages.setTypeface(tf);
   		
   		//holder.Name.setTypeface(tf);
        	
        	holder.messages.getPaint().setShadowLayer(10, 21, 50, Color.BLACK);
        	
		//	holder.Name.getPaint().setMaskFilter(new BlurMaskFilter(5, Blur.NORMAL));
		//	holder.Name.getPaint().setShadowLayer(10, 21, 2, Color.BLACK);
			
			
			holder.pic=(ImageView)vi.findViewById(R.id.imageView_mockgalleryItem);
        	holder.decrypte=(Button)vi.findViewById(R.id.decrypte);
			holder.decrypte.setOnClickListener(new View.OnClickListener()
			  {
				
				
		            @Override
		            public void onClick(View view)
		            {
		               
		            	Walk_theme.message_id=message_ids.get(position);
		            	
		            	
		            	
		            	System.out.println("msgid"+Walk_theme.message_id);
		           
		            	
		                Intent i = new Intent(context, Enter_Pin.class);
		                
		                
		            	((Activity)context).finish();
		               
		                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		                context.getApplicationContext().startActivity(i);
		            }
		        });
		
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
		if(!(TextUtils.isEmpty(photo.get(position))))
		{
		
			mCache.loadBitmap(context,photo.get(position), holder.pic);  
		}

		return vi;
	}
	
	
	
	
}
