package ru.itbasis.utils.zk.ui.dialog.form.fields;

import org.zkoss.zul.Label;

public class FieldId extends AbstractField<Long> {
	private Long  id;
	private Label label;

	public FieldId() {
		super();
		label = new Label();
		label.setHflex(DEFAULT_HFLEX);
		label.setParent(getBox());
	}

	public FieldId(final Long value) {
		this();
		setRawValue(value);
	}

	@Override
	public Long getRawValue() {
		return id;
	}

	@Override
	public void setRawValue(final Long value) {
		this.id = value;
		if (value != null && value > 0) {
			label.setValue(Long.toString(value));
		} else {
			label.setValue("");
		}
	}
}
