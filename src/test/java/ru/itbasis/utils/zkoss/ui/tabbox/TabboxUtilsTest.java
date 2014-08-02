package ru.itbasis.utils.zkoss.ui.tabbox;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Div;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;

import java.util.List;

@Test(groups = {"tabbox", "utils"}, dependsOnGroups = {"zulUtils"})
public class TabboxUtilsTest {
	private static final transient Logger LOG = LoggerFactory.getLogger(TabboxUtilsTest.class.getName());

	@DataProvider(name = "dataAppendTab_Exists")
	public static Object[][] dataAppendTab_Exists() {
		return new Object[][]{{"tab0", "tabLabel0", new Label()},
		                      {"tab1", "tabLabel0", new Label("demo")},
		                      {"tab1", "tabLabel1", new Label()},
		                      {"tab2", "tabLabel1", new Label()}};
	}

	@DataProvider(name = "dataAppendTab_New")
	public static Object[][] dataAppendTab_New() {
		return new Object[][]{{"tab0", "tabLabel0", new Div(), 0}, {"tab_0", "tabLabel0", new Div(), 1}, {"tab1", "tabLabel1", new Div(), 0}};
	}

	@DataProvider(name = "dataFindTab4Null")
	public static Object[][] dataFindTab4Null() {
		return new Object[][]{{null}, {"id"}, {"#id"}, {""}};
	}

	@DataProvider(name = "dataFixTabPanels")
	public static Object[][] dataFixTabPanels() {
		return new Object[][]{{0, 0}, {1, 0}, {2, 1}, {3, 0}, {0, 3}, {0, 5}, {1, 4}};
	}

	@DataProvider(name = "dataGetComp")
	public static Object[][] dataGetComp() {
		return new Object[][]{{"class:ru.itbasis.utils.zkoss.ui.tabbox.DummyGetCompTab0", DummyGetCompTab0.class},
		                      {"class:ru.itbasis.utils.zkoss.ui.tabbox.DummyGetCompTab1", DummyGetCompTab1.class},
		                      {"zul:dummyGetCompZul0", Include.class},
		                      {"zul:dummyGetCompZul1", Include.class},
		                      {"class:unknownClass", null},
		                      {"class:", null},
		                      {"zul:", null},
		                      {"unknownTag:", null},
		                      {"", null},
		                      {null, null}};
	}

	@DataProvider(name = "dataGetTabFromChildren")
	public static Object[][] dataGetTabFromChildren() {
		final Label label0 = new Label();
		label0.setId(DummyTabbox.CHILD_ID);

		final Label label1 = new Label();
		label1.setId("test");

		return new Object[][]{{label0, true}, {label1, false}, {null, false}};
	}

	@DataProvider(name = "dataGetTabId")
	public static Object[][] dataGetTabId() {
		final Label item0 = new Label();
		item0.setId("label");
		final Div item1 = new Div();
		item1.setId("div");
		return new Object[][]{{"tabId_label", item0}, {"tabId_div", item1}, {null, null}};
	}

	@Test(dependsOnGroups = {"findTab"}, dependsOnMethods = {"testFixTabPanels"}, dataProvider = "dataAppendTab_Exists")
	public void testAppendOrReplaceTab_Exists(final String tabId, final String tabNewLabel, final Component child) {
		final DummyTabbox tabbox = new DummyTabbox();

		final int expectedSize = tabbox.getTabs().getChildren().size();
		final Tab findTab = TabboxUtils.findTab(tabbox, tabId);
		final String expectedLabel = findTab.getLabel();
		TabboxUtils.fixTabPanels(tabbox);
		final List<Component> expectedChilds = findTab.getLinkedPanel().getChildren();
		LOG.trace("expectedChilds: {}", expectedChilds);

		final Tab tab = TabboxUtils.appendOrReplaceTab(tabbox, tabId, tabNewLabel, child);

		Assert.assertEquals(tab.getId(), tabId);
		Assert.assertNotEquals(tab.getLabel(), expectedLabel);
		Assert.assertEquals(tab.getLabel(), tabNewLabel);

		Assert.assertEquals(tabbox.getTabs().getChildren().size(), expectedSize);

		final List<Component> list = tab.getLinkedPanel().getChildren();
		Assert.assertEquals(list.size(), 1);
		Assert.assertEquals(list.get(0), child);
		if (CollectionUtils.sizeIsEmpty(expectedChilds)) {
			Assert.assertNotEquals(list.get(0), expectedChilds.get(0));
		}
	}

