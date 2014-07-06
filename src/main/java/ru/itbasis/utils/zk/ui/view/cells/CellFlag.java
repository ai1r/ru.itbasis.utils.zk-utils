package ru.itbasis.utils.zk.ui.view.cells;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zhtml.I;
import org.zkoss.zul.Listcell;

// TODO Задокументировать
// FIXME Добавить обработку событий (ON_CLICK, etc)
public final class CellFlag extends Listcell {
	public static final String DEFAULT_ZCLASS_CHECKED   = "z-icon-check-square-o";
	public static final String DEFAULT_ZCLASS_UNCHECKED = "z-icon-square-o";
	public static final String LABEL_SUFFIX_SCLASS = ".zclass";
	public static final String LABEL_SUFFIX_TITLE  = ".title";
	private static final transient Logger LOG = LoggerFactory.getLogger(CellFlag.class.getName());

	private I _tagI;

	private boolean _checked;
	private boolean _titleVisible;

	private String _zclassOn;
	private String _zclassOff;

	private boolean _useLabelZclass;

	public CellFlag() {
		_zclassOn = DEFAULT_ZCLASS_CHECKED;
		_zclassOff = DEFAULT_ZCLASS_UNCHECKED;

		_tagI = new I();
		_tagI.setParent(this);
	}

	public CellFlag build() {
		setState(_checked ? _zclassOn : _zclassOff);
		return this;
	}

	public boolean isChecked() {
		return _checked;
	}

	public CellFlag setChecked(final boolean value) {
		this._checked = value;
		build();
		return this;
	}

	public boolean isTitleVisible() {
		return _titleVisible;
	}

	public CellFlag setTitleVisible(final boolean value) {
		this._titleVisible = value;
		return this;
	}

	public boolean isUseLabelsFromZclass() {
		return _useLabelZclass;
	}

	public CellFlag setUseLabelsFromZclass(final boolean value) {
		this._useLabelZclass = value;
		return this;
	}

	private void setState(final String preffix) {
		final String sclass = (_useLabelZclass ? Labels.getRequiredLabel(preffix + LABEL_SUFFIX_SCLASS) : preffix);
		LOG.trace("sclass: {}", sclass);
		_tagI.setSclass(sclass);

		if (!_titleVisible) {
			return;
		}
		final String key = preffix + LABEL_SUFFIX_TITLE;
		final String title = Labels.getLabel(key);
		LOG.trace("title: {}", title);
		if (StringUtils.isEmpty(title)) {
			LOG.error("not found label value from key: '{}'", key);
		}
		_tagI.setDynamicProperty("title", title);
	}

	public CellFlag toggleChecked() {
		setChecked(!isChecked());
		return this;
	}

}
