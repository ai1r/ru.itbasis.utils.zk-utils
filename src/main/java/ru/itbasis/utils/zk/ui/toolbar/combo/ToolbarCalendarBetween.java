package ru.itbasis.utils.zk.ui.toolbar.combo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.*;
import ru.itbasis.utils.zk.DateUtils;
import ru.itbasis.utils.zk.LogMsg;
import ru.itbasis.utils.zk.entity.BetweenCalendar;

public class ToolbarCalendarBetween extends ToolbarComboFilter<BetweenCalendar> {
	private transient static final Logger LOG = LoggerFactory.getLogger(ToolbarCalendarBetween.class.getName());

	private Calendar fieldStart;
	private Calendar fieldEnd;

	public ToolbarCalendarBetween(Toolbar parent) {
		super(parent);
		setFilter(new BetweenCalendar());
	}

	public ToolbarCalendarBetween(Toolbar parent, String labelName) {
		super(parent, labelName);
		setFilter(new BetweenCalendar());
	}

	public ToolbarCalendarBetween(Toolbar parent, String labelName, EventListener<Event> listener) {
		this(parent, labelName);
		addEventListener(Events.ON_CHANGE, listener);
	}

	@Override
	protected void initPopup() {
		Box hbox = new Hbox();
		fieldStart = new Calendar();
		fieldStart.setParent(hbox);
		fieldEnd = new Calendar();
		fieldEnd.setParent(hbox);

		Box vbox = new Vbox();
		vbox.setParent(getDropdown());
		vbox.appendChild(hbox);
		vbox.setAlign("end");

		appendButtonApply(vbox, new Event$Apply$OnClick());

		addEventListener(Events.ON_OPEN, new Event$OnOpen());
	}

	@Override
	public void setFilter(BetweenCalendar value) {
		super.setFilter(value);

		fieldStart.setValue(value.getStart().getTime());
		fieldEnd.setValue(value.getEnd().getTime());
	}

	@Override
	protected void updateLabel() {
		final String sStart = DateUtils.formatAsShortDate(filter.getStart());
		final String sEnd = DateUtils.formatAsShortDate(filter.getEnd());
		setLabel(Labels.getRequiredLabel(labelName, new Object[]{sStart, sEnd}));
	}

	private class Event$Apply$OnClick implements EventListener<Event> {
		@Override
		public void onEvent(Event event) throws Exception {
			LOG.trace(LogMsg.EVENT, event);

			final java.util.Calendar start = DateUtils.toCalendar(fieldStart.getValue());
			final java.util.Calendar end = DateUtils.toCalendar(fieldEnd.getValue());
			BetweenCalendar t = new BetweenCalendar(start, end);

			if (!t.isValid()) {
				// TODO Вынести labels в метод
				throw new WrongValueException(btnApply, Labels.getRequiredLabel("err.date.beetwen.startAfterEnd"));
			}
			getDropdown().close();
			setFilter(t);
		}
	}

	private class Event$OnOpen implements EventListener<Event> {
		@Override
		public void onEvent(Event event) throws Exception {
			fieldStart.setValue(filter.getStart().getTime());
			fieldEnd.setValue(filter.getEnd().getTime());
		}
	}
}
