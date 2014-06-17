package ru.itbasis.utils.zk.ui.view.cells;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zhtml.I;
import org.zkoss.zul.Listcell;

// TODO Задокументировать
public class CellFlag extends Listcell {
	private transient static final Logger LOG = LoggerFactory.getLogger(CellFlag.class.getName());

	public static final String ZCLASS_CHECKED   = "z-icon-check-square-o";
	public static final String ZCLASS_UNCHECKED = "z-icon-square-o";

	private I      tagI;
	private String prefixLabel;

	public CellFlag() {
		this(false, null);
	}

	public CellFlag(final boolean flag) {
		this(flag, null);
	}

	public CellFlag(final boolean checked, final String prefixLabel) {
		LOG.debug("checked: {}, prefixLabel: {}", checked, prefixLabel);

		tagI = new I();
		this.prefixLabel = prefixLabel;
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
		if (prefixLabel != null && !prefixLabel.trim().isEmpty()) {
			title = Labels.getLabel(prefixLabel, "");
			zclass = Labels.getLabel(prefixLabel + ".zclass", defZclass);
		}
		LOG.trace("title: {}", title);
		LOG.trace("zclass: {}", zclass);
		tagI.setSclass(zclass);
		if (!title.isEmpty()) {
			tagI.setDynamicProperty("title", title);
		}
	}
}
