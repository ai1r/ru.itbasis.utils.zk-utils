package ru.itbasis.utils.zk.ui.dialog.form;

import org.zkoss.zk.ui.Component;
import ru.itbasis.utils.core.model.IId;
import ru.itbasis.utils.zk.ui.dialog.IDialogFrame;

@Deprecated
public interface IDialogFormFrame<Item extends IId> extends IDialogFrame {

	default String getTabTitle() {
		return null;
	}

	void initFormFields(Component parent);

	Item loadFieldData(Item item);

	Item saveFieldData(Item item);
}
