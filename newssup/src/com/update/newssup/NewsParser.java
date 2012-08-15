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
