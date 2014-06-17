package ru.itbasis.utils.zk.ui.form.fields;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
		_text.addEventListener(Events.ON_CHANGE, new Event$Default$OnChange());
	}

	@Override
	public String getRawValue() {
		return _text.getValue();
	}

	@Override
	public void setRawValue(String value) {
		if (value == null) {
			_text.setRawValue("");
			return;
		}
		_text.setValue(value);
	}

	public int getRows() {
		return _text.getRows();
	}

	public void setRows(int value) {
		assert value > 0;
		_text.setRows(value);
	}

	public void setConstraint(String value) {
		LOG.debug("constraint: {}", value);
		_text.setConstraint(value);
	}
}
