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

import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import com.update.newssup.NewsItem;

public class NewsParser extends DefaultHandler {
		private String elementName=null; 
        private final static String TAG_ITEM = "item";
        private final static String[] newstags = { "title", "link", "pubDate"};
        
        private NewsItem currentitem = null;
        private ArrayList<NewsItem> itemarray = null;
        private boolean isParsing = false;
        private StringBuilder builder = new StringBuilder();
        
        public NewsParser(ArrayList<NewsItem> itemarray) {
                super();
                
                this.itemarray = itemarray;
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);
            
            if(isParsing && (elementName==newstags[0] ||elementName==newstags[1]||elementName==newstags[2]) && null != builder)
            {
                    builder.append(ch,start,length);
            }
        }
        
        @Override
        public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
                super.startElement(uri, localName, qName, attributes);
                
                elementName=localName;
                if(localName.equalsIgnoreCase(TAG_ITEM))
                {
                        currentitem = new NewsItem();
                        isParsing = true;
                        
                        itemarray.add(currentitem);
                }
                else
                {
                        builder = null;
                        if(localName != TAG_ITEM)
                                builder = new StringBuilder();
                }
        }
        
        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
                super.endElement(uri, localName, qName);
                
                if(localName.equalsIgnoreCase(TAG_ITEM))
                {
                        isParsing = false;
                }
                else if(localName != TAG_ITEM)
                {
                        if(isParsing)
                        {
                        	 if (localName==newstags[0]) {currentitem.title = builder.toString();}
                        	 else if (localName==newstags[1]) {currentitem.link = builder.toString();}
                        	 else if (localName==newstags[2]) {currentitem.date = builder.toString();}
                        	
                        }
                } 
        }
}