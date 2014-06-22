package ru.itbasis.utils.zk.ui.toolbar.combo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Toolbar;
import ru.itbasis.utils.zk.LogMsg;

// FIXME Избавиться от параметров в конструкторе
public class ToolbarComboFilter<T> extends ToolbarCombo {
	private static final transient Logger LOG = LoggerFactory.getLogger(ToolbarComboFilter.class.getName());

	protected T      filter;
	protected String labelName;

	protected Button btnApply;

	public ToolbarComboFilter(final Toolbar toolbar) {
		super(toolbar);
	}

	public ToolbarComboFilter(final Toolbar toolbar, final String lName) {
		super(toolbar);
		setLabelName(lName);
	}

	public ToolbarComboFilter(final Toolbar toolbar, final Popup popup) {
		super(toolbar, popup);
	}

	public ToolbarComboFilter(final Toolbar toolbar, final String lName, final Popup popup) {
		super(toolbar, popup);
		setLabelName(lName);
	}

	public Button appendButtonApply(final Component parent, final EventListener<Event> listener) {
		btnApply = new Button(Labels.getLabel("form.action.apply", "apply"));
		btnApply.setParent(parent);
		btnApply.addEventListener(Events.ON_CLICK, listener);
		return btnApply;
	}

	public T getFilter() {
		return filter;
	}

	public void setFilter(final T value) {
		LOG.trace(LogMsg.VALUE, value);
		this.filter = value;
		updateLabel();
		Events.postEvent(Events.ON_CHANGE, _this, value);
	}

	public void setLabelName(final String labelName) {
		this.labelName = labelName;
	}

	protected void updateLabel() {
		setLabel(Labels.getLabel(labelName, new Object[]{filter.toString()}));
	}
}
