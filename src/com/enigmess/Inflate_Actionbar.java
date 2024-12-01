package com.enigmess;
import android.app.ActionBar;
import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class Inflate_Actionbar 
{

public static void customTitle(LayoutInflater inflater,final ActionBar actionBar, String title,int id,Context context)
	{
	View titleLayout ;
	
	
	if(!TextUtils.isEmpty(title))
	{
			
		titleLayout = (ViewGroup) inflater.inflate(R.layout.actionbar_settext, null);
		
		ImageView titleimgView2= (ImageView) titleLayout.findViewById(R.id.backbutton);
	 	titleimgView2.setImageResource(id);
		
	 	
        ImageView titleimgView = (ImageView) titleLayout.findViewById(R.id.addfrds);
 	    titleimgView.setImageResource(id);

        ImageView titleimgView1 = (ImageView) titleLayout.findViewById(R.id.logout);
 
       titleimgView1.setImageResource(id);


      TextView titleView = (TextView) titleLayout.findViewById(R.id.txtTitle);
 
       String fontPath ="fonts/Lato-Light.ttf";
      Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
      titleView.setTypeface(tf);
      titleView.setText(title);	
		}
	
	
 
 else
 {
	 titleLayout = (ViewGroup) inflater.inflate(R.layout.actionbar_settext, null);
		
 }
	
	actionBar.setCustomView(titleLayout, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);   
	actionBar.setDisplayShowCustomEnabled(true);
		
	}
}
