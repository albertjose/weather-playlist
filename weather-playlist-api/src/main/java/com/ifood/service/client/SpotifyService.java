package com.ifood.service.client;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifood.client.SpotifyClient;
import com.ifood.domain.ResultPlaylistCategory;
import com.ifood.domain.ResultPlaylistItem;
import com.ifood.domain.ResultTrack;
import com.ifood.domain.ResultTrackPlaylist;
import com.ifood.domain.SpotifyToken;
import com.ifood.domain.Track;
import com.ifood.exception.SpotifyAuthException;
import com.ifood.exception.SpotifyResultException;
import com.ifood.helper.MapperHelper;

@Service
public class SpotifyService {

	SpotifyClient spotifyClient;

	SpotifyAuthService spotifyAuthService;

	@Autowired
	MapperHelper mapperHelper;

	@Autowired
	public SpotifyService(SpotifyClient spotifyClient, SpotifyAuthService spotifyAuthService) {
		this.spotifyClient = spotifyClient;
		this.spotifyAuthService = spotifyAuthService;
	}

	public List<Track> getTracks(String category) throws SpotifyResultException, SpotifyAuthException {
		SpotifyToken spotifyToken = spotifyAuthService.getToken();
		String authorization = String.format("%s %s", spotifyToken.getToken_type(), spotifyToken.getAccess_token());

		List<ResultPlaylistItem> playlistList = getPlaylistByCategory(authorization, category);

		Random r = new Random();
		ResultPlaylistItem playList = playlistList.get(r.nextInt(playlistList.size()));

		List<ResultTrack> tracksList = getTracksByPlaylist(authorization, playList.getId());

		return mapperHelper.fromList(tracksList, Track.class);
	}

	public List<ResultPlaylistItem> getPlaylistByCategory(String authorization, String category)
			throws SpotifyResultException {
		ResultPlaylistCategory playListByCategory = spotifyClient.getPlaylistByCategory(authorization, category);
		if (playListByCategory == null || playListByCategory.getPlaylists() == null
				|| playListByCategory.getPlaylists().getItems() == null
				|| playListByCategory.getPlaylists().getItems().isEmpty()) {
			throw new SpotifyResultException("Not found playlist for current category.");
		} else {
			return playListByCategory.getPlaylists().getItems();
		}
	}

	private List<ResultTrack> getTracksByPlaylist(String authorization, String playlistId) {
		ResultTrackPlaylist trackResult = spotifyClient.getTracksByPlaylistId(authorization, playlistId);
		if (trackResult == null || trackResult.getItems() == null || trackResult.getItems().isEmpty()) {
			return null;
		} else {
			return trackResult.getItems();
		}
	}

}
