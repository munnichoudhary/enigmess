  package com.enigmess;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class Sign_Up extends Activity implements OnClickListener,UploadListener
{
	ImageButton create_ac_btn;
	String resultString = "Could not get response";
	EditText name,user_name,edit_text_email,edit_text_password,edit_text_confirm_password,edit_text_enterpin;
	String strname,u_name,email,password,enterpin;
	TextView typename,minchar;
	String userid_sign_up;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_up);
		 ActionBar actionBar = getActionBar();
			actionBar.hide();
			
			setGUI();
	}
	
	private void setGUI()
	{
		name=(EditText)findViewById(R.id.edit_text_name);
		
		
		user_name=(EditText)findViewById(R.id.edit_text_uname);
		
		
		edit_text_email=(EditText)findViewById(R.id.edit_text_email);
		
		
		edit_text_password=(EditText)findViewById(R.id.edit_text_password);
		
		
		edit_text_password=(EditText)findViewById(R.id.edit_text_password);
		
		
		edit_text_enterpin=(EditText)findViewById(R.id.edit_text_enterpin);
		
		
		create_ac_btn=(ImageButton)findViewById(R.id.create_ac_btn);
		
		
		typename=(TextView)findViewById(R.id.type_name);
		minchar=(TextView)findViewById(R.id.min_char);
		
		 String fontPath ="fonts/Lato-Light.ttf";
		  Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
		  
		  name.setTypeface(tf);
		  user_name.setTypeface(tf);
		  edit_text_email.setTypeface(tf);
		  edit_text_password.setTypeface(tf);
		  edit_text_enterpin.setTypeface(tf);
		  
		  String fontPath1 ="fonts/Lato-Regular.ttf";
		  Typeface tf1 = Typeface.createFromAsset(getAssets(), fontPath1);
		  
		  typename.setTypeface(tf1);
		  minchar.setTypeface(tf1);
		  
		create_ac_btn.setOnClickListener(this);
	}
	@Override
	public void onClick(View v)
	{
		getValues();
        upload();
        
		
	}
	
	private void getValues()
	{
		strname= name.getText().toString();	
		u_name=user_name.getText().toString();
		email=edit_text_email.getText().toString();
		
	
		password=edit_text_password.getText().toString();
		
		
		enterpin=edit_text_enterpin.getText().toString();
		
	
		try
		{
			strname=URLEncoder.encode(strname, "UTF-8");
			Walk_theme.strname=strname;
			u_name=URLEncoder.encode(u_name, "UTF-8");
			Walk_theme.u_name=u_name;
			email=URLEncoder.encode(email, "UTF-8");
			Walk_theme.email=email;
			password=URLEncoder.encode(password, "UTF-8");
			enterpin=URLEncoder.encode(enterpin, "UTF-8");
			Walk_theme.enterpin=enterpin;
			//Intent i = new Intent(Sign_Up.this, Enter_Pin.class);
			
			
		}
		
		catch(UnsupportedEncodingException e)
		{
			 String response = "Could not convert data";
			 Pass_Url.showMessage(Sign_Up.this, response);   
			 e.printStackTrace();	
		}
	}

	@Override
	public void processFinish(String output) 
	{
		// TODO Auto-generated method stub
		JSONObject resultObject=null;
		try{

		 resultObject = new JSONObject(output);
		 resultString = resultObject.getString("response");
		 userid_sign_up = resultObject.getString("userid");
			Walk_theme.userid_sign_up  =  userid_sign_up;
		// Walk_theme.userid  =  userid;
		 System.out.println("resultString"+output);
		 System.out.println("userid"+userid_sign_up );
		}
		catch(JSONException e)
		{
			e.printStackTrace();
		}
		Pass_Url.showMessage(Sign_Up.this, resultString);
		if(TextUtils.equals(resultString, "success"))
	    {
			System.out.println("success"+resultString);
			 Intent myIntent = new Intent(Sign_Up.this, Sign_In.class);       
             startActivity(myIntent);
             Sign_Up.this.finish();
	    }
		
		
	}
	
	@Override
    protected void onDestroy() 
	{     
        super.onDestroy();    
    }
	
	 private void upload()
     {
		 
    	 getValues();
    	  if(TextUtils.isEmpty(strname) || TextUtils.isEmpty(u_name) || TextUtils.isEmpty(email)|| TextUtils.isEmpty(password)||TextUtils.isEmpty(enterpin))
  		    {
  			    Pass_Url.showEmptyMessage(Sign_Up.this, "Parsing section");
  		    }
    	  
    	  else
    	  {
    		  Uploade uploadData = new Uploade(Sign_Up.this);
    	 	     uploadData.response = Sign_Up.this;
    	 	
    			      String url = Pass_Url.api + "register.php?name=" + strname + "&user_name=" +u_name +"&password="+password +"&email="+email +"&pin="+enterpin;
    			      System.out.println("Url"+url);
    			     
    		    		uploadData.execute(url);
    	 }  	
     }
	 
}
