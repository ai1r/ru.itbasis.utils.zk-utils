package ru.itbasis.utils.zk;

public interface IThis<T> {
	@SuppressWarnings("unchecked")
	default T getThis() {
		return (T) this;
	}
}
