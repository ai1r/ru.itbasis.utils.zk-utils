package ru.itbasis.utils.zk.ui.dialog.form.fields;

import org.zkoss.mesg.Messages;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.mesg.MZul;

public class FieldYesNo extends AbstractField<Boolean> {
	private static final long serialVersionUID = -2802802845447974869L;

	private final Combobox _combo;

	public FieldYesNo() {
		_combo = new Combobox();
		_combo.setConstraint(CONSTRAINT_NOEMPTY);
		_combo.setHflex(DEFAULT_HFLEX);
		_combo.setReadonly(true);
		_combo.setAutodrop(true);
		_combo.setParent(getBox());

		_combo.appendItem(Messages.get(MZul.NO));
		_combo.appendItem(Messages.get(MZul.YES));

		_combo.addEventListener(Events.ON_CHANGE, new Event$Default$OnChange());
	}

	@Override
	public Boolean getRawValue() {
		return _combo.getSelectedIndex() == 1;
	}

	@Override
	public void setRawValue(final Boolean value) {
		_combo.setSelectedIndex(value ? 1 : 0);
	}
}
