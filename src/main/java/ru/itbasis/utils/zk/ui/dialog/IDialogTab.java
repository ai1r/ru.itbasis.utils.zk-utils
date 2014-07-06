package ru.itbasis.utils.zk.ui.dialog;

public interface IDialogTab<Frame extends AbstractDialogFrame> {
	String LABEL_CORE_DIALOG_TAB_GENERAL_TITLE = "core.dialog.tab.general.title";

	String TAB_ID_GENERAL = "tabGeneral";

	Frame getFrame();
}
