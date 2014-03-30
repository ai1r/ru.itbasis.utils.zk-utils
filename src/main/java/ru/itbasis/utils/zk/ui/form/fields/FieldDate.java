package ru.itbasis.utils.zk.ui.form.fields;

import org.apache.commons.lang3.time.DateUtils;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Datebox;

import java.util.Calendar;

public class FieldDate extends AbstractField<Calendar> {
	private Datebox _date;

	public FieldDate() {
		super();
		_date = new Datebox();
		_date.setHflex(DEFAULT_HFLEX);
		_date.setParent(box);
	}

	public FieldDate(EventListener<Event> listener) {
		this();
		_date.addEventListener(Events.ON_CHANGE, listener);
	}

	@Override
	public void clear() {
		_date.setValue(null);
	}

	@Override
	public void setValue(Calendar value) {
		if (value != null) {
			_date.setValue(value.getTime());
		} else {
			clear();
		}
		Events.postEvent(Events.ON_CHANGE, _date, value);
	}

	@Override
	public Calendar getValue() {
		return DateUtils.toCalendar(_date.getValue());
	}

}