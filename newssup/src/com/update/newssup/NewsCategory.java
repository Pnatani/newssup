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
* https://github.com/Pnatani/newssup/blob/master/LICENSE.odt
*
* Following is the link for the repository: https://github.com/Pnatani/newssup
* 
* Written by Pratibha Natani <pnatani@pdx.edu>
* 
* References: (Detailed references at the end of the page)
* -Tutorial by IBM on building a mobile RSS Reader.
* https://www.ibm.com/developerworks/xml/tutorials/x-androidrss/
* This tutorial introduces XML handling on the Android platform. 
* It demonstrates  you'll build out the important elements which are relevant to XML handling and rendering on the Android platform.
*
* -Taken inspiration from below link for implementing XMLParsing:
* http://code.google.com/p/krvarma-android-samples/
* 
* -Application is powered by Google News API.  
* http://support.google.com/news/bin/answer.py?hl=en&answer=59255
* 
* -Application is powered by BBC News API. 
* http://www.bbc.co.uk/news/10628494
*
*/


package com.update.newssup;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.app.ListActivity;
import android.view.LayoutInflater;
import android.os.AsyncTask;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import com.update.newssup.NewsItem;
import com.update.newssup.NewsParser;


public class NewsCategory extends ListActivity implements OnInitListener{

		public String inputurl = null;
		public String newsprovider = null;
        private ArrayList<NewsItem> newslist = null;
        private NewsAdaptor newsadaptor = null;
        public NewsItem itemdata = null;
    	private TextToSpeech tts=null;
    	boolean tts_Active = false;
    	boolean on_Pause = false;
   	    private int Position = 0;
   	    private ImageButton backbutton;
   	    private ImageButton playbutton;
   	    private ImageButton stopbutton;
   	    
    @Override
    public void onCreate(Bundle savedInstanceState) {        
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.categorymenu);
        newslist = new ArrayList<NewsItem>();

        
        //extracting parameter passed from NewsMenu screen
        Intent i = getIntent();
        Bundle b = i.getExtras();
        inputurl = b.getString("ARRIVING_FROM");
        newsprovider = b.getString("NEWS_PROVIDER");
        
        tts = new TextToSpeech(this,
                this  //TextToSpeech.OnInitListener
                );
        

        TextView tv = (TextView)findViewById(R.id.newssource);  
        tv.setText(newsprovider);
        
        playbutton = (ImageButton) findViewById(R.id.playbutton);    
        playbutton.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
        	   tts_Active = true;
        	   Position=0;
        	   speech();
             }
        });  
        
        stopbutton = (ImageButton) findViewById(R.id.stopbutton);    
        stopbutton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        	   if (tts != null){
           		tts.stop();
           		tts_Active = false;
       	   				}
              
             }
        });  
   
        
        backbutton = (ImageButton) findViewById(R.id.backbutton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	  if (tts != null){
            		  	tts.stop();
                 		tts_Active = false;
             	   				}
            	  if (newsprovider == "Google News")
            	  	   { 
            	  		 Intent myIntent = new Intent(v.getContext(), NewsMenuggl.class);
            	  		 startActivityForResult(myIntent, 0);
            	  		 }
            	  else if (newsprovider == "BBC News")
            		   { 
            		  	 Intent myIntent1 = new Intent(v.getContext(), NewsMenubbc.class);
            	  		 startActivityForResult(myIntent1, 0);
            		   }
     	  		 NewsCategory.this.finish();
            }
        });	
        new ExtractFeed().execute();    

     }
    
    
    public void onInit(int status) {
 
        if (status == TextToSpeech.SUCCESS) {
	        	tts_Active = true;
	        	{
	                playbutton.setEnabled(true);
	                Position=0;
	                speech();
	            }
	 
        } 
        else {
        		Log.e("TTS", "Initilization Failed!");
        }
 
    }
 
    private void speech() {
 
    	for (int i = 0; i < newslist.size(); i++)
        {
    		NewsItem a = newslist.get(i);
    		tts.speak(a.title, TextToSpeech.QUEUE_ADD, null);
    		Position = Position+1;
        }
    }
    
    
    @Override
        protected void onListItemClick(ListView list, View view, int pos, long id) {
                super.onListItemClick(list, view, pos, id);
                
                NewsItem newsdata = newslist.get(pos);
                
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(newsdata.link));
                
                startActivity(intent);
        }
    
    private class ExtractFeed extends AsyncTask<Void, Void, Void>
    {
        private ProgressDialog progress = null;
        		
				@Override
                protected Void doInBackground(Void... params) {
					 try
				        {
				           URL newsurl = new URL(inputurl);
				           SAXParserFactory factory = SAXParserFactory.newInstance();
				           SAXParser parser = factory.newSAXParser();
				           XMLReader xmlreader = parser.getXMLReader();
				           NewsParser theRssHandler = new NewsParser(newslist);
				           xmlreader.setContentHandler(theRssHandler);
				           InputSource inputstream = new InputSource(newsurl.openStream());
				           xmlreader.parse(inputstream);
				        }
				        catch (Exception ex)
				        {
				        	ex.printStackTrace();
				        }
					
                        newsadaptor = new NewsAdaptor(NewsCategory.this, R.layout.newstagsvw,newslist);
                        return null;
                }
                @Override
                protected void onPreExecute() {
                        progress = ProgressDialog.show(
                                        NewsCategory.this, null, "24/7 updates for you...");
                        
                        super.onPreExecute();
                }
                
                @Override
                protected void onPostExecute(Void result) {
                        setListAdapter(newsadaptor);
                        
                        progress.dismiss();
                        
                        super.onPostExecute(result);
                }
    }
    
    private class NewsAdaptor extends ArrayAdapter<NewsItem>{
        		private List<NewsItem> listitem = null;
        
        		//constructor
                public NewsAdaptor(Context cxt, int txtview, List<NewsItem> list) {
                        super(cxt, txtview, list);
                        this.listitem = list;
                }
               
               public View getView(int inpos, View inview, ViewGroup ingroup) {
                        View view = inview;
                        itemdata = listitem.get(inpos);
                        if(view == null)
                        {
                                LayoutInflater layinf = (LayoutInflater)NewsCategory.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                view = layinf.inflate(R.layout.newstagsvw, null);
                        }
                        
                        
                        if(itemdata != null)
                        {
                                TextView title = (TextView)view.findViewById(R.id.txtTitle);
                                TextView date = (TextView)view.findViewById(R.id.txtDate);                                
                                title.setText(itemdata.title);
                                date.setText("on " + itemdata.date);
                        }
                        
                        return view;
                } 
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
* 
* 3) Application is powered by Google News API.  
* http://support.google.com/news/bin/answer.py?hl=en&answer=59255
* In an effort to provide our users with easier access to updates about news topics 
* of interest to them, we offer RSS feeds of Google News. Feeds are available for 
* any section of Google News (such as Sports or Business), for your Google News 
* search results, or for your customized Google News page. When you subscribe to a 
* Google News feed using a feed reader, you'll receive a regularly updated summary 
* of relevant news articles along with links to the full articles. 
* Google News feeds are available in RSS 2.0 format.
* 
* 4) Application is powered by BBC News API. 
* http://www.bbc.co.uk/news/10628494
* BBC News provides feeds for both the desktop website as well as for our mobile 
* site and the most popular feeds are listed here.
*/
