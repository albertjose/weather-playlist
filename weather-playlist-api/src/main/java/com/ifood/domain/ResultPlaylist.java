package com.ifood.domain;

import java.util.List;

public class ResultPlaylist {

	private List<ResultPlaylistItem> items;

	public ResultPlaylist() {
		super();
	}

	public ResultPlaylist(List<ResultPlaylistItem> items) {
		super();
		this.items = items;
	}

	public List<ResultPlaylistItem> getItems() {
		return items;
	}

	public void setItems(List<ResultPlaylistItem> items) {
		this.items = items;
	}
}
