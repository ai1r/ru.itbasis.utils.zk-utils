package ru.itbasis.utils.zk.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.*;
import ru.itbasis.utils.zk.Utils;

import java.util.List;

final public class TabboxUtils {
	private transient static final Logger LOG = LoggerFactory.getLogger(TabboxUtils.class.getName());

	public static final String FLAG_TAB_LABEL      = "|";
	public static final String FLAG_TAB_PAGE_ZUL   = "zul:";
	public static final String FLAG_TAB_PAGE_CLASS = "class:";

	private static final String PREFIX_TAB_ID     = "tabId_";
	private static final String LOG_TAB_PAGE_PATH = "tabPagePath: '{}'";

	private TabboxUtils() {
	}

	public static Tab goTab(final Component root, final Menuitem menuitem) {
		String tabLabel = menuitem.getLabel();
		String tabPagePath = menuitem.getValue();
		LOG.trace(LOG_TAB_PAGE_PATH, tabPagePath);
		if (tabPagePath.contains(FLAG_TAB_LABEL)) {
			final int of = tabPagePath.indexOf(FLAG_TAB_LABEL);
			tabLabel = tabPagePath.substring(0, of);
			tabPagePath = tabPagePath.substring(of + 1);
			LOG.trace(LOG_TAB_PAGE_PATH, tabPagePath);
		}

		List<Component> tabboxes = Selectors.find(root, Tabbox.class.getSimpleName());
		LOG.trace("tabboxes: {}", tabboxes);

		Tabbox tabbox = (Tabbox) tabboxes.get(0);

		Component compChild = getComp(tabPagePath);
		return goTab(tabbox, tabLabel, compChild);
	}

	public static Tab goTab(final Tabbox tabbox, final String label, final Class childClass) {
		try {
			final String tabId = PREFIX_TAB_ID + Integer.toString(childClass.getCanonicalName().hashCode());
			final Tab tab = findTab(tabbox, tabId);
			if (tab != null) {
				return tab;
			}
			return appendTab(tabbox, tabId, label, (Component) childClass.newInstance());
		} catch (InstantiationException | IllegalAccessException e) {
			LOG.error(e.getMessage(), e);
		}
		return null;
	}

	public static Tab goTab(final Tabbox tabbox, final String label, final Component child) {
		if (child == null) {
			return null;
		}

		String tabId = PREFIX_TAB_ID + child.getUuid();
		final Tab tab = findTab(tabbox, tabId);
		if (tab != null) {
			return tab;
		}

		return appendTab(tabbox, tabId, label, child);
	}

	public static Tab findTab(final Tabbox tabbox, String tabId) {
		if (!tabId.startsWith("#")) {
			tabId = "#" + tabId;
		}
		LOG.trace("tabId: {}", tabId);
		List<Component> tabs = Selectors.find(tabbox, tabId);
		if (tabs.size() > 0) {
			final Tab tab = (Tab) tabs.get(0);
			tabbox.setSelectedTab(tab);
			return tab;
		}
		return null;
	}

	public static Component getComp(String tabPagePath) {
		LOG.trace(LOG_TAB_PAGE_PATH, tabPagePath);
		if (tabPagePath.startsWith(FLAG_TAB_PAGE_ZUL)) {
			tabPagePath = Utils.getZulPage(tabPagePath.substring(FLAG_TAB_PAGE_ZUL.length()));
			LOG.trace(LOG_TAB_PAGE_PATH, tabPagePath);
			final Include include = new Include();
			include.invalidate();
			include.setSrc(tabPagePath);
			return include;

		} else if (tabPagePath.startsWith(FLAG_TAB_PAGE_CLASS)) {
			tabPagePath = tabPagePath.substring(FLAG_TAB_PAGE_CLASS.length());
			LOG.trace("className: '{}'", tabPagePath);
			try {
				Class clazz = Class.forName(tabPagePath);
				return (Component) clazz.newInstance();
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				LOG.error(e.getMessage(), e);
			}
		}
		return null;
	}

	private static Tab appendTab(final Tabbox tabbox,
	                             final String tabId,
	                             final String tabLabel,
	                             final Component compChild) {
		LOG.trace("tabId: '{}'", tabId);
		LOG.trace("tabLabel: '{}'", tabLabel);
		LOG.trace("compChild: {}", compChild);

		fixTabPanels(tabbox);

		Tab tab = new Tab(tabLabel);
		tab.setClosable(true);
		tab.setSelected(true);
		tab.setId(tabId);

		Tabpanel tabPanel = new Tabpanel();
		tabPanel.appendChild(compChild);

		tabPanel.setParent(tabbox.getTabpanels());
		tab.setParent(tabbox.getTabs());

		tabPanel.invalidate();

		return tab;
	}

	public static void fixTabPanels(Tabbox tabbox) {
		Tabs tabs = tabbox.getTabs();
		if (tabs == null) {
			tabs = new Tabs();
			tabs.setParent(tabbox);
		}

		Tabpanels tabPanels = tabbox.getTabpanels();
		if (tabPanels == null) {
			tabPanels = new Tabpanels();
			tabPanels.setParent(tabbox);
		}

		int cTabs = tabs.getChildren().size();
		int cPanels = tabPanels.getChildren().size();
		if (cTabs == cPanels) {
			return;
		}
		for (int i = cPanels; i < cTabs; i++) {
			tabPanels.appendChild(new Tabpanel());
		}
	}
}
