package com.ifood.domain;

public class GenreParty implements Genre {
	private static final String PARTY_GENRE = "party";

	@Override
	public String getName() {
		return PARTY_GENRE;
	}
}