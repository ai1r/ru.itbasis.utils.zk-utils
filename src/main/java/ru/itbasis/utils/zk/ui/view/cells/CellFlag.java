package ru.itbasis.utils.zk.ui.view.cells;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zhtml.I;
import org.zkoss.zul.Listcell;

// TODO Задокументировать
public class CellFlag extends Listcell {
	public static final String ZCLASS_CHECKED   = "z-icon-check-square-o";
	public static final String ZCLASS_UNCHECKED = "z-icon-square-o";

	private static final transient Logger LOG = LoggerFactory.getLogger(CellFlag.class.getName());

	private I      tagI;
	private String _prefixLabel;

	public CellFlag() {
		this(false, null);
	}

	public CellFlag(final boolean flag) {
		this(flag, null);
	}

	public CellFlag(final boolean checked, final String prefixLabel) {
		LOG.debug("checked: {}, prefixLabel: {}", checked, prefixLabel);

		tagI = new I();
		this._prefixLabel = prefixLabel;
		if (checked) {
			makeIcon(ZCLASS_CHECKED);
		} else {
			makeIcon(ZCLASS_UNCHECKED);
		}
		tagI.setParent(this);
	}


	private void makeIcon(final String defZclass) {
		String title = "";
		String zclass = defZclass;
		if (_prefixLabel != null && !_prefixLabel.trim().isEmpty()) {
			title = Labels.getLabel(_prefixLabel, "");
			zclass = Labels.getLabel(_prefixLabel + ".zclass", defZclass);
		}
		LOG.trace("title: {}", title);
		LOG.trace("zclass: {}", zclass);
		tagI.setSclass(zclass);
		if (!title.isEmpty()) {
			tagI.setDynamicProperty("title", title);
		}
	}
}
