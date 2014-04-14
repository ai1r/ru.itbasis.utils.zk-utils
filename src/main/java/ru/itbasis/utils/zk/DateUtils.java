package ru.itbasis.utils.zk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.text.DateFormats;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	private transient static final Logger LOG = LoggerFactory.getLogger(DateUtils.class.getName());

	private static SimpleDateFormat SDF_SHORT;
	private static SimpleDateFormat SDF_DATE_SHORT;

	static {
		SDF_SHORT = new SimpleDateFormat(DateFormats.getDateTimeFormat(DateFormat.SHORT, DateFormat.SHORT, null, null));
		SDF_DATE_SHORT = new SimpleDateFormat(DateFormats.getDateFormat(DateFormat.SHORT, null, null));
	}

	public static Calendar getFirstDay(final Calendar value) {
		Calendar result = truncate(value, Calendar.MONTH);
		LOG.trace(LogMsg.VALUE, result.getTime());
		return result;
	}

	public static Calendar getLastDay(final Calendar value) {
		Calendar result = ceiling(value, Calendar.MONTH);
		result.add(Calendar.DAY_OF_MONTH, -1);
		LOG.trace(LogMsg.VALUE, result.getTime());
		return result;
	}

	@Deprecated
	public static String getShortDate(final Date value) {
		return formatAsShortDate(value);
	}

	public static String formatAsShortDate(final Date date) {
		return getShortDate(toCalendar(date));
	}

	@Deprecated
	public static String getShortDate(final Calendar value) {
		return formatAsShortDate(value);
	}

	public static String formatAsShortDate(final Calendar value) {
		return SDF_DATE_SHORT.format(value.getTime());
	}
}