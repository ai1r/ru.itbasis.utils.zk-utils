package ru.itbasis.utils.zk.ui.form.fields;

import org.zkoss.zul.Label;

public class FieldId extends AbstractField<Long> {
	private Long  id;
	private Label label;

	public FieldId() {
		super();
		label = new Label();
		label.setHflex(DEFAULT_HFLEX);
		label.setParent(box);
	}

	public FieldId(Long id) {
		this();
		setValue(id);
	}

	@Override
	public Long getValue() {
		return id;
	}

	@Override
	public void setValue(Long id) {
		this.id = id;
		if (id != null && id > 0) {
			label.setValue(Long.toString(id));
		} else {
			clear();
		}
	}

	@Override
	public void clear() {
		label.setValue("");
	}
}