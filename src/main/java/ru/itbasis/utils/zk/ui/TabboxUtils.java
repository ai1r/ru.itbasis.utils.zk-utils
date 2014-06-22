package ru.itbasis.utils.zk.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Include;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import ru.itbasis.utils.zk.Utils;

import java.util.List;

public final class TabboxUtils {
	public static final String FLAG_TAB_LABEL      = "|";
	public static final String FLAG_TAB_PAGE_ZUL   = "zul:";
	public static final String FLAG_TAB_PAGE_CLASS = "class:";

	private static final transient Logger LOG = LoggerFactory.getLogger(TabboxUtils.class.getName());

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

		final List<Component> tabboxes = Selectors.find(root, Tabbox.class.getSimpleName());
		LOG.trace("tabboxes: {}", tabboxes);

		final Tabbox tabbox = (Tabbox) tabboxes.get(0);

		final Component compChild = getComp(tabPagePath);
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

		final String tabId = PREFIX_TAB_ID + child.getUuid();
		final Tab tab = findTab(tabbox, tabId);
		if (tab != null) {
			return tab;
		}

		return appendTab(tabbox, tabId, label, child);
	}

	public static Tab findTab(final Tabbox tabbox, final String tabId) {
		LOG.trace("tabId: {}", tabId);

		final List<Component> tabs = Selectors.find(tabbox, ((!tabId.startsWith("#")) ? ("#" + tabId) : tabId));
		if (tabs.size() > 0) {
			final Tab tab = (Tab) tabs.get(0);
			tabbox.setSelectedTab(tab);
			return tab;
		}
		return null;
	}

	public static Component getComp(final String tabPagePath) {
		LOG.trace(LOG_TAB_PAGE_PATH, tabPagePath);
		if (tabPagePath.startsWith(FLAG_TAB_PAGE_ZUL)) {
			final String zulPage = Utils.getZulPage(tabPagePath.substring(FLAG_TAB_PAGE_ZUL.length()));
			LOG.trace(LOG_TAB_PAGE_PATH, zulPage);
			final Include include = new Include();
			include.invalidate();
			include.setSrc(zulPage);
			return include;

		} else if (tabPagePath.startsWith(FLAG_TAB_PAGE_CLASS)) {
			final String className = tabPagePath.substring(FLAG_TAB_PAGE_CLASS.length());
			LOG.trace("className: '{}'", className);
			try {
				final Class clazz = Class.forName(className);
				return (Component) clazz.newInstance();
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				LOG.error(e.getMessage(), e);
			}
		}
		return null;
	}

	private static Tab appendTab(final Tabbox tabbox, final String tabId, final String tabLabel, final Component compChild) {
		LOG.trace("tabId: '{}'", tabId);
		LOG.trace("tabLabel: '{}'", tabLabel);
		LOG.trace("compChild: {}", compChild);

		fixTabPanels(tabbox);

		final Tab tab = new Tab(tabLabel);
		tab.setClosable(true);
		tab.setSelected(true);
		tab.setId(tabId);

		final Tabpanel tabPanel = new Tabpanel();
		tabPanel.appendChild(compChild);

		tabPanel.setParent(tabbox.getTabpanels());
		tab.setParent(tabbox.getTabs());

		tabPanel.invalidate();

		return tab;
	}

	public static void fixTabPanels(final Tabbox tabbox) {
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

		final int cTabs = tabs.getChildren().size();
		final int cPanels = tabPanels.getChildren().size();
		if (cTabs == cPanels) {
			return;
		}
		for (int i = cPanels; i < cTabs; i++) {
			tabPanels.appendChild(new Tabpanel());
		}
	}
}
