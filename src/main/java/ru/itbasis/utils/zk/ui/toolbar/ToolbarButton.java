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
import ru.itbasis.utils.core.ISelf;
import ru.itbasis.utils.core.LogMsg;
import ru.itbasis.utils.zk.ui.ILabelResource;

public class ToolbarButton extends Toolbarbutton implements ILabelResource<ToolbarButton>, ISelf<ToolbarButton> {
	public static final String DEFAULT_LABEL = "button";

	private static final transient Logger LOG = LoggerFactory.getLogger(ToolbarButton.class.getName());

	private static final String MODE_TOGGLE = "toggle";

	private static final long serialVersionUID = 3244605766196305574L;

	public ToolbarButton() {
	}

	public ToolbarButton(final Toolbar parent) {
		setParent(parent);
	}

	public ToolbarButton addClickListener(final EventListener<Event> listener) {
		addEventListener(Events.ON_CLICK, listener);
		return this;
	}

	public ToolbarButton disableToggle() {
		setMode("default");
		return this;
	}

	public ToolbarButton enableToggle() {
		setMode(MODE_TOGGLE);
		return this;
	}

	public boolean isToogle() {
		return getMode().contains(MODE_TOGGLE);
	}

	@Override
	public ToolbarButton setLabelResource(final String value) {
		LOG.trace(LogMsg.VALUE, value);
		if (StringUtils.isEmpty(value)) {
			setLabel(DEFAULT_LABEL);
		} else {
			setLabel(Labels.getLabel(value, DEFAULT_LABEL));
		}
		return this;
	}

}
