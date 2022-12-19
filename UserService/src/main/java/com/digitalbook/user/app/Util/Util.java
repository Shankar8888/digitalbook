package com.digitalbook.user.app.Util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

public class Util {
	 public static Date convertServerTimeToUTC() {	

			
			String dateFormat = "dd-MMM-yyyy HH:mm:ss";
	    	SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
	    	TimeZone utcTimeZone = TimeZone.getTimeZone("UTC");
	    	sdf.setTimeZone(utcTimeZone);
	    	String strUTCDate = sdf.format(new Date());
	    	
	    	String dateFormat1 = "dd-MMM-yyyy HH:mm:ss";
	    	SimpleDateFormat sdf1 = new SimpleDateFormat(dateFormat1);  
	    	
	    	Date utcDate=null;
	    	
	    	try{
	    		utcDate = sdf1.parse(strUTCDate);
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}    	
			return utcDate; 
			
		}
	  
	  public static String getDateTimeStr(String format) {	

			
		//String dateFormat = "dd-MMM-yyyy HH:mm:ss";
	  	SimpleDateFormat sdf = new SimpleDateFormat(format);
	  	String strDate = sdf.format(new Date());
	    	
			return strDate; 
			
		}


		public static Date covertLocalTimeToUTC(String localTime) {
		
			Date utcTime = new Date();
			String dateFormat = "dd-MMM-yyyy HH:mm:ss";
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			TimeZone utcTimeZone = TimeZone.getTimeZone("UTC");
			sdf.setTimeZone(utcTimeZone);
			String strUTCDate = sdf.format(localTime);
			try {
				utcTime = sdf.parse(strUTCDate);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return utcTime;
		}

		public static Date convertUTCToLocalTime(Date utcTime) {
		
			Date localTime = null;
			try {
		
				long ts = System.currentTimeMillis();
				Date dt = new Date(ts);
				localTime = new Date(utcTime.getTime() + TimeZone.getDefault().getOffset(dt.getTime()));
		
			} catch (Exception e) {
				e.printStackTrace();
			}
		
			return localTime;
		}
		
		public static Date getDate() {
			return convertServerTimeToUTC();
		}
		
		public static LocalDateTime getLocalDateTime() {

//			//Getting the default zone id
//			ZoneId defaultZoneId = ZoneId.systemDefault();
//			
//			//Converting the date to Instant
//			Instant instant = convertServerTimeToUTC().toInstant();
//			
//			//Converting the Date to LocalDate
//			return instant.atZone(defaultZoneId).toLocalDate();
			
			return new java.sql.Timestamp(convertServerTimeToUTC().getTime()).toLocalDateTime();
		}
		public static LocalDate getLocalDate() {

//			//Getting the default zone id
//			ZoneId defaultZoneId = ZoneId.systemDefault();
//			
//			//Converting the date to Instant
//			Instant instant = convertServerTimeToUTC().toInstant();
//			
//			//Converting the Date to LocalDate
//			return instant.atZone(defaultZoneId).toLocalDate();
			
			return new java.sql.Date(convertServerTimeToUTC().getTime()).toLocalDate();
		}
		
		public static boolean isNull(Object obj) {
			return Objects.isNull(obj);
		}
		
		public static boolean isEmptyArray(List<?> array){	
			if ( !Objects.isNull(array))
			  return array.isEmpty();
			else
			  return	true;
		}
		
		public static boolean isNumeric(String number) {
			try{			
				double d = Double.parseDouble(number);
			}catch(Exception e){
				return false;
			}
			
			return true;
		}
		
		public static boolean isStringEmpty(String inputStr) {
			
			if(!Objects.isNull(inputStr))
				return  inputStr.length()==0;	
			else
				return true;
		}
		
}
