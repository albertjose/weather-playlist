package com.ifood.helper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class MapperHelper {

	private ModelMapper modelMapper;

	@Autowired
	public MapperHelper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public <T, D> List<D> fromList(List<T> origin, Class<D> destiny) {
		return origin.stream().map(item -> fromObject(item, destiny)).collect(Collectors.toList());
	}

	public <T, D> Set<D> fromSet(Set<T> origin, Class<D> destiny) {
		return origin.stream().map(item -> fromObject(item, destiny)).collect(Collectors.toSet());
	}

	public <T, D> Page<D> fromPage(Page<T> origin, Pageable pageable, Class<D> destiny) {
		List<D> content = origin.getContent().stream().map(item -> fromObject(item, destiny))
				.collect(Collectors.toList());
		return new PageImpl<D>(content, pageable, origin.getTotalElements());
	}

	public <T, D> D fromObject(T origin, Class<D> destiny) {
		return modelMapper.map(origin, destiny);
	}

}
