package ru.itbasis.utils.zk.ui.dialog.form.columns;

import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import ru.itbasis.utils.zk.ui.ZkDefaultProperties;

public class GridTwoColumn extends Columns {
	public GridTwoColumn() {
		setVisible(false);

		final Column c0 = new Column();
		c0.setAlign("right");
		c0.setWidth(ZkDefaultProperties.DEFAULT_COLUMN_LABEL_WIDTH);
		c0.setParent(this);

		final Column c1 = new Column();
		c1.setParent(this);
	}
}
