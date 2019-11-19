import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.sql.Timestamp;

public class UtilPractice {

	public static void main(String args[]) {
	
		/* today() */
		SimpleDateFormat format1 = new SimpleDateFormat("E,d MMMM yyyyMMdd");
		Date time = new Date();
		String time1 = format1.format(time);
		
		System.out.println(time1); 
		
		
		/* getAge() */
		Calendar calendar = Calendar.getInstance();
		
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int date = calendar.get(Calendar.DATE);
		
		int today = year * 10000 + month * 100 + 100 + date;
		System.out.println(today);
		System.out.println((today-19980421)/10000);
		
		
		/* 1970/1/1 ���ķ��� �ð��� �и��ʷ� ��� 
		 * System.currentTimeMillis();
		 * ��� ���� GMTString
		 * toGMTString() */ 
		Calendar cal = Calendar.getInstance();
		long expires = cal.getTimeInMillis(); // 1970/1/1 ���ķ��� �ð��� �и��ʷ� �޾ƿ�.
		System.out.println(expires);
		
		Date timegmt2 = new Date(expires);
		System.out.println(timegmt2.toGMTString());
		// toGMTString()�Լ��� �ȵ���̵忡�� ��� �Ұ�, �밡�ٷ� �����.
		
		
	}
	
}
