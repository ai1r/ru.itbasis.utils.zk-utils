package ru.itbasis.utils.zk.ui.toolbar;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Calendar;
import org.zkoss.zul.Combobutton;
import org.zkoss.zul.Toolbar;
import ru.itbasis.utils.zk.DateUtils;

public class ToolbarCalendar extends ToolbarCombo {
	private Calendar calendar;
	private String   label;

	public ToolbarCalendar(Toolbar toolbar, String label, EventListener<Event> listener) {
		this(toolbar, label, java.util.Calendar.getInstance(), listener);
	}

	public ToolbarCalendar(Toolbar toolbar, String label, java.util.Calendar date, EventListener<Event> listener) {
		super(toolbar);
		this.label = label;
		initCalendar(date);
		updateLabel();
		calendar.addEventListener(Events.ON_CHANGE, listener);
	}

	private void initCalendar(java.util.Calendar value) {
		calendar = new Calendar();
		calendar.setParent(getDropdown());
		calendar.setValue(value.getTime());
		calendar.addEventListener(Events.ON_CHANGE, new Event$OnChange$Calendar());
	}

	public java.util.Calendar getValue() {
		return org.apache.commons.lang3.time.DateUtils.toCalendar(calendar.getValue());
	}

	private void updateLabel() {
		setLabel(Labels.getRequiredLabel(label, new String[]{DateUtils.getShortDate(calendar.getValue())}));
	}

	private class Event$OnChange$Calendar implements EventListener<Event> {

		@Override
		public void onEvent(Event event) throws Exception {
			// TODO Убрать событие для листания календаря
			updateLabel();
			((Combobutton) _this).getDropdown().close();
		}
	}
}