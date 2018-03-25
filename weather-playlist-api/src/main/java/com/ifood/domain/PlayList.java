package com.ifood.domain;

import java.util.List;

public class PlayList {
	private String name;
	private List<Track> tracks;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Track> getTracks() {
		return tracks;
	}

	public void setTracks(List<Track> tracks) {
		this.tracks = tracks;
	}
}
