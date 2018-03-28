package com.ifood.domain;

public class GenrePop implements Genre {
	private static final String POP_GENRE = "pop";

	@Override
	public String getName() {
		return POP_GENRE;
	}
}