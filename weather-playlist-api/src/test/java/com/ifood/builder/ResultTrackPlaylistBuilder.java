package com.ifood.builder;

import java.util.Arrays;
import java.util.List;

import com.ifood.domain.ResultTrack;
import com.ifood.domain.ResultTrackPlaylist;

public class ResultTrackPlaylistBuilder {

	public List<ResultTrack> items = Arrays.asList(ResultTrackBuilder.build().now());

	private ResultTrackPlaylistBuilder() {
	}

	public static ResultTrackPlaylistBuilder build() {
		return new ResultTrackPlaylistBuilder();
	}

	public ResultTrackPlaylist now() {
		return new ResultTrackPlaylist(items);
	}

}
