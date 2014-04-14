package ru.itbasis.utils.zk.ui.toolbar.combo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Toolbar;
import ru.itbasis.utils.zk.LogMsg;

public class ToolbarComboFilter<T> extends ToolbarCombo {
	private transient static final Logger LOG = LoggerFactory.getLogger(ToolbarComboFilter.class.getName());

	protected T      filter;
	protected String labelName;

	public ToolbarComboFilter(Toolbar toolbar) {
		super(toolbar);
	}

	public ToolbarComboFilter(Toolbar toolbar, String labelName) {
		super(toolbar);
		setLabelName(labelName);
		updateLabel();
	}

	public ToolbarComboFilter(Toolbar toolbar, Popup popup) {
		super(toolbar, popup);
	}

	public ToolbarComboFilter(Toolbar toolbar, String labelName, Popup popup) {
		super(toolbar, popup);
		setLabelName(labelName);
		updateLabel();
	}

	public T getFilter() {
		return filter;
	}

	public void setFilter(T value) {
		LOG.trace(LogMsg.VALUE, value);
		this.filter = value;
		updateLabel();
		Events.postEvent(Events.ON_CHANGE, _this, value);
	}

	protected void updateLabel() {
		setLabel(Labels.getLabel(labelName, new Object[]{filter.toString()}));
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
}
