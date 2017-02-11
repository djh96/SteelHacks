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

public class Ghost implements Speechlet
{
	Tree dict = new Tree();
	String soFar;
	int numPlay;
	int where;
	String goFor;
	@Override
	public void onSessionStarted(final SessionStartedRequest request, final Session session)
    throws SpeechletException
    {
		log.info("onSessionStarted requestId={}, sessionId={}", request.getRequestId(),
        session.getSessionId());
        soFar = "";
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
				soFar = soFar + letter;
				if(where != 1)
				{
					where--;
					BufferedReader bro = new BufferedReader(new FileReader("usableWords.txt"))
					String line = bro.readLine();
					while (line != null)
					{
					    line = bro.readLine();
					    if(line.equals(soFar)) 
					    {
					    	return getTerminateResponse();
					    }
					}
					bro.close();
					return getRemindResponse();
				}
				else if(where-1 == 0)
				{
					where = numPlay
			        BufferedReader bro = new BufferedReader(new FileReader("usableWords.txt"))
					String line = bro.readLine();
					while (line != null)
					{
					    line = bro.readLine();
					    if(line.equals(soFar)) 
					    {
					    	return getTerminateResponse();
					    }
					}
					bro.close();
					Node cur = dict.root;
					for(int i = 0; i < soFar.length(); i++) 
					{
						cur = cur.getChild();
						while(cur.getLetter() != soFar.charAt(i))
						{
							cur = cur.getNext();
							if(cur == null)
							{
								return getChallengeResponse();
							}
						}	
					}
					ArrayList<String> goodLetters = new ArrayList<String>();
					ArrayList<String> allLetters = new ArrayList<String>();
					String play;
					allLetters.add(new String(a)); allLetters.add(new String(b)); allLetters.add(new String(c)); allLetters.add(new String(d)); 
					allLetters.add(new String(e)); allLetters.add(new String(f)); allLetters.add(new String(g)); allLetters.add(new String(h));
					allLetters.add(new String(i)); allLetters.add(new String(k)); allLetters.add(new String(l)); allLetters.add(new String(m));
					allLetters.add(new String(n)); allLetters.add(new String(l)); allLetters.add(new String(o)); allLetters.add(new String(p));
					allLetters.add(new String(r)); allLetters.add(new String(s)); allLetters.add(new String(t)); allLetters.add(new String(u)); 
					allLetters.add(new String(v)); allLetters.add(new String(w)); allLetters.add(new String(y)); 

					cur = cur.getChild();
					while(cur.getNext() != null)
					{
						if(cur.getChild() != null) 
						{
							goodLetters.add(cur.getLetter());
						}
						else
						{
							allLetters.remove(cur.getLetter());
						}
						cur = cur.getNext();
					}
					if(goodLetters.size() == 0)
					{
						Random rand = new Random();
						int  n = rand.nextInt(allLetters.size());
						play = allLetters.get(n);
						soFar += play;
					}
					else
					{
						Random rand = new Random();
						int  n = rand.nextInt(goodLetters.size());

						play = goodLetters.get(n);

						soFar += play;
					}
					return getLetterResponse();
				}
				else
				{
					return getWelcomeResponse();
				}
				
			}
			catch(IOException e)
			{}
		}
		else if ("ChallengeIntent".equals(intentName))
		{
			try
			{
				BufferedReader wl = new BufferedReader(new FileReader("wordlist.txt"));										
				String line = wl.readLine();
				while(!(line.startsWith(soFar)) && wl.readLine() != null)
				{
					line = wl.readLine();
				}
				w1.close();
				if(line == null) 
				{
					return getLoseResponse();
				}
				else
				{
					goFor = line;
					return getWinResponse();
				}
			}
			catch(IOException e)
			{}
		}
		else if ("NumberIntent".equals(intentName))
		{
			String number = intent.getSlot("number").getValue();
			numPlay = Integer.parseInt(number);
			where = numPlay;
			return getBeginResponse();

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

	private SpeechletResponse getBeginResponse()
	{
		String x = "I will go last, please say the first letter";
		PlainTextOutputSpeech y = new PlainTextOutputSpeech();
		y.setText(x);
		Reprompt z = new Reprompt();
		z.setOutputSpeech(y);
		return SpeechletResponse.newAskResponse(y, z);
	}

	private SpeechletResponse getWelcomeResponse()
	{
		String x = "how many players are playing?"
		PlainTextOutputSpeech y = new PLainTextOutputSpeech();
		y.setText(x);
		Reprompt z = new Reprompt();
		z.setOutputSpeech(y);
		return SpeechletResponse.newAskResponse(y, z);
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

	private SpeechletResponse getRemindResponse()
	{
		String x;
		for(int i=0; i<soFar.length(); i++)
		{
			if(i==0)
				x = soFar.charAt(0);
			x = x + ", " + soFar.charAt(i);
		}
		String a = x + ", please say the next letter";
		PlainTextOutputSpeech y = new PlainTextOutputSpeech();
		y.setText(x);
		PlainTextOutputSpeech b = new PLainTextOutputSpeech();
		b.setText(a);
		Reprompt z = new Reprompt();
		z.setOutputSpeech(b);
		return SpeechletResponse.newAskResponse(y, b);
	}

	private SpeechletResponse getTerminateResponse()
	{
		String x = "You formed the word " + soFar + ", you lose, scrub";
		PlainTextOutputSpeech y = new PlainTextOutputSpeech();
		y.setText(x);
		return SpeechletResponse.newTellResponse(y);
	}

	private SpeechletResponse getChallengeResponse()
	{
		String x = "challenge";
		PlainTextOutputSpeech y = new PlainTextOutputSpeech();
		y.setText(x);
		return SpeechletResponse.newTellResponse(y);
	}

	private SpeechletResponse getLetterResponse()
	{
		String x = "" + soFar.charAt(soFar.length()-1);
		PlainTextOutputSpeech y = new PlainTextOutputSpeech();
		y.setText(x);
		PLainTextOutputSpeech a = new PLainTextOutputSpeech();
		a.setText("I said the letter, " + x)
		Reprompt z = new Reprompt();
		z.setOutputSpeech(a);
		return SpeechletRespons.newAskResponse(y, z);
	}

	private SpeechletResponse getLoseResponse()
	{
		String x = "You caught me";
		PLainTextOutputSpeech y = new PLainTextOutputSpeech();
		y.setText(x);
		return SpeechletResponse.newTellResponse(y);
	}

	private SpeechletResponse getWinResponse()
	{
		String x = "I was thinking of the word " + goFor + ", you lose, scrub";
		PlainTextOutputSpeech y = new PlainTextOutputSpeech();
		y.setText(x);
		return SpeechletResponse.newTellResponse(y);
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