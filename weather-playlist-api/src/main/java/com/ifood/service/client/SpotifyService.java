package com.ifood.service.client;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifood.cache.PlaylistGenreCacheDecorator;
import com.ifood.cache.TrackPlaylistCacheDecorator;
import com.ifood.client.SpotifyClient;
import com.ifood.domain.ResultPlaylistCategory;
import com.ifood.domain.ResultPlaylistItem;
import com.ifood.domain.ResultTrack;
import com.ifood.domain.ResultTrackItem;
import com.ifood.domain.ResultTrackPlaylist;
import com.ifood.domain.SpotifyToken;
import com.ifood.domain.cache.PlaylistGenreCache;
import com.ifood.domain.cache.TrackPlaylistCache;
import com.ifood.exception.SpotifyAuthException;
import com.ifood.exception.SpotifyResultException;

@Service
public class SpotifyService {

	SpotifyClient spotifyClient;

	SpotifyAuthService spotifyAuthService;

	PlaylistGenreCacheDecorator playlistGenreCache;

	TrackPlaylistCacheDecorator trackPlaylistCache;

	@Autowired
	public SpotifyService(SpotifyClient spotifyClient, SpotifyAuthService spotifyAuthService,
			PlaylistGenreCacheDecorator playlistGenreCache, TrackPlaylistCacheDecorator trackPlaylistCache) {
		this.spotifyClient = spotifyClient;
		this.spotifyAuthService = spotifyAuthService;
		this.playlistGenreCache = playlistGenreCache;
		this.trackPlaylistCache = trackPlaylistCache;
	}

	public List<ResultTrack> getTracks(String category) throws SpotifyResultException, SpotifyAuthException {

		SpotifyToken spotifyToken = spotifyAuthService.getToken();
		String authorization = String.format("%s %s", spotifyToken.getTokenType(), spotifyToken.getAccessToken());

		String playListId = getRandomPlaylistByCategory(authorization, category);
		return getTracksByPlaylist(authorization, playListId);

	}

	private String getRandomPlaylistByCategory(String authorization, String category) throws SpotifyResultException {
		Random r = new Random();
		// find in redis
		PlaylistGenreCache cachedCategory = playlistGenreCache.findOneByGenre(category);
		if (cachedCategory != null) {
			return cachedCategory.getPlaylistId();
		} else {
			List<ResultPlaylistItem> resultPlayList = getPlaylistByCategory(authorization, category);
			ResultPlaylistItem playList = resultPlayList.get(r.nextInt(resultPlayList.size()));
			return playList.getId();
		}
	}

	private List<ResultPlaylistItem> getPlaylistByCategory(String authorization, String category)
			throws SpotifyResultException {
		ResultPlaylistCategory playListByCategory = spotifyClient.getPlaylistByCategory(authorization, category);
		if (playListByCategory == null || playListByCategory.getPlaylists() == null
				|| playListByCategory.getPlaylists().getItems() == null
				|| playListByCategory.getPlaylists().getItems().isEmpty()) {
			throw new SpotifyResultException("Sorry. We did not find playlists for the current category.");
		} else {
			// cache values in redis
			List<PlaylistGenreCache> list = playListByCategory.getPlaylists().getItems().stream()
					.map(e -> new PlaylistGenreCache(category, e.getId())).collect(Collectors.toList());
			playlistGenreCache.saveAll(list);
			return playListByCategory.getPlaylists().getItems();
		}
	}

	public List<ResultTrack> getTracksByPlaylist(String authorization, String playlistId)
			throws SpotifyResultException {
		List<TrackPlaylistCache> cachedTracks = trackPlaylistCache.findByPlaylistId(playlistId);
		ResultTrackPlaylist trackResult = null;
		if (cachedTracks != null && !cachedTracks.isEmpty()) {
			List<ResultTrack> items = cachedTracks.stream()
					.map(e -> new ResultTrack(new ResultTrackItem(e.getTrackName()))).collect(Collectors.toList());
			return items;
		} else {
			trackResult = spotifyClient.getTracksByPlaylistId(authorization, playlistId);

			if (trackResult == null || trackResult.getItems() == null || trackResult.getItems().isEmpty()) {
				throw new SpotifyResultException("Sorry. We did not find tracks for the current category.");
			} else {
				// cache values in redis
				cachedTracks = trackResult.getItems().stream()
						.map(e -> new TrackPlaylistCache(e.getTrack().getName(), playlistId))
						.collect(Collectors.toList());
				trackPlaylistCache.saveAll(cachedTracks);

				return trackResult.getItems();
			}
		}
	}

}
