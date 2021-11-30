package TriePackage;

import java.util.HashMap;

public class Trie<t> {

	public int size;			// size of Trie
	private Node<t> root;		// root of Trie
	
	public Trie() {
		// constructor to assign values for root and size of Trie
		this.root = new Node<t>();
		this.size = 0;
	}
	
	public void put(String key, t value)				// method to insert key value pair in Trie
	{
		HashMap<Character, Node<t>> children = this.root.children;
		Node<t> node = null;
		for(int i= 0;i<= (key.length()-1); ++i)			 
		{
			char ch= key.charAt(i);
			
			if(children.containsKey(ch))
			{
				node=children.get(ch);
			}
			else {
				node= new Node<t> (ch);
				children.put(ch,node);
			}
			
			if(i== (key.length()-1))
			{
				node.value = value;
			}
			
			children = node.children;
		}	
		this.size += 1;
	}

	//this method search a word in the Trie and return page Index if word is present
	public t searchWord(String w)			
	{
		HashMap<Character, Node<t>> children = this.root.children;
		Node<t> node =null;
		t pageIndex = null;
		
		for(int i = 0; i<= (w.length()-1); ++i)
		{
			char ch= w.charAt(i);
			
			if(children.containsKey(ch))
			{
				node = children.get(ch);
			}
			else
				return null;
			
			if(i == (w.length()-1))
					{
				pageIndex= node.value;
					}
			children = node.children;
		}
		return pageIndex;
		}
		
	}
	
