package word_processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

public class StringCleaner
{
	//removes the junk words from a string
	public String CleanString(String input)
	{
		return Input(input);
	}
	
	public String Input(String input)
	{
		String[] inputArray;
		
		inputArray = input.split(" ");
		return BuildString(RemoveSuffix(RemovePrefix(RemoveThreeLetterWords(RemoveConjucntions(ChangeVowels(RemoveEnglishWords(RemoveNonLetters(inputArray))))))));
	}

	public int NumberOfUniqueWord(String path)
	{
		String[] inputArray;

		inputArray = ReadTextFile(path).split(" ");
		return UniqueWordCount(RemoveSuffix(RemovePrefix(RemoveThreeLetterWords(RemoveConjucntions(ChangeVowels(RemoveEnglishWords(RemoveNonLetters(inputArray)))))))).size();
	}

	//returns a hashtable of the unique words and the times they repeated in a sentence
	public Hashtable<String, Integer> UniqueWordTable(String input)
	{
		String[] inputArray;
		
		inputArray = input.split(" ");
		
		return UniqueWordCount(inputArray);
	}
	
	//returns a hashtable of the unique words in a txt file
	public Hashtable<String, Integer> UniqueWord(String path)
	{
		String[] inputArray;

		inputArray = ReadTextFile(path).split(" ");
		return UniqueWordCount(RemoveThreeLetterWords(RemoveSuffix(RemovePrefix(RemoveConjucntions(ChangeVowels(RemoveEnglishWords(RemoveNonLetters(inputArray))))))));
	}
	
	public ArrayList<String> RemoveNonLetters(String[] input)
	{
		ArrayList<String> treatedWords = new ArrayList<String>(1);
		char[] word;
		String st = "";
		
		for(int i = 0; i<input.length; i++)
		{
			treatedWords.add(input[i].toLowerCase());//lower case words
		}
		
		
		for (int i = 0; i < treatedWords.size(); i++)
		{
			word = treatedWords.get(i).toCharArray();
			for(int j= 0; j<word.length; j++)
			{
				if(Character.isLetter(word[j]))
				{
					st+=word[j];
				}
			}
			treatedWords.set(i, st);
			st = "";
		}
		
		treatedWords.remove("");//removes null words from the arrayList
		return treatedWords;
	}
	
