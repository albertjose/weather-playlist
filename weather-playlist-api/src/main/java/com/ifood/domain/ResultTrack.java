package com.ifood.domain;

public class ResultTrack {
	private ResultTrackItem track;

	public ResultTrackItem getTrack() {
		return track;
	}

	public void setTrack(ResultTrackItem track) {
		this.track = track;
	}

	public String getName() {
		return track != null ? track.getName() : null;
	}

}
