package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import word_processor.StringCleaner;
import word_processor.TFIDF;

public class Main 
{
	static final String positivePath = "D:\\Users\\Mickey\\eclipse-workspace\\TF-IDF v2\\src\\Positive.txt";
	static final String neutralPath = "D:\\Users\\Mickey\\eclipse-workspace\\TF-IDF v2\\src\\Neutral.txt";
	static final String negativePath = "D:\\Users\\Mickey\\eclipse-workspace\\TF-IDF v2\\src\\Negative.txt";
	static TFIDF tfidfPositive = new TFIDF();
	static TFIDF tfidfNeutral = new TFIDF();
	static TFIDF tfidfNegative = new TFIDF();
	
	public static void main(String[] args)
	{
		
		tfidfPositive.TFIDFTable(positivePath);
		tfidfNeutral.TFIDFTable(neutralPath);
		tfidfNegative.TFIDFTable(negativePath);

		WriteFile();
		System.out.println("Finished Writing");
	}

	public static String[][] PutIntoTable(Hashtable<String, Integer> uniqueWordTable)
	{
		int i = 0;
		
		String[][] classifiedArray = new String[uniqueWordTable.size()][5];
		
		for (Map.Entry m: uniqueWordTable.entrySet()) 
		{
			classifiedArray[i][0] = m.getKey().toString();
			classifiedArray[i][1] = m.getValue().toString();
			
			if(tfidfPositive.tfIdfTable.containsKey(m.getKey()))
			{
				classifiedArray[i][2] = String.valueOf(tfidfPositive.tfIdfTable.get(m.getKey())+1);
			}
			else
			{
				classifiedArray[i][2] = "1";
			}
			
			if(tfidfNeutral.tfIdfTable.containsKey(m.getKey()))
			{
				classifiedArray[i][3] = String.valueOf(tfidfNeutral.tfIdfTable.get(m.getKey())+1);
			}
			else
			{
				classifiedArray[i][3] = "1";
			}
			
			if(tfidfNegative.tfIdfTable.containsKey(m.getKey()))
			{
				classifiedArray[i][4] = String.valueOf(tfidfNegative.tfIdfTable.get(m.getKey())+1);
			}
			else
			{
				classifiedArray[i][4] = "1";
			}
			
			i++;
		}
		
		return classifiedArray;
	}
	
	public static void WriteFile()
    {
		StringCleaner uniqueWordTable = new StringCleaner();
		String compiledFilePath = "D:\\Users\\Mickey\\eclipse-workspace\\TF-IDF V2\\src\\Compiled.txt";
		String[][] outputTable;
		
        File csvFileDirectory = new File("D:\\Users\\Mickey\\eclipse-workspace\\TF-IDF V2\\src\\TF-IDF.csv");
        
        outputTable = PutIntoTable(uniqueWordTable.UniqueWord(compiledFilePath));
        
        try (BufferedWriter br = new BufferedWriter(new FileWriter(csvFileDirectory.getAbsoluteFile())))
        {
            br.write("Word"+","+"Count"+","+"Positive TFIDF"+","+"Neutral TFIDF"+","+"Negative TFIDF");
            br.newLine();

            
            System.out.println("Currently Writing File");
            for(int i = 0; i<outputTable.length; i++)
            {
            	br.write(outputTable[i][0]+",");
            	br.write(outputTable[i][1]+",");
            	br.write(outputTable[i][2]+",");
            	br.write(outputTable[i][3]+",");
            	br.write(outputTable[i][4]);
            	br.newLine();
            }
            br.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
