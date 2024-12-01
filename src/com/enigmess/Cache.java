package com.enigmess;

import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

public class Cache {
	private LruCache<String, Bitmap> mBitmapCache;
	private ArrayList<String> mCurrentTasks;
	private int mMaxWidth;
	private int mMaxHeight;
	
	public Cache(int size, int maxWidth, int maxHeight) {
		mMaxWidth = maxWidth;
		mMaxHeight = maxHeight;
		
		mBitmapCache = new LruCache<String, Bitmap>(size) {
			protected int sizeOf(String key, Bitmap b) {
			   // Assuming that one pixel contains four bytes.
	           return b.getHeight() * b.getWidth() * 4;
			}
		};
		
		mCurrentTasks = new ArrayList<String>();	
	}
	
	private void addBitmapToCache(String key, Bitmap bitmap) {
        if (getBitmapFromCache(key) == null) {
            mBitmapCache.put(key, bitmap);
        }
    }

    private Bitmap getBitmapFromCache(String key) {    	
    			Bitmap b = mBitmapCache.get(key);
    			return getCircleBitmap(b);
    }
    
    /**
     * Gets a bitmap from cache. <br/><br/>
     * If it is not in cache, this method will:
     * <br/>
     * <b>1:</b> check if the bitmap url is currently being processed in the
     * BitmapLoaderTask and cancel if it is already in a task (a control to see
     * if it's inside the currentTasks list).
     * <br/>
     * <b>2:</b> check if an internet connection is available and continue if so.
     * <br/>
     * <b>3:</b> download the bitmap, scale the bitmap if necessary and put it into
     * the memory cache.
     * <br/>
     * <b>4:</b> Remove the bitmap url from the currentTasks list.
     * <br/>
     * <b>5:</b> Notify the ListAdapter.
     * 
     * @param mainActivity - Reference to activity object, in order to
     * call notifyDataSetChanged() on the ListAdapter.
     * @param imageKey - The bitmap url (will be the key).
     * @param imageView - The ImageView that should get an
     * available bitmap or a placeholder image.
     * @param isScrolling - If set to true, we skip executing more tasks since
     * the user probably has flinged away the view.
     */

    public void loadBitmap(Context context, String string, ImageView epicture) {
    	 final Bitmap bitmap = getBitmapFromCache(string); 
         
         if (bitmap != null) {
             epicture.setImageBitmap(bitmap);
         } else {
        	 epicture.setImageResource(R.drawable.ic_launcher);
 		    	BitmapLoaderTask task = new BitmapLoaderTask(string);
 			    task.execute();
         	
     	} 
		System.out.println("PATH"+string);
	}
    
    

    private class BitmapLoaderTask extends AsyncTask<Void, Void, Bitmap> {
   // 	private EventAdapter mListAdapter;
    	private String mImageKey;
    	
    	public BitmapLoaderTask(String imageKey) {
    		mImageKey = imageKey;
    	}
    	
    	@Override
    	protected void onPreExecute() {
    		mCurrentTasks.add(mImageKey);
    	}

        @Override
        protected Bitmap doInBackground(Void... params) {
        	Bitmap b = null;
        	try {
        		URL url = new URL(mImageKey);
        	    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        	    connection.connect();
        	    b = BitmapFactory.decodeStream(connection.getInputStream());
    			
        	    if (b != null) {
	                int width = b.getWidth();
	    			int height = b.getHeight();
	    			
	    			if (width >= mMaxWidth || height >= mMaxHeight) {
		    			while (true) {
		    			    if (width <= mMaxWidth || height <= mMaxHeight) {
		    			        break;
		    			    }
		    			        width /= 2;
		    			    	height /= 2;
		    			}
		    			b = Bitmap.createScaledBitmap(b, width, height, false);
	    			}
	    			connection.disconnect();
        	    	
	        	    addBitmapToCache(mImageKey, b); 
	        	    return b;
        	    }
        	    return null;
        	} catch (IOException e) {
        		if (e != null) {
        			e.printStackTrace();
        		}
        		return null;
        	}
        }

        @Override
        protected void onPostExecute(Bitmap param) {
        	mCurrentTasks.remove(mImageKey);
        	if (param != null) {
        		//mListAdapter.notifyDataSetChanged();
        	} 
        }
    }

	public void clear() {
		mBitmapCache.evictAll();
	}
	
	private Bitmap getCircleBitmap(Bitmap bitmap) {
		Bitmap output = null;
		
		if(bitmap != null)
		{
			output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getWidth(), Config.ARGB_8888);

			    Canvas canvas = new Canvas(output);

			    final int color = 0xff424242;
			    final Paint paint = new Paint();
			    final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

			    float r = 0;

			    if (bitmap.getWidth() > bitmap.getHeight()) {
			        r = bitmap.getHeight() / 2;
			    } else {
			        r = bitmap.getWidth() / 2;
			    }

			    paint.setAntiAlias(true);
			    canvas.drawARGB(0, 0, 0, 0);
			    paint.setColor(color);
			    canvas.drawCircle(r, r, r, paint);
			    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			    canvas.drawBitmap(bitmap, rect, rect, paint);
			    
		}
		return output;
	}

	
	}
