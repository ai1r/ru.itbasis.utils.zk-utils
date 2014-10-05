package ru.itbasis.utils.zk.ui.dialog.form;

import org.zkoss.zul.Columns;
import ru.itbasis.utils.core.model.IId;
import ru.itbasis.utils.zk.ui.dialog.AbstractDialogFrame;
import ru.itbasis.utils.zk.ui.dialog.form.columns.GridTwoColumn;

public abstract class AbstractDialogFormFrame<Self extends AbstractDialogFormFrame, Item extends IId> extends AbstractDialogFrame<Self>
	implements IFormFrame<Self, Item> {

	private Item _item;

	@Override
	public Item getItem() {
		return _item;
	}

	@Override
	public Self setItem(final Item value) {
		_item = value;
		return getSelf();
	}

	protected Columns initGridColumns() {
		return new GridTwoColumn();
	}

	@Override
	public Item loadFieldData(final Item value) {
		setItem(value);
		return getItem();
	}
}
