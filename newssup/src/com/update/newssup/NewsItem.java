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
