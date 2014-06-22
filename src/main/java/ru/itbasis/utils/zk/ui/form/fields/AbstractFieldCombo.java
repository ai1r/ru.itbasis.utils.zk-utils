package ru.itbasis.utils.zk.ui.form.fields;

import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Combobutton;

@Deprecated
public abstract class AbstractFieldCombo<T> extends AbstractField<T> {
	protected Combobutton _combo;

	protected T _item;

	protected AbstractFieldCombo() {
		super();
		_combo = new Combobutton();
		// TODO Почему?
//		_combo.setHflex(DEFAULT_HFLEX);
//		_combo.setWidth(DEFAULT_WIDTH);
		_combo.setParent(getBox());
		getSelf().addEventListener(Events.ON_CHANGE, new Event$Default$OnChange());
		initPopup();
	}

	@Override
	public T getRawValue() {
		return _item;
	}

	protected abstract void initPopup();
}