	@Test(dependsOnGroups = {"findTab"}, dataProvider = "dataAppendTab_New")
	public void testAppendOrReplaceTab_New(final String tabId, final String tabLabel, final Component child, final int changeCount) {
		final DummyTabbox tabbox = new DummyTabbox();
		final int expectedCount = tabbox.getTabs().getChildren().size() + changeCount;

		final Tab tab = TabboxUtils.appendOrReplaceTab(tabbox, tabId, tabLabel, child);
		Assert.assertNotNull(tab);
		Assert.assertEquals(tab.getId(), tabId);
		Assert.assertEquals(tab.getLabel(), tabLabel);
		Assert.assertEquals(tabbox.getTabs().getChildren().size(), expectedCount);

		final List<Component> list = tab.getLinkedPanel().getChildren();
		Assert.assertEquals(list.size(), 1);
		Assert.assertEquals(list.get(0), child);
	}

	@Test(groups = {"findTab"}, dependsOnMethods = {"testFindTab4Null", "testFindTab_1"})
	public void testFindAndActivateTab_1() {
		DummyTabbox tabbox = new DummyTabbox();
		final int size = DummyTabbox.COUNT_INDEX;

		for (int i = 0; i < size; i++) {
			final String id = "#tab" + i;
			final Tab findTab = TabboxUtils.findAndActivateTab(tabbox, id);
			Assert.assertNotNull(findTab);
			Assert.assertEquals("#" + findTab.getId(), id);
			Assert.assertEquals(findTab.getId(), tabbox.getSelectedTab().getId());
		}
	}

	@Test(groups = {"findTab"}, dependsOnMethods = {"testFindTab4Null", "testFindTab_2"})
	public void testFindAndActivateTab_2() {
		DummyTabbox tabbox = new DummyTabbox();

		final int size = DummyTabbox.COUNT_INDEX;

		for (int i = 0; i < size; i++) {
			final String id = "tab" + i;
			final Tab findTab = TabboxUtils.findAndActivateTab(tabbox, id);
			Assert.assertNotNull(findTab);
			Assert.assertEquals(findTab.getId(), id);
			Assert.assertEquals(findTab.getId(), tabbox.getSelectedTab().getId());
		}
	}

	@Test(groups = {"findTab"}, dependsOnMethods = {"testFindTab4Null", "testFindTab_3"})
	public void testFindAndActivateTab_3() {
		DummyTabbox tabbox = new DummyTabbox();
		final List<Tab> genTabs = tabbox.getTabs().getChildren();

		genTabs.forEach(tab -> {
			final Tab findTab = TabboxUtils.findAndActivateTab(tabbox, tab.getId());
			Assert.assertNotNull(findTab);
			Assert.assertEquals(findTab.getId(), tab.getId());
			Assert.assertEquals(findTab.getId(), tabbox.getSelectedTab().getId());
		});
	}

	@Test(groups = {"findTab"}, dataProvider = "dataFindTab4Null")
	public void testFindTab4Null(final String tabId) throws Exception {
		Assert.assertNull(TabboxUtils.findTab(null, tabId));
		Assert.assertNull(TabboxUtils.findTab(new Tabbox(), tabId));
	}

	@Test(groups = {"findTab"}, dependsOnMethods = {"testFindTab4Null"})
	public void testFindTab_1() {
		DummyTabbox tabbox = new DummyTabbox();
		final int size = DummyTabbox.COUNT_INDEX;

		for (int i = 0; i < size; i++) {
			final String id = "#tab" + i;
			final Tab findTab = TabboxUtils.findTab(tabbox, id);
			Assert.assertNotNull(findTab);
			Assert.assertEquals("#" + findTab.getId(), id);
			Assert.assertNotEquals(findTab.getId(), tabbox.getSelectedTab().getId());
		}
	}

