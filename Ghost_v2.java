import java.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;

public class Ghost implements Speechlet
{
	Tree dict = new Tree();
	String soFar;
	int numPlay;
	int where;
	@Override
	public void onSessionStarted(final SessionStartedRequest request, final Session session)
    throws SpeechletException
    {
		log.info("onSessionStarted requestId={}, sessionId={}", request.getRequestId(),
        session.getSessionId());
		try
		{
			BufferedReader br = new BufferedReader(new FileReader("usableWords.txt"));
			String line = br.readLine();
			while(line != null)
			{
				dict.add(line);
				line = br.readLine();
			}
			br.close;
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
		}
	}
	@Override
	public SpeechletResponse onLaunch(final LaunchRequest request, final Session session)
    throws SpeechletException
    {
		log.info("onLaunch requestId={}, sessionId={}", request.getRequestId(),
        session.getSessionId());
		return getWelcomeResponse();
	}
	@Override
	public SpeechletResponse onIntent(final IntentRequest request, final Session session)
    throws SpeechletException
    {
		log.info("onIntent requestId={}, sessionId={}", request.getRequestId(),
        session.getSessionId());
        Intent intent = request.getIntent();
        String intentName = (intent != null) ? intent.getName() : null;
        if ("LetterIntent".equals(intentName))
        {
            String letter = intent.getSlot("letter").getValue();
            try
			{
				BufferedReader br2 = new BufferedReader(new FileReader("info.txt"));
				String theWord = br2.readLine();
				String number = br2.readLine();
				String numberPlayers = br.readLine();
				br.close;
				BufferedWriter bw = new BufferedWriter(new FileWriter("info.txt"));
				bw.write(theWord + letter);
				bw.newLine();
				if(Integer.parseInt(number) != 1)
				{
					bw.write(Integer.parseInt(number) -1);
					BufferedWriter bwop = new BufferedWriter(new FileWriter("info.txt", true));
					bwop.write(play);
					bwop.close();
					saytheWordInLetters + letter ();
				}
				else if(number-1 == 0)
				{
					bw.write(numberPlayers);
					bw.newLine();
					bw.write(numberPLayers);
					bw.close();
			        String soFar = theWord + letter;
			        BufferedReader bro = new BufferedReader(new FileReader("usableWords.txt"))
					String line = bro.readLine();
					while (line != null)
					{
					    line = bro.readLine();
					    if(line.equals(soFar)) 
					    {
					    	terminate();
					    }
					}
					bro.close();
					int possible = 1;
					Node cur = dict.root;
					char play;
					for(int i = 0; i < soFar.length(); i++) 
					{
						cur = cur.getChild();
						while(cur.getLetter() != soFar.charAt(i))
						{
							cur = cur.getNext();
							if(cur == null)
							{
								challenge();
							}
						}	
					}
					Node cur2 = cur;
					cur = cur.getChild();
					while(cur.getNext() != null)
					{
						possible++;
						cur = cur.getNext();
					}
					Random rand = new Random();
					int  n = rand.nextInt(possible) + 1;
					cur2 = cur2.getChild();
					for(int i = 1; i < n; i++) 
					{
						cur2 = cur2.getNext();
					}
					play = cur2.getLetter();
					BufferedWriter bwo = new BufferedWriter(new FileWriter("info.txt", true));
					bwo.write(play);
					bwo.close();
					sayPlay();
				}
				
			}
			catch(IOException e)
			{}
		}   
	    else if ("AMAZON.HelpIntent".equals(intentName))
        {
            return getHelpResponse();
        }
        else
        {
            throw new SpeechletException("Invalid Intent");
        }
	}

	private SpeechletResponse getHelpResponse()
	{
		String whatSay = "Please say a letter, challenge, or stop";
		PlainTextOutputSpeech whatDo = new PlainTextOutputSpeech();
		whatDo.setText(whatSay);
		Reprompt sayAgain = new Reprompt();
		sayAgain.setOutputSpeech(whatDo);
		return SpeechletResponse.newAskResponse(whatDo, sayAgain);
	}

	public static class Tree
	{
		Node root;

		public Tree()
		{
			root = new Node();
		}

		public void add(String word)
		{
			Node cur = root;
			for(int i=0;i<word.length(); i++)
			{
				if(cur.getChild() == null)
				{
					cur.setChild(new Node(word.substring(i,i+1), (i == (word.length() -1))));
						cur = cur.getChild();
				}
				else
				{
					cur = cur.getChild();
					while(!cur.getLetter().equals(word.substring(i,i+1)) && cur.getNext() != null)
					{
						cur = cur.getNext();
					}
					if(cur.getNext() == null && !cur.getLetter().equals(word.substring(i,i+1)))
					{
						cur.setNext(new Node(word.substring(i,i+1), (i == (word.length() -1))));
						cur = cur.getNext();
					}
					else
					{

					}
				}
			}
		}



		public class Node
		{
			String letter;
			boolean end;
			Node child;
			Node next;

			public Node()
			{
				//hi
			}

			public Node(String x, boolean y)
			{
				letter = x;
				end = y;
			}

			public void setNext(Node x)
			{
				next = x;
			}

			public void setEnd(boolean x)
			{
				end = x;
			}

			public boolean getEnd()
			{
				return end;
			}

			public void setChild(Node x)
			{
				child = x;
			}

			public Node getNext()
			{
				return next;
			}

			public Node getChild()
			{
				return child;
			}

			public String getLetter()
			{
				return letter;
			}

		}
	}
}
