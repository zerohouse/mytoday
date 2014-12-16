package mytoday;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

	private Util(){
		
	}
	
	public static Date parseDate(Object object, String dateformat){
		SimpleDateFormat datetime = new SimpleDateFormat(dateformat);
		Date date = null;
		try {
			date = datetime.parse(object.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
}
