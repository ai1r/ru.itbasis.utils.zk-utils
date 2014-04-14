package ru.itbasis.utils.zk.ui.toolbar.combo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.*;
import ru.itbasis.utils.zk.LogMsg;

public class ToolbarCombo extends Combobutton {
	private transient static final Logger LOG = LoggerFactory.getLogger(ToolbarCombo.class.getName());

	protected Component _this;

	public ToolbarCombo(Toolbar toolbar, Popup popup) {
		_this = this;
		setMold("toolbar");
		setParent(toolbar);
		addEventListener(Events.ON_CLICK, new Event$OnClick());
		appendChild(popup);
		initPopup();
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

	protected void initPopup() {
	}

	public void appendMenuseparator() {
		new Menuseparator().setParent(this.getDropdown());
	}

	public Menuitem appendMenuitem(final String labelName) {
		assert this.getDropdown() instanceof Menupopup;

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
			LOG.trace(LogMsg.EVENT, event);
			Combobutton cb = (Combobutton) event.getTarget();
			cb.open();
		}
	}
}