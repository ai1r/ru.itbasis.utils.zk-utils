package ru.itbasis.utils.zk.ui.tabbox;

import org.apache.commons.lang3.StringUtils;
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
import ru.itbasis.utils.core.LogMsg;
import ru.itbasis.utils.zk.ZulUtils;

import java.util.List;

public final class TabboxUtils {
	public static final String FLAG_TAB_LABEL      = "|";
	public static final String FLAG_TAB_PAGE_CLASS = "class:";
	public static final String FLAG_TAB_PAGE_ZUL   = "zul:";

	public static final String PREFIX_TAB_ID = "tabId_";

	private static final transient Logger LOG = LoggerFactory.getLogger(TabboxUtils.class.getName());

	private static final String LOG_TAB_PAGE_PATH = "tabPagePath: '{}'";

	private TabboxUtils() {
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tab> T appendOrReplaceTab(final Tabbox tabbox, final String tabId, final String tabLabel, final Component compChild) {
		LOG.trace("tabId: '{}'", tabId);
		LOG.trace("tabLabel: '{}'", tabLabel);
		LOG.trace("compChild: {}", compChild);

		T tab = findTab(tabbox, tabId);
		if (null == tab) {
			tab = (T) new Tab(tabLabel);
			tab.setId(tabId);
		}

		tab.setLabel(tabLabel);

		return appendTab(tabbox, tab, compChild);
	}

	public static <T extends Tab> T appendTab(final Tabbox tabbox, final T tab, final Component compChild) {
		LOG.trace("tab: {}", tab);
		LOG.trace("compChild: {}", compChild);

		tab.setParent(tabbox.getTabs());
		fixTabPanels(tabbox);

		final Tabpanel tabPanel = tab.getLinkedPanel();
		tabPanel.appendChild(compChild);
		tabPanel.invalidate();

		tabbox.setSelectedTab(tab);
		return tab;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tab> T findAndActivateTab(final Tabbox tabbox, final String tabId) {
		LOG.trace("tabId: {}", tabId);
		final T tab = findTab(tabbox, tabId);
		if (tab != null) {
			tabbox.setSelectedTab(tab);
		}
		return tab;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tab> T findTab(final Tabbox tabbox, final String tabId) {
		LOG.trace("tabId: {}", tabId);
		if (null == tabbox || StringUtils.isEmpty(tabId)) {
			return null;
		}

		final List<Component> tabs = Selectors.find(tabbox, StringUtils.prependIfMissing(tabId, "#"));
		if (tabs.size() > 0) {
			return (T) tabs.get(0);
		}
		return null;
	}

	public static void fixTabPanels(final Tabbox tabbox) {
		if (tabbox == null) {
			return;
		}

		final Tabs tabs = getTabs(tabbox);
		final Tabpanels tabPanels = getTabpanels(tabbox);

		final int cTabs = tabs.getChildren().size();
		final int cPanels = tabPanels.getChildren().size();

		if (cTabs > cPanels) {
			for (int i = cPanels; i < cTabs; i++) {
				tabPanels.appendChild(new Tabpanel());
			}

		} else if (cTabs < cPanels) {
			for (int i = cTabs; i < cPanels; i++) {
				tabs.appendChild(new Tab());
			}
		}

	}

	public static Component getComp(final String tabPagePath) {
		LOG.trace(LOG_TAB_PAGE_PATH, tabPagePath);
		if (tabPagePath == null) {
			return null;
		}

		if (tabPagePath.startsWith(FLAG_TAB_PAGE_ZUL) && !FLAG_TAB_PAGE_ZUL.equals(tabPagePath)) {
			final String zulPage = ZulUtils.makeFullZulPath(tabPagePath.substring(FLAG_TAB_PAGE_ZUL.length()));
			LOG.trace(LOG_TAB_PAGE_PATH, zulPage);
			final Include include = new Include();
			include.invalidate();
			include.setSrc(zulPage);
			return include;

		} else if (tabPagePath.startsWith(FLAG_TAB_PAGE_CLASS) && !FLAG_TAB_PAGE_CLASS.equals(tabPagePath)) {
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

	public static Tab getTabFromChildren(final Tabbox tabbox, final Component children) {
		if (children == null) {
			return null;
		}

		final Tab tab = findAndActivateTab(tabbox, getTabId(children));

		LOG.debug(LogMsg.FOUND_ITEM, tab);
		return tab;
	}

	public static Tab getTabFromChildren(final Tabbox tabbox, final String label, final Class childClass) {
		if (childClass == null) {
			return null;
		}

		try {
			final String tabId = PREFIX_TAB_ID + Integer.toString(childClass.getCanonicalName().hashCode());
			final Tab tab = findAndActivateTab(tabbox, tabId);
			if (tab != null) {
				return tab;
			}
			return appendOrReplaceTab(tabbox, tabId, label, (Component) childClass.newInstance());
		} catch (InstantiationException | IllegalAccessException e) {
			LOG.error(e.getMessage(), e);
		}
		return null;
	}

	public static String getTabId(final Component children) {
		if (children == null) {
			return null;
		}
		return PREFIX_TAB_ID + children.getId();
	}

	public static Tabpanels getTabpanels(final Tabbox tabbox) {
		if (tabbox == null) {
			return null;
		}
		Tabpanels tabPanels = tabbox.getTabpanels();
		if (tabPanels == null) {
			tabPanels = new Tabpanels();
			tabPanels.setParent(tabbox);
		}
		return tabPanels;
	}

	public static Tabs getTabs(final Tabbox tabbox) {
		if (tabbox == null) {
			return null;
		}
		Tabs tabs = tabbox.getTabs();
		if (tabs == null) {
			tabs = new Tabs();
			tabs.setParent(tabbox);
		}
		return tabs;
	}

	// TODO add tests
	public static Tab goTab(final Component root, final Menuitem menuitem) {
		if (root == null || menuitem == null) {
			return null;
		}

		String tabLabel = menuitem.getLabel();
		String tabPagePath = menuitem.getValue();
		LOG.trace(LOG_TAB_PAGE_PATH, tabPagePath);

		if (tabPagePath.contains(FLAG_TAB_LABEL)) {
			final int of = tabPagePath.indexOf(FLAG_TAB_LABEL);
			tabLabel = tabPagePath.substring(0, of);
			tabPagePath = tabPagePath.substring(of + 1);
			LOG.trace(LOG_TAB_PAGE_PATH, tabPagePath);
		}

		Tabbox tabbox;
		if (root instanceof Tabbox) {
			tabbox = (Tabbox) root;
		} else {
			final List<Component> tabboxes = Selectors.find(root, Tabbox.class.getSimpleName());
			LOG.trace("tabboxes: {}", tabboxes);

			tabbox = (Tabbox) tabboxes.get(0);
		}
		if (tabbox == null) {
			LOG.error("not found tabbox from root component: {}", root);
			return null;
		}

		final Component compChild = getComp(tabPagePath);
		Tab tab = getTabFromChildren(tabbox, compChild);

		if (tab == null) {
			tab = appendOrReplaceTab(tabbox, getTabId(compChild), tabLabel, compChild);
		}

		tabbox.setSelectedTab(tab);
		return tab;
	}

}
