package ru.itbasis.utils.zk.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabpanel;
import ru.itbasis.utils.zk.ui.TabboxUtils;

public class TabSelectListener implements EventListener<Event> {
	private static final transient Logger LOG = LoggerFactory.getLogger(TabSelectListener.class.getName());

	@Override
	public void onEvent(final Event event) throws Exception {
		final Tab tab = (Tab) event.getTarget();

		Clients.clearBusy(tab.getRoot());

		LOG.trace("tab({})): {}", tab.getIndex(), tab);

		final String pageUri = (String) tab.getAttribute("pageUri");

		TabboxUtils.fixTabPanels(tab.getTabbox());

		final Tabpanel tabPanel = tab.getLinkedPanel();
		if (tabPanel.getFirstChild() != null) {
			return;
		}

		final Component comp = TabboxUtils.getComp(pageUri);
		LOG.trace("comp: {}", comp);
		comp.setParent(tab.getLinkedPanel());

		Clients.clearBusy(tab.getRoot());
	}

}
