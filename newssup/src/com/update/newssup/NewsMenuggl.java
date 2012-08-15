/* 
* “newssup”- Open Source Android application which shows news updates using 
* Google and BBC News API. User can read as well as listen to the news.     
*
* Copyright (C) 2012  Pratibha Natani
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>.
*
* Please see the file “License” in this distribution for the license terms. Link is:
* https://github.com/Pnatani/newssup/License
*
* Following is the link for the repository: https://github.com/Pnatani/newssup
* 
* Written by Pratibha Natani <pnatani@pdx.edu>
* 
* References:
* -Tutorial by IBM on building a mobile RSS Reader.
* https://www.ibm.com/developerworks/xml/tutorials/x-androidrss/
* This tutorial introduces XML handling on the Android platform. 
* It demonstrates  you'll build out the important elements which are relevant to XML handling and rendering on the Android platform.
*
* -Taken inspiration from below link for implementing XMLParsing:
* http://code.google.com/p/krvarma-android-samples/
*
*/

package com.update.newssup;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
public class NewsMenuggl extends Activity {
        
    @Override
    public void onCreate(Bundle savedInstanceState) {
     	super.onCreate(savedInstanceState);
    	setContentView(R.layout.mainmenuggl);
    
    	final ImageButton topnews = (ImageButton) findViewById(R.id.topnews);
    	final ImageButton worldnews = (ImageButton) findViewById(R.id.worldnews);    
    	final ImageButton usnews = (ImageButton) findViewById(R.id.usnews);    
    	final ImageButton busnews = (ImageButton) findViewById(R.id.busnews);    
    	final ImageButton technews = (ImageButton) findViewById(R.id.technews);    
    	final ImageButton sportnews = (ImageButton) findViewById(R.id.sportnews);
    	final ImageButton healthnews = (ImageButton) findViewById(R.id.healthnews);
    	final ImageButton spotlight = (ImageButton) findViewById(R.id.spotlight);
    	final ImageButton backbutton = (ImageButton) findViewById(R.id.backbutton);    
    	
    	topnews.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	// Perform action on click
            	Intent myIntent = new Intent(v.getContext(), NewsCategory.class);
            	myIntent.putExtra("ARRIVING_FROM", "http://news.google.com/news?pz=1&cf=all&ned=us&hl=en&output=rss");
            	myIntent.putExtra("NEWS_PROVIDER", "Google News");            	
            	startActivityForResult(myIntent, 0);
             }
        });   
        
    	worldnews.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	// Perform action on click
            	Intent myIntent = new Intent(v.getContext(), NewsCategory.class);
            	myIntent.putExtra("ARRIVING_FROM", "http://news.google.com/news?pz=1&cf=all&ned=us&hl=en&topic=w&output=rss");
            	myIntent.putExtra("NEWS_PROVIDER", "Google News");
            	startActivityForResult(myIntent, 0);
             }
        });
        
    	usnews.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	// Perform action on click
            	Intent myIntent = new Intent(v.getContext(), NewsCategory.class);
            	myIntent.putExtra("ARRIVING_FROM", "http://news.google.com/news?pz=1&cf=all&ned=us&hl=en&topic=n&output=rss");
            	myIntent.putExtra("NEWS_PROVIDER", "Google News");
            	startActivityForResult(myIntent, 0);
             }
        });
       
    	busnews.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	// Perform action on click
            	Intent myIntent = new Intent(v.getContext(), NewsCategory.class);
            	myIntent.putExtra("ARRIVING_FROM", "http://news.google.com/news?pz=1&cf=all&ned=us&hl=en&topic=b&output=rss");
            	myIntent.putExtra("NEWS_PROVIDER", "Google News");
            	startActivityForResult(myIntent, 0);
             }
        });
        
    	technews.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	// Perform action on click
            	Intent myIntent = new Intent(v.getContext(), NewsCategory.class);
            	myIntent.putExtra("ARRIVING_FROM", "http://news.google.com/news?pz=1&cf=all&ned=us&hl=en&topic=tc&output=rss");
            	myIntent.putExtra("NEWS_PROVIDER", "Google News");
            	startActivityForResult(myIntent, 0);
             }
        });
        
      
        
    	sportnews.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	// Perform action on click
            	Intent myIntent = new Intent(v.getContext(), NewsCategory.class);
            	myIntent.putExtra("ARRIVING_FROM", "http://news.google.com/news?pz=1&cf=all&ned=us&hl=en&topic=s&output=rss");
            	myIntent.putExtra("NEWS_PROVIDER", "Google News");
                startActivityForResult(myIntent, 0);
             }
        });
        
    	healthnews.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	// Perform action on click
            	Intent myIntent = new Intent(v.getContext(), NewsCategory.class);
            	myIntent.putExtra("ARRIVING_FROM", "http://news.google.com/news?pz=1&cf=all&ned=us&hl=en&topic=m&output=rss");
            	myIntent.putExtra("NEWS_PROVIDER", "Google News");
            	startActivityForResult(myIntent, 0);
             }
        });
    	
    	
    	spotlight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	// Perform action on click
            	Intent myIntent = new Intent(v.getContext(), NewsCategory.class);
            	myIntent.putExtra("ARRIVING_FROM", "http://news.google.com/news?pz=1&cf=all&ned=us&hl=en&topic=ir&output=rss");
            	myIntent.putExtra("NEWS_PROVIDER", "Google News");
                startActivityForResult(myIntent, 0);
             }
        });
    	
        backbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {            	
              	   Intent myIntent = new Intent(v.getContext(), MainScreen.class);
              	   startActivityForResult(myIntent, 0);
              	   NewsMenuggl.this.finish();
            }
        });
        
        
}      
}

/*
*  Reference details:
*  
* 1) https://www.ibm.com/developerworks/xml/tutorials/x-androidrss/
* This tutorial introduces XML handling on the Android platform. 
* This tutorial is explains following sections:
* RSS basics
* Android RSS reader application architecture
* Fetching and parsing XML data with SAX
* Rendering RSS data in Android
* 
* 2) http://code.google.com/p/krvarma-android-samples/
* This site has various sample Android applications which have helped me in coding
* in Android, including RSS Parsing. 
*/
