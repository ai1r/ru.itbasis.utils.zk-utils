package ru.itbasis.utils.zk.ui.toolbar.combo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Combobutton;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.Menuseparator;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Toolbar;

public class ToolbarCombo extends Combobutton {
	private static final transient Logger LOG = LoggerFactory.getLogger(ToolbarCombo.class.getName());

	protected Component _this;

	public ToolbarCombo(final Toolbar toolbar, final Popup popup) {
		_this = this;
		setMold("toolbar");
		setParent(toolbar);
		addEventListener(Events.ON_CLICK, new Event$OnClick());
		appendChild(popup);
		initPopup();
	}

	public ToolbarCombo(final Toolbar toolbar) {
		this(toolbar, new Popup());
	}

	public ToolbarCombo(final Toolbar toolbar, final String label) {
		this(toolbar);
		setLabel(Labels.getRequiredLabel(label));
	}

	public ToolbarCombo(final Toolbar toolbar, final String label, final Popup popup) {
		this(toolbar, popup);
		setLabel(Labels.getRequiredLabel(label));
	}

	public Menuitem appendMenuitem(final String labelName) {
		assert this.getDropdown() instanceof Menupopup;

		final Menuitem item = new Menuitem(Labels.getRequiredLabel(labelName));
		item.setParent(this.getDropdown());
		return item;
	}

	@SuppressWarnings("unchecked")
	public Menuitem appendMenuitem(final String labelName, final EventListener listener) {
		final Menuitem menuitem = appendMenuitem(labelName);
		menuitem.addEventListener(Events.ON_CLICK, listener);
		return menuitem;
	}

	public void appendMenuseparator() {
		new Menuseparator().setParent(this.getDropdown());
	}

	protected void initPopup() {
	}

	private class Event$OnClick implements EventListener<Event> {
		@Override
		public void onEvent(final Event event) throws Exception {
			((Combobutton) event.getTarget()).open();
		}
	}
}
