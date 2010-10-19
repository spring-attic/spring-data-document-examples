package com.springsource.myrestaurants.controllers;

import java.util.concurrent.RejectedExecutionException;

import android.content.Context;
import android.content.Intent;

public class NavigationManager {
		
	public static boolean startActivity(Context context, Class<?> activity) {
		try {				
			Intent intent = new Intent();
		    intent.setClass(context, activity);
		    context.startActivity(intent);
		} catch (RejectedExecutionException ex) { }
		
	    return true;
	}	
}
