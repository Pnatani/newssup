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
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import com.update.newssup.NewsItem;
import com.update.newssup.NewsParser;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
//added
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
//end

public class NewsCategory extends ListActivity implements OnInitListener{

		public String inputurl = null;
        private ArrayList<NewsItem> newslist = null;
        private ArrayList<NewsItem> speaktag = null;
        private NewsAdaptor newsadaptor = null;
        public NewsItem itemdata = null;
    	private TextToSpeech tts=null;
 //   	private Button soundbutton;
    	boolean tts_Active = false;
    	boolean on_Pause = false;
 //   	private Button stopbutton;
 //   	private Button backbutton;
 //   	private Button pausebutton;
 //   	private Button resumebutton;
   	    private int Position = 0;
   	    private ImageButton backbutton;
   	    private ImageButton soundbutton;
   	    private ImageButton stopbutton;
   	    private ImageButton pausebutton;
   	 	private ImageButton resumebutton;
   	 	
   	 
    @Override
    public void onCreate(Bundle savedInstanceState) {        
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.categorymenu);
        newslist = new ArrayList<NewsItem>();

       
        //extracting parameter passed from NewsMenu screen
        Intent i = getIntent();
        Bundle b = i.getExtras();
        inputurl = b.getString("ARRIVING_FROM");
        
        tts = new TextToSpeech(this,
                this  //TextToSpeech.OnInitListener
                );
        
     
        soundbutton = (ImageButton) findViewById(R.id.soundbutton);    
        soundbutton.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
           /* 	// fill parceable and launch activity
                Intent intent = new Intent().setClass(getBaseContext (), TextSpeech.class);
                ArrayList <NewsItem> v_newslist = new ArrayList <NewsItem>();

                //filling v_newslist list before passing it to another activity
                for (int i = 0; i < newslist.size(); i++)
                    v_newslist.add (newslist.get(i));

                intent.putParcelableArrayListExtra ("passinglist", v_newslist);
                startActivity(intent);
            */
        	   tts_Active = true;
        	   Position=0;
        	   speakout();
             }
        });  
        
        stopbutton = (ImageButton) findViewById(R.id.stopbutton);    
        stopbutton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {


        	   if (tts != null){
           		tts.stop();
           	//	tts.shutdown(); //should i add it?
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
                 	   Intent myIntent = new Intent(v.getContext(), NewsMenu.class);
              	//   Intent it=new Intent(NewsMenu.this,NewsCategory.class);
              	   startActivityForResult(myIntent, 0);
              	   NewsCategory.this.finish();
            }
        });
        
        
        
  /*      pausebutton = (ImageButton) findViewById(R.id.pausebutton);    
        pausebutton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {

        	   if (tts != null){
           		tts.stop();
           		tts_Active = false;
           		on_Pause = true;
       	   				}
             }
        });
        
        resumebutton = (ImageButton) findViewById(R.id.resumebutton);    
        resumebutton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		
        	if (on_Pause == true){
        	   on_Pause = false;
        	   for (int i = Position-1; i < newslist.size(); i++)
               {
           		NewsItem a = newslist.get(i);
           		tts.speak(a.title, TextToSpeech.QUEUE_ADD, null);
           		Position = Position+1;
               }
        	}
        	   //speakout();
           }
        });*/
     
        
        new ExtractFeed().execute();    

     }
    
    
    public void onInit(int status) {
 
        if (status == TextToSpeech.SUCCESS) {
	        	tts_Active = true;
	         
	        	/* int result = tts.setLanguage(Locale.US);
	 
	            if (result == TextToSpeech.LANG_MISSING_DATA
	                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
	                Log.e("TTS", "This Language is not supported");
	            } else */
	        	{
	                soundbutton.setEnabled(true);
	                Position=0;
	                speakout();
	            }
	 
        } 
        else {
        		Log.e("TTS", "Initilization Failed!");
        }
 
    }
 
    private void speakout() {
 
//    	String[] str = null;
    	for (int i = 0; i < newslist.size(); i++)
        {
    		NewsItem a = newslist.get(i);
    		tts.speak(a.title, TextToSpeech.QUEUE_ADD, null);
    		Position = Position+1;
        }
    	//should call shutdown?
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
                   //commented      NewsItem itemdata = listitem.get(inpos);
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