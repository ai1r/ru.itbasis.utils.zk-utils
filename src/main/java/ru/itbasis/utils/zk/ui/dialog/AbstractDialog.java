package ru.itbasis.utils.zk.ui.dialog;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.ConventionWires;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Label;
import org.zkoss.zul.LayoutRegion;
import org.zkoss.zul.North;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.West;
import org.zkoss.zul.Window;
import ru.itbasis.utils.core.ISelf;
import ru.itbasis.utils.zk.LogMsg;
import ru.itbasis.utils.zk.ui.TabboxUtils;

public abstract class AbstractDialog<Self extends AbstractDialog> extends Window implements ISelf<Self> {
	public static final String DEFAULT_HFLEX = "1";
	public static final String DEFAULT_VFLEX = "1";

	protected static final int MIN_FORM_HEIGHT   = 400;
	protected static final int MIN_FORM_WIDTH    = 500;
	protected static final int MIN_PREVIEW_WIDTH = 400;

	private static final transient Logger LOG = LoggerFactory.getLogger(AbstractDialog.class.getName());

	private Borderlayout        _layout;
	private AbstractDialogFrame _frameGeneral;
	private Toolbar             _toolbar;
	private boolean             _enablePreview;
	private Tabbox              _tabBox;

	public AbstractDialog(final Page page) {
		LOG.trace(LogMsg.PAGE, page);

		setClosable(true);
		setBorder(true);
		setTitle(StringUtils.SPACE);

		setMinwidth(MIN_FORM_WIDTH);
		setMinheight(MIN_FORM_HEIGHT);

		setHeight(Integer.toString(MIN_FORM_HEIGHT) + "px");
		setWidth(Integer.toString(MIN_FORM_WIDTH) + "px");

		setMaximizable(true);
		setSizable(true);

		ConventionWires.wireVariables(this, this);
		ConventionWires.addForwards(this, this);

		initLayout();

		setPage(page);
	}

	protected abstract AbstractDialogFrame initFrameGeneral();

	protected abstract void initTitle();

	protected Self appendTab(final AbstractDialogTab tab) {
		if (!isTabEnabled()) {
			enableTabs();
		}
		tab.setClosable(false);
		TabboxUtils.appendTab(_tabBox, tab, tab.getFrame());
		return getSelf();
	}

	protected Self appendTabClosable(final AbstractDialogTab tab) {
		appendTab(tab);
		tab.setClosable(true);
		return getSelf();
	}

	private void disablePreview() {
		_enablePreview = false;

		final Center center = _layout.getCenter();
		Components.removeAllChildren(center);
		_frameGeneral.setParent(center);
		setWidth(MIN_FORM_WIDTH + "px");
		if (_layout.getWest() != null) {
			_layout.removeChild(_layout.getWest());
		}
	}

	public Self disableTabs() {
		_frameGeneral.setParent(getMainBox());
		Components.removeAllChildren(_tabBox);
		_tabBox = null;
		return getSelf();
	}

	public void enablePreview(final boolean flag) {
		_enablePreview = flag;
		if (flag) {
			enablePreview(MIN_FORM_WIDTH);
		} else {
			disablePreview();
		}
	}

	public void enablePreview(final int formWidth) {
		_enablePreview = true;

		West west = _layout.getWest();
		if (west == null) {
			west = new West();
			west.setBorder("none");
			west.setSplittable(true);
			west.setParent(_layout);
		}
		west.setWidth(formWidth + "px");
		setWidth((MIN_PREVIEW_WIDTH + formWidth) + "px");
		_frameGeneral.setParent(getMainBox());
	}

	public Self enableTabs() {
		initTabbox();
		return getSelf();
	}

	@SuppressWarnings("unchecked")
	protected <T extends AbstractDialogFrame> T getFrameGeneral() {
		if (_frameGeneral == null) {
			_frameGeneral = initFrameGeneral();
		}
		return (T) _frameGeneral;
	}

	protected LayoutRegion getMainBox() {
		if (_enablePreview) {
			return _layout.getWest();
		}
		return _layout.getCenter();
	}

	protected LayoutRegion getPreviewBox() {
		if (isEnablePreview()) {
			return _layout.getCenter();
		}
		return null;
	}

	protected String getTabId(final String suffix) {
		return getUuid().hashCode() + "_" + suffix;
	}

	public Toolbar getToolbar() {
		assert _toolbar != null;
		return _toolbar;
	}

	private void initLayout() {
		_layout = new Borderlayout();
		_layout.setVflex(DEFAULT_VFLEX);
		_layout.setParent(this);

		initLayoutNorth();
		initLayoutCenter();
	}

	private void initLayoutCenter() {
		final Center center = new Center();
		center.setBorder("true");
		center.setParent(_layout);

		_frameGeneral = initFrameGeneral();
		_frameGeneral.setParent(center);

		initLayoutCenterContent();
	}

	protected void initLayoutCenterContent() {
	}

	private void initLayoutNorth() {
		_toolbar = new Toolbar();
		_toolbar.setHflex(DEFAULT_HFLEX);
		if (!initToolbarContent(_toolbar)) {
			_toolbar = null;
			return;
		}

		final North north = new North();
		north.setBorder("none");
		north.setParent(_layout);
		north.appendChild(_toolbar);
	}

	protected void initTabBoxExtTabs(final Tabbox tb) {
	}

	protected AbstractDialogTab initTabGeneral(final Tabbox tb) {
		final TabDefault tab = TabboxUtils.findTab(tb, getTabId(IDialogTab.TAB_ID_GENERAL));
		if (tab == null) {
			appendTab(new TabDefault());
		}
		return tab;
	}

	private void initTabbox() {
		_tabBox = new Tabbox();
		_tabBox.setVflex(DEFAULT_VFLEX);

		initTabGeneral(_tabBox);
		initTabBoxExtTabs(_tabBox);

		getMainBox().appendChild(_tabBox);
	}

	protected boolean initToolbarContent(final Toolbar tb) {
		return false;
	}

	public boolean isEnablePreview() {
		return _enablePreview;
	}

	public boolean isTabEnabled() {
		return _tabBox != null;
	}

	public void preview(final Component comp) {
		if (_layout.getWest() == null) {
			return;
		}
		final Center infoBox = _layout.getCenter();
		Components.removeAllChildren(infoBox);
		if (comp != null) {
			comp.setParent(infoBox);
			return;
		}
		final Vbox vbox = new Vbox();
		vbox.setPack("center");
		vbox.setAlign("center");
		vbox.appendChild(new Label(Labels.getLabel("empty.exInfo")));
		vbox.setParent(infoBox);
	}

	public Self toogleTabs() {
		if (isTabEnabled()) {
			return disableTabs();
		}
		return enableTabs();
	}

	private class TabDefault extends AbstractDialogTab<AbstractDialogFrame> {
		public TabDefault() {
			super(Labels.getRequiredLabel(IDialogTab.LABEL_CORE_DIALOG_TAB_GENERAL_TITLE));
		}

		@Override
		public AbstractDialogFrame getFrame() {
			return getFrameGeneral();
		}
	}
}
