
public class TrackingPractice {

	public static void main(String[] args) {
	
		test("uu");
		test1();
	
	}
	
	public static void test(String a, String... b) {
		
		String jj = a;
		int ww = b.length;
		
		System.out.println(jj);
		System.out.println(ww);
	}
	
	// 가변인자를 매개변수로 받는 함수는 호출 시 매개변수를 입력하지 않아도 상관 없음.
	public static void test1(String... b) {
		
		System.out.println("good");
		
	}
	
}
