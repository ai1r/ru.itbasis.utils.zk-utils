package ru.itbasis.utils.zk.ui.form.fields;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.mesg.Messages;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.mesg.MZul;
import ru.itbasis.utils.zk.LogMsg;

public class FieldYesNo extends AbstractField<Boolean> {
	private static final transient Logger LOG = LoggerFactory.getLogger(FieldYesNo.class.getName());

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
		LOG.trace(LogMsg.VALUE, value);
		_combo.setSelectedIndex(value ? 1 : 0);
	}
}
