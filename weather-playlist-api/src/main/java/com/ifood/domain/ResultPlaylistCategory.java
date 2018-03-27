package com.ifood.domain;

public class ResultPlaylistCategory {
	private ResultPlaylist playlists;

	public ResultPlaylistCategory() {
		super();
	}

	public ResultPlaylistCategory(ResultPlaylist playlists) {
		super();
		this.playlists = playlists;
	}

	public ResultPlaylist getPlaylists() {
		return playlists;
	}

	public void setPlaylists(ResultPlaylist playlists) {
		this.playlists = playlists;
	}
}
