package com.ifood.builder;

import com.ifood.domain.TrackResponse;

public class TrackResponseBuilder {

	private static final String MUSIC_NAME = "Last Resort";

	private String name = MUSIC_NAME;

	public static TrackResponseBuilder build() {
		return new TrackResponseBuilder();
	}

	public TrackResponse now() {
		return new TrackResponse(name);
	}

}
