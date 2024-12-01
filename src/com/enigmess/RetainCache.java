package com.enigmess;

public class RetainCache 
{
	private static RetainCache sSingleton;
    public Cache mRetainedCache;

    public static RetainCache getOrCreateRetainableCache()
    {
        if (sSingleton == null)
         {
        	sSingleton = new RetainCache();
         }
        return sSingleton;
    }
    
} 
