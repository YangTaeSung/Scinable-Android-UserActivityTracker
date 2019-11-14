import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UtilPractice {

	public static void main(String args[]) {
	
		/* today() 
		SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
		Date time = new Date();
		String time1 = format1.format(time);
		
		System.out.println(time1); */
		
		
		/* getAge() */
		Calendar calendar = Calendar.getInstance();
		
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int date = calendar.get(Calendar.DATE);
		
		int today = year * 10000 + month * 100 + 100 + date;
		System.out.println(today);
		System.out.println((today-19980421)/10000);
		
	}
	
}
