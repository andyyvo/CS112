/* 
 * LLBag.java
 * 
 * Computer Science 112, Boston University
 * Andy Vo
 */

import java.util.*;

/*
 * An implementation of a bag data structure using a linked list.
 */
public class LLBag implements Bag {
    // Inner class for a node.
    private class Node {
        private Object item;
        private Node next;
        
        private Node(Object i, Node n) {
            item = i;
            next = n;
        }
    }

    // fields of the LLBag object
    private Node head;     // dummy head node
    private int numItems;  // # of items in the list

    /**
     * Constructs an empty LLBag object with a dummy head node.
     */
    public LLBag() {
        head = new Node(null, null);
        numItems = 0;
    }

    /**
     * numItems - accessor method that returns the number of items 
     * in this ArrayBag.
     */
    public int numItems() {
        return numItems;
    }

    /**
     * add - adds the specified item to this LLLBag. Returns true if there 
     * is room to add it, and false otherwise.
     * Throws an IllegalArgumentException if the item is null.
     */
    public boolean add(Object item) {
        if (item == null) {
            throw new IllegalArgumentException("item must be non-null");
        }
        // Linked Lists are never full :)
        Node newNode = new Node(item, null);
        newNode.next = head.next;
        head.next = newNode;
        numItems++;
        return true;
    }

    /**
     * remove - removes one occurrence of the specified item (if any)
     * from this LLBag.  Returns true on success and false if the
     * specified item (i.e., an object equal to item) is not in this LLBag.
     */
    public boolean remove(Object item) {
        Node curr = head.next;
        while (curr.next != null) {
            if (curr.next.item.equals(item)) {
                //shift of linked nodes
                curr.next = curr.next.next;
                numItems--;
                return true;
            }
            curr = curr.next;
        }
        //not found
        return false;
    }

    /**
     * contains - returns true if the specified item is in the Bag, and
     * false otherwise.
     */
    public boolean contains(Object item) {
        Node curr = head.next;
        while (curr != null) {
            if (curr.item.equals(item)) {
                return true;
            }
            curr = curr.next;
        }
        //not found
        return false;
    }

    /**
     * grab - returns a reference to a randomly chosen item in this LLBag.
     */
    public Object grab() {
        if (numItems == 0) {
            throw new IllegalStateException("the bag is empty");
        }
        int whichOne = (int)(Math.random() * numItems);
        int counter = 0;
        Node trav = head.next;
        while (counter < whichOne) {
            trav = trav.next;
            counter++;
        }
        return trav.item;
    }

    /**
     * toString - converts this LLBag into a string that can be printed.
     * Overrides the version of this method inherited from the Object class.
     */
    public Object[] toArray() {
        Object[] copy = new Object[numItems];
        Node trav = head.next;

        for (int i = 0; i < numItems; i++) {
            copy[i] = trav.item;
            trav = trav.next;
        }
        
        return copy;
    }

    /**
     * toString - converts this LLBag into a string that can be printed.
     * Overrides the version of this method inherited from the Object class.
     */
    public String toString() {
        String str = "{";
        Node trav = head.next;
        while (trav != null) {
            str += trav.item;
            if (trav.next == null) {
                str += "}";
            } else {
                str += ", ";
            }
            trav = trav.next;
        }
        return str;
    }

    public static void main(String[] args) {
        // Create a Scanner object for user input.
        Scanner scan = new Scanner(System.in);
        
        // Create an LLBag named bag1.
        Bag bag1 = new LLBag();
        //scan.nextLine();    // consume the rest of the line
        
        // Read in strings, add them to bag1, and print out bag1.
        String itemStr;
        String response;
        int i = 0;
        while (true) {
            System.out.println("Would you like to add an item?");
            response = scan.nextLine();
            if (response.toLowerCase().equals("yes")) {
                System.out.print("item " + i + ": ");
                itemStr = scan.nextLine();
                bag1.add(itemStr);
                i++;
            } else if (response.toLowerCase().equals("no")) {
                break;
            } else {
                throw new IllegalArgumentException("Invalid input.");
            }
        }
        System.out.println("bag 1 = " + bag1);
        System.out.println();
        
        // Select a random item and print it.
        Object item = bag1.grab();
        System.out.println("grabbed " + item);
        System.out.println();
        
        // Iterate over the objects in bag1, printing them one per line.
        Object[] items = bag1.toArray();
        for (int j = 0; j < items.length; j++) {
            System.out.println(items[j]);
        }
        System.out.println();
        
        // Get an item to remove from bag1, remove it, and reprint the bag.
        System.out.print("item to remove: ");
        itemStr = scan.nextLine();
        if (bag1.contains(itemStr)) {
            bag1.remove(itemStr);
        }
        System.out.println("bag 1 = " + bag1);
        System.out.println();
    }
}