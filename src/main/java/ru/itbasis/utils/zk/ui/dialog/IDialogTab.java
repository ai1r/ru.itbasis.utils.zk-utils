package ru.itbasis.utils.zk.ui.dialog;

import ru.itbasis.utils.core.ISelf;

public interface IDialogTab<Self extends IDialogTab, Frame extends IDialogFrame> extends ISelf<Self> {

	String TAB_ID_GENERAL = "tabGeneral";

	Frame getFrame();

	boolean isClosable();

	void setClosable(boolean closable);

	public String getLabel();

	public void setLabel(final String value);
}
