/*
 * ChainedHashTable.java
 *
 * Computer Science 112, Boston University
 * 
 * Modifications and additions by:
 *     name: Andy Vo
 *     email: andyvo
 */

import java.util.*;     // to allow for the use of Arrays.toString() in testing

/*
 * A class that implements a hash table using separate chaining.
 */
public class ChainedHashTable implements HashTable {
    /* 
     * Private inner class for a node in a linked list
     * for a given position of the hash table
     */
    private class Node {
        private Object key;
        private LLQueue<Object> values;
        private Node next;
        
        private Node(Object key, Object value) {
            this.key = key;
            values = new LLQueue<Object>();
            values.insert(value);
            next = null;
        }
    }
    
    private Node[] table;      // the hash table itself
    private int numKeys;       // the total number of keys in the table
    private int size;
        
    /* hash function */
    public int h1(Object key) {
        int h1 = key.hashCode() % table.length;
        if (h1 < 0) {
            h1 += table.length;
        }
        return h1;
    }
    
    /*** Add your constructor here ***/
    public ChainedHashTable(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("size must be positive");
        }
        table = new Node[size];
        this.size = size;
    }
    
    /*
     * insert - insert the specified (key, value) pair in the hash table.
     * Returns true if the pair can be added and false if there is overflow.
     * Will never have overflow.
     */
    public boolean insert(Object key, Object value) {
        if (key == null) {
            throw new IllegalArgumentException("key must be non-null");
        }

        int i = h1(key);
        Node trav = table[i];
        
        // check if chain already has key
        while (trav != null) {
            if (trav.key.equals(key)) {
                return true;
            }
            trav = trav.next;
        }

        // otherwise, add key to beginning of chain
        Node storage = table[i]; // carries temporary chain
        table[i] = new Node(key, value); // new node at front
        table[i].next = storage;

        numKeys++;
        return true;
    }
    
    /*
     * search - search for the specified key and return the
     * associated collection of values, or null if the key 
     * is not in the table
     */
    public Queue<Object> search(Object key) {
        if (key == null) {
            throw new IllegalArgumentException("key must be non-null");
        }

        int i = h1(key);
        Node trav = table[i];

        while (trav != null) {
            if (trav.key.equals(key)) {
                return trav.values;
            }
            trav = trav.next;
        }
        return null;
    }
    
    /* 
     * remove - remove from the table the entry for the specified key
     * and return the associated collection of values, or null if the key 
     * is not in the table
     */
    public Queue<Object> remove(Object key) {
        if (key == null) {
            throw new IllegalArgumentException("key must be non-null");
        }

        int i = h1(key);
        Node trav = table[i];
        Node trail = null;

        while (trav != null) {
            if (trav.key.equals(key) && trail == null) {
                Queue<Object> removed = trav.values;
                table[i] = table[i].next;
                numKeys--;
                return removed;
            } else if (trav.key.equals(key) && trail != null) {
                Queue<Object> removed = trav.values;
                trail.next = trav.next;
                numKeys--;
                return removed;
            }
            trail = trav;
            trav = trav.next;
        }
        return null;
    }
    
    
    /*** Add the other required methods here ***/
    
    /**
     * getNumKeys - accessor method for the number of keys
     */
    public int getNumKeys() {
        return numKeys;
    }

    /**
     * load - although a hash table that uses separate chaining won't overflow,
     * it can become top full to offer decent performance. To allow clients
     * to measure the degree of fullness, load() returns a value that
     * represents the load factor of the table: numKeys divided by size of table.
     */
    public double load() {
        return (double)numKeys / size;
    }

    /**
     * getAllKeys - allows clients to obtain all keys in the hash table by
     * returning an array of type Object containing all the keys.
     */
    public Object[] getAllKeys() {
        Object[] list = new Object[numKeys];
        int keyNums = 0;
        for (int i = 0; i < size; i++) {
            Node trav = table[i];
            while (trav != null) {
                list[keyNums] = trav.key;
                keyNums++;
                trav = trav.next;
            }
        }
        return list;
    }

    public void resize(int newSize) {
        if (newSize <= size) {
            throw new IllegalArgumentException("Value of resize() should be larger than the current size.");
        }
        ChainedHashTable newTable = new ChainedHashTable(newSize);
        
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                Node trav = table[i];
                while (trav != null) {
                    while(!trav.values.isEmpty()) {
                        newTable.insert(trav.key, trav.values.remove());
                    }
                    trav = trav.next;
                }
            }
        }
        this.table = newTable.table;
        return;
    }
    
    /*
     * toString - returns a string representation of this ChainedHashTable
     * object. *** You should NOT change this method. ***
     */
    public String toString() {
        String s = "[";
        
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) {
                s += "null";
            } else {
                String keys = "{";
                Node trav = table[i];
                while (trav != null) {
                    keys += trav.key;
                    if (trav.next != null) {
                        keys += "; ";
                    }
                    trav = trav.next;
                }
                keys += "}";
                s += keys;
            }
        
            if (i < table.length - 1) {
                s += ", ";
            }
        }       
        
        s += "]";
        return s;
    }

    public static void main(String[] args) {
        /** Add your unit tests here **/

        ChainedHashTable table = new ChainedHashTable(5);
        table.insert("howdy", 15);
        table.insert("goodbye", 10);
        System.out.println(table.insert("apple", 5));
        System.out.println(table);
        System.out.println(table.getNumKeys());
        System.out.println(table.search("apple"));
        System.out.println(table.remove("apple"));
        System.out.println(table);
        System.out.println(table.getNumKeys());

        System.out.println();

        ChainedHashTable table2 = new ChainedHashTable(5);
        table2.insert("howdy", 15);
        table2.insert("goodbye", 10);
        table2.insert("apple", 5);
        System.out.println(table2.load());
        table2.insert("pear", 6);
        System.out.println(table2.load());

        System.out.println();

        ChainedHashTable table3 = new ChainedHashTable(5);
        table3.insert("howdy", 15);
        table3.insert("goodbye", 10);
        table3.insert("apple", 5);
        table3.insert("howdy", 25);    // insert a duplicate
        Object[] keys = table3.getAllKeys();
        System.out.println(Arrays.toString(keys));

        System.out.println();
        
        ChainedHashTable table4 = new ChainedHashTable(5);
        table4.insert("howdy", 15);
        table4.insert("goodbye", 10);
        table4.insert("apple", 5);
        System.out.println(table4);
        table4.resize(7);
        System.out.println(table4);
    }
}
