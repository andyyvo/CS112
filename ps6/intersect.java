/*
 * Andy Vo
 */

public class intersect extends LLList {

    /**
     * O(m^2+mn^2+nm^2) algorithm for intersect of two linked lists.
     */
    public static LLList intersect(LLList list1, LLList list2) {
        LLList inters = new LLList();
    
        for (int i = 0; i < list1.length(); i++) {
            Object item1 = list1.getItem(i);
            for (int j = 0; j < list2.length(); j++) {
                Object item2 = list2.getItem(j);
                if (item2.equals(item1)) {
                    inters.addItem(item2, inters.length());
                    break;   // move onto the next item from list1
                }
            }
        }
    
        return inters;
    }

    /**
     * Hopefully this is a more efficient method for intersection
     * of two linked lists than nested for loops O(n^2).
     */
    public static LLList intersect1(LLList list1, LLList list2) {
        LLList inters = new LLList();
    
        ListIterator iter1 = list1.iterator();

        if (list1 == null || list2 == null) {
            throw new IllegalArgumentException();
        }
        while (iter1.hasNext()) {
            Object one = iter1.next();
            ListIterator iter2 = list2.iterator();
            while (iter2.hasNext()) {
                Object two = iter2.next();
                if (one.equals(two)) {
                    inters.addItem(one, 0);
                }
            }
        }
        return inters;
    }
    
    public static void main(String[] args) {
        LLList list1 = new LLList();
        list1.addItem(1,0);
        list1.addItem(8,0);
        list1.addItem(3,0);
        list1.addItem(7,0);
        list1.addItem(10,0);

        LLList list2 = new LLList();
        list2.addItem(4,0);
        list2.addItem(5,0);
        list2.addItem(3,0);
        list2.addItem(7,0);
        list2.addItem(11,0);

        System.out.println(list1);
        System.out.println(list2);
        System.out.println(intersect1(list1, list2));
    }
}