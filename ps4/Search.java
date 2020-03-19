public class Search {
	public static boolean search(Object item, Object[] arr) {
	    if (arr == null) {
	        throw new IllegalArgumentException();
	    }
	    for (int i = 0; i < arr.length; i++) {
	        if (arr[i].equals(item)) {
	            return true;
	        }
	    }
	    return false;
	}
	public static boolean search(Object item, Object[] arr, int start) {
	    if (arr == null) {
	        throw new IllegalArgumentException();
	    }
	    if (start < arr.length) {
	    	if (arr[start] == item) {
	    		return true;
	    	} else {
	    		return search(item,arr,start + 1);
	    	}
	    }
	    return false;
	}
	public static void main(String[] args) {
		Object[] arr = {"apple",24,0.2,'k',"chicken","word"};
		System.out.println(search("word",arr));
		System.out.println(search("apple",arr,3));
	}
}