/*
* newssup - is an open source Android application which collates news updates from various sources at a common place.
* The application provides a list of RSS feed providers to the user, e.g. Google News, Yahoo News etc. User can select the provider of his choice. The user is given an option of choosing various sections like  Top-Stories, World News etc under every RSS feed provider to see the news headlines.
* 
* Application written in Java.
* Application user Google News API currently.
*
* Following is the link for the repository: https://github.com/Pnatani/newssup
* 
* Written by Pratibha Natani <pnatani@pdx.edu>
* 
* References:
* http://www.ibm.com/developerworks/xml/tutorials/x-androidrss/.html
* This tutorial introduces XML handling on the Android platform. 
* It demonstrates  you'll build out the important elements which are relevant to XML handling and rendering on the Android platform.
*
*/
package com.update.newssup;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
public class NewsMenu extends Activity {
        
    @Override
    public void onCreate(Bundle savedInstanceState) {
     	super.onCreate(savedInstanceState);
    	setContentView(R.layout.mainmenu);
    
    	final Button button1 = (Button) findViewById(R.id.button1);
    	final Button button2 = (Button) findViewById(R.id.button2);    
    	
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	// Perform action on click
            	Intent myIntent = new Intent(v.getContext(), NewsCategory.class);
            	myIntent.putExtra("ARRIVING_FROM", "http://news.google.com/news?ned=us&topic=h&output=rss");
            	startActivityForResult(myIntent, 0);
             }
        });   
        
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	// Perform action on click
            	Intent myIntent = new Intent(v.getContext(), NewsCategory.class);
            	myIntent.putExtra("ARRIVING_FROM", "http://news.google.com/news?ned=us&topic=w&output=rss");
                startActivityForResult(myIntent, 0);
             }
        });   
        
}      
}