package ru.itbasis.utils.zk.ui.toolbar;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Toolbarbutton;

public class ToolbarButton extends Toolbarbutton {
	private static final String MODE_TOGGLE = "toggle";

	public ToolbarButton(Toolbar parent) {
		setParent(parent);
	}

	public ToolbarButton(Toolbar parent, String label) {
		this(parent);
		setLabel(Labels.getRequiredLabel(label));
	}

	public ToolbarButton(Toolbar parent, String label, EventListener<Event> listener) {
		this(parent, label);
		addEventListener(Events.ON_CLICK, listener);
	}

	public void setToggle() {
		setMode(MODE_TOGGLE);
	}

	public boolean isToogle() {
		return getMode().contains(MODE_TOGGLE);
	}

}
