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

import android.os.Parcel;
import android.os.Parcelable;

public class NewsItem implements Parcelable{
        public String title;
        public String date;
        public String link;
        
        public NewsItem (String t, String d, String l)
        {
        	title=t;
        	date=d;
        	link=l;
        }
        
        public NewsItem (Parcel in)
        {
             title = in.readString ();
             date = in.readString ();
             link = in.readString ();
        }
        
        public NewsItem() {
        	
        }

		public static final Parcelable.Creator<NewsItem> CREATOR
        = new Parcelable.Creator<NewsItem>() 
       {
             public NewsItem createFromParcel(Parcel in) 
             {
                 return new NewsItem(in);
             }

             public NewsItem[] newArray (int size) 
             {
                 return new NewsItem[size];
             }
        };
       
        
		public int describeContents ()
        {
            return 0;
        }

        public void writeToParcel (Parcel dest, int flags)
        {
            dest.writeString (title);
            dest.writeString (date);
            dest.writeString (link);
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
