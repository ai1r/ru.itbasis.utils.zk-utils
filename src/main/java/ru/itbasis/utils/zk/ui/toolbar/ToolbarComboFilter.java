package ru.itbasis.utils.zk.ui.toolbar;

import org.zkoss.zul.Popup;
import org.zkoss.zul.Toolbar;

public class ToolbarComboFilter<T> extends ToolbarCombo {
	protected T      filter;

	public ToolbarComboFilter(Toolbar toolbar) {
		super(toolbar);
	}

	public ToolbarComboFilter(Toolbar toolbar, Popup popup) {
		super(toolbar, popup);
	}

	public T getFilter() {
		return filter;
	}

	public void setFilter(T filter) {
		this.filter = filter;
	}
}
