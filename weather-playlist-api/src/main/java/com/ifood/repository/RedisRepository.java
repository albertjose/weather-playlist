package com.ifood.repository;

import java.util.Map;

public interface RedisRepository<T> {

	Map<Object, Object> findAll();

	void add(T movie);

	void delete(String id);

	T find(String id);

}