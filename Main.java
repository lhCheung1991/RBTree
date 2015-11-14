package RBTree;

import java.util.Comparator;

public class Main 
{

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		RBTree<Integer, Integer> tree = new RBTree<Integer, Integer>(new Comp());
		tree.insert(1, 1);
		tree.insert(2, 2);
		tree.insert(3, 3);
		tree.insert(4, 4);
		
		tree.delete(1);
	}

}

class Comp implements Comparator<Integer>
{

	@Override
	public int compare(Integer o1, Integer o2) 
	{
		// TODO Auto-generated method stub
		if (o1 < o2)
		{
			return -1;
		}
		else if (o1 > o2)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	
}