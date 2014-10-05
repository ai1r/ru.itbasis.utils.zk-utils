package ru.itbasis.utils.zk.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.util.Composer;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;

import java.util.List;

public class ComponentListener implements Composer {
	private static final transient Logger LOG = LoggerFactory.getLogger(ComponentListener.class.getName());

	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		LOG.trace("comp: {}", comp);
		final List<Component> list = Selectors.find(comp, Tabbox.class.getSimpleName());
		LOG.trace("list: {}", list);
		for (Component cmp : list) {
			final Tabbox tabbox = (Tabbox) cmp;
			initTabboxEvents(tabbox);
		}
	}

	@SuppressWarnings("unchecked")
	private void initTabboxEvents(final Tabbox tabbox) {
		LOG.trace("tabbox: {}", tabbox);
		for (EventListener<? extends Event> eventListener : tabbox.getEventListeners(Events.ON_SELECT)) {
			if (eventListener instanceof TabSelectListener) {
				return;
			}
		}
		tabbox.addEventListener(Events.ON_SELECT, new TabSelectListener());
		final Tab tab = tabbox.getSelectedTab();
		final Tabpanel tabPanel = tab.getLinkedPanel();
		LOG.trace("tab: {}, tabPanel: {}", tab, tabPanel);
		Events.postEvent(Events.ON_SELECT, tab, null);
	}
}
