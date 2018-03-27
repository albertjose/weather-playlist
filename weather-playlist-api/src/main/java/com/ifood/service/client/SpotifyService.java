package com.ifood.service.client;

import java.util.List;

import com.ifood.domain.ResultPlaylistItem;
import com.ifood.domain.ResultTrack;
import com.ifood.exception.SpotifyAuthException;
import com.ifood.exception.SpotifyResultException;

public interface SpotifyService {
	ResultPlaylistItem getRandomPlaylistByCategory(String category) throws SpotifyResultException, SpotifyAuthException;

	List<ResultTrack> getTracksByPlaylist(String playlistId) throws SpotifyResultException, SpotifyAuthException;
}
