/*
 * StringNode.java
 *
 * Computer Science 112
 * 
 * modified by:
 *   name: Andy Vo
 *   email: andyvo@bu.edu
 */

import java.io.*;
import java.util.*;

/**
 * A class for representing a string using a linked list.
 * Each character of the string is stored in a separate node.  
 *
 * This class represents one node of the linked list.  The string as a
 * whole is represented by storing a reference to the first node in
 * the linked list. The methods in this class are static methods that
 * take a reference to a string linked-list as a parameter. This
 * approach allows us to use recursion to write many of the methods,
 * and it also allows the methods to handle empty strings, which are 
 * represented using a value of null.
 * 
 * Some methods are done recursively and others are done through iteration.
 */
public class StringNode {
    private char ch;
    private StringNode next;
    
    /**
     * Constructor
     */
    public StringNode(char c, StringNode n) {
        this.ch = c;
        this.next = n;
    }
    
    /**
     * getNode - private helper method that returns a reference to
     * node i in the given linked-list string.  If the string is too
     * short or if the user passes in a negative i, the method returns null.
     */
    private static StringNode getNode(StringNode str, int i) {
        if (i < 0 || str == null) {    // base case 1: not found
            return null;
        } else if (i == 0) {           // base case 2: just found
            return str;
        } else {
            return getNode(str.next, i - 1);
        }
    }
    
    /*****************************************************
      * Public methods (in alphabetical order)
      *****************************************************/
    
    /**
     * charAt - returns the character at the specified index of the
     * specified linked-list string, where the first character has
     * index 0.  If the index i is < 0 or i > length - 1, the method
     * will end up throwing an IllegalArgumentException.
     */
    public static char charAt(StringNode str, int i) {
        if (str == null) {
            throw new IllegalArgumentException("the string is empty");
        } 
        
        StringNode node = getNode(str, i);
        
        if (node != null) {
            return node.ch;     
        } else {
            throw new IllegalArgumentException("invalid index: " + i);
        }
    }
    
    /**
     * convert - converts a standard Java String object to a linked-list
     * string and returns a reference to the linked-list string
     */
    public static StringNode convert(String s) {
        if (s.length() == 0) {
            return null;
        }
        
        StringNode firstNode = new StringNode(s.charAt(0), null);
        StringNode prevNode = firstNode;
        StringNode nextNode;
        
        for (int i = 1; i < s.length(); i++) {
            nextNode = new StringNode(s.charAt(i), null);
            prevNode.next = nextNode;
            prevNode = nextNode;
        }
        
        return firstNode;
    }
    
    /**
     * copy - returns a copy of the given linked-list string
     */
    public static StringNode copy(StringNode str) {
        if (str == null) {
            return null;
        }
        StringNode copyFirst = new StringNode(str.ch, null);
        StringNode trav = str.next;
        StringNode copy = copyFirst;
        while (trav != null) {
            copy.next = new StringNode(trav.ch, null);
            trav = trav.next;
            copy = copy.next;
        }
        return copyFirst;
    }
    
    /**
     * deleteChar - deletes character i in the given linked-list string and
     * returns a reference to the resulting linked-list string
     */
    public static StringNode deleteChar(StringNode str, int i) {
        if (str == null) {
            throw new IllegalArgumentException("string is empty");
        } else if (i < 0) { 
            throw new IllegalArgumentException("invalid index: " + i);
        } else if (i == 0) { 
            str = str.next;
        } else {
            StringNode prevNode = getNode(str, i-1);
            if (prevNode != null && prevNode.next != null) {
                prevNode.next = prevNode.next.next;
            } else {
                throw new IllegalArgumentException("invalid index: " + i);
            }
        }
        
        return str;
    }
    
    /**
     * indexOf - returns the position of the first occurrence of
     * character ch in the given linked-list string.  If there is
     * none, returns -1.
     */
    public static int indexOf(StringNode str, char ch) {
        if (str == null) {
            return -1;
        }
        StringNode trav = str;
        int count = 0;
        while (trav != null) {
            if (trav.ch == ch) {
                return count;
            } else {
                count++;
                trav = trav.next;
            }
        }
        return -1;
        
    }
    
