package ru.itbasis.utils.zk.ui.form.fields;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Textbox;

public class FieldText extends AbstractField<String> {
	private transient static final Logger LOG = LoggerFactory.getLogger(FieldText.class.getName());

	protected Textbox _text;

	public FieldText() {
		super();

		_text = new Textbox();
		_text.setHflex(DEFAULT_HFLEX);
		_text.setConstraint(CONSTRAINT_NOEMPTY);
		_text.setParent(box);
	}

	public FieldText(EventListener<Event> listener) {
		this();
		_text.addEventListener(Events.ON_CHANGE, listener);
	}

	@Override
	public String getValue() {
		return _text.getValue();
	}

	@Override
	public void setValue(String value) {
		LOG.trace("value: {}", value);
		if (value == null || value.isEmpty()) {
			clear();
			return;
		}
		_text.setValue(value);
		Events.postEvent(Events.ON_CHANGE, _text, value);
	}

	@Override
	public void clear() {
		_text.setValue("");
		Events.postEvent(Events.ON_CHANGE, _text, null);
	}

	public void setConstraint(String value) {
		LOG.debug("constraint: {}", value);
		_text.setConstraint(value);
	}
}
