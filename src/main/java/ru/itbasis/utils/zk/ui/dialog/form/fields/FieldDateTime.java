package ru.itbasis.utils.zk.ui.dialog.form.fields;

import org.apache.commons.lang3.time.DateUtils;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Timebox;

import java.util.Calendar;

public class FieldDateTime extends AbstractField<Calendar> {
	private static final long serialVersionUID = 1309198443540591705L;

	private Datebox _date;
	private Timebox _time;

	public FieldDateTime() {
		super();

		final EventListener<Event> listener = new Event$Default$OnChange();

		_date = new Datebox();
		_date.setHflex(DEFAULT_HFLEX);
		_date.setParent(getBox());
		_date.addEventListener(Events.ON_CHANGE, listener);

		_time = new Timebox();
		_time.setHflex(DEFAULT_HFLEX);
		_time.setParent(getBox());
		_time.addEventListener(Events.ON_CHANGE, listener);

	}

	@Override
	public Calendar getRawValue() {
		final Calendar calD = DateUtils.toCalendar(_date.getValue());
		final Calendar calT = DateUtils.toCalendar(_time.getValue());
		calD.set(Calendar.HOUR, calT.get(Calendar.HOUR));
		calD.set(Calendar.MINUTE, calT.get(Calendar.MINUTE));
		calD.set(Calendar.SECOND, calT.get(Calendar.SECOND));
		return calD;
	}

	@Override
	public void setRawValue(final Calendar value) {
		if (value == null) {
			_date.setValue(null);
			_time.setValue(null);
		} else {
			_date.setValue(value.getTime());
			_time.setValue(value.getTime());
		}
	}

}
