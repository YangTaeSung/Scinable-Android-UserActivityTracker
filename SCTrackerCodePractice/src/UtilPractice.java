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
		
		
		/* 1970/1/1 이후로의 시간을 밀리초로 계산 
		 * System.currentTimeMillis();
		 * 출력 형식 GMTString
		 * toGMTString() */ 
		Calendar cal = Calendar.getInstance();
		long expires = cal.getTimeInMillis(); // 1970/1/1 이후로의 시간을 밀리초로 받아옴.
		System.out.println(expires);
		
		Date timegmt2 = new Date(expires);
		System.out.println(timegmt2.toGMTString());
		// toGMTString()함수는 안드로이드에서 사용 불가, 노가다로 만들기.
		
		
	}
	
}
