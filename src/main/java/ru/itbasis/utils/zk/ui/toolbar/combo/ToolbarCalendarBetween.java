package ru.itbasis.utils.zk.ui.toolbar.combo;

import org.apache.commons.lang3.time.DateUtils;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Box;
import org.zkoss.zul.Calendar;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Vbox;
import ru.itbasis.utils.zk.entity.BetweenCalendar;

public class ToolbarCalendarBetween extends ToolbarComboFilter<BetweenCalendar> {
	private static final long serialVersionUID = 4308433607796959147L;

	private Calendar fieldStart;
	private Calendar fieldEnd;

	public ToolbarCalendarBetween(final Toolbar parent) {
		super(parent);
		setFilter(new BetweenCalendar());
	}

	public ToolbarCalendarBetween(final Toolbar parent, final String labelName) {
		super(parent, labelName);
		setFilter(new BetweenCalendar());
	}

	public ToolbarCalendarBetween(final Toolbar parent, final String labelName, final EventListener<Event> listener) {
		this(parent, labelName);
		addEventListener(Events.ON_CHANGE, listener);
	}

	@Override
	protected void initPopup() {
		final Box hbox = new Hbox();
		fieldStart = new Calendar();
		fieldStart.setParent(hbox);
		fieldEnd = new Calendar();
		fieldEnd.setParent(hbox);

		final Box vbox = new Vbox();
		vbox.setParent(getDropdown());
		vbox.appendChild(hbox);
		vbox.setAlign("end");

		appendButtonApply(vbox, new Event$Apply$OnClick());

		addEventListener(Events.ON_OPEN, new Event$OnOpen());
	}

	@Override
	public void setFilter(final BetweenCalendar value) {
		super.setFilter(value);

		fieldStart.setValue(value.getStart().getTime());
		fieldEnd.setValue(value.getEnd().getTime());
	}

	@Override
	protected void updateLabel() {
		final String sStart = ru.itbasis.utils.core.utils.DateUtils.formatAsShortDate(filter.getStart());
		final String sEnd = ru.itbasis.utils.core.utils.DateUtils.formatAsShortDate(filter.getEnd());
		setLabel(Labels.getRequiredLabel(labelName, new Object[]{sStart, sEnd}));
	}

	private class Event$Apply$OnClick implements EventListener<Event> {
		public static final String MSG_ERROR_DATE_BEETWEN_START_AFTER_END = "err.date.beetwen.startAfterEnd";

		@Override
		public void onEvent(final Event event) throws Exception {
			final java.util.Calendar start = DateUtils.toCalendar(fieldStart.getValue());
			final java.util.Calendar end = DateUtils.toCalendar(fieldEnd.getValue());
			final BetweenCalendar t = new BetweenCalendar().setStart(start).setEnd(end);

			if (!t.isValid()) {
				// TODO Вынести labels в метод
				throw new WrongValueException(btnApply, Labels.getRequiredLabel(MSG_ERROR_DATE_BEETWEN_START_AFTER_END));
			}
			getDropdown().close();
			setFilter(t);
		}
	}

	private class Event$OnOpen implements EventListener<Event> {
		@Override
		public void onEvent(final Event event) throws Exception {
			fieldStart.setValue(filter.getStart().getTime());
			fieldEnd.setValue(filter.getEnd().getTime());
		}
	}
}
