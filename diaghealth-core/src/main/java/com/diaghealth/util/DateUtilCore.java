package com.diaghealth.util;

import java.util.Date;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class DateUtilCore {
	
	public static Date getCurrentDateIST(){
		
		//Server Time 
		Date date = new Date();
		//TimeZone tz = TimeZone.getDefault();
		
		DateTime dt = new DateTime(date);
		DateTimeZone dtZone = DateTimeZone.forID("Asia/Kolkata");
		DateTime dateTimeIST = dt.withZone(dtZone);
		//TimeZone timezoneIST = dtZone.toTimeZone();
		Date dateIST = dateTimeIST.toLocalDateTime().toDate(); //Convert to LocalDateTime first
		
		return dateIST;
	}
}
