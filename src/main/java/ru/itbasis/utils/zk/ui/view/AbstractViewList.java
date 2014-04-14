package ru.itbasis.utils.zk.ui.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.text.DateFormats;
import org.zkoss.zhtml.Text;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.ListitemRenderer;
import ru.itbasis.utils.zk.LogMsg;
import ru.itbasis.utils.zk.ui.view.cells.CellFlag;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

abstract public class AbstractViewList extends AbstractView {
	private transient static final Logger LOG = LoggerFactory.getLogger(AbstractViewList.class.getName());

	protected Listbox _list;

	@Override
	protected void initLayoutCenterChild() {
		_list = initList(getCenter());
		initHeaders();
	}

	protected Listbox initList(Component parent) {
		Listbox list = new Listbox();
		list.setParent(parent);
		list.setVflex(DEFAULT_VFLEX);
		list.setAutopaging(true);
		list.setPagingPosition("both");
		list.setMold("paging");
		return list;
	}

	abstract protected void initHeaders();

	abstract protected void loadData();

	abstract public class AbstractListitemRenderer<T> implements ListitemRenderer<T> {
		protected SimpleDateFormat sdfDT;
		protected SimpleDateFormat sdfD;

		protected AbstractListitemRenderer() {
			sdfD = new SimpleDateFormat(DateFormats.getDateFormat(DateFormat.SHORT, null, null));
			sdfDT = new SimpleDateFormat(DateFormats.getDateTimeFormat(DateFormat.SHORT, DateFormat.SHORT, null, null));
		}

		/**
		 * @param prefixLabel If != "" then zclass=prefixLabel+".zclass"
		 */
		protected Listcell cellFlag(Boolean flag, String prefixLabel) {
			LOG.trace("flag: {}, prefixLabel: {}", flag, prefixLabel);
			return new CellFlag(flag, prefixLabel);
		}

		protected Listcell cellFlag(Boolean flag) {
			LOG.trace("flag: {}", flag);
			return new CellFlag(flag);
		}

		protected Listcell cellDate(Calendar calendar) {
			Listcell cell = new Listcell();
			if (null == calendar) {
				return cell;
			}

			final String value = sdfD.format(calendar.getTime());
			LOG.trace(LogMsg.VALUE, value);

			cell.appendChild(new Text(value));

			return cell;
		}

		protected Listcell cellDateTime(Calendar calendar) {
			Listcell cell = new Listcell();
			if (null == calendar) {
				return cell;
			}

			final String value = sdfDT.format(calendar.getTime());
			LOG.trace(LogMsg.VALUE, value);

			cell.appendChild(new Text(value));

			return cell;
		}

	}

	public class Event$Listitem$OnClick implements EventListener<Event> {
		@Override
		public void onEvent(Event event) throws Exception {
			if (actionEdit != null) {
				actionEdit.setDisabled(false);
			}
		}
	}

	public class Event$List$Refresh implements EventListener<Event> {
		@Override
		public void onEvent(Event event) throws Exception {
			LOG.trace(LogMsg.EVENT, event);
			if (actionEdit != null) {
				actionEdit.setDisabled(true);
			}
			loadData();
		}
	}
}