package com.ifood.domain;

import java.io.Serializable;
import java.util.List;

public class ResultTrackPlaylist implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<ResultTrack> items;

	public ResultTrackPlaylist() {
		super();
	}

	public ResultTrackPlaylist(List<ResultTrack> items) {
		super();
		this.items = items;
	}

	public List<ResultTrack> getItems() {
		return items;
	}

	public void setItems(List<ResultTrack> items) {
		this.items = items;
	}

}
