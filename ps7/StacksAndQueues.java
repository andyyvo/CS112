/**
 * Andy Vo
 * 
 * Guidelines to follow from problem set:
 * 
 * 1. Method may use either another stack or a queue to assist it.
 *    It may not use an array, linked list, or other data structure.
 *    When choosing between a stack or a queue, choose the one that
 *    leads to the more efficient implementation.
 * 
 * 2. You should assume that the method does not have access to the
 *    internals of the collection objects, and thus you can only
 *    interact with them using the methods in the interfaces discussed
 *    in lecture.
 * 
 * 3. Methods should be static and not return values.
 */

public class StacksAndQueues {

    /**
     * Takes a Stack object "stack" and Object "item", and removes all
     * occurrences of that item from the stack. After the removals, the
     * remaining items should still be in the same order with respect
     * to each other.
     * 
     * For example, given a stack (from top to bottom) {5,2,7,2,10},
     * removing all occurrences of 2 will result in (from top to bottom)
     * {5,7,10}.
     */
    public static void remAllStack(Stack<Object> stack, Object item) {
        Stack<Object> temp = new LLStack<Object>();
        while (!stack.isEmpty()) {
            if (!stack.peek().equals(item)) {
                temp.push(stack.peek());
            }
            stack.pop();
        }
        while (!temp.isEmpty()) {
            stack.push(temp.peek());
            temp.pop();
        }
    }

    /**
     * Takes a Queue object "queue" and Object "item", and removes all
     * occurrences of that item from the stack. After the removals, the
     * remaining items should still be in the same order with respect
     * to each other.
     * 
     * For example, given a queue (from front to rear) {5,2,7,2,10},
     * removing all occurrences of 2 will result in (from front to rear)
     * {5,7,10}.
     */
    public static void remAllQueue(Queue<Object> queue, Object item) {
        Queue<Object> temp = new LLQueue<Object>();
        while (!queue.isEmpty()) {
            if (!queue.peek().equals(item)) {
                temp.insert(queue.peek());
            }
            queue.remove();
        }
        while (!temp.isEmpty()) {
            queue.insert(temp.peek());
            temp.remove();
        }
    }

    public static void main(String[] args) {
        Stack<Object> list1 = new LLStack<Object>();
        list1.push(5);
        list1.push(2);
        list1.push(7);
        list1.push(2);
        list1.push(10);
        System.out.println(list1);
        remAllStack(list1, 2);
        System.out.println(list1);

        System.out.println();

        Queue<Object> list2 = new LLQueue<Object>();
        list2.insert(5);
        list2.insert(2);
        list2.insert(7);
        list2.insert(2);
        list2.insert(10);
        System.out.println(list2);
        remAllQueue(list2, 2);
        System.out.println(list2);
    }
}