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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import android.view.View;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.app.ListActivity;
import android.view.LayoutInflater;
import android.os.AsyncTask;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import com.update.newssup.NewsItem;
//import com.update.newssup.NewsParser;
import com.update.newssup.NewsParser;
public class NewsCategory extends ListActivity {
		
		public String inputurl = null;
        private ArrayList<NewsItem> newsitem = null;
        private NewsAdaptor newsadaptor = null;
         
    @Override
    public void onCreate(Bundle savedInstanceState) {        
         newsitem = new ArrayList<NewsItem>();
         //added to resolve SuperNotCalledException issue
        	super.onCreate(savedInstanceState);
           	setContentView(R.layout.categorymenu);

        //extracting parameter passed from NewsMenu screen
        Intent i = getIntent();
        Bundle b = i.getExtras();
        inputurl = b.getString("ARRIVING_FROM");
        
        //logging activity for debugging
     /*   Log.v(inputurl, "activity created" );
        Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, inputurl, duration);
        toast.show();
     */
        new ExtractFeed().execute();    
     }
    
    @Override
        protected void onListItemClick(ListView list, View view, int pos, long id) {
                super.onListItemClick(list, view, pos, id);
                
                NewsItem newsdata = newsitem.get(pos);
                
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
				           NewsParser theRssHandler = new NewsParser(newsitem);
				           xmlreader.setContentHandler(theRssHandler);
				           InputSource inputstream = new InputSource(newsurl.openStream());
				           xmlreader.parse(inputstream);
				        }
				        catch (Exception ex)
				        {
				        	ex.printStackTrace();
				        }
					
                        newsadaptor = new NewsAdaptor(NewsCategory.this, R.layout.newstagsvw,newsitem);
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
                        NewsItem itemdata = listitem.get(inpos);
                        
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