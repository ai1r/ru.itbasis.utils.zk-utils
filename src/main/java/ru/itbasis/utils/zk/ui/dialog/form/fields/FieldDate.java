package ru.itbasis.utils.zk.ui.dialog.form.fields;

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
		_date.setParent(getBox());
		_date.addEventListener(Events.ON_CHANGE, new Event$Default$OnChange());
	}

	public FieldDate(final EventListener<Event> listener) {
		super(listener);
	}

	@Override
	public Calendar getRawValue() {
		return DateUtils.toCalendar(_date.getValue());
	}

	@Override
	public void setRawValue(final Calendar value) {
		if (value != null) {
			_date.setValue(value.getTime());
		} else {
			_date.setValue(null);
		}
	}

}
