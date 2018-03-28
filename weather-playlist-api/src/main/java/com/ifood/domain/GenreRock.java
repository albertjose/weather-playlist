package com.ifood.domain;

public class GenreRock implements Genre {
	private static final String ROCK_GENRE = "rock";

	@Override
	public String getName() {
		return ROCK_GENRE;
	}
}