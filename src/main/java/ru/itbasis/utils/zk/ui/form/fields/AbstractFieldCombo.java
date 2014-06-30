package ru.itbasis.utils.zk.ui.form.fields;

import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Combobutton;

@Deprecated
public abstract class AbstractFieldCombo<Item> extends AbstractField<Item> {
	protected Combobutton _combo;

	protected Item _item;

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
	public Item getRawValue() {
		return _item;
	}

	protected abstract void initPopup();
}
