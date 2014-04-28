package ru.itbasis.utils.zk.ui.form.fields;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Textbox;

public class FieldText extends AbstractField<String> {
	private transient static final Logger LOG = LoggerFactory.getLogger(FieldText.class.getName());

	public static final int DEFAULT_ROWS = 3;

	protected Textbox _text;

	public FieldText() {
		super();

		_text = new Textbox();
		_text.setHflex(DEFAULT_HFLEX);
		_text.setParent(getBox());
	}

	public FieldText(EventListener<Event> listener) {
		this();
		addEventListener(Events.ON_CHANGE, listener);
	}

	@Override
	public String getValue() {
		return _text.getValue();
	}

	@Override
	public void setValue(String value) {
		if (value == null) {
			clear();
			return;
		}
		_text.setValue(value);
		Events.postEvent(Events.ON_CHANGE, _this, value);
	}

	@Override
	public void clear() {
		setValue("");
	}

	public void setConstraint(String value) {
		LOG.debug("constraint: {}", value);
		_text.setConstraint(value);
	}

	public void setRows(int value) {
		assert value > 0;
		_text.setRows(value);
	}

	public int getRows() {
		return _text.getRows();
	}
}
