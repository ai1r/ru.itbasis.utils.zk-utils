package ru.itbasis.utils.zk.ui.form.fields;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.OpenEvent;
import org.zkoss.zk.ui.util.ConventionWires;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Bandpopup;
import ru.itbasis.utils.zk.IThis;

abstract public class AbstractComboField<T> extends Bandbox implements IThis<AbstractComboField>, IField<T> {
	private static final transient Logger LOG = LoggerFactory.getLogger(AbstractComboField.class.getName());

	protected T genericType;

	public AbstractComboField() {
		ConventionWires.wireVariables(this, this);
		setHflex(IField.DEFAULT_HFLEX);

		setReadonly(true);

		appendChild(new Bandpopup());

		this.addEventListener(Events.ON_OPEN, new Event$Popup$onOpen());
	}

	abstract protected String coerceToString(Object value);

	abstract protected void initPopup();

	@Override
	@SuppressWarnings("unchecked")
	public T getRawValue() {
		if (_value == null) {
			return null;
		}
		return (T) _value;
	}

	@Override
	public void setRawValue(Object value) {
		super.setRawValue(value);
		Events.postEvent(Events.ON_CHANGE, getThis(), value);
	}

	@Override
	protected Object marshall(Object value) {
		return coerceToString(value);
	}

	private class Event$Popup$onOpen implements EventListener<OpenEvent> {
		@Override
		public void onEvent(OpenEvent event) throws Exception {
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
