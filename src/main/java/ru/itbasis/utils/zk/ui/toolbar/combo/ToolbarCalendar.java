package ru.itbasis.utils.zk.ui.toolbar.combo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Box;
import org.zkoss.zul.Calendar;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Vbox;
import ru.itbasis.utils.zk.DateUtils;

public class ToolbarCalendar extends ToolbarComboFilter<java.util.Calendar> {
	private transient static final Logger LOG = LoggerFactory.getLogger(ToolbarCalendar.class.getName());

	private Calendar calendar;


	public ToolbarCalendar(Toolbar toolbar, String labelName) {
		super(toolbar, labelName);
		setFilter(java.util.Calendar.getInstance());
	}

	public ToolbarCalendar(Toolbar toolbar, String labelName, EventListener<Event> listener) {
		this(toolbar, labelName);
		addEventListener(Events.ON_CHANGE, listener);
	}

	@Override
	protected void initPopup() {
		Box box = new Vbox();
		box.setParent(getDropdown());
		box.setAlign("end");

		calendar = new Calendar();
		calendar.setParent(box);

		appendButtonApply(box, new Event$Apply$OnClick());

		addEventListener(Events.ON_OPEN, new Event$OnOpen());
	}

	@Override
	protected void updateLabel() {
		setLabel(Labels.getRequiredLabel(labelName, new String[]{DateUtils.formatAsShortDate(filter)}));
	}

	private class Event$Apply$OnClick implements EventListener<Event> {
		@Override
		public void onEvent(Event event) throws Exception {
			getDropdown().close();
			setFilter(org.apache.commons.lang3.time.DateUtils.toCalendar(calendar.getValue()));
		}
	}

	private class Event$OnOpen implements EventListener<Event> {
		@Override
		public void onEvent(Event event) throws Exception {
			calendar.setValue(filter.getTime());
		}
	}
}