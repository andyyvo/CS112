import java.util.Iterator;

/*
 * Problem7.java
 * 
 * Computer Science 112, Boston Univerity
 * Andy Vo
 */

public class Problem7 {
    /*
     * getAllOdds (ArrayList version) - takes the ArrayList vals (which is 
     * assumed to contain only integers) and creates and returns a new 
     * ArrayList containing all of the odd integers in vals.
     */
    public static ArrayList getAllOdds(ArrayList vals) {
        if (vals == null || vals.length() == 0) {
            throw new IllegalArgumentException("Invalid input for vals.");
        }
        ArrayList odds = new ArrayList(vals.length());
        int j = 0;
        for (int i = 0; i < vals.length(); i++) {
            int val = (Integer)vals.getItem(i);
            if (val % 2 == 1) {
                odds.addItem(val, j);
                j++;
            }
        }
        return odds;
    }
    
    /*
     * getAllOdds (LLList version) - takes the LLList vals (which is 
     * assumed to contain only integers) and creates and returns a new 
     * LLList containing all of the odd integers in vals.
     */
    public static LLList getAllOdds(LLList vals) {
        if (vals == null || vals.length() == 0) {
            throw new IllegalArgumentException("Invalid input for vals.");
        }
        LLList odds = new LLList();
        ListIterator iter = vals.iterator();
        while(iter.hasNext()) {
            int val = (Integer)iter.next();
            if (val % 2 == 1) {
                odds.addItem(val, 0);
            }
        }
        return odds;
    }
    
    public static void main(String[] args) {
        Integer[] vals = {2, 5, 14, 6, 5, 8, 3};  
        ArrayList list1 = new ArrayList(vals);
        ArrayList odds1 = Problem7.getAllOdds(list1);
        System.out.println(odds1);
        
        LLList list2 = new LLList(vals);
        LLList odds2 = Problem7.getAllOdds(list2);
        System.out.println(odds2);

        Integer[] vals3 = {11, 15, 1, 7, 5, 9, 3};  
        ArrayList list3 = new ArrayList(vals3);
        ArrayList odds3 = Problem7.getAllOdds(list3);
        System.out.println(odds3);
        
        LLList list4 = new LLList(vals3);
        LLList odds4 = Problem7.getAllOdds(list4);
        System.out.println(odds4);
    }
}