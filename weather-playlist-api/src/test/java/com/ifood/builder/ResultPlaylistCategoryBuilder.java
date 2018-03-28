package com.ifood.builder;

import com.ifood.domain.ResultPlaylist;
import com.ifood.domain.ResultPlaylistCategory;

public class ResultPlaylistCategoryBuilder {

	ResultPlaylist playlists = ResultPlaylistBuilder.build().now();

	private ResultPlaylistCategoryBuilder() {
	}

	public static ResultPlaylistCategoryBuilder build() {
		return new ResultPlaylistCategoryBuilder();
	}

	public ResultPlaylistCategory now() {
		return new ResultPlaylistCategory(playlists);
	}

}
