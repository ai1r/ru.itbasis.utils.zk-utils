package ru.itbasis.utils.zk.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import ru.itbasis.utils.zk.DateUtils;

import java.util.Calendar;

public final class BetweenCalendar {
	private Calendar start;
	private Calendar end;

	public BetweenCalendar() {
		final Calendar c = Calendar.getInstance();
		this.setStart(DateUtils.getFirstDay(c));
		this.setEnd(DateUtils.getLastDay(c));
	}

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("start", this.start != null ? this.start.getTimeInMillis() : null);
		builder.append("end", this.end != null ? this.end.getTimeInMillis() : null);
		return builder.toString();
	}

	public boolean isValid() {
		return DateUtils.truncatedCompareTo(this.start, this.end, Calendar.DAY_OF_MONTH) <= 0;
	}

	public Calendar getStart() {
		return this.start;
	}

	public BetweenCalendar setStart(final Calendar value) {
		this.start = value;
		return this;
	}

	public Calendar getEnd() {
		return this.end;
	}

	public BetweenCalendar setEnd(final Calendar value) {
		this.end = value;
		return this;
	}
}
