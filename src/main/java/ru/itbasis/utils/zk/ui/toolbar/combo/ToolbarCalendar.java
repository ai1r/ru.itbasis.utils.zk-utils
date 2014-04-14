package ru.itbasis.utils.zk.ui.toolbar.combo;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Calendar;
import org.zkoss.zul.Toolbar;
import ru.itbasis.utils.zk.DateUtils;

public class ToolbarCalendar extends ToolbarComboFilter<java.util.Calendar> {
	private Calendar calendar;

	public ToolbarCalendar(Toolbar toolbar, String labelName, EventListener<Event> listener) {
		super(toolbar, labelName);
		calendar.addEventListener(Events.ON_CHANGE, listener);
	}

	@Override
	protected void initPopup() {
		calendar = new Calendar();
		calendar.setParent(getDropdown());
		calendar.addEventListener(Events.ON_CHANGE, new Event$OnChange$Calendar());
	}

	@Override
	protected void updateLabel() {
		setLabel(Labels.getRequiredLabel(labelName, new String[]{DateUtils.getShortDate(filter)}));
	}

	private class Event$OnChange$Calendar implements EventListener<Event> {

		@Override
		public void onEvent(Event event) throws Exception {
			// TODO Убрать событие для листания календаря
			filter = org.apache.commons.lang3.time.DateUtils.toCalendar(calendar.getValue());
			updateLabel();
			getDropdown().close();
		}
	}
}