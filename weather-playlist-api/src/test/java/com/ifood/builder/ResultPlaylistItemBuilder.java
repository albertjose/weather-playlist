package com.ifood.builder;

import com.ifood.domain.ResultPlaylistItem;

public class ResultPlaylistItemBuilder {

	private static final String PLAYLIST_ID = "a1b2c3";

	private String playlistId = PLAYLIST_ID;

	public static ResultPlaylistItemBuilder build() {
		return new ResultPlaylistItemBuilder();
	}

	public ResultPlaylistItem now() {
		return new ResultPlaylistItem(playlistId);
	}

}
