/*
 * TwoQueuesStack.java
 * 
 * Computer Science 112, Boston University
 * Andy Vo
 */

 /*
 * A generic class that implements the Stack interface using two queues.
 * Note: Stack is "last in, first out; Queue is "first in, first out".
 */
public class TwoQueuesStack<T> implements Stack<T> {
    private LLQueue<T> q1 = new LLQueue<T>(); //queue 1
    private LLQueue<T> q2 = new LLQueue<T>(); //queue 2
    private int top;                       //index of the top item

    /**
     * Constructor
     */
    public TwoQueuesStack() {
        top = -1;
    }

    /**
     * push - adds the specified item to the top of the stack.
     * Always returns true, because the linked list is never full.
     * 
     * Time complexity: O(n). We are inserting each new element to
     * q2 to keep it as the top element. We remove all elements in
     * q1 one at a time if there are any and insert them into q2
     * so that the new element is always positioned at the front of
     * q2. We then swap q1 with q2. O(n) because we iterate through
     * q1.
     * 
     * Note: push can be O(1) in the event where the new element is
     * always added to the rear of q1 and kept as the top stack element
     * if we change "top" to a reference to the top rather than an
     * index.
     */
    public boolean push(T item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        q2.insert(item);
        top++;
        while (!q1.isEmpty()) {
            q2.insert(q1.remove());
        }
        LLQueue<T> temp = q1;
        q1 = q2;
        q2 = temp;
        return true;
    }

    /**
     * pop - removes the item at the top of the stack and returns a
     * reference to the removed object.  Returns null if the stack is
     * empty.
     * 
     * Time complexity: O(1). Queues and stacks share a common trait
     * in that both remove from the top of the data structure.
     * Since queues and stacks always have a reference to the
     * top-most object, it will remove it in constant time.
     * 
     * Note: pop can be O(n) if push is O(1). Essentially, we would
     * need to move all items up to the top item in q1 to q2, remove
     * the top item, and then swap q1 with q2.
     */
    public T pop() {
        if (top == -1) {
            return null;
        }
        top--;
        return q1.remove();
    }

    /**
     * peek - returns a reference to the item at the top of the stack
     * without removing it. Returns null is the stack is empty.
     * 
     * Time complexity: O(1). Using LLQueue, peek has a reference to
     * the front item in constant time. Thus, it is O(1) time. We are
     * simply returning the reference to the front.
     */
    public T peek() {
        if (top == -1) {
            return null;
        }
        return q1.peek();
    }
}