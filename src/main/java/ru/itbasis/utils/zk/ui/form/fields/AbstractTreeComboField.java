package ru.itbasis.utils.zk.ui.form.fields;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Treeitem;
import ru.itbasis.utils.core.model.ITitle;
import ru.itbasis.utils.core.service.ITreeService;
import ru.itbasis.utils.zk.LogMsg;
import ru.itbasis.utils.zk.ui.tree.AbstractTree;

public abstract class AbstractTreeComboField<Value, Service extends ITreeService, Tree extends AbstractTree, Filter>
	extends AbstractComboField<Value> {
	private static final transient Logger LOG = LoggerFactory.getLogger(AbstractTreeComboField.class.getName());

	private Service treeService;

	private Tree   _tree;
	private Filter filter;

	public AbstractTreeComboField() {
		super();
		treeService = initService();
	}

	protected abstract Service initService();

	protected abstract Tree initTree();

	@Override
	protected String coerceToString(final Object value) {
		LOG.trace(LogMsg.VALUE, value);
		if (value == null) {
			return "";
		}
		if (value instanceof ITitle) {
			return ((ITitle) value).getTitle();
		}
		return (String) value;
	}

	public Filter getFilter() {
		return filter;
	}

	public void setFilter(final Filter filter) {
		this.filter = filter;
		this.setRawValue(null);
		this.setText(StringUtils.EMPTY);
		Components.removeAllChildren(getDropdown());
	}

	public Tree getTree() {
		if (_tree == null) {
			_tree = initTree();
			_tree.setVflex(false);
		}
		return _tree;
	}

	public ITreeService getTreeService() {
		if (treeService == null) {
			treeService = initService();
		}
		return treeService;
	}

	@SuppressWarnings("unchecked")
	protected void initPartPopup() {
		final Bandpopup popup = getDropdown();

		_tree = getTree();
		_tree.setFilter(filter).setParent(popup);
	}

	public class Event$Default$TreeItem$onDoubleClick implements EventListener<Event> {
		@Override
		public void onEvent(final Event event) throws Exception {
			LOG.trace(LogMsg.EVENT, event);

			final Treeitem ti = (Treeitem) event.getTarget();
			setRawValue(getTreeService().getItem(ti.getValue()));

			Events.postEvent(Events.ON_CHANGE, getDropdown().getParent(), getRawValue());
			close();
		}
	}

	public class Event$Default$TreeCategory$onDoubleClick implements EventListener<Event> {
		@Override
		public void onEvent(final Event event) throws Exception {
			LOG.trace(LogMsg.EVENT, event);

			final Treeitem ti = (Treeitem) event.getTarget();
			setRawValue(getTreeService().getCategory(ti.getValue()));

			Events.postEvent(Events.ON_CHANGE, getDropdown().getParent(), getRawValue());
			close();
		}
	}

}
