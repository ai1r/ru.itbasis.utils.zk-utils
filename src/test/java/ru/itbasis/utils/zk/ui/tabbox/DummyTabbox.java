package ru.itbasis.utils.zk.ui.tabbox;

import org.zkoss.zul.Label;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;

public class DummyTabbox extends Tabbox {

	public static final String CHILD_ID    = "label";
	public static final int    COUNT_INDEX = 4;

	public DummyTabbox() {
		final Tabs tabs = new Tabs();
		appendChild(tabs);

		{
			Tab tab = new Tab("not empty");
			tabs.appendChild(tab);
			this.setSelectedTab(tab);

			this.appendChild(new Tabpanels());
			final Tabpanel tabpanel = new Tabpanel();
			final Label label = new Label("test");
			label.setId(CHILD_ID);
			tabpanel.appendChild(label);
			tabpanel.setParent(this.getTabpanels());

			tab.setId(TabboxUtils.PREFIX_TAB_ID + label.getId());
		}

		{
			Tab tab = new Tab("selectedTab");
			tab.setId("tabSelected");
			tabs.appendChild(tab);
			this.setSelectedTab(tab);
		}

		for (int i = 0; i < COUNT_INDEX; i++) {
			final String id = "tab" + i;
			final Tab tab = new Tab(id);
			tab.setId(id);
			tabs.appendChild(tab);
		}

	}

}
