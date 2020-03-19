public class sumMaker {
	public static void generateSums(int n) {
    	for (int i = 1; i <= n; i++) {
        	int sum = 0;
        	for (int j = 1; j <= i; j++) {
            	sum = sum + j; // how many times is this executed?
        	}
        	System.out.println(sum);
    	}
	}

	public static void sumGen(int n) {
		int sum = 0;
		int timer = 1;
		while (timer <= n) {
			sum += timer;
			timer++;
			System.out.println(sum);
		}
	}
	public static void main(String[] args) {
		generateSums(4);
		sumGen(4);
	}
}