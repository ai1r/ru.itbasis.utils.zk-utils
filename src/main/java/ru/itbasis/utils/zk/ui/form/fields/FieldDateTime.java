package ru.itbasis.utils.zk.ui.form.fields;

import org.apache.commons.lang3.time.DateUtils;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Timebox;

import java.util.Calendar;

public class FieldDateTime extends AbstractField<Calendar> {
	private Datebox _date;
	private Timebox _time;

	public FieldDateTime() {
		super();

		_date = new Datebox();
		_date.setHflex(DEFAULT_HFLEX);
		_date.setParent(getBox());

		_time = new Timebox();
		_time.setHflex(DEFAULT_HFLEX);
		_time.setParent(getBox());
	}

	public FieldDateTime(EventListener<Event> listener) {
		this();
		_date.addEventListener(Events.ON_CHANGE, listener);
		_time.addEventListener(Events.ON_CHANGE, listener);
	}

	@Override
	public Calendar getValue() {
		Calendar calD = DateUtils.toCalendar(_date.getValue());
		Calendar calT = DateUtils.toCalendar(_time.getValue());
		calD.set(Calendar.HOUR, calT.get(Calendar.HOUR));
		calD.set(Calendar.MINUTE, calT.get(Calendar.MINUTE));
		calD.set(Calendar.SECOND, calT.get(Calendar.SECOND));
		return calD;
	}

	@Override
	public void setValue(Calendar value) {
		if (value == null) {
			clear();
		} else {
			_date.setValue(value.getTime());
			_time.setValue(value.getTime());
		}
		Events.postEvent(Events.ON_CHANGE, _time, value);
	}

	@Override
	public void clear() {
		_date.setValue(null);
		_time.setValue(null);
	}
}
