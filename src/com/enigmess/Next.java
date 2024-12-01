package com.enigmess;
import java.net.URL;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
public class Next extends Activity 
{
	
	URL image;
	String name,profileurl;
	ImageView img;
	TextView txtname;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.next);	
		Intent intent=getIntent();
		name=intent.getExtras().getString("name");
		profileurl=intent.getExtras().getString("Image");
		System.out.println("ImageURL"+profileurl+"Name"+name);
		img=(ImageView)findViewById(R.id.profilepic);
		txtname=(TextView)findViewById(R.id.lblEmail);
		
	}

}
