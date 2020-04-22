/*
 * Palindrome.java
 *
 * Computer Science 112
 *
 * Modifications and additions by:
 *     name: Andy Vo
 *     username: andyvo
 */
   
public class Palindrome {
    public static boolean isPal(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Input is not valid. Please enter a string.");
        }
        if (str.equals("") || str.length() == 1) {
            return true;
        }
        str = str.toLowerCase();

        ArrayQueue<Character> arr = new ArrayQueue<Character>(str.length());
        ArrayStack<Character> rev = new ArrayStack<Character>(str.length());

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) >= 97 && str.charAt(i) <= 122) {
                arr.insert(str.charAt(i));
                rev.push(str.charAt(i));
            }
        }
        while (!arr.isEmpty()) {
            if (!(arr.remove() == rev.pop())) {
                return false;
            }
        }
        return true;
    }
    
    public static void main(String[] args) {
        System.out.println("--- Testing method isPal ---");
        System.out.println();

        System.out.println("(0) Testing on \"A man, a plan, a canal, Panama!\"");
        try {
            boolean results = isPal("A man, a plan, a canal, Panama!");
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println("true");
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == true);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        
        System.out.println();
        
        System.out.println("(1) Testing on \"Madam, I'm Adam.\"");
        try {
            boolean results = isPal("Madam, I'm Adam.");
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println("true");
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == true);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        
        System.out.println();

        System.out.println("(2) Testing on \"How many chickens crossed the road? 2.\"");
        try {
            boolean results = isPal("How many chickens crossed the road? 2.");
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println("false");
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == false);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        
        System.out.println();

        System.out.println("(3) Testing on \"Palindrome :) Semordnilap\"");
        try {
            boolean results = isPal("Palindrome :) Semordnilap");
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println("true");
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == true);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        
        System.out.println();

        System.out.println("(4) Testing on \"~L i v e  o n  t i m e, E M I T  N O  E V I L~\"");
        try {
            boolean results = isPal("~L i v e  o n  t i m e, E M I T  N O  E V I L~");
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println("true");
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == true);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        
        System.out.println();

        System.out.println("(5) Testing on \"1234567890chicken~;|!@#$%^&*()neckihc\"");
        try {
            boolean results = isPal("1234567890chicken~;|!@#$%^&*()nekcihc");
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println("true");
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == true);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }

        System.out.println();

        System.out.println("(6) Testing on \"Do it for the @vine--\"");
        try {
            boolean results = isPal("Do it for the @vine--");
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println("false");
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == false);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
    }
}