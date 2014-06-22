package ru.itbasis.utils.zk.ui.toolbar;

import org.zkoss.zul.Space;
import org.zkoss.zul.Toolbar;

public class ToolbarSeparator extends Space {
	public ToolbarSeparator(final Toolbar parent) {
		setParent(parent);
		setBar(true);
	}
}
