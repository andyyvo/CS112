/* 
 * BigInt.java
 *
 * A class for objects that represent non-negative integers of 
 * up to 20 digits.
 */
import java.util.Arrays;
public class BigInt  {
    // the maximum number of digits in a BigInt -- and thus the length
    // of the digits array
    private static final int SIZE = 20;
    
    // the array of digits for this BigInt object
    private int[] digits;
    
    // the number of significant digits in this BigInt object
    private int numSigDigits;

    /*
     * Default, no-argument constructor -- creates a BigInt that 
     * represents the number 0.
     */
    public BigInt() {
        this.digits = new int[SIZE];
        this.numSigDigits = 1;  // 0 has one sig. digit--the rightmost 0!
    }

    /*
     * Constructor -- creates BigInt that initializes an array input.
     */
    public BigInt(int[] arr) {
        validate(arr);
        this.digits = new int[BigInt.SIZE];
        int clip = 0;
        while (arr[clip] == 0 && clip < BigInt.SIZE - 1) {
            clip++;
        }
        this.numSigDigits = arr.length - clip;
        for (int i = BigInt.SIZE - this.getNumSigDigits(); i < BigInt.SIZE; i++) {
            this.digits[i] = arr[clip];
            clip++;
        }
    }

    /*
     * Validate -- private helper class
     * Makes sure an array for BigInt is allowed.
     */
    private void validate(int[] arr) {
        if (arr == null || arr.length > BigInt.SIZE) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] >= 10) {
                throw new IllegalArgumentException();
            }
        }
    }

    /*
     * Getter method for numSigDigits.
     */
    public int getNumSigDigits() {
        return this.numSigDigits;
    }
    
    /*
     * toString method.
     */
    public String toString() {
        String numString = "";
        for (int i = BigInt.SIZE - this.getNumSigDigits(); i < BigInt.SIZE; i++) {
            numString += this.digits[i];
        }
        return numString;
    }

    /*
     * Constructor -- intializes an integer input.
     */
    public BigInt(int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        this.digits = new int[BigInt.SIZE];
        int indx = BigInt.SIZE - 1;
        int numSD = 0;
        if (n == 0) {
            this.numSigDigits = 1;
        }
        while (n > 0) {
            this.digits[indx] = n % 10;
            n = n / 10;
            indx--;
            numSD++;
        }
        this.numSigDigits = numSD;
    }

    /*
     * Compares one BigInt value to another.
     * this.compareTo(other);
     * if this > other, return 1
     * if this < other, return -1
     * if this == other, return 0
     */
    public int compareTo(BigInt other) {
        if (other == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < BigInt.SIZE; i++) {
            if (this.digits[i] < other.digits[i]) {
                return -1;
            } else if (this.digits[i] > other.digits[i]) {
                return 1;
            }
        }
        return 0;
    }

    /*
     * Add two BigInt objects together.
     * this.add(other);
     */
    public BigInt add(BigInt other) {
        if (other == null) {
            throw new IllegalArgumentException();
        }
        int[] copy = new int[BigInt.SIZE];
        for (int a = 0; a < BigInt.SIZE; a++) {
            copy[a] = this.digits[a];
        }
        boolean carry = false;
        for (int i = BigInt.SIZE - 1; i >= 0; i--) {
            copy[i] += other.digits[i];
            if (carry == true) {
                copy[i] += 1;
                carry = false;
            }
            if (copy[i] >= 10) {
                carry = true;
                copy[i] -= 10;
            }
            if (i == 0 && carry == true) {
                throw new ArithmeticException();
            }
        }
        BigInt newSum = new BigInt(copy);
        return newSum;
    }

    /*
     * Multiply two BigInt objects together.
     * this.mul(other);
     */
    public BigInt mul(BigInt other) {
        if (other == null) {
            throw new IllegalArgumentException();
        }
        int addVal = 0;
        int carryVal = 0;
        BigInt total = new BigInt();
        BigInt tempTotal = new BigInt();
        for (int i = BigInt.SIZE - 1; i > BigInt.SIZE - this.getNumSigDigits() - 1; i--) {
            int[] arrTotal = new int[BigInt.SIZE];
            for (int j = BigInt.SIZE - 1; j > BigInt.SIZE - other.getNumSigDigits() - 2; j--) {
                addVal = this.digits[i] * other.digits[j] + carryVal;
                carryVal = addVal / 10;
                addVal = addVal % 10;
                arrTotal[i + j - 19] = addVal;
                if ((i + j - 20) < 0) {
                    if (carryVal > 0) {
                        throw new ArithmeticException();
                    } else {
                        break;
                    }
                } else {
                    arrTotal[i + j - 20] = carryVal;
                }
            }
            tempTotal = new BigInt(arrTotal);
            total = total.add(tempTotal);
        }
        return total;        
    }
    public static void main(String [] args) {
        System.out.println("Unit tests for the BigInt class.");
        System.out.println();

        /* 
         * You should uncomment and run each test--one at a time--
         * after you build the corresponding methods of the class.
         */
        
        System.out.println("Test 1: result should be 7");
        int[] a1 = { 0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,3,4,5,6,7 };
        BigInt b1 = new BigInt(a1);
        System.out.println(b1.getNumSigDigits());
        System.out.println();
        
        System.out.println("Test 2: result should be 1234567");
        b1 = new BigInt(a1);
        System.out.println(b1);
        System.out.println();

        System.out.println("Test 3: result should be 0");
        int[] a2 = { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 };
        BigInt b2 = new BigInt(a2);
        System.out.println(b2);
        System.out.println();

        System.out.println("Test 4: should throw an IllegalArgumentException");
        try {
            int[] a3 = { 0,0,0,0,23,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 };
            BigInt b3 = new BigInt(a3);
            System.out.println("Test failed.");
        } catch (IllegalArgumentException e) {
            System.out.println("Test passed.");
        } catch (Exception e) {
            System.out.println("Test failed: threw wrong type of exception.");
        }
        System.out.println();

        System.out.println("Test 5: result should be 1234567");
        b1 = new BigInt(1234567);
        System.out.println(b1);
        System.out.println();

        System.out.println("Test 6: result should be 0");
        b2 = new BigInt(0);
        System.out.println(b2);
        System.out.println();

        System.out.println("Test 7: should throw an IllegalArgumentException");
        try {
            BigInt b3 = new BigInt(-4);
            System.out.println("Test failed.");
        } catch (IllegalArgumentException e) {
            System.out.println("Test passed.");
        } catch (Exception e) {
            System.out.println("Test failed: threw wrong type of exception.");
        }
        System.out.println();

        System.out.println("Test 8: result should be 0");
        b1 = new BigInt(12375);
        b2 = new BigInt(12375);
        System.out.println(b1.compareTo(b2));
        System.out.println();

        System.out.println("Test 9: result should be -1");
        b2 = new BigInt(12378);
        System.out.println(b1.compareTo(b2));
        System.out.println();

        System.out.println("Test 10: result should be 1");
        System.out.println(b2.compareTo(b1));
        System.out.println();

        System.out.println("Test 11: result should be 0");
        b1 = new BigInt(0);
        b2 = new BigInt(0);
        System.out.println(b1.compareTo(b2));
        System.out.println();

        System.out.println("Test 12: result should be\n123456789123456789");
        int[] a4 = { 3,6,1,8,2,7,3,6,0,3,6,1,8,2,7,3,6 };
        int[] a5 = { 8,7,2,7,4,0,5,3,0,8,7,2,7,4,0,5,3 };
        BigInt b4 = new BigInt(a4);
        BigInt b5 = new BigInt(a5);
        BigInt sum = b4.add(b5);
        System.out.println(sum);
        System.out.println();

        System.out.println("Test 13: result should be\n123456789123456789");
        System.out.println(b5.add(b4));
        System.out.println();

        System.out.println("Test 14: result should be\n3141592653598");
        b1 = new BigInt(0);
        int[] a6 = { 3,1,4,1,5,9,2,6,5,3,5,9,8 };
        b2 = new BigInt(a6);
        System.out.println(b1.add(b2));
        System.out.println();

        System.out.println("Test 15: result should be\n10000000000000000000");
        int[] a19 = { 9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9 };    // 19 nines!
        b1 = new BigInt(a19);
        b2 = new BigInt(1);
        System.out.println(b1.add(b2));
        System.out.println();

        System.out.println("Test 16: should throw an ArithmeticException");
        int[] a20 = { 9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9 };  // 20 nines!
        try {
            b1 = new BigInt(a20);
            System.out.println(b1.add(b2));
        } catch (ArithmeticException e) {
            System.out.println("Test passed.");
        } catch (Exception e) {
            System.out.println("Test failed: threw wrong type of exception.");
        }
        System.out.println();

        System.out.println("Test 17: result should be 5670");
        b1 = new BigInt(135);
        b2 = new BigInt(42);
        BigInt product = b1.mul(b2);
        System.out.println(product);
        System.out.println();

        System.out.println("Test 18: result should be\n99999999999999999999");
        b1 = new BigInt(a20);   // 20 nines -- see above
        b2 = new BigInt(1);
        System.out.println(b1.mul(b2));
        System.out.println();

        System.out.println("Test 19: should throw an ArithmeticException");
        try {
            b1 = new BigInt(a20);
            b2 = new BigInt(2);
            System.out.println(b1.mul(b2));
        } catch (ArithmeticException e) {
            System.out.println("Test passed.");
        } catch (Exception e) {
            System.out.println("Test failed: threw wrong type of exception.");
        }
        System.out.println();
    }
}
