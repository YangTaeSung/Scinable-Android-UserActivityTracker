
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
	
	// �������ڸ� �Ű������� �޴� �Լ��� ȣ�� �� �Ű������� �Է����� �ʾƵ� ��� ����.
	public static void test1(String... b) {
		
		System.out.println("good");
		
	}
	
}
