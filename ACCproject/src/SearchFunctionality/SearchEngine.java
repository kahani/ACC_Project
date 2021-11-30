package SearchFunctionality;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import TriePackage.CrawlerMain;
import TriePackage.Trie;

public class SearchEngine {
	
	private static Trie<ArrayList<Integer>> trie;
	private final String wordRegex = "[[ ]*|[,]*|[)]*|[(]*|[\"]*|[;]*|[-]*|[:]*|[']*|[’]*|[\\.]*|[:]*|[/]*|[!]*|[?]*|[+]*]+";
	private static HashSet<String> allLinks;

	private static LinkedList<String> suggestions = new LinkedList<>(); 
	
	public HashSet<String> createTrie(String urlName) throws IOException {
		Hash hash =  new Hash();
		CrawlerMain crawler = new CrawlerMain();
		 		
		trie = new Trie<ArrayList<Integer>>();
		
		HashSet<String> wordsunrequired = hash.savepages("unwanted.txt");		
	
		allLinks = crawler.getPageUrls(urlName, 1);
		
		HashSet<String> temp = null;
		String text;
		String wrd;
		String[] wordsplit;
		
		Iterator<String> linkIterator = null;
		Iterator<String> wordIterator = null;
		
		linkIterator = allLinks.iterator();
		
		int i = 0;
		while(linkIterator.hasNext()) {
			String s1 = linkIterator.next();
			text = CrawlerMain.HTMLtoText(s1);
			
			if(text.length() == 0) {
				continue;
			}
			
			text = text.toLowerCase();
			wordsplit = text.split(wordRegex);
			
			for(String s: wordsplit) {
				suggestions.add(s);
			}
			
			suggestions.removeAll(wordsunrequired);
			
			temp = new HashSet<String>(Arrays.asList(wordsplit));
			temp.remove(wordsunrequired);
			
			wordIterator = temp.iterator();
			
			while(wordIterator.hasNext()) {
				wrd = (String) wordIterator.next();
				ArrayList<Integer> arrList = trie.searchWord(wrd);
				
				
				if (arrList == null) {
					//storing words in trie
					trie.put(wrd, new ArrayList<Integer>(Arrays.asList(i)));
				} else {
					arrList.add(i);
				}
			}

			i++;
		}
		
		return allLinks;
	}
	
	public String[] search (String[] index) {
		
		int[] count = new int[allLinks.size()];
		List<String> links = new ArrayList<String>(allLinks);
		
		ArrayList<Integer> tmp = null;
		for (int i = 0; i < index.length; i++) {
			tmp = trie.searchWord(index[i].toLowerCase());//tmp stored the links position
			
			if (tmp != null) {
				for (int k = 0; k < tmp.size(); k++) {
					count[tmp.get(k)]++;
				}
			} else {
				System.out.println("The word <" + index[i] + "> is not in any file!" );
				suggestWords(index[i]);				
				return null;
			}
		}
		/*Answers stores the indexes of the webPages*/ 
		ArrayList<String> webPages = new ArrayList<String>();
		for (int m = 0; m < count.length; ++m) {
			if (count[m] == index.length) {
				webPages.add(links.get(m));
			}
		}
		return webPages.toArray(new String[0]);
	}
	// function to provide suggestions on the base of spell checking
	public static void suggestWords(String s) {
			
		int dstnc = 10000;
		String suggest = "No Suggestions!";
		
		for(String suggest1: suggestions) {
			int d = EditDistance.calcuateEditDistance(s, suggest1);
			if(d < dstnc) {
				suggest = suggest1;
				dstnc = d;
			}
		}
		
		System.out.println("Did you mean " + suggest + "?");
		
	}
	
}
