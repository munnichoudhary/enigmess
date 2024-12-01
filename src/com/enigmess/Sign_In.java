package com.enigmess;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.FacebookException;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.OnErrorListener;

public class Sign_In extends Activity
{
	private String TAG = "Sign_In";
	String name;
	URL imgUrl;
	EditText txtemail,edit_email_forget;
	EditText txtpass;
	TextView forgot;
	String  userid;
	Dialog   dialog;
	ImageButton submit_btn_forget;
	SharedPreferences.Editor editit;
	SharedPreferences SP;
	public static String PREFS_NAME="settings";
	String emailid;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_in);


		ActionBar actionBar = getActionBar();
		actionBar.hide();


		dialog = new Dialog(Sign_In.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		dialog.setContentView(R.layout.forget_password);


		RelativeLayout layone= (RelativeLayout)findViewById(R.id.form_login);

		forgot=(TextView)findViewById(R.id.forgot);
		txtemail=(EditText)findViewById(R.id.edit_text_username);
		txtpass=(EditText)findViewById(R.id.edit_text_pass);
		LoginButton authButton = (LoginButton) findViewById(R.id.authButton);

		ImageButton signin = (ImageButton)findViewById(R.id.btn_login);
		ImageButton signup = (ImageButton)findViewById(R.id.imgbtn_sign_up);


		String fontPath ="fonts/Lato-Light.ttf";
		Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);

		txtemail.setTypeface(tf);
		txtpass.setTypeface(tf);

		String fontPath1 ="fonts/Lato-Regular.ttf";
		Typeface tf1 = Typeface.createFromAsset(getAssets(), fontPath1);

		forgot.setTypeface(tf1);
		forgot.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{

				dialog.show();

				edit_email_forget = (EditText) dialog.findViewById(R.id.edit_email_forget);
				submit_btn_forget=(ImageButton)dialog.findViewById(R.id.submit_btn_forget);
				submit_btn_forget.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{ 
                    	if(v== submit_btn_forget)
						{
							if((TextUtils.isEmpty( edit_email_forget.getText().toString())))
							{

								Toast.makeText(Sign_In.this, "Fill in your login details", Toast.LENGTH_LONG).show();
							}

							else
							{
								FetchData fetchData = new FetchData(Sign_In.this);
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
												Intent intent = new Intent(Sign_In.this,new Sign_In().getClass());
												startActivity(intent);
											}
											else
											{
												Toast.makeText(Sign_In.this, "not fetching data", Toast.LENGTH_LONG).show();
											}
										}
										catch(JSONException je)
										{

											Toast.makeText(Sign_In.this, "Could not fetch data", Toast.LENGTH_LONG).show();
										}

									}
								};
								try
								{
									fetchData.execute("http://gamestaxi.webfactional.com/gametaxi/api/forget_pass.php?email="+URLEncoder.encode(edit_email_forget.getText().toString(),"UTF-8"));
								}
								catch(UnsupportedEncodingException je)
								{

									Toast.makeText(Sign_In.this, "Could not check your details", Toast.LENGTH_LONG).show();

								}
							}
						}
					}
				});
			}
		});

		signup.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				Intent myIntent = new Intent(Sign_In.this, Sign_Up.class);       
				startActivity(myIntent);
			}
		});

		signin.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				if((TextUtils.isEmpty(txtemail.getText().toString())) || (TextUtils.isEmpty(txtpass.getText().toString())))
				{

					Toast.makeText(Sign_In.this, "Fill in your login details", Toast.LENGTH_LONG).show();
				}

				else
				{

					FetchData fetchData = new FetchData(Sign_In.this);
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
									userid=  obj.getString("user_id");	
									Walk_theme.userName = txtemail.getText().toString();
									Walk_theme.password = txtpass.getText().toString();
									Walk_theme.userid  =  userid;
									SP = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
									editit = SP.edit();
									editit.putString("username", Walk_theme.userName);                    
									editit.putString("password",Walk_theme.password);
									editit.putString("userid",Walk_theme.userid);
									editit.commit();
									System.out.println("hhh"+Walk_theme.userid);
									Intent intent = new Intent(Sign_In.this,new Messages().getClass());	
									startActivity(intent);
									Sign_In.this.finish();
								}
								else
								{
									Toast.makeText(Sign_In.this, "not fetching data", Toast.LENGTH_LONG).show();

								}
							}
							catch(JSONException je)
							{

								Toast.makeText(Sign_In.this, "Could not fetch data", Toast.LENGTH_LONG).show();
							}

						}
					};
					try
					{
						fetchData.execute("http://gamestaxi.webfactional.com/gametaxi/api/signin.php?email="+URLEncoder.encode(txtemail.getText().toString(),"UTF-8") +"&password="+ URLEncoder.encode(txtpass.getText().toString(),"UTF-8")+"&login=app");


					}
					catch(UnsupportedEncodingException je)
					{

						Toast.makeText(Sign_In.this, "Could not check your details", Toast.LENGTH_LONG).show();

					}
				}
			}


		});
		authButton.setBackgroundResource(R.drawable.login_fb_btn);
		authButton.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
		authButton.setOnErrorListener(new OnErrorListener()
		{


			@Override
			public void onError(FacebookException error)
			{
				Log.i(TAG, "Error " + error.getMessage());
			}
		});
		// set permission list, Don't foeget to add email
		authButton.setReadPermissions(Arrays.asList("public_profile","email"));
		// session state call back event
		authButton.setSessionStatusCallback(new Session.StatusCallback() 
		{




			@SuppressWarnings("deprecation")
			@Override
			public void call(Session session, SessionState state, Exception exception)
			{

				if (session.isOpened()) {
					Log.i(TAG,"Access Token"+ session.getAccessToken());
					Request.executeMeRequestAsync(session,
							new Request.GraphUserCallback() 
					{
						@Override
						public void onCompleted(GraphUser user,Response response)
						{
							if (user != null)
							{ 
								Log.i(TAG,"User ID "+ user.getId());
								Log.i(TAG,"Email "+ user.asMap().get("email"));
								
								
								Log.i(TAG,"Birthday "+ user.getBirthday());
								Log.i(TAG,"Link "+ user.getLink());
								Log.i(TAG,"Link "+ user.getName());
								name=user.getName();
								
								emailid = user.asMap().get("email")
										.toString();
								try
								{
									imgUrl = new URL("https://graph.facebook.com/"
											+ user.getId() + "/picture?type=large");

									FetchData fetchData = new FetchData(Sign_In.this);
									fetchData.response = new FetchListener() {

										@Override
										public void fetchFinish(String output) {
											

											System.out
													.println("fetchfinish"
															+ output);
											
											try {
												JSONObject obj = new JSONObject(output);
												String status = obj.getString("response");
												
												System.out.println("Response"+ status);

												if (TextUtils.equals(status,"success")) {
													Toast.makeText(Sign_In.this,"Responce"+ status,Toast.LENGTH_LONG).show();
													Walk_theme.userid = obj.getString("user_id");
												//	Walk_theme.Login_from = "fb";
													
													SP = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
													editit = SP.edit();
													editit.putString("userid",Walk_theme.userid);
													editit.commit();
                                                  System.out.println("fbuseid"+Walk_theme.userid );
													Intent intent = new Intent(Sign_In.this,Messages.class);
													startActivity(intent);
													Sign_In.this.finish();

												} 
												else
												{
													

												}
											} catch (JSONException je) 
											{
												Walk_theme.showMessage(Sign_In.this,"Could not fetch data");
											}

										}

									};
									try {

										fetchData.execute("http://gamestaxi.webfactional.com/gametaxi/api/signin.php?email="+URLEncoder.encode(emailid.toString(),"UTF-8")+"&login=facebook");

									
									} catch (UnsupportedEncodingException je) {
										Walk_theme.showMessage(Sign_In.this,"Could not check your details");
									}

								} catch (MalformedURLException e) {
									
									e.printStackTrace();
								}

							}
						}
					});
		}

	}
});
}
	public void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	}


	@Override
	protected void onDestroy()
	{
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
