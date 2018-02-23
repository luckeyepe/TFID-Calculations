package word_processor;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map.Entry;

public class TFIDF 
{
	public Hashtable<String, Double> tfIdfTable = new Hashtable<>();
	Hashtable<String, Double> tfTable = new Hashtable<>();
	ArrayList<String> wordList;
	
	public Hashtable<String, Double> TFIDFTable(String path)
	{
		double sumOfTFWeight =0.0;
		int numberOfUniqueWords;

		FileToString fileToString = new FileToString();
		
		wordList = fileToString.ConvertToStringList(path);


        //calculating the tf value for each word
		for (String string : wordList) 
		{
			TFCalculation(string);
		}

		//sum of the tf weight
		for (Entry<String, Double> e: tfTable.entrySet())
		{
			sumOfTFWeight+=e.getValue();
		}

		//number of unique words
		numberOfUniqueWords = new StringCleaner().NumberOfUniqueWord(path);

		//calculation for the tfidf values for each word
        for (Entry<String, Double> e: tfTable.entrySet())
        {
            tfIdfTable.put(e.getKey(),((e.getValue()+1)/(sumOfTFWeight+numberOfUniqueWords)));
        }

        return tfIdfTable;
	}
	
	public void DisplayTFIDF()
	{
		for (Entry<String, Double> e: tfIdfTable.entrySet())
        {
            System.out.println("Word: "+e.getKey()+", TFIDF value = "+e.getValue());
        }
	}
	public void TFCalculation(String input)
	{
		StringCleaner cleaner = new StringCleaner();
		int numberOfAppearance;
		double numberOfSentencesContain;
		double tfValue;
		double stringLength;

		Hashtable<String, Integer> tempHashTable = cleaner.UniqueWordTable(input);
		
		String[] tempStringArray = input.split(" ");
		stringLength = tempStringArray.length*1.0;

		for (int i = 0; i < tempStringArray.length; i++) 
		{
			if(tempHashTable.containsKey(tempStringArray[i]))
			{
				numberOfAppearance = tempHashTable.get(tempStringArray[i]);
				numberOfSentencesContain = NumberOfSentencesContaining(tempStringArray[i])*1.0;
				tfValue = (numberOfAppearance/stringLength)*(Math.log10(wordList.size()/(numberOfSentencesContain)*1.0));

				if(!tfTable.containsKey(tempStringArray[i]))
				{
					tfTable.put(tempStringArray[i], tfValue);
				}
				else
                {
					tfTable.put(tempStringArray[i], (tfTable.get(tempStringArray[i]) + tfValue));
				}
			}
		}
	}

	//counts the number of sentences that contain a word
	public int NumberOfSentencesContaining(String input)
	{
		int numberOfLines=0;
		
		for (String string : wordList)
		{
			if(string.contains(input))
			{
				numberOfLines++;
			}
		}
		
		return numberOfLines;
	}

}