    /** 
     * insertAfter - inserts the specified new character (newChar) 
     * after the first occurrence of the specified character (afterChar)
     * in the linked-list string to which str refers.
     * If afterChar is not in the string, the method adds the character
     * to the end of the string. Returns a reference to the first node
     * in the modified linked list, because the first node will change
     * if the original string was empty.
     */
    public static StringNode insertAfter(StringNode str, char newChar, 
                                         char afterChar) 
    {
        StringNode newNode = new StringNode(newChar, null);
        
        // Special case - str is empty
        if (str == null) {
            return newNode;
        
        // Case - we found the node of afterChar to add newChar to
        } else if (str.ch == afterChar) {
            newNode.next = str.next;
            str.next = newNode;
            return str;
        
        // Otherwise - we keep recursively traversing
        } else {
            str.next = insertAfter(str.next, newChar, afterChar);
            return str;
        }
    }
    
    /**
     * insertChar - inserts the character ch before the character
     * currently in position i of the specified linked-list string.
     * Returns a reference to the resulting linked-list string.
     */
    public static StringNode insertChar(StringNode str, int i, char ch) {
        StringNode newNode, prevNode;
        
        if (i < 0) { 
            throw new IllegalArgumentException("invalid index: " + i);
        } else if (i == 0) {
            newNode = new StringNode(ch, str);
            str = newNode;
        } else {
            prevNode = getNode(str, i - 1);
            if (prevNode != null) {
                newNode = new StringNode(ch, prevNode.next);
                prevNode.next = newNode;
            } else {
                throw new IllegalArgumentException("invalid index: " + i);
            }
        }
        
        return str;
    }
    
    /**
     * insertSorted - inserts character ch in the correct position
     * in a sorted list of characters (i.e., a sorted linked-list string)
     * and returns a reference to the resulting list.
     */
    public static StringNode insertSorted(StringNode str, char ch) {
        StringNode newNode, trail, trav;
        
        // Find where the character belongs.
        trail = null;
        trav = str;
        while (trav != null && trav.ch < ch) {
            trail = trav;
            trav = trav.next;
        }
        
        // Create and insert the new node.
        newNode = new StringNode(ch, trav);
        if (trail == null) {
            // We never advanced the prev and trav references, so
            // newNode goes at the start of the list.
            str = newNode;
        } else { 
            trail.next = newNode;
        }
        
        return str;
    }
    
    /**
     * isPrefix - determines if the linked-list string specified by prefix 
     * is a prefix of the linked-list string specified by str -- i.e., if 
     * str starts with the characters in prefix. It returns true if str 
     * starts with prefix and false otherwise.
     */
    public static boolean isPrefix(StringNode prefix, StringNode str) {
        StringNode pre = prefix;
        StringNode trav = str;
        while (pre != null) {
            if (pre.ch != trav.ch) {
                return false;
            }
            pre = pre.next;
            trav = trav.next;
        }
        return true;
    }
    
    /**
     * length - recursively determines the number of characters in the
     * linked-list string to which str refers
     */
    public static int length(StringNode str) {
        if (str == null) {
            return  0;
        } else {
            return 1 + length(str.next);
        }
    }
    
    /**
     * numOccur - find the number of occurrences of the character
     * ch in the linked list to which str refers
     */
    public static int numOccur(StringNode str, char ch) {
        if (str == null) {
            return 0;
        }
        
        int numInRest = numOccur(str.next, ch);
        if (str.ch == ch) {
            return 1 + numInRest;
        } else {
            return numInRest;
        }
    }
    
    /**
     * print - recursively writes the specified linked-list string to System.out
     */
    public static void print(StringNode str) {
        if (str == null) {
            return;
        } else {
            System.out.print(str.ch);
            print(str.next);
        }
    }
    
    /**
     * read - reads a string from an input stream and returns a
     * reference to a linked list containing the characters in the string
     */
    public static StringNode read(InputStream in) throws IOException { 
        char ch = (char)in.read();
        
        if (ch == '\n') {    // the string ends when we hit a newline character
            return null;         
        } else {
            StringNode restOfString = read(in);
            StringNode first = new StringNode(ch, restOfString);
            return first;
        }
    }
    
