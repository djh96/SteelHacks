import java.io.*;

public class TreeTester
{
	public static void main(String[] args)
	{
		Tree dict = new Tree();
		try
		{
			BufferedReader br = new BufferedReader(new FileReader("wordlist.txt"));
			String line = br.readLine();
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