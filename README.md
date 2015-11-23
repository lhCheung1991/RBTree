# A Red Black Tree implemented in Java

Red Black Tree is such a marvelous data structure. It is mostly the most popular Balanced Binary Search Tree that you can see it in C++ as stl::map and in Java as TreeMap.

# Code Example
![Alt text](https://github.com/lhCheung1991/RBTree/blob/master/constructor.png?raw=true "Optional Title")

The above code shows the constructor of class RBTree in RBTree.java, you may notice that it requests a Comparator. This Comparator is used to specific the order of keys of Nodes in a BST.

![Alt text](https://github.com/lhCheung1991/RBTree/blob/master/comp.png?raw=true "Optional Title")
![Alt text](https://github.com/lhCheung1991/RBTree/blob/master/init.png?raw=true "Optional Title")

The above code shows how to initialize a RBTree object, you should define a class, like Comp, which implements the interface Comparator,  and then pass an Object into the constructor of RBTree.

![Alt text](https://github.com/lhCheung1991/RBTree/blob/master/insert.png?raw=true "Optional Title")

The above code shows the insert function of RBTree.java. You may notice that I have create a RBTree object and specific the types of key and value as Interger, so insert function should be called like tree.insert(Integer, Integer)


