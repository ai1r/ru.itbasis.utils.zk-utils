package ru.itbasis.utils.zk.ui.dialog.form;

import org.zkoss.zk.ui.Component;
import ru.itbasis.utils.core.ISelf;
import ru.itbasis.utils.core.model.IId;

public interface IFormFrame<Self extends IFormFrame, Item extends IId> extends ISelf<Self> {
	Item getItem();

	Self setItem(Item value);

	void initFormFields(Component parent);

	Item loadFieldData(Item item);

	Item saveFieldData(Item item);

	void setParent(Component parent);

	@SuppressWarnings("unchecked")
	default <T> Item convertItem(T item) {
		return (Item) item;
	}

}
