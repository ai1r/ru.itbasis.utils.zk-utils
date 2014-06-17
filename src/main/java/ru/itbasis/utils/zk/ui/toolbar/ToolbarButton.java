package ru.itbasis.utils.zk.ui.toolbar;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Toolbarbutton;
import ru.itbasis.utils.zk.LogMsg;
import ru.itbasis.utils.zk.ui.ILabelResource;

public class ToolbarButton extends Toolbarbutton implements ILabelResource<ToolbarButton> {
	private transient static final Logger LOG = LoggerFactory.getLogger(ToolbarButton.class.getName());

	private static final String MODE_TOGGLE = "toggle";

	public static final String DEFAULT_LABEL = "button";

	protected ToolbarButton _this;

	public ToolbarButton(Toolbar parent) {
		_this = this;
		setParent(parent);
	}

	@Override
	public ToolbarButton setLabelResource(String value) {
		LOG.trace(LogMsg.VALUE, value);
		if (StringUtils.isEmpty(value)) {
			setLabel(DEFAULT_LABEL);
		} else {
			setLabel(Labels.getLabel(value, DEFAULT_LABEL));
		}
		return this;
	}

	public ToolbarButton addClickListener(EventListener<Event> listener) {
		addEventListener(Events.ON_CLICK, listener);
		return this;
	}

	public ToolbarButton enableToggle() {
		setMode(MODE_TOGGLE);
		return this;
	}

	public ToolbarButton disableToggle() {
		setMode("default");
		return this;
	}

	public boolean isToogle() {
		return getMode().contains(MODE_TOGGLE);
	}

}
