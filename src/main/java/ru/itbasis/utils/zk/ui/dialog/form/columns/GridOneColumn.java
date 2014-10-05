package ru.itbasis.utils.zk.ui.dialog.form.columns;

import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;

public class GridOneColumn extends Columns {
	public GridOneColumn() {
		setVisible(false);

		final Column c0 = new Column();
		c0.setParent(this);
	}
}
