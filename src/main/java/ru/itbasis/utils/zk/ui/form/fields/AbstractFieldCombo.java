package ru.itbasis.utils.zk.ui.form.fields;

import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Combobutton;

@Deprecated
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
		getThis().addEventListener(Events.ON_CHANGE, new Event$Default$OnChange());
		initPopup();
	}

	@Override
	public T getRawValue() {
		return _item;
	}

	abstract protected void initPopup();
}
