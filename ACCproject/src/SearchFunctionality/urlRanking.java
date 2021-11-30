package SearchFunctionality;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class urlRanking {
     
	private static String regex = "[[ ]*|[,]*|[)]*|[(]*|[\"]*|[;]*|[-]*|[:]*|[']*|[’]*|[\\.]*|[:]*|[/]*|[!]*|[?]*|[+]*]+";
	
	//function calculate the occurrence of searched word in a web page and find page ranking.
	public static int WordOcuurence(String URL, String WORD) throws IOException{
		
		Document webPages = Jsoup.connect(URL).get();
		String data = webPages.body().text();
		Map<String, WordElement> map_content = new HashMap<String, WordElement>();
		BufferedReader brReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8))));
		String tempStr;
		
		while((tempStr = brReader.readLine()) != null){
			
			String word [] = tempStr.split(regex);
			for (String allwords : word){
				
				if("".equals(allwords)){
					
					continue;
				}
				
				WordElement wordElement = map_content.get(allwords);
				
				if(allwords.equalsIgnoreCase(WORD)){
					
					if(wordElement == null){
						
						wordElement = new WordElement();
						wordElement.word = allwords;
						wordElement.count = 0;
						map_content.put(allwords,wordElement);
					}
					wordElement.count++;
				}
			}
		}
		brReader.close();
		SortedSet<WordElement> sort = new TreeSet<WordElement>(map_content.values());
		int p = 0;
		int max = 1000;
		
		LinkedList <String> unusedWords = new LinkedList <>();
		try {
			BufferedReader wordbr = new BufferedReader(new FileReader("unwanted.txt"));
			String w;
			while ((w = wordbr.readLine()) != null){
				
				unusedWords.add(w);
			}
			wordbr.close();
		}
		catch (FileNotFoundException e){
			
			System.out.println("Oops!! Sorry..The desired word not found");
		}
		
		for(WordElement words : sort) {
			if(p >= max){
				
				break;
			}
			if (unusedWords.contains(words.word)){
				
				p++;
				max++;
			}
			else{
				
				p++;
				return words.count;
			}
		}
		return 0;
	}
	
	public static class WordElement implements Comparable<WordElement>
	{
		String word;
		int count;

		@Override
		public int hashCode() {
			return word.hashCode();
		}
		@Override
		public boolean equals(Object object) {
			return word.equals(((WordElement) object).word);
		}
		@Override
		public int compareTo(WordElement we) {
			return we.count - count;
		}	
	}
}

