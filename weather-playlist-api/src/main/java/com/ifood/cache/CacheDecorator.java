package com.ifood.cache;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public abstract class CacheDecorator<T> implements Cache<T> {

	CrudRepository<T, String> repository;

	protected CacheDecorator(CrudRepository<T, String> repository) {
		this.repository = repository;
	}

	@Override
	public T find(String keyid) {
		Optional<T> value = repository.findById(keyid);
		if (value.isPresent()) {
			return value.get();
		}
		return null;
	}

	@Override
	public void save(T cache) {
		repository.save(cache);
	}
}