	//removing English words
	public ArrayList<String> RemoveEnglishWords(ArrayList<String> input)
	{
		String csvFileDirectory = "D:\\Users\\Mickey\\eclipse-workspace\\TF-IDF v1\\src\\EnglishDictionary.csv";
        String englishWord;
        
        Collections.sort(input);//sorts input arrayList to alphabetical order to make it easier to find the matching English words

        try (BufferedReader br = new BufferedReader(new FileReader(csvFileDirectory)))
        {
            while (null != (englishWord = br.readLine()))
            {
                for (int i = 0; i < input.size(); i++)
                {
                    if (englishWord.equals(input.get(i)))
                    {
                       input.remove(i);
                       i--;
                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
		return input;
    }
	
	//change vowels
	public ArrayList<String > ChangeVowels(ArrayList<String> input)
	 {
		 for (int i = 0; i < input.size(); i++)
	     {
			 if (input.get(i).contains("e") || input.get(i).contains("o"))
	         {
				 input.set(i, input.get(i).replace('e', 'i'));
				 input.set(i, input.get(i).replace('o', 'u'));
	         }
	     }

	        return input;
	 }
	 
	public ArrayList<String> RemoveConjucntions(ArrayList<String> input)
	 {
		 String csvFileDirectory = "D:\\Users\\Mickey\\eclipse-workspace\\TF-IDF v1\\src\\ConjunctionWords.csv";
		 String conjunction;

		 try (BufferedReader br = new BufferedReader(new FileReader(csvFileDirectory)))
        {
            while (null != (conjunction = br.readLine()))
            {
                for (int i = 0; i < input.size(); i++)
                {
                    if (conjunction.equals(input.get(i)))
                    {
                       input.remove(i);
                       i--;
                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
		 return input;
	 }
	 
	public ArrayList<String> RemovePrefix(ArrayList<String> input)
	 {
		String csvFileDirectory = "D:\\Users\\Mickey\\eclipse-workspace\\TF-IDF v1\\src\\Prefix.csv";
        String prefix;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFileDirectory)))
        {
            while (null != (prefix = br.readLine()))
            {
            	for (int i = 0; i < input.size(); i++)
            	{
	            	if (input.get(i).startsWith(prefix))
	                {
	            		input.set(i, input.get(i).replaceFirst(prefix, ""));
	                }
            	}
        	}
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
		return input;
	 }
	 
	public ArrayList<String> RemoveSuffix(ArrayList<String> input)
	 {
		String csvFileDirectory = "D:\\Users\\Mickey\\eclipse-workspace\\TF-IDF v1\\src\\Suffix.csv";
        String suffix;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFileDirectory)))
        {
        	while (null != (suffix = br.readLine()))
            {
            	for (int i = 0; i < input.size(); i++)
            	{
            		if (input.get(i).endsWith(suffix))
                    {
	            		input.set(i, ReplaceLast(input.get(i), suffix, ""));
                    }
            	}
        	}
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
		return input;
	}
	
	 
	public String ReplaceLast(String string, String substring, String replacement)
    {
        int index = string.lastIndexOf(substring);

        if (index == -1)
        {
            return string;
        }

        return string.substring(0, index) + replacement;
    }
	
	public ArrayList<String> RemoveThreeLetterWords(ArrayList<String> input)
	{
		for (int i = 0; i < input.size(); i++)
        {
            if (input.get(i).length()<=3)
            {
                input.remove(i);
                i--;
            }
        }
		
		return input;
	}
	
	public String BuildString(ArrayList<String> input)
	{
		String output = "";
		for (String string : input)
		{
			output+=(string+" ");
		}
		
		return ReplaceLast(output," ","");
	}
	

	public Hashtable<String,Integer> UniqueWordCount(ArrayList<String> treatedWords)
    {
        Hashtable<String, Integer> uniqueWordTable = new Hashtable<String, Integer>();
        ArrayList<String> uniqueWords;

        uniqueWords = new ArrayList<>(1);

        for (int i = 0; i < treatedWords.size(); i++)
        {
            if (!uniqueWords.contains(treatedWords.get(i)))
            {
            	uniqueWords.add(treatedWords.get(i));
            	uniqueWordTable.put(treatedWords.get(i), 1);//value of 1 is placed since the times that this word has repeated is 1
            }
            else
            {
            	//increases value of the number of times that the word has repeated
				uniqueWordTable.put(treatedWords.get(i), uniqueWordTable.get(treatedWords.get(i)) + 1);
            }
        }
        
        return uniqueWordTable;
    }

	public Hashtable<String,Integer> UniqueWordCount(String[] treatedWords)
	{
		Hashtable<String, Integer> uniqueWordTable = new Hashtable<String, Integer>();
		ArrayList<String> uniqueWords;

		uniqueWords = new ArrayList<>(1);

		for (int i = 0; i < treatedWords.length; i++)
		{
			if (!uniqueWords.contains(treatedWords[i]))
			{
				uniqueWords.add(treatedWords[i]);
				uniqueWordTable.put(treatedWords[i], 1);//value of 1 is placed since the times that this word has repeated is 1
			}
			else
			{
				//increases value of the number of times that the word has repeated
				uniqueWordTable.put(treatedWords[i], uniqueWordTable.get(treatedWords[i]) + 1);
			}
		}

		return uniqueWordTable;
	}

	static String ReadTextFile(String path)
	{
		File filePositive = new File(path);
		String temp;
		String output = "";
		
		try(BufferedReader br = new BufferedReader(new FileReader(filePositive)))
		{  
		  while ((temp = br.readLine()) != null)
		  {
			  output+=(temp+" ");
		  }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return output;
	}

}
