package ru.itbasis.utils.zk.ui.dialog;

import org.apache.commons.lang3.StringUtils;
import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Tab;
import ru.itbasis.utils.zk.LabelProperties;

public final class DialogTab<Frame extends IDialogFrame> extends Tab implements IDialogTab<DialogTab, Frame> {

	private Frame frame;

	public DialogTab(final Frame value) {
		this(value, null);
	}

	public DialogTab(final Frame value, final String label) {
		assert value != null;
		frame = value;
		setLabel(StringUtils.isEmpty(label) ? Labels.getLabel(LabelProperties.LABEL_CORE_DIALOG_TAB_GENERAL_TITLE, "General") : label);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Frame getFrame() {
		return frame;
	}

}
