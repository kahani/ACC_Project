package main;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import SearchFunctionality.urlRanking;
import SearchFunctionality.SearchEngine;
public class execute {

	private static final String url = "https://www.w3.org/";
	private static final String Regex = "[[ ]*|[,]*|[)]*|[(]*|[\"]*|[;]*|[-]*|[:]*|[']*|[’]*|[\\.]*|[:]*|[/]*|[!]*|[?]*|[+]*]+";
	
	public static void main(String [] args) throws IOException {
		
		SearchEngine searchEngine = new SearchEngine();
		System.out.println("\t---------------------------Execution begins---------------------------");

		HashSet<String> trie = searchEngine.createTrie(url);//create hash table using trie
		System.out.println("\tTrie created.");
		boolean flag = true;
		Scanner sc = new Scanner(System.in);
		String searchWord;
		while(flag) {
			
			System.out.printf("\t--- Enter word to search:- ");
			//Storing input from the user
			searchWord = sc.next();
			if(!searchWord.equals(null)) {
				//Splitting word using Regex
				String [] splitWord = searchWord.split(Regex);
				String[] allSearchedPages = searchEngine.search(splitWord);
				try {
					if (allSearchedPages != null) {
						
						//this map will store unsorted links
						Map<String, Integer> unsortedLinks = null;
						unsortedLinks = new HashMap<>();
						
						//storing the links with its word occurrence
						for (String url : allSearchedPages) {
							unsortedLinks.put(url, urlRanking.WordOcuurence(url, searchWord));
						}
						LinkedHashMap<String, Integer> reverseMap = new LinkedHashMap<>();
						
						unsortedLinks.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEachOrdered(x -> reverseMap.put(x.getKey(), x.getValue()));
						System.out.println("");
						// to print the page ranks, word occurrence & search link
				        System.out.println("|Rank|---------- | Word Occurrence |  | Search Links |");	
				        System.out.println("\n");
				        int count = 1;
				        for (Map.Entry<String, Integer> putin : reverseMap.entrySet()) {
				        	if(count > 10)
				        		break;
				            System.out.println("| "+count+" |---------- | "+putin.getValue() + " | --> | " + putin.getKey()+" |");
				            count++;
				        }
				        System.out.println("\n\n");
				        // prompt to choose to continue or exit the web search engine
				        System.out.println("Press 'Y' for further searching & 'N' to Exit...");
				        
				        while(true) {
				        	String inpute = sc.next();
				        	if(inpute.equals("Y")||inpute.equals("y")) {
					        	break;
					        }
				        	else if(inpute.equals("N")||inpute.equals("n")) {
				        		flag = false;
					        	System.out.println("Thanks for searching...\nBye...");
					        	System.exit(1);
					        	sc.close();
				        	}
				        	else {
				        		
				        		System.out.println("Oops!!! Wrong input.\nPlease input valid character. \n Press 'Y' for further searching & 'N' to Exit...");
				        	}
				        }
					}
					
				
				}
				catch(Exception e) {
					System.out.println(e.getMessage());
				}
		}
		else 
		{
			System.out.println(searchWord+" not Found, Please enter correct word.");
			continue;
		}	
		}
	}
}
