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
import ru.itbasis.utils.zk.LabelProperties;
import ru.itbasis.utils.zk.ZkLogMsg;
import ru.itbasis.utils.zk.ui.ZkDefaultProperties;
import ru.itbasis.utils.zk.ui.dialog.form.AbstractDialogFormFrame;
import ru.itbasis.utils.zk.ui.tabbox.TabboxUtils;

// TODO Убрать имплементацию DefaultProperties - рудимент.
public abstract class AbstractDialog<Self extends AbstractDialog> extends Window implements IDialog<Self> {

	private static final transient Logger LOG = LoggerFactory.getLogger(AbstractDialog.class.getName());

	private Borderlayout _layout;
	private IDialogFrame _frameGeneral;
	private Toolbar      _toolbar;
	private boolean      _enablePreview;
	private Tabbox       _tabBox;

	public AbstractDialog(final Page page) {
		LOG.trace(ZkLogMsg.PAGE, page);

		setClosable(true);
		setBorder(true);
		setTitle(StringUtils.SPACE);

		setMinwidth(ZkDefaultProperties.MIN_FORM_WIDTH);
		setMinheight(ZkDefaultProperties.MIN_FORM_HEIGHT);

		setHeight(Integer.toString(ZkDefaultProperties.MIN_FORM_HEIGHT) + "px");
		setWidth(Integer.toString(ZkDefaultProperties.MIN_FORM_WIDTH) + "px");

		setMaximizable(true);
		setSizable(true);

		ConventionWires.wireVariables(this, this);
		ConventionWires.addForwards(this, this);

		initLayout();

		setPage(page);
	}

	protected abstract IDialogFrame initFrameGeneral();

	@SuppressWarnings("unused")
	protected abstract void initTitle();

	protected Self appendTab(final AbstractDialogFormFrame frame) {
		return appendTab(new DialogTab<>(frame));
	}

	private IDialogTab appendTab(final AbstractDialogFormFrame frame, final boolean closable) {
		return appendTab(new DialogTab<>(frame), closable);
	}

	protected Self appendTab(final DialogTab tab) {
		appendTab(tab, false);
		return getSelf();
	}

	protected IDialogTab appendTab(final DialogTab tab, final boolean closable) {
		if (!isTabEnabled()) {
			enableTabs();
		}
		tab.setClosable(closable);
		return TabboxUtils.appendTab(_tabBox, tab, tab.getFrame());
	}

	@SuppressWarnings("unused")
	protected Self appendTabClosable(final DialogTab tab) {
		appendTab(tab, true);
		return getSelf();
	}

	private void disablePreview() {
		_enablePreview = false;

		final Center center = _layout.getCenter();
		Components.removeAllChildren(center);
		_frameGeneral.setParent(center);
		setWidth(ZkDefaultProperties.MIN_FORM_WIDTH + "px");
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
		setWidth((ZkDefaultProperties.MIN_PREVIEW_WIDTH + formWidth) + "px");
		_frameGeneral.setParent(getMainBox());
	}

	@Override
	public Self enablePreview(final boolean flag) {
		_enablePreview = flag;
		if (flag) {
			enablePreview(ZkDefaultProperties.MIN_FORM_WIDTH);
		} else {
			disablePreview();
		}
		return getSelf();
	}

	@Override
	public Toolbar getToolbar() {
		assert _toolbar != null;
		return _toolbar;
	}

	@Override
	public boolean isEnablePreview() {
		return _layout.getWest() != null;
	}

	@Override
	public boolean isTabEnabled() {
		return _tabBox != null;
	}

	@Override
	public Self setTabEnabled(final boolean flag) {
		if (flag) {
			enableTabs();
		} else {
			disableTabs();
		}
		return getSelf();
	}

	@Override
	public LayoutRegion setPreview(final Component comp) {
		if (!isEnablePreview()) {
			enablePreview(true);
		}

		final Center infoBox = _layout.getCenter();
		Components.removeAllChildren(infoBox);
		if (comp != null) {
			comp.setParent(infoBox);
			return null;
		}

		final Vbox vbox = new Vbox();
		vbox.setPack("center");
		vbox.setAlign("center");
		vbox.appendChild(new Label(Labels.getLabel(LABEL_CORE_EMPTY_EXT_INFO, "no data...")));
		vbox.setParent(infoBox);
		return getPreviewBox();
	}

	@Override
	public Self toogleTabs() {
		if (isTabEnabled()) {
			return disableTabs();
		}
		return enableTabs();
	}

	private Self enableTabs() {
		initTabBox();
		return getSelf();
	}

	@SuppressWarnings("unchecked")
	protected <T extends IDialogFrame> T getFrameGeneral() {
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

	private void initLayout() {
		_layout = new Borderlayout();
		_layout.setVflex(ZkDefaultProperties.DEFAULT_VFLEX);
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

	@Deprecated
	protected void initLayoutCenterContent() {
	}

	private void initLayoutNorth() {
		_toolbar = new Toolbar();
		if (!initToolbarContent(_toolbar)) {
			_toolbar = null;
			return;
		}
		_toolbar.setHflex(ZkDefaultProperties.DEFAULT_HFLEX);

		final North north = new North();
		north.setBorder("none");
		north.setParent(_layout);
		north.appendChild(_toolbar);
	}

	private void initTabBox() {
		_tabBox = new Tabbox();
		_tabBox.setVflex(ZkDefaultProperties.DEFAULT_VFLEX);

		initTabGeneral(_tabBox);
		initTabBoxExtTabs(_tabBox);

		getMainBox().appendChild(_tabBox);
	}

	@SuppressWarnings("unused")
	protected void initTabBoxExtTabs(final Tabbox tb) {
	}

	private DialogTab initTabGeneral(final Tabbox tb) {
		final DialogTab tab = TabboxUtils.findTab(tb, getTabId(IDialogTab.TAB_ID_GENERAL));
		if (tab == null) {
			appendTab(new DialogTab<>(getFrameGeneral(), Labels.getRequiredLabel(LabelProperties.LABEL_CORE_DIALOG_TAB_GENERAL_TITLE)));
		}
		return tab;
	}

	@SuppressWarnings("unused")
	protected boolean initToolbarContent(final Toolbar tb) {
		return false;
	}

}
