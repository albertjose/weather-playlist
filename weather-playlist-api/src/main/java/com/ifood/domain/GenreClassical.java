package com.ifood.domain;

public class GenreClassical implements Genre {
	private static final String CLASSICAL_GENRE = "classical";

	@Override
	public String getName() {
		return CLASSICAL_GENRE;
	}
}