	@Test(groups = {"findTab"}, dependsOnMethods = {"testFindTab4Null"})
	public void testFindTab_2() {
		DummyTabbox tabbox = new DummyTabbox();
		final int size = DummyTabbox.COUNT_INDEX;

		for (int i = 0; i < size; i++) {
			final String id = "tab" + i;
			final Tab findTab = TabboxUtils.findTab(tabbox, id);
			Assert.assertNotNull(findTab);
			Assert.assertEquals(findTab.getId(), id);
			Assert.assertNotEquals(findTab.getId(), tabbox.getSelectedTab().getId());
		}
	}

	@Test(groups = {"findTab"}, dependsOnMethods = {"testFindTab4Null"})
	public void testFindTab_3() {
		DummyTabbox tabbox = new DummyTabbox();
		final List<Tab> genTabs = tabbox.getTabs().getChildren();

		final Tab selectedTab = tabbox.getSelectedTab();

		genTabs.forEach(tab -> {
			final Tab findTab = TabboxUtils.findTab(tabbox, tab.getId());
			Assert.assertNotNull(findTab);
			Assert.assertEquals(findTab.getId(), tab.getId());
			if (selectedTab == null || !selectedTab.getId().equals(findTab.getId())) {
				Assert.assertNotEquals(findTab.getId(), tabbox.getSelectedTab().getId());
			}
		});
	}

	@Test(dataProvider = "dataFixTabPanels")
	public void testFixTabPanels(final int initTabs, final int initPanels) throws Exception {
		final int expectedCount = Math.max(initTabs, initPanels);

		final Tabbox tabbox = new Tabbox();
		if (initTabs > 0) {
			final Tabs tabs = new Tabs();
			tabbox.appendChild(tabs);
			for (int i = 0; i < initTabs; i++) {
				tabs.appendChild(new Tab());
			}
		}
		if (initPanels > 0) {
			final Tabpanels tabPanels = new Tabpanels();
			tabbox.appendChild(tabPanels);
			for (int i = 0; i < initPanels; i++) {
				tabPanels.appendChild(new Tabpanel());
			}
		}

		TabboxUtils.fixTabPanels(tabbox);
		Assert.assertEquals(tabbox.getTabs().getChildren().size(), expectedCount);
		Assert.assertEquals(tabbox.getTabpanels().getChildren().size(), expectedCount);
	}

	@Test(dataProvider = "dataGetComp")
	public void testGetComp(final String compPath, Class expectedClass) throws Exception {
		final Component comp = TabboxUtils.getComp(compPath);
		if (expectedClass == null) {
			Assert.assertNull(comp);
		} else {
			Assert.assertNotNull(comp);
			Assert.assertTrue(comp.getClass().isAssignableFrom(expectedClass));

			if (comp instanceof Include) {
				LOG.info("include source: {}", ((Include) comp).getSrc());
			}
		}
	}

	@Test(dataProvider = "dataGetTabFromChildren")
	public void testGetTabFromChildren(final Component children, final boolean expectedFound) throws Exception {
		final DummyTabbox tabbox = new DummyTabbox();

		final Tab tab = TabboxUtils.getTabFromChildren(tabbox, children);
		Assert.assertEquals(tab != null, expectedFound);
	}

	@Test(dataProvider = "dataGetTabId")
	public void testGetTabId(final String expectedTabId, final Component children) {
		final String tabId = TabboxUtils.getTabId(children);
		Assert.assertEquals(tabId, expectedTabId);
	}

	@Test
	public void testGetTabpanels() throws Exception {
		Assert.assertNull(TabboxUtils.getTabpanels(null));
		final Tabbox tabbox = new Tabbox();
		Assert.assertNull(tabbox.getTabpanels());
		Assert.assertNotNull(TabboxUtils.getTabpanels(tabbox));
	}

	@Test
	public void testGetTabs() throws Exception {
		Assert.assertNull(TabboxUtils.getTabs(null));
		final Tabbox tabbox = new Tabbox();
		Assert.assertNull(tabbox.getTabs());
		Assert.assertNotNull(TabboxUtils.getTabs(tabbox));
	}
}