    /**
     * removeAllSpaces - removes all spaces from the linked-list string
     * to which str refers. Modifies the linked list itself, rather than
     * creating a new list. Returns a reference to the first node
     * in the modified linked list, because the first node will change
     * if the old first node contained a space.
     */
    public static StringNode removeAllSpaces(StringNode str) {
        // Case - empty string
        if (str == null) {
            return null;
        }
        
        // Case - leading empty space
        while (str.ch == ' ') {
            str = str.next;
        }

        // Case - middle and trailing empty space
        StringNode trav = str.next;
        StringNode trail = str;
        while (trav != null) {
            if (trav.ch == ' ') {
                trail.next = trav.next;
                trav = trav.next;
            } else {
                trav = trav.next;
                trail = trail.next;              
            }

        }
        return str;
    }
    
    /*
     * toString - creates and returns the Java string that
     * the current StringNode represents.  Note that this
     * method -- unlike the others -- is a non-static method.
     * Thus, it will not work for empty strings, since they
     * are represented by a value of null, and we can't use
     * null to invoke this method.
     */
    public String toString() {
        String str = "";
        
        StringNode trav = this;   // start trav on the current node    
        while (trav != null) {
            str = str + trav.ch;
            trav = trav.next;
        }
        
        return str;
    }
    
    /**
     * toUpperCase - converts all of the characters in the specified
     * linked-list string to upper case.  Modifies the list itself,
     * rather than creating a new list.
     */
    public static void toUpperCase(StringNode str) {        
        if (str == null) {
            return;
        } else {
            str.ch = Character.toUpperCase(str.ch);
            toUpperCase(str.next);
        }
    } 
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        
        // toUpperCase
        StringNode str = StringNode.convert("fine");
        System.out.print("Here's a string: "); 
        System.out.println(str);    // implicit toString call
        System.out.print("Here it is in upper-case letters: "); 
        StringNode.toUpperCase(str);
        System.out.println(str);
        
        // indexOf
        System.out.print("Enter a string: ");
        String s = in.nextLine();
        StringNode str1 = StringNode.convert(s);
        System.out.print("\nWhat character to search for? ");
        char ch = in.nextLine().charAt(0);
        int index = StringNode.indexOf(str1, ch);
        if (index == -1) {
            System.out.println("Not in the string.");
        } else {
            System.out.print("The first occurrence of that character ");
            System.out.println("is at position " + index);
        }
        
        // isPrefix
        System.out.print("\nEnter a second string (for testing isPrefix): ");
        s = in.nextLine();
        StringNode str2 = StringNode.convert(s);
        System.out.print(str2);
        if (StringNode.isPrefix(str2, str1)) {
            System.out.print(" is ");
        } else {
            System.out.print(" is not ");
        }
        System.out.println("a prefix of " + str1);        
        
        // deleteChar and copy
        int n = -1;
        while (n < 0) {
            System.out.print("\nIndex of character to delete (>= 0)? ");
            n = in.nextInt();
            in.nextLine();
        }
        StringNode copy = StringNode.copy(str1);
        try {
            str1 = StringNode.deleteChar(str1, n);
            StringNode.print(str1);
        } catch (IllegalArgumentException e) {
            System.out.println("The string is too short.");
        }
        
        // The copy should be unchanged!
        str1 = copy;
        System.out.print("\nReturning to the unchanged copy: ");
        System.out.println(copy);
        
        // insertAfter
        System.out.print("What character to insert? ");
        ch = in.nextLine().charAt(0);
        System.out.print("\nWhat character to insert after? ");
        char after = in.nextLine().charAt(0);
        str1 = StringNode.insertAfter(str1, ch, after);
        System.out.println(str1);
        
        // removeAllSpaces
        System.out.print("\nType a string that includes spaces: ");
        s = in.nextLine();
        StringNode str3 = StringNode.convert(s);
        str3 = StringNode.removeAllSpaces(str3);
        System.out.print("\nHere it is with spaces removed: ");
        System.out.println(str3);
    }
}
