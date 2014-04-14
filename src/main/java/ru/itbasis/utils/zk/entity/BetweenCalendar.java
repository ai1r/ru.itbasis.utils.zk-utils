package ru.itbasis.utils.zk.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import ru.itbasis.utils.zk.DateUtils;

import java.util.Calendar;

public class BetweenCalendar {
	private Calendar start;
	private Calendar end;

	public BetweenCalendar() {
		Calendar c = Calendar.getInstance();
		setStart(DateUtils.getFirstDay(c));
		setEnd(DateUtils.getLastDay(c));
	}

	public BetweenCalendar(Calendar start, Calendar end) {
		setStart(start);
		setEnd(end);
	}

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("start", start != null ? start.getTimeInMillis() : null);
		builder.append("end", end != null ? end.getTimeInMillis() : null);
		return builder.toString();
	}

	public boolean isValid() {
		return DateUtils.truncatedCompareTo(start, end, Calendar.DAY_OF_MONTH) <= 0;
	}

	public Calendar getStart() {
		return start;
	}

	public void setStart(Calendar start) {
		this.start = start;
	}

	public Calendar getEnd() {
		return end;
	}

	public void setEnd(Calendar end) {
		this.end = end;
	}
}
