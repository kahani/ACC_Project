package TriePackage;

import java.io.*;
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlerMain {
	 
	//max depth to crawl
	private static final int MaxDepth = 4;
	//max page limit to crawl
	private static final int MaxPage = 100;							
	private static HashSet<String> urls;
	
	public CrawlerMain(){
		urls = new HashSet<String>();
	}
	//HTML to text conversion...
	public static String HTMLtoText(String ConstUrl){
		try {
			Document doc = Jsoup.connect(ConstUrl).get();
			String txt = doc.body().text();
			return txt;		
		}
		catch(Exception e){
			return "Couldn't convert to Text";
			
		}
	}
	
	public Elements getUrlsFromPage(String url) throws IOException{
		Document document = Jsoup.connect(url).get();
		Elements urlsFromPage= document.select("a[href]");
		
			return urlsFromPage;		
	}
	
	public HashSet<String> getPageUrls(String url,int initDepth){
		// to check if url is present and the depth is within limit(50)
		if((!urls.contains(url) && (initDepth <= MaxDepth))){
			try{
				urls.add(url);
				//print crawler links
				if(urls.size() >= MaxPage) // exit if page limit over
					return urls;
			
				Elements urlsFromPage = getUrlsFromPage(url);
			
				initDepth++;
			
				for(Element e: urlsFromPage){
					getPageUrls(e.attr("abs:href"), initDepth);
				}			
			}catch(IOException IOex){}		
		}
		return urls;
	}
}
