package RBTree;

import java.util.Comparator;

public class RBTree<K, V> 
{
	private final boolean RED = false;
	private final boolean BLACK = true;
	
	private Node<K, V> nullNode = null;
	private Node<K, V> root = null;    // initialized to nullNode, but point to root when
										// tree is not empty
	private Comparator<K> comp = null;
	
	public RBTree(Comparator<K> _comp)
	{
		nullNode = new Node<K, V>(null, null);
		nullNode.leftChild = nullNode;    // nullNode don't care who is his father
		nullNode.rightChild = nullNode;
		nullNode.color = BLACK;    // NIL node should be black
		root = nullNode;    // root point to nullNode when the tree is empty
		comp = _comp;
	}
	
	/**************rotate operation begin*********************/
	private Node<K, V> singleLeftRotate(Node<K, V> subTree)
	{
		Node<K, V> newSubRoot = subTree.rightChild;
		
		subTree.rightChild = newSubRoot.leftChild;
		if (newSubRoot.leftChild != nullNode)
		{
			newSubRoot.leftChild.parent = subTree;
		}
		
		newSubRoot.parent = subTree.parent;
		if (subTree.parent == nullNode)
		{
			root = newSubRoot;
		}
		else
		{
			if (subTree.parent.leftChild == subTree)
			{
				subTree.parent.leftChild = newSubRoot;
			}
			else
			{
				subTree.parent.rightChild = newSubRoot;
			}
		}
		
		subTree.parent = newSubRoot;
		newSubRoot.leftChild = subTree;
		
		return newSubRoot;
	}
	
	private Node<K, V> singleRightRotate(Node<K, V> subTree)
	{
		Node<K, V> newSubRoot = subTree.leftChild;
		
		subTree.leftChild = newSubRoot.rightChild;
		if (newSubRoot.rightChild != nullNode)
		{
			newSubRoot.rightChild.parent = subTree;
		}
		
		newSubRoot.parent = subTree.parent;
		if (subTree.parent == nullNode)
		{
			root = newSubRoot;
		}
		else
		{
			if (subTree.parent.leftChild == subTree)
			{
				subTree.parent.leftChild = newSubRoot;
			}
			else
			{
				subTree.parent.rightChild = newSubRoot;
			}
		}
		
		subTree.parent = newSubRoot;
		newSubRoot.rightChild = subTree;
		
		return newSubRoot;
	}
	/**************rotate operation end*********************/
	
	/**************search for in-order successor begin*********************/
	public Node<K, V> getInOrderSuccessor(Node<K, V> y)    // y.rightChild should not be nullNode
	{
		Node<K, V> x = y.rightChild;
		while (x.leftChild != nullNode)
		{
			x = x.leftChild;
		}
		
		return x;
	}
	/**************search for in-order successor end*********************/
	
	/**************insert operation begin*********************/
	public void insert(K k, V v)
	{
		Node<K, V> y = nullNode;
		Node<K, V> x = root;    // search the insert position from root
		
		while (x != nullNode)
		{
			y = x;    // when x point to nullNode, y should be the parent of new node
			if (comp.compare(k, x.k) < 0)
			{
				x = x.leftChild;
			}
			else if (comp.compare(k, x.k) > 0)
			{
				x = x.rightChild;
			}
			else
			{
				// key can not be duplicated
				System.err.println("key can not be duplicated");
				return;
			}
		}
		
		Node<K, V> newNode = new Node<K, V>(k, v);
		newNode.parent = y;    // y should be the parent of newNode, y could be nullNode
		
		if (y == nullNode)    // tree is empty, root point to nullNode when the tree is empty 
		{
			root = newNode;    // newNode should be the root
		}
		else
		{
			if (comp.compare(newNode.k, y.k) < 0)
			{
				y.leftChild = newNode;
			}
			else
			{
				y.rightChild = newNode;
			}
		}
		
		newNode.leftChild = nullNode;
		newNode.rightChild = nullNode;
		newNode.color = RED;    // newNode should be red
		insertFixup(newNode);
	}
	
	private void insertFixup(Node<K, V> x)
	{
		while (x.parent.color == RED)    // x point to the red node, when x.parent.color is black
										// RED-BLACK principle maintain
		{
			if (x.parent.parent.leftChild == x.parent)    // x.parent is the left child of x.parent.parent
			{
				Node<K, V> y = x.parent.parent.rightChild;
				if (y.color == RED)    // reverse color
				{
					x.parent.color = BLACK;
					y.color = BLACK;
					x.parent.parent.color = RED;
					x = x.parent.parent;    // cautious that x could be root
				}
				else    // recover the RED-BLACK principle
				{
					if (x.parent.rightChild == x)    // need left rotation
					{
						x = x.parent;    // make sure x point to red node
						singleLeftRotate(x);
					}
					
					x.parent.color = BLACK;
					x.parent.parent.color = RED;
					singleRightRotate(x.parent.parent);
					
				}
			}
			else    // x.parent is the right child of x.parent.parent
			{
				Node<K, V> y = x.parent.parent.leftChild;
				if (y.color == RED)    // reverse color
				{
					x.parent.color = BLACK;
					y.color = BLACK;
					x.parent.parent.color = RED;
					x = x.parent.parent;    // cautious that x could be root
				}
				else
				{
					if (x.parent.leftChild == x)    // need right rotation
					{
						x = x.parent;
						singleRightRotate(x);
					}
					
					x.parent.color = BLACK;
					x.parent.parent.color = RED;
					singleLeftRotate(x.parent.parent);
					
				}
			}
		}
		
		root.color = BLACK;    // x always point to the red node, only when x point to the root
								// x should turn red to black
	}
	/**************insert operation end*********************/
	
