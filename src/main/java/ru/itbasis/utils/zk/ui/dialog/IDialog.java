package ru.itbasis.utils.zk.ui.dialog;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.LayoutRegion;
import org.zkoss.zul.Toolbar;
import ru.itbasis.utils.core.ISelf;
import ru.itbasis.utils.zk.ui.ZkDefaultProperties;

public interface IDialog<Self extends IDialog> extends ISelf<Self>, ZkDefaultProperties {
	String LABEL_CORE_EMPTY_EXT_INFO = "empty.exInfo";

	Self enablePreview(final boolean flag);

	Toolbar getToolbar();

	boolean isEnablePreview();

	boolean isTabEnabled();

	Self setTabEnabled(final boolean flag);

	LayoutRegion setPreview(final Component comp);

	Self toogleTabs();
}
