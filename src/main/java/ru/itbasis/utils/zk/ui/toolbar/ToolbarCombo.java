package ru.itbasis.utils.zk.ui.toolbar;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.*;

public class ToolbarCombo extends Combobutton {
	protected Component _this;

	public ToolbarCombo(Toolbar toolbar, Popup popup) {
		_this = this;
		setMold("toolbar");
		setParent(toolbar);
		addEventListener(Events.ON_CLICK, new Event$OnClick());
		appendChild(popup);
	}

	public ToolbarCombo(Toolbar toolbar) {
		this(toolbar, new Popup());
	}

	public ToolbarCombo(Toolbar toolbar, final String label) {
		this(toolbar);
		setLabel(Labels.getRequiredLabel(label));
	}

	public ToolbarCombo(Toolbar toolbar, final String label, Popup popup) {
		this(toolbar, popup);
		setLabel(Labels.getRequiredLabel(label));
	}

	public void appendMenuseparator() {
		new Menuseparator().setParent(this.getDropdown());
	}

	public Menuitem appendMenuitem(final String labelName) {
		Menuitem item = new Menuitem(Labels.getRequiredLabel(labelName));
		item.setParent(this.getDropdown());
		return item;
	}

	@SuppressWarnings("unchecked")
	public Menuitem appendMenuitem(final String labelName, EventListener listener) {
		Menuitem menuitem = appendMenuitem(labelName);
		menuitem.addEventListener(Events.ON_CLICK, listener);
		return menuitem;
	}

	private class Event$OnClick implements EventListener<Event> {
		@Override
		public void onEvent(Event event) throws Exception {
			Combobutton cb = (Combobutton) event.getTarget();
			cb.open();
		}
	}
}