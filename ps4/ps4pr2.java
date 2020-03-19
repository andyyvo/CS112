public class ps4pr2 {
	public static int foo(int x, int y) {
    	if (y == 0) {
        	return x;
    	} else if (x <= y) {
        	return y;
	    } else {
 	        int result1 = foo(x - 1, y - 1);
	        int result2 = foo(x - 1, y + 1);
	        return result1 + result2;
	    }
	}
	public static void main(String[] args) {
		System.out.println(foo(5,3));
	}
}