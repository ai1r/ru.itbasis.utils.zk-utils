package ru.itbasis.utils.zkoss.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.time.DateUtils;
import ru.itbasis.utils.zkoss.ZkDateUtils;

import java.util.Calendar;

public final class BetweenCalendar {
	private Calendar _start;
	private Calendar _end;

	public BetweenCalendar(final Calendar start, final Calendar end) {
		setStart(start);
		setEnd(end);
	}

	public BetweenCalendar() {
		final Calendar c = Calendar.getInstance();
		this.setStart(ZkDateUtils.getFirstDay(c));
		this.setEnd(ZkDateUtils.getLastDay(c));
	}

	public Calendar getEnd() {
		return this._end;
	}

	public BetweenCalendar setEnd(final Calendar value) {
		this._end = value;
		return this;
	}

	public Calendar getStart() {
		return this._start;
	}

	public BetweenCalendar setStart(final Calendar value) {
		this._start = value;
		return this;
	}

	public boolean isValid() {
		return DateUtils.truncatedCompareTo(this._start, this._end, Calendar.DAY_OF_MONTH) <= 0;
	}

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("start", this._start != null ? this._start.getTimeInMillis() : null);
		builder.append("end", this._end != null ? this._end.getTimeInMillis() : null);
		return builder.toString();
	}
}
