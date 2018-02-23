package word_processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileToString
{
	//reads the lines in a text file and converts it into an array list
	public ArrayList<String> ConvertToStringList(String path)
	{
		ArrayList<String> output = new ArrayList<>(1);
		File inputFile = new File(path);
		String temp;
		
		try(BufferedReader br = new BufferedReader(new FileReader(inputFile)))
		{  
		  while ((temp = br.readLine()) != null)
		  {
			  output.add(CleanStringList(temp));
		  }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return output;
	}
	
	public String CleanStringList(String input)
	{
		StringCleaner cleaner = new StringCleaner();
		
		return cleaner.CleanString(input);
	}

}
