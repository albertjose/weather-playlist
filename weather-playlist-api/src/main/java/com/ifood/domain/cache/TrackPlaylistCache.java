package com.ifood.domain.cache;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("trackPlaylist")
public class TrackPlaylistCache implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	private String trackName;
	@Indexed
	private String playlistId;

	public TrackPlaylistCache(String trackName, String playlistId) {
		super();
		this.trackName = trackName;
		this.playlistId = playlistId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTrackName() {
		return trackName;
	}

	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}

	public String getPlaylistId() {
		return playlistId;
	}

	public void setPlaylistId(String playlistId) {
		this.playlistId = playlistId;
	}

}
