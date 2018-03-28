package com.ifood.builder;

import com.ifood.domain.ResultTrack;
import com.ifood.domain.ResultTrackItem;

public class ResultTrackBuilder {

	private ResultTrackItem resultTrackItem = ResultTrackItemBuilder.build().now();

	public static ResultTrackBuilder build() {
		return new ResultTrackBuilder();
	}

	public ResultTrack now() {
		return new ResultTrack(resultTrackItem);
	}
}
