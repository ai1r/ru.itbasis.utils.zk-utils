package ru.itbasis.utils.zk.ui.form.fields.combo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.OpenEvent;
import org.zkoss.zk.ui.util.ConventionWires;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Bandpopup;
import ru.itbasis.utils.core.ISelf;
import ru.itbasis.utils.zk.LogMsg;
import ru.itbasis.utils.zk.ui.form.fields.IField;

public abstract class AbstractComboField<Value> extends Bandbox implements ISelf<AbstractComboField>, IField<Value> {
	private static final transient Logger LOG = LoggerFactory.getLogger(AbstractComboField.class.getName());

	protected Value genericType;

	public AbstractComboField() {
		ConventionWires.wireVariables(this, this);
		setHflex(IField.DEFAULT_HFLEX);

		setReadonly(true);

		final Bandpopup popup = new Bandpopup();
		appendChild(popup);

		this.addEventListener(Events.ON_OPEN, new Event$Popup$onOpen());
	}

	protected abstract String coerceToString(final Object value);

	protected abstract void initPopup();

	@Override
	@SuppressWarnings("unchecked")
	public Value getRawValue() {
		if (_value == null) {
			return null;
		}
		return (Value) _value;
	}

	@Override
	public void setRawValue(final Object value) {
		LOG.trace(LogMsg.VALUE, value);
		super.setRawValue(value);
		Events.postEvent(Events.ON_CHANGE, getSelf(), value);
	}

	@Override
	protected Object marshall(final Object value) {
		return coerceToString(value);
	}

	private class Event$Popup$onOpen implements EventListener<OpenEvent> {
		@Override
		public void onEvent(final OpenEvent event) throws Exception {
			if (!event.isOpen()) {
				return;
			}
			if (getDropdown().getChildren().size() > 0) {
				return;
			}
			initPopup();
		}
	}
}
