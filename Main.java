package RBTree;

import java.util.Comparator;

public class Main 
{

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		RBTree<Integer, Integer> tree = new RBTree<Integer, Integer>(new Comp());
		for (int i = 0; i < 5; i++)
		{
			tree.insert(i, i);
		}
		
		System.out.println(tree);
		System.out.println();
		
		for (int i = 10; i > 5; i--)
		{
			tree.insert(i, i);
		}
		
		System.out.println(tree);
		System.out.println();
		
		tree.delete(3);
		System.out.println(tree);
		System.out.println();
		
		tree.delete(10);
		System.out.println(tree);
		System.out.println();
		
		tree.delete(4);
		System.out.println(tree);
		System.out.println();
		
		tree.delete(1);
		System.out.println(tree);
		System.out.println();
		
		tree.delete(2);
		System.out.println(tree);
		System.out.println();
		
		tree.delete(0);
		System.out.println(tree);
		System.out.println();
		
		tree.delete(8);
		System.out.println(tree);
		System.out.println();
		
		tree.delete(6);
		System.out.println(tree);
		System.out.println();
		
		tree.delete(7);
		tree.delete(9);
		System.out.println(tree);
		System.out.println();
		
		long begin = System.currentTimeMillis();
		
		for (int i = 0; i < 1000000; i++)
		{
			tree.insert(i, i);
		}
		
		long end = System.currentTimeMillis();
		
		System.out.println((end - begin) / 1000.0 + " s");
		
		System.out.println(tree.get(8989));
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
