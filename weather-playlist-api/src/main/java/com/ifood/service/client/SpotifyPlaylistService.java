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
public class SpotifyPlaylistService implements SpotifyService {

	SpotifyClient spotifyClient;

	SpotifyAuthCredentialsFlowService spotifyAuthService;

	PlaylistGenreCacheDecorator playlistGenreCache;

	TrackPlaylistCacheDecorator trackPlaylistCache;

	@Autowired
	public SpotifyPlaylistService(SpotifyClient spotifyClient, SpotifyAuthCredentialsFlowService spotifyAuthService,
			PlaylistGenreCacheDecorator playlistGenreCache, TrackPlaylistCacheDecorator trackPlaylistCache) {
		this.spotifyClient = spotifyClient;
		this.spotifyAuthService = spotifyAuthService;
		this.playlistGenreCache = playlistGenreCache;
		this.trackPlaylistCache = trackPlaylistCache;
	}

	@Override
	public List<ResultTrack> getTracks(String category) throws SpotifyResultException, SpotifyAuthException {
		String playListId = getRandomPlaylistByCategory(category);
		return getTracksByPlaylist(playListId);

	}

	private String getRandomPlaylistByCategory(String category) throws SpotifyResultException, SpotifyAuthException {
		Random r = new Random();
		// find in redis
		PlaylistGenreCache cachedCategory = playlistGenreCache.findOneByGenre(category);
		if (cachedCategory != null) {
			return cachedCategory.getPlaylistId();
		} else {
			List<ResultPlaylistItem> resultPlayList = getPlaylistByCategory(category);
			ResultPlaylistItem playList = resultPlayList.get(r.nextInt(resultPlayList.size()));
			return playList.getId();
		}
	}

	private List<ResultPlaylistItem> getPlaylistByCategory(String category)
			throws SpotifyResultException, SpotifyAuthException {
		ResultPlaylistCategory playListByCategory = spotifyClient.getPlaylistByCategory(getAuthorization(), category);
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

	private List<ResultTrack> getTracksByPlaylist(String playlistId)
			throws SpotifyResultException, SpotifyAuthException {
		List<TrackPlaylistCache> cachedTracks = trackPlaylistCache.findByPlaylistId(playlistId);
		if (cachedTracks != null && !cachedTracks.isEmpty()) {
			List<ResultTrack> items = cachedTracks.stream()
					.map(e -> new ResultTrack(new ResultTrackItem(e.getTrackName()))).collect(Collectors.toList());
			return items;
		} else {
			ResultTrackPlaylist trackResult = spotifyClient.getTracksByPlaylistId(getAuthorization(), playlistId);

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

	private String getAuthorization() throws SpotifyAuthException {
		SpotifyToken spotifyToken = spotifyAuthService.getToken();
		return String.format("%s %s", spotifyToken.getTokenType(), spotifyToken.getAccessToken());
	}

}
