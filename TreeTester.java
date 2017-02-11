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
					soFar = line;
					line = br.readLine();
					while(line != null)
					{
						dict.add(line);
						line = br.readLine();
					}

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

		        if ("HelloWorldIntent".equals(intentName))
		        {
		            return getHelloResponse();
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