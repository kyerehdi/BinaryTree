/****
 * Derrick Kyereh, 11/5/2021, cmsc 256 Fall 2021
 */
package cmsc256;

import bridges.base.BinTreeElement;

import bridges.connect.Bridges;
import bridges.validation.RateLimitException;

import java.io.IOException;


public class BinSearchTree<E>implements BinTreeInterface<E> {
    private int size;
    private BinTreeElement<E> root;


    @Override
    public BinTreeElement<E> getRoot() {

        return root;
    }

    @Override
    public boolean add(E element) {
        BinTreeElement node = new BinTreeElement(element);

        boolean wasAdded = true;
        if (root == null) {
            root = node;
        } else {
            wasAdded = addToParent(root, node);
        }
        if (wasAdded) {
            size++;
        }
        return wasAdded;
    }

    @Override
    public boolean remove(E element) {
        BinTreeElement node = new BinTreeElement(element);
        if (root == null) {
            return false;

        }
        if (root.compareTo(node) == 0) {
            if (root.getLeft() == null) {
                root = root.getLeft();
            } else if (root.getRight() == null) {
                root = root.getRight();

            } else {
                BinTreeElement formerRight = root.getRight();
                root = root.getLeft();
                addToParent(root, formerRight);
            }
            size--;
            return true;
        }
        return removeSubNode(root, node);
    }


    private boolean addToParent(BinTreeElement<E> parentNode, BinTreeElement<E> addNode) {
    String C= (String) parentNode.getValue();
    String D= (String) addNode.getValue();


        int compareParent=C.compareTo(D);
        boolean wasAdded = false;
        if (compareParent > 0) {
            if (parentNode.getLeft() == null) {
                parentNode.setLeft(addNode);
                wasAdded = true;
            } else {
                wasAdded = addToParent(parentNode.getLeft(), addNode);
            }

        } else if (compareParent < 0) {
            if (parentNode.getRight() == null) {
                parentNode.setRight(addNode);
                wasAdded = true;
            } else {
                wasAdded = addToParent(parentNode.getRight(), addNode);
            }

        }
        return wasAdded;
    }

    private boolean removeSubNode(BinTreeElement parent, BinTreeElement removeValue) {
        int compareParent =parent.compareTo(removeValue);
        BinTreeElement subTree = null;
        if (compareParent > 0)
            subTree = parent.getLeft();
        else
            subTree = parent.getRight();


        if (subTree == null) {
            return false;
        }
        if (subTree.compareTo(removeValue) == 0) {
            BinTreeElement replacement;
            if (subTree.getLeft() == null) {
                replacement = subTree.getLeft();
            } else {
                BinTreeElement formerRight = subTree.getRight();
                replacement = subTree.getLeft();
                addToParent(replacement, formerRight);

            }
            if (compareParent > 0) {
                parent.setLeft(replacement);
            } else {
                parent.setRight(replacement);
            }
            size--;
            return true;
        }
        return removeSubNode(subTree, removeValue);
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }
public boolean SH(BinTreeElement node, BinTreeElement target){
        if(node == null) return false;
    int COM= node.compareTo(target);
    if(COM==0)return true;
    if(COM <0) {
        return SH(node.getLeft(), target);
    }
        return SH(node.getRight(),target);
}
    @Override
    public boolean search(E target) {
        BinTreeElement K = new BinTreeElement(target);
        return SH(root,K);
    }


    public String inorderTraverseTree(BinTreeElement focusnode) {
        String C = "";
        if(root ==null)
            throw new NullPointerException();
        if (focusnode != null) {
            C=C +
                    this.inorderTraverseTree(focusnode.getLeft());
            C = C + focusnode.getValue() + "  ";
            C=C +
                    this.inorderTraverseTree(focusnode.getRight());
        }

        return C;
    }
    @Override
    public String inorder() {
       return inorderTraverseTree(root);


    }
public String postorderTraverseTree(BinTreeElement focusnode){
    String K = "";
    if (focusnode != null) {
        K = K + this.postorderTraverseTree(focusnode.getLeft());
        K = K + this.postorderTraverseTree(focusnode.getRight());
        K = K + focusnode.getValue() + "  ";
    }
    return K;
}



    @Override
    public String postorder() {
        return postorderTraverseTree(root);
    }
public String preorderTraverseTree(BinTreeElement focusnode){
        String D= "";


    if (focusnode != null) {
        D = D + focusnode.getValue() + "  ";
        D = D +
                this.preorderTraverseTree(focusnode.getLeft());
        D = D +
                this.preorderTraverseTree(focusnode.getRight());
    }
    return D;
}
    @Override
    public String preorder() {
        return preorderTraverseTree(root);
    }

    public int height() {

        return height1(root);
    }
public int height1(BinTreeElement node){
        if(node == null)
            return -1;
        else {
            int D = height1(node.getLeft());
            int C = height1(node.getRight());


            if (D > C)
             return (D +1);
             else
                 return (C+1);

        }
}
public boolean IsFull(BinTreeElement node) {
        if(node!= null){
            if(node.getRight()==null && node.getLeft()==null){
                return true;
            }
            if(node.getRight()!=null && node.getLeft() !=null){
                return IsFull(root.getLeft()) && IsFull(node.getRight());
            }
        }
        return false;
}
    public boolean isFullBST() {

        return IsFull(root);
    }
    public int getNumberOfLeaves (){
        return getnumberOfLeaves(root);
    }
   public int getnumberOfLeaves(BinTreeElement node){
       if(node== null)
           return 0;
       if(node.getLeft()==null && node.getRight()== null)
           return 1;
       else return getnumberOfLeaves(node.getLeft()) +getnumberOfLeaves(node.getRight());
    }

    public int getNumberOfNonLeaves(){
        return getNumberofnonleaves(root);
    }
    public int getNumberofnonleaves(BinTreeElement node) {
        if(node == null ||(node.getRight()== null && node.getLeft()==null))
            return 0;

        return 1+ getNumberofnonleaves(node.getLeft()) + getNumberofnonleaves(node.getRight());

    }

    public static void main(String[] args) {
        Bridges bridges = new Bridges(3, "kyerehdi", "865795350400");
        bridges.setTitle("Display on Bridges with labels");

        BinSearchTree<String> names = new BinSearchTree<>();

        names.add("Frodo");
        names.add("Dori");
        names.add("Bilbo");
        names.add("Kili");
        names.add("Gandalf");
        names.add("Fili");
        names.add("Thorin");
        names.add("Nori");

     System.out.println(names.inorder());
     System.out.println(names.postorder());
     System.out.println(names.preorder());
     //Construct tree
names.root.setLabel("Frodo");
names.root.getLeft().setLabel("Dori");
names.root.getLeft().getLeft().setLabel("Bilbo");
names.root.getLeft().getRight().setLabel("Fili");
names.root.getRight().setLabel("Kili");
names.root.getRight().getLeft().setLabel("Gandalf");
names.root.getRight().getRight().setLabel("Thorin");
names.root.getRight().getRight().getLeft().setLabel("Nori");

        bridges.setDataStructure(names.root);
        try {
            bridges.visualize();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RateLimitException e) {
            e.printStackTrace();
        }


    }
}
