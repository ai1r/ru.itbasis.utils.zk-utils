package ru.itbasis.utils.zk.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.util.GenericInitiator;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabs;

import java.util.List;

@Deprecated
public class PageListener extends GenericInitiator {
	private static final transient Logger LOG = LoggerFactory.getLogger(PageListener.class.getName());

	@Override
	public void doAfterCompose(final Page page) {
		super.doAfterCompose(page);

		final List<Component> list = Selectors.find(page, Tabbox.class.getSimpleName());
		LOG.trace("list: {}", list);
		for (Component cmp : list) {
			final Tabbox tabbox = (Tabbox) cmp;
			initTabboxEvents(tabbox);
		}

	}

	private void initTabboxEvents(final Tabbox tabbox) {
		LOG.trace("tabbox: {}", tabbox);

		appendTabboxListener(tabbox);

		Tab tab = tabbox.getSelectedTab();

		if (tab == null) {
			final Tabs tabboxTabs = tabbox.getTabs();
			if (tabboxTabs == null) {
				return;
			}
			final List<Component> tabs = tabboxTabs.getChildren();
			if (!tabs.isEmpty()) {
				tab = (Tab) tabs.get(0);
			}
		}
		if (tab == null) {
			return;
		}
		final Tabpanel tabPanel = tab.getLinkedPanel();
		LOG.trace("tab: {}, tabPanel: {}", tab, tabPanel);
		Events.postEvent(Events.ON_SELECT, tab, null);
	}

	private void appendTabboxListener(final Tabbox tabbox) {
		boolean bListen = false;
		final Iterable<EventListener<? extends Event>> listeners = tabbox.getEventListeners(Events.ON_SELECT);
		for (EventListener<? extends Event> eventListener : listeners) {
			if (eventListener instanceof TabSelectListener) {
				bListen = true;
				break;
			}
		}

		if (!bListen) {
			tabbox.addEventListener(Events.ON_SELECT, new TabSelectListener());
		}
	}
}
