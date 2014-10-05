package ru.itbasis.utils.zk.ui.dialog.form.fields.text;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Textbox;
import ru.itbasis.utils.zk.ui.dialog.form.fields.AbstractField;

public class FieldText extends AbstractField<String> {
	public static final int DEFAULT_ROWS = 3;

	public static final String LABEL_CONSTRAINT_EMAIL = "constraint.email";

	private static final transient Logger LOG = LoggerFactory.getLogger(FieldText.class.getName());

	private static final long serialVersionUID = -576938683137585852L;

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
	public void setRawValue(final String rawValue) {
		if (rawValue == null) {
			_text.setRawValue("");
			return;
		}
		_text.setValue(rawValue);
	}

	public int getRows() {
		return _text.getRows();
	}

	public void setRows(final int rows) {
		assert rows > 0;
		_text.setRows(rows);
	}

	public void setConstraint(final String constraint) {
		LOG.debug("constraint: {}", constraint);
		_text.setConstraint(constraint);
	}
}
