package com.ifood.domain;

import java.io.Serializable;

public class ResultTrack implements Serializable {
	private static final long serialVersionUID = 1L;

	private ResultTrackItem track;

	public ResultTrack() {
		super();
	}

	public ResultTrack(ResultTrackItem track) {
		super();
		this.track = track;
	}

	public ResultTrackItem getTrack() {
		return track;
	}

	public void setTrack(ResultTrackItem track) {
		this.track = track;
	}

}
