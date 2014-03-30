package ru.itbasis.utils.zk.ui.form.fields;

import org.zkforge.ckez.CKeditor;
import org.zkoss.zk.ui.event.Events;

import java.util.Map;

public class FieldRichText extends AbstractField<String> {
	private CKeditor _editor;

	public FieldRichText() {
		super();
		_editor = new CKeditor();
		_editor.setHflex(DEFAULT_HFLEX);
		_editor.setCustomConfigurationsPath("/static/js/ckeditor.js");
		_editor.setParent(box);
	}

	public void setConfigPath(String value) {
		_editor.setCustomConfigurationsPath(value);
	}

	public void setConfig(Map<String, Object> config) {
		_editor.setConfig(config);
	}

	public Map<String, Object> getConfig() {
		return _editor.getConfig();
	}

	@Override
	public String getValue() {
		return _editor.getValue();
	}

	@Override
	public void setValue(String value) {
		if (value == null || value.isEmpty()) {
			clear();
			return;
		}
		_editor.setValue(value);
		Events.postEvent(Events.ON_CHANGE, _editor, null);
	}

	@Override
	public void clear() {
		_editor.setValue("");
		Events.postEvent(Events.ON_CHANGE, _editor, null);
	}
}
