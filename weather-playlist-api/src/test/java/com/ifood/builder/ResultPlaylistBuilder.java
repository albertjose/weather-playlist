package com.ifood.builder;

import java.util.Arrays;
import java.util.List;

import com.ifood.domain.ResultPlaylist;
import com.ifood.domain.ResultPlaylistItem;

public class ResultPlaylistBuilder {

	private List<ResultPlaylistItem> items = Arrays.asList(ResultPlaylistItemBuilder.build().now());

	private ResultPlaylistBuilder() {
	}

	public static ResultPlaylistBuilder build() {
		return new ResultPlaylistBuilder();
	}

	public ResultPlaylist now() {
		return new ResultPlaylist(items);
	}

}
