package ru.itbasis.utils.zk.ui.form.fields;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.ConventionWires;
import org.zkoss.zul.Button;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.impl.InputElement;
import ru.itbasis.utils.core.ISelf;

public abstract class AbstractField<T> extends AbstractComponent implements IField<T>, ISelf<AbstractField> {
	private static final transient Logger LOG = LoggerFactory.getLogger(AbstractField.class.getName());

	private HtmlBasedComponent box;

	static {
		addClientEvent(InputElement.class, Events.ON_CHANGE, CE_IMPORTANT | CE_REPEAT_IGNORE);
		addClientEvent(InputElement.class, Events.ON_CHANGING, CE_DUPLICATE_IGNORE);
		addClientEvent(InputElement.class, Events.ON_ERROR, CE_DUPLICATE_IGNORE | CE_IMPORTANT);
	}

	public AbstractField() {
		ConventionWires.wireVariables(this, this);
	}

	public AbstractField(final EventListener<Event> listener) {
		this();
		this.addEventListener(Events.ON_CHANGE, listener);
	}

	@SuppressWarnings("unused")
	protected Button appendActionAdd(final EventListener<Event> listener) {
		final Button action = new Button();
		action.setIconSclass(ICON_NEW);
		if (listener != null) {
			action.addEventListener(Events.ON_CLICK, listener);
		}
		action.setParent(getBox());
		return action;
	}

	@SuppressWarnings("unused")
	protected Button appendActionEdit(final EventListener<Event> listener) {
		final Button action = new Button();
		action.setIconSclass(ICON_EDIT);
		if (listener != null) {
			action.addEventListener(Events.ON_CLICK, listener);
		}
		action.setParent(getBox());
		return action;
	}

	@SuppressWarnings("unused")
	protected Button appendActionRefresh(final EventListener<Event> listener) {
		final Button action = new Button();
		action.setIconSclass(ICON_REFRESH);
		if (listener != null) {
			action.addEventListener(Events.ON_CLICK, listener);
		}
		action.setParent(getBox());
		return action;
	}

	public HtmlBasedComponent getBox() {
		if (box == null) {
			initBox();
		}
		return box;
	}

	@Deprecated
	public final T getValue() {
		return getRawValue();
	}

	@SuppressWarnings("unused")
	@Deprecated
	public final void setValue(final T value) {
		setRawValue(value);
		Events.postEvent(Events.ON_CHANGE, getSelf(), getRawValue());
	}

	protected HtmlBasedComponent initBox() {
		box = new Hbox();
		box.setHflex(DEFAULT_WIDTH);
		return box;
	}

	@SuppressWarnings("unused")
	public boolean isVisibleRow() {
		return box.getParent().getParent().isVisible();
	}

	@SuppressWarnings("unused")
	public void setVisibleRow(final boolean flag) {
		LOG.trace("box: {}", box);
		box.getParent().getParent().setVisible(flag);
	}

	public class Event$Default$OnChange implements EventListener<Event> {
		@Override
		public void onEvent(final Event event) throws Exception {
			Events.postEvent(Events.ON_CHANGE, getSelf(), getRawValue());
		}
	}

}
