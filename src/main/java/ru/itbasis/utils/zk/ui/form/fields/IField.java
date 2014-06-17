package ru.itbasis.utils.zk.ui.form.fields;

public interface IField<T> {
	String ICON_NEW                   = "z-icon-plus";
	String ICON_EDIT                  = "z-icon-pencil";
	String ICON_REFRESH               = "z-icon-refresh";
	String DEFAULT_WIDTH              = "98%";
	String DEFAULT_HFLEX              = "1";
	String CONSTRAINT_NOEMPTY         = "no empty,end_after";
	String CONSTRAINT_NUMBER_POSITIVE = "no negative,no zero,end_after";

	public T getRawValue();

	public void setRawValue(T value);

}
