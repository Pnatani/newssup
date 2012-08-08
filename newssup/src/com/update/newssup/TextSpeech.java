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
import android.speech.tts.TextToSpeech;

import android.speech.tts.TextToSpeech.OnInitListener;

import java.util.ArrayList;
import java.util.Locale;
import android.app.Activity;
import android.os.Bundle;


public class TextSpeech extends Activity implements OnInitListener {
	private TextToSpeech tts;
    private ArrayList<NewsItem> vnewslist = null;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categorymenu);
        tts = new TextToSpeech(this, this);
        vnewslist = getIntent().getParcelableArrayListExtra ("passinglist");

    }   
    public void onInit(int arg0) {

    	tts.setLanguage(Locale.US);
//    	String speechText = newslist; //"Mexican opposition candidate Enrique Pena Nieto's campaign team claimed victory in the country's presidential election on Sunday after exit polls showed him winning by a comfortable margin.";
//    	tts.speak(speechText, TextToSpeech.QUEUE_FLUSH, null);

    	for (int i = 0; i < vnewslist.size(); i++)
        {
         NewsItem a = vnewslist.get(i);
         tts.speak(a.title, TextToSpeech.QUEUE_ADD, null);
        }
    	
    	
    	}

}