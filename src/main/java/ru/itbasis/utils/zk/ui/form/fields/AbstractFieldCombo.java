package ru.itbasis.utils.zk.ui.form.fields;

import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Combobutton;

abstract public class AbstractFieldCombo<T> extends AbstractField<T> {
	protected Combobutton _combo;

	protected T _item;

	protected AbstractFieldCombo() {
		super();
		_combo = new Combobutton();
		// TODO Почему?
//		_combo.setHflex(DEFAULT_HFLEX);
//		_combo.setWidth(DEFAULT_WIDTH);
		_combo.setParent(getBox());
		initPopup();
	}

	@Override
	public T getValue() {
		return _item;
	}

	@Override
	public void clear() {
		_combo.setLabel(Labels.getLabel("empty.combo"));
		_item = null;
	}

	abstract protected void initPopup();
}
