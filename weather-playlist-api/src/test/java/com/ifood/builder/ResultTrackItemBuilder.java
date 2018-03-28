package com.ifood.builder;

import com.ifood.domain.ResultTrackItem;

public class ResultTrackItemBuilder {

	private static final String MUSIC_NAME = "Last Resort";

	private String name = MUSIC_NAME;

	public static ResultTrackItemBuilder build() {
		return new ResultTrackItemBuilder();
	}

	public ResultTrackItem now() {
		return new ResultTrackItem(name);
	}

}
