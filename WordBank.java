import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.File;
import java.io.BufferedWriter;
import java.io.PrintStream;

public class WordBank
{
	public static ArrayList<String> words = new ArrayList<String>();
	public static ArrayList<String> usableWords = new ArrayList<String>();


	public static void main(String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader("wordlist.txt"));
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		    	words.add(line);
		        line = br.readLine();
		    }
		} finally {
		    br.close();
		}

		for(int i = 0; i < words.size(); i++) 
		{
			boolean good = true;
			String word = words.get(i);

			for (int j = 0; j < words.size(); j++)
			{
				//System.out.println(word + ":" + words.get(j));
				if((word.startsWith(words.get(j))) && words.get(j).length() > 3 && i!=j || word.length() < 4)
				{
					//System.out.println(word.startsWith(words.get(j)));
					//System.out.println(word + ":" + words.get(j));
					good = false;	
					break;

				}
			}
				if (good)
				{
					//System.out.println(word);
					usableWords.add(word);	
				}
		}
		//FileWriter writer = new FileWriter("usableWords.txt"); 
		//BufferedWriter out = new BufferedWriter(writer);
		PrintStream fileStream = new PrintStream(new File("usableWords.txt"));
		for(String str: usableWords) {
		  fileStream.println(str);
		  //out.newLine();
		
		}
		//writer.close();
		System.out.println(usableWords.size());
	}
}