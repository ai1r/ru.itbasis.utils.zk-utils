package ru.itbasis.utils.zk.ui.tree;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treecol;
import org.zkoss.zul.Treecols;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;
import ru.itbasis.utils.core.ISelf;
import ru.itbasis.utils.core.LogMsg;
import ru.itbasis.utils.core.model.IDescription;
import ru.itbasis.utils.core.model.IId;
import ru.itbasis.utils.core.model.ITitle;
import ru.itbasis.utils.core.model.tree.ITreeCategory;
import ru.itbasis.utils.core.model.tree.ITreeItem;
import ru.itbasis.utils.core.service.ITreeService;

import java.util.List;

public abstract class AbstractTree<Self extends AbstractTree, Category extends ITreeCategory, Item extends ITreeItem, Filter> extends Tree
	implements ISelf<Self> {

	private static final transient Logger LOG = LoggerFactory.getLogger(AbstractTree.class.getName());

	protected ITreeService<Category, Item, Filter> treeService;

	private Filter  filter;
	private boolean itemVisible;
	private boolean defaultCategoryOpenState;

	private EventListener<Event> listenerCategoryClick;
	private EventListener<Event> listenerCategoryDoubleClick;
	private EventListener<Event> listenerItemClick;
	private EventListener<Event> listenerItemDoubleClick;

	protected AbstractTree() {
		setVflex(true);
		setSizedByContent(true);
		initTreeCols();
		appendChild(new Treechildren());
	}

	protected abstract Treecols initTreeCols();

	protected Treecell appendCellDescription(final Treerow row, final Object o) {
		final Treecell cell = new Treecell();
		LOG.trace("item: {}", o);

		row.appendChild(cell);
		if (!(o instanceof IDescription)) {
			return cell;
		}
		cell.setLabel(((IDescription) o).getDescription());
		return cell;
	}

	protected Treecell appendCellId(final Treerow row, final Object o) {
		assert o instanceof IId;
		LOG.trace("item: {}", o);

		final Treecell cell = new Treecell(((IId) o).getId().toString());
		row.appendChild(cell);
		return cell;
	}

	protected Treecell appendCellTitle(final Treerow row, final Object o) {
		assert o instanceof ITitle;
		LOG.trace("item: {}", o);

		final Treecell cell = new Treecell(((ITitle) o).getTitle());
		row.appendChild(cell);
		return cell;
	}

	public void build() {
		final Treechildren tc = getTreechildren();
		Components.removeAllChildren(tc);
		if (filter != null) {
			loadRootFromFilter(tc);
		} else {
			loadRoot(tc);
		}
	}

	protected Treeitem findItemFromId(final String tiId) {
		final List<Component> componentList = Selectors.find(getSelf(), "#" + tiId);
		LOG.trace(LogMsg.FOUND_ITEMS, componentList.size());
		if (CollectionUtils.isNotEmpty(componentList)) {
			return (Treeitem) componentList.get(0);
		}
		return null;
	}

	public Filter getFilter() {
		return filter;
	}

	public Self setFilter(final Filter value) {
		this.filter = value;
		if (getTreechildren() != null && getTreechildren().getItemCount() > 0) {
			build();
		}
		return getSelf();
	}

	public EventListener<Event> getListenerCategoryClick() {
		return listenerCategoryClick;
	}

	public Self setListenerCategoryClick(final EventListener<Event> value) {
		this.listenerCategoryClick = value;
		return getSelf();
	}

	public EventListener<Event> getListenerCategoryDoubleClick() {
		return listenerCategoryDoubleClick;
	}

	public Self setListenerCategoryDoubleClick(final EventListener<Event> value) {
		this.listenerCategoryDoubleClick = value;
		return getSelf();
	}

	public EventListener<Event> getListenerItemClick() {
		return listenerItemClick;
	}

	public Self setListenerItemClick(final EventListener<Event> value) {
		this.listenerItemClick = value;
		return getSelf();
	}

	public EventListener<Event> getListenerItemDoubleClick() {
		return listenerItemDoubleClick;
	}

	public Self setListenerItemDoubleClick(final EventListener<Event> value) {
		this.listenerItemDoubleClick = value;
		return getSelf();
	}

	protected String getTreeCategoryId(final Category category) {
		return "cat_" + getUuid().hashCode() + "_" + category.getId().toString();
	}

	protected String getTreeItemId(final Item item) {
		return "item_" + getUuid().hashCode() + "_" + item.getId().toString();
	}

	public ITreeService getTreeService() {
		return treeService;
	}

	public Self setTreeService(final ITreeService<Category, Item, Filter> value) {
		this.treeService = value;
		return getSelf();
	}

	public boolean isDefaultCategoryOpenState() {
		return defaultCategoryOpenState;
	}

	public Self setDefaultCategoryOpenState(final boolean value) {
		this.defaultCategoryOpenState = value;
		return getSelf();
	}

	public boolean isItemVisible() {
		return itemVisible;
	}

	public Self setItemVisible(final boolean value) {
		this.itemVisible = value;
		getTreecols().getChildren().stream().filter(component -> (component.hasAttribute(TreeColumns.ATTR_COLUMN_ITEM) && (Boolean) component
			.getAttribute(TreeColumns.ATTR_COLUMN_ITEM))).forEach(component -> component.setVisible(value));
		return getSelf();
	}

	protected void loadChildrens(final Treechildren tc, final Category category) {
		LOG.trace("category: {}", category);

		if (!tc.getChildren().isEmpty()) {
			// TODO Добавить флаг на повторное чтение
			return;
		}

		final List<Category> categoryList = treeService.getChildrenCategoryAll(category);
		makeTreeCategories(tc, categoryList);

		if (itemVisible) {
			makeTreeItems(tc, category.getChildItems());
		}
	}

	protected void loadRoot(final Treechildren tc) {
		makeTreeCategories(tc, treeService.getRootCategoryAll());
	}

	protected void loadRootFromFilter(final Treechildren tc) {
		makeTreeCategories(tc, treeService.getRootCategoryAll(filter));
	}

	protected Treeitem makeRowItem(final Treechildren tc, final Object item) {
		LOG.trace(LogMsg.ITEM, item);
		final Treeitem ti = makeRowItem(item);
		ti.setParent(tc);
		return ti;
	}

	protected Treeitem makeRowItem(final Object item) {
		assert item instanceof IId;

		LOG.trace(LogMsg.ITEM, item);

		final Treeitem ti = new Treeitem();
		ti.setValue(((IId) item).getId());


		final Treerow row = new Treerow();

		final String sClass = item.getClass().getSimpleName() + " " + (item instanceof ITreeCategory ? "tree-category" : "tree-item");
		LOG.trace("sclass: '{}'", sClass);
		row.setSclass(sClass);
//		row.setParent(ti);

		appendCellTitle(row, item);
		appendCellDescription(row, item);

		ti.appendChild(row);
		return ti;
	}

	protected void makeTreeCategories(final Treechildren tc, final List<Category> categories) {
		if (CollectionUtils.isEmpty(categories)) {
			return;
		}
		LOG.trace("categories: {}", categories.size());
		for (Category category : categories) {
			makeTreeCategory(tc, category);
		}
	}

	private Treeitem makeTreeCategory(final Treechildren tc, final Category category, final String treeCategoryId) {
		final Treeitem ti = makeRowItem(tc, category);
		ti.setId(treeCategoryId);

		if (listenerCategoryClick != null) {
			ti.addEventListener(Events.ON_CLICK, listenerCategoryClick);
		}
		if (listenerCategoryDoubleClick != null) {
			ti.addEventListener(Events.ON_DOUBLE_CLICK, listenerCategoryDoubleClick);
		}

		// TODO Кажется тут можно оптимизировать
		if (CollectionUtils.isNotEmpty(category.getChildCategories()) || (itemVisible && CollectionUtils.isNotEmpty(category.getChildItems()))) {
			ti.setOpen(defaultCategoryOpenState);
			final Treechildren tc1 = new Treechildren();
			tc1.setParent(ti);
			if (defaultCategoryOpenState) {
				loadChildrens(tc1, category);
			} else {
				ti.addEventListener(Events.ON_OPEN, new Event$TreeItem$OnOpen());
			}
		}
		return ti;
	}

	protected Treeitem makeTreeCategory(final Treechildren tc, final Category category) {
		LOG.trace(LogMsg.ITEM, category);

		final String treeCategoryId = getTreeCategoryId(category);
		LOG.trace("treeCategoryId: {}", treeCategoryId);

		final Treeitem ti = findItemFromId(treeCategoryId);
		if (ti != null) {
			return ti;
		}

		return makeTreeCategory(tc, category, treeCategoryId);
	}


	protected Treeitem makeTreeItem(final Treechildren tc, final Item item) {
		LOG.trace(LogMsg.ITEM, item);

		final String treeItemId = getTreeItemId(item);
		LOG.trace("treeItemId: {}", treeItemId);

		Treeitem ti = findItemFromId(treeItemId);
		if (ti != null) {
			return ti;
		}

		ti = makeRowItem(tc, item);
		ti.setId(treeItemId);

		if (listenerItemClick != null) {
			ti.addEventListener(Events.ON_CLICK, listenerItemClick);
		}
		if (listenerItemDoubleClick != null) {
			ti.addEventListener(Events.ON_DOUBLE_CLICK, listenerItemDoubleClick);
		}

		ti.setId(getTreeItemId(item));
		return ti;
	}

	protected void makeTreeItems(final Treechildren tc, final List<Item> items) {
		if (CollectionUtils.isEmpty(items)) {
			return;
		}
		LOG.trace(LogMsg.ITEMS, items.size());
		for (Item item : items) {
			makeTreeItem(tc, item);
		}
	}

	@Override
	public void onPageAttached(final Page newpage, final Page oldpage) {
		super.onPageAttached(newpage, oldpage);
		build();
	}

	private final class Event$TreeItem$OnOpen implements EventListener<Event> {
		@Override
		public void onEvent(final Event event) throws Exception {
			final Treeitem ti = (Treeitem) event.getTarget();
			final Treechildren tc = ti.getTreechildren();
			final Long catId = ti.getValue();
			final Category category = treeService.getCategory(catId);
			loadChildrens(tc, category);
		}
	}

	protected final class TreeColumns extends Treecols {
		public static final String ATTR_COLUMN_ITEM = "itemColumn";

		public TreeColumns() {
			setSizable(true);
		}

		public TreeColumns appendColumn(final String labelName) {
			return appendColumn(makeColumn(labelName));
		}

		public TreeColumns appendColumn(final Treecol column) {
			column.setParent(this);
			return this;
		}

		public TreeColumns appendColumnItem(final String labelName) {
			final Treecol column = makeColumn(labelName);
			column.setVisible(isItemVisible());
			column.setAttribute(ATTR_COLUMN_ITEM, Boolean.TRUE);
			return appendColumn(column);
		}

		protected Treecol makeColumn(final String labelName) {
			final String label = Labels.getLabel(labelName, StringUtils.EMPTY);
			final Treecol column = new Treecol(label);
			column.setParent(this);
			return column;
		}
	}

}
