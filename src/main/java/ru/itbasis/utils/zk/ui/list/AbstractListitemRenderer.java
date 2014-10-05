package ru.itbasis.utils.zk.ui.list;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zhtml.Text;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.ListitemRenderer;
import ru.itbasis.utils.core.LogMsg;
import ru.itbasis.utils.core.utils.DateUtils;
import ru.itbasis.utils.zk.ui.view.cells.CellFlag;

import java.util.Calendar;

public abstract class AbstractListitemRenderer<T> implements ListitemRenderer<T> {
	private static final transient Logger LOG = LoggerFactory.getLogger(AbstractListitemRenderer.class.getName());

//	protected SimpleDateFormat sdfDT;
//	protected SimpleDateFormat sdfD;

	protected AbstractListitemRenderer() {
//		sdfD = new SimpleDateFormat(DateFormats.getDateFormat(DateFormat.SHORT, null, null));
//		sdfDT = new SimpleDateFormat(DateFormats.getDateTimeFormat(DateFormat.SHORT, DateFormat.SHORT, null, null));
	}

	protected Listcell cellDate(final Calendar calendar) {
		final Listcell cell = new Listcell();
		if (null == calendar) {
			return cell;
		}

		final String value = DateUtils.formatAsShortDate(calendar);
		LOG.trace(LogMsg.VALUE, value);

		cell.appendChild(new Text(value));

		return cell;
	}

	protected Listcell cellDateTime(final Calendar calendar) {
		final Listcell cell = new Listcell();
		if (null == calendar) {
			return cell;
		}

		final String value = DateUtils.formatAsShortDateTime(calendar);
		LOG.trace(LogMsg.VALUE, value);

		cell.appendChild(new Text(value));

		return cell;
	}

	protected Listcell cellFlag(final Boolean flag) {
		LOG.trace("flag: {}", flag);
		return new CellFlag().setChecked(flag);
	}

}
