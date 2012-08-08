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
import android.widget.ImageButton;
import android.widget.TextView;
public class MainScreen extends Activity {
	private ImageButton gglbutton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
     	super.onCreate(savedInstanceState);
    	setContentView(R.layout.mainscreen);
    	TextView t2 = null;
    	t2=new TextView(this); 
    	
        t2=(TextView)findViewById(R.id.Intro2); 
        
    	
    	TextView t1 = null;
    	t1=new TextView(this); 
    	
        t1=(TextView)findViewById(R.id.Intro1); 
        
    	TextView t = null;
    	t=new TextView(this); 
    	
        t=(TextView)findViewById(R.id.Intro); 
        t.setText("newssup Get the latest news update for the channels of your choice. Top Stories, Business, Sports update all at single place. Take  advantage  of speech capability.                          Powered by Google, Yahoo and CNN RSS feeds"); 
   
	gglbutton = (ImageButton) findViewById(R.id.button1);
    gglbutton.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
             Intent myIntent = new Intent(v.getContext(), NewsMenu.class);
          	 startActivityForResult(myIntent, 0);
          	 MainScreen.this.finish();
        }
    });
    
}
}