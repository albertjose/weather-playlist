package com.ifood.cache;

public interface Cache<T> {
	T find(String keyid);

	void save(T cache);
}
