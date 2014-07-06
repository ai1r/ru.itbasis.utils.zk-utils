package ru.itbasis.utils.zk.ui.dialog.form;

import ru.itbasis.utils.core.model.IId;
import ru.itbasis.utils.zk.ui.dialog.IDialogFrame;

public interface IDialogFormFrame<Item extends IId> extends IDialogFrame {

	default String getTabTitle() {
		return null;
	}

	<Form extends AbstractDialogForm> void initFormFields(Form form);

	Item loadFieldData(Item item);

	Item saveFieldData(Item item);
}