	/**************delete operation begin*********************/
	public void delete(K k)
	{
		Node<K, V> y = root;
		while (y != nullNode)    // search for the node to delete
		{
			if (comp.compare(k, y.k) < 0)
			{
				y = y.leftChild;
			}
			else if (comp.compare(k, y.k) > 0)
			{
				y = y.rightChild;
			}
			else
			{
				break;
			}
		}
		
		if (y == nullNode)
		{
			System.err.println("Key doesn't exist.");
			return;
		}
		//----------------------------------------------------
		if (y.leftChild != nullNode && y.rightChild != nullNode)    // y point to the real deleted node
		{
			Node<K, V> ySuccessor = getInOrderSuccessor(y);
			y.k = ySuccessor.k;
			y.v = ySuccessor.v;
			y = ySuccessor;
		}
		
		// y may point to a single red node, a single black node or a single black-red branch
		Node<K, V> x = null;
		if (y.leftChild != nullNode)    // right child should be nullNode
		{
			x = y.leftChild;
		}
		else    // right child could be nullNode or not
		{
			x = y.rightChild;    // x could be nullNode
		}
		
		x.parent = y.parent;
		
		if (y.parent == nullNode)    // y point to root
		{
			root = x;
		}
		else
		{
			if (y.parent.leftChild == y)
			{
				y.parent.leftChild = x;
			}
			else
			{
				y.parent.rightChild = x;
			}
		}
		
		if (y.color = BLACK)    // y should be a single black node or a single black-red branch
								// if y.color == RED, y could not be root and y should be a single red node
		{
			deleteFixup(x);
		}
		
	}
	
	private void deleteFixup(Node<K, V> x)
	{
		while (x != root && x.color == BLACK)    // x point to the double-black-none-root node
		{
			if (x.parent.leftChild == x)    // x is the left Child of its parent
			{
				Node<K, V> w = x.parent.rightChild;
				
				if (w.color == RED)    // make x's new brother w.color to be black
				{
					w.color = BLACK;
					x.parent.color = RED;
					singleLeftRotate(x.parent);
					w = x.parent.rightChild;    // change w to a new w
				}
				
				// w.color == BLACK now
				if (w.leftChild.color == BLACK && w.rightChild.color == BLACK)
				{
					w.color = RED;
					x = x.parent;    // the lacking black flow up 
				}
				else
				{
					if (w.rightChild.color == BLACK)    // w.leftChild.color == RED
					{
						w.color = RED;
						w.leftChild.color = BLACK;
						singleRightRotate(w);
						w = x.parent.rightChild;
					}
					
					w.color = x.parent.color;
					x.parent.color = BLACK;
					w.rightChild.color = BLACK;
					singleLeftRotate(x.parent);
					
					x = root;    // break while condition
				}
			}
			else    // x is the right Child of its parent
			{
				Node<K, V> w = x.parent.leftChild;
				
				if (w.color == RED)
				{
					w.color = BLACK;
					x.parent.color = RED;
					singleRightRotate(x.parent);
					w = x.parent.leftChild;
				}
				
				if (w.leftChild.color == BLACK && w.rightChild.color == BLACK)
				{
					w.color = RED;
					x = x.parent;
				}
				else
				{
					if (w.leftChild.color == BLACK)    // w.rightChild.color == RED
					{
						w.color = RED;
						w.rightChild.color = BLACK;
						singleLeftRotate(w);
						w = x.parent.leftChild;
					}
					
					w.color = x.parent.color;
					x.parent.color = BLACK;
					w.leftChild.color = BLACK;
					singleRightRotate(x.parent);
					
					x = root;
				}
			}
		}
		
		x.color = BLACK;    // if x point to root, it okay to drop the additional black
							// if x.color == RED, then change the red to black to recover the lacking black
	}
	
	/**************delete operation end*********************/
	
	/**************get operation begin*********************/
	public V get(K k)
	{
		
		Node <K, V> curNode = root;
		while (curNode != nullNode)
		{
			if (comp.compare(k, curNode.k) < 0)
			{
				curNode = curNode.leftChild;
			}
			else if (comp.compare(k, curNode.k) > 0)
			{
				curNode = curNode.rightChild;
			}
			else 
			{
				break;
			}
		}
		
		if (curNode == nullNode)
		{
			System.err.println("Key doesn't exist.");
			return null;
		}
		
		return curNode.v;
	}
	/**************get operation end*********************/
	
	/**************** tree node ***********************/
	private class Node<K, V>
	{
		K k;
		V v;
		Node<K, V> parent, leftChild, rightChild;
		boolean color;
		public Node(K k, V v) 
		{
			super();
			this.k = k;
			this.v = v;
		}
	}
	/**************** tree node ***********************/

	private void printTreePreOrder(Node<K, V> subTree)
	{
		if (subTree != nullNode)
		{
			String color = subTree.color == BLACK ? "BLACK" : "RED";
			System.out.println(subTree.k + " " + subTree.v + " " + color);
			printTreePreOrder(subTree.leftChild);
			printTreePreOrder(subTree.rightChild);
		}
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		printTreePreOrder(root);
		return "";
	}
}
