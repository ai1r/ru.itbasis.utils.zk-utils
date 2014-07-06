package ru.itbasis.utils.zk.ui.dialog.form;

import org.zkoss.zul.Columns;
import ru.itbasis.utils.core.model.IId;
import ru.itbasis.utils.zk.ui.dialog.AbstractDialogFrame;

public abstract class AbstractDialogFormFrame<ItemFromTab extends IId> extends AbstractDialogFrame implements IDialogFormFrame<ItemFromTab> {
	@Override
	protected Columns initGridColumns() {
		return new GridTwoColumn();
	}
}
