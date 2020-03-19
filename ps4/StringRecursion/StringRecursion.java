public class StringRecursion {

    // The methods that you write must be purely recursive. The use of iteration (i.e., for, while, or do-while loops) is not allowed.

    // The only built-in String methods that you may use are charAt, length, equals, and substring. No use of other String methods is allowed.
    // In addition, make sure to follow any additional restrictions specified in the problem.

    // Do not use any static variables — i.e., variables that are declared outside of a method.

    // Use the headers specified for the methods without changing them in any way.

    // Limit yourself to writing the methods specified below. Do not write any additional “helper” methods that assist the required methods;
    // rather, the methods listed below should provide all of their required functionality by themselves.

    /*
     * Recursively print "str" backwards
     */
    public static void printReverse(String str) {
        if (str.equals("") || str == null) {
            System.out.println(str);
        } else {
            System.out.print(str.charAt(str.length() - 1));
            printReverse(str.substring(0,str.length() - 1));
        }
    }

    /*
     * Recursively return a stirng in which any leading and/or
     * trailing spaces in "str" are removed
     */
    public static String trim(String str) {
        if (str == null) {
            return null;
        } else if (str.equals("")) {
            return "";
        } else {
            if (str.charAt(0) != ' ' && str.charAt(str.length() - 1) != ' ') {
                return str;
            } else if (str.charAt(0) == ' ' && str.charAt(str.length() - 1) == ' ') {
                return trim(str.substring(1, str.length() - 1));
            } else if (str.charAt(0) == ' ' && str.charAt(str.length() - 1) != ' ') {
                return trim(str.substring(1));
            } else if (str.charAt(0) != ' ' && str.charAt(str.length() - 1) == ' ') {
                return trim(str.substring(0,str.length() - 1));
            }
        }
        return str;
    }

    /*
     * Recursively find and return the index of the first occurence of
     * the character "ch" in the string "str", or -1 if "ch" does not
     * occur in "str".
     */
    public static int find(char ch, String str) {
        if (str.equals("") || str == null) {
            return -1;
        } else if (str.charAt(0) == ch) {
            return 0;
        } else if (str.charAt(0) != ch) {
            int finding = find(ch, str.substring(1));
            if (finding == -1) {
                return -1;
            } else {
                return finding + 1;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        printReverse("Terriers");
        System.out.println(trim(" hello world    "));
        System.out.println(find('b',"Rabbit"));
        System.out.println(find('P',"Rabbit"));
    }
}