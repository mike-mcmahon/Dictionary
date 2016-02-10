/*
 * Course: CCPS 305 Algorithms and Data Structures
 * Term: Fall 2015
 * Student Name: Mike McMahon, A.Sc.T.
 * Student ID: 500605231
 * Date: November 15, 2015
 * 
 * Assignment #2 - Write an algorithm that reads a text file and compiles a sorted list of
 * unique words.  The algorithm also includes a count of occurrences for each word.
 * 
 * This algorithm makes use of two data structures to accomplish this, a HashTable, and a tree.
 * 
 * => class Dictionary implements the algorithm for the dictionary that is called upon and displayed via the client
 * class. <=
 * 
 * class methods:
 * 		- public void makeDictionary(File file)
 * 			- creates the dictionary
 * 
 * 		- public TreeMap<String, Integer> getTree()
 * 			- returns a Tree object containing the dictionary in sorted order
 * 
 */

import java.util.*;
import java.io.*;

public class Dictionary
{
	//data fields used by this class
	private HashMap<String, Integer> dictionary;
	private TreeMap<String, Integer> dictionaryTree;
	private int occurance;
	private int temp;
	private String key;
	private Scanner in;

	/*
	 * Method used to create the dictionary.
	 */
	public void makeDictionary (File file)
	{
		//create my HashMap and TreeMap objects
		dictionary = new HashMap<String, Integer>();
		dictionaryTree = new TreeMap<String, Integer>();
		
		//create the input scanner object and read in the text file
		try
		{
			in = new Scanner(file);
			
			//while scanning the input text, take each string object as a key and
			//insert it into the HashTable if it doesn't exist.  If it does exist, increment its
			//occurrence and chain it to the bucket.
			while(in.hasNext())
			{
				key = in.next();
				
				if(dictionary.containsKey(key))
				{
					temp = dictionary.get(key);
					occurance = temp + 1;
				}
				else
				{
					occurance = 1;
				}
				dictionary.put(key,  occurance);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.out.println("No File Found, ERROR");
		}
		finally
		{
			//close the input scanner
			in.close();
		}
		
		//now that the HashTable has been created, we must take the unique keys
		//and place them in a tree structure that keep the keys in sorted order. 
		//we do this by adding each key to the tree.
		
		for(HashMap.Entry<String, Integer> tableEntry : dictionary.entrySet())
		{
			dictionaryTree.put(tableEntry.getKey(), tableEntry.getValue());
		}
		
		
		//This block is used for printing the dictionary to the system console only.
		//and is not used in the final configuration.  The final configuration prints
		//the resulting output to a text file.
		//***************************************************************************
		//now that the tree is populated we can iterate over the tree
		//printing the values in ascending order to the system console.
		//for(Map.Entry<String, Integer> entry : dictionaryTree.entrySet())
        //{
        //	System.out.printf("%-25s %s %d times\n", entry.getKey(), "occurs", entry.getValue());
        //	
        //}
        

	}

	
	/*
	 * Returns the Tree data structure that contains the dictionary.
	 */
	public TreeMap<String, Integer> getTree()
	{
		return dictionaryTree;
	}
}
