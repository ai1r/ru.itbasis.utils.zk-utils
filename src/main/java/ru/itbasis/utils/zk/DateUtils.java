package ru.itbasis.utils.zk;

import org.zkoss.text.DateFormats;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

final public class DateUtils {
	private static SimpleDateFormat SDF_SHORT;
	private static SimpleDateFormat SDF_DATE_SHORT;

	static {
		SDF_SHORT = new SimpleDateFormat(DateFormats.getDateTimeFormat(DateFormat.SHORT, DateFormat.SHORT, null, null));
		SDF_DATE_SHORT = new SimpleDateFormat(DateFormats.getDateFormat(DateFormat.SHORT, null, null));
	}

	private DateUtils() {
	}

	public static String getShortDate(Date date) {
		return getShortDate(org.apache.commons.lang3.time.DateUtils.toCalendar(date));
	}

	public static String getShortDate(Calendar calendar) {
		return SDF_DATE_SHORT.format(calendar.getTime());
	}
}