package ru.itbasis.utils.zk.ui.dialog;

import org.zkoss.zk.ui.util.ConventionWires;
import org.zkoss.zul.Tab;

public abstract class AbstractDialogTab<Frame extends AbstractDialogFrame> extends Tab implements IDialogTab<Frame> {
	public AbstractDialogTab(final String label) {
		super(label);
		ConventionWires.wireVariables(this, this);
		ConventionWires.addForwards(this, this);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Frame getFrame() {
		return (Frame) getFirstChild();
	}
}
