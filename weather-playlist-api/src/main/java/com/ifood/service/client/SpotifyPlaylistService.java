package com.ifood.service.client;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final Logger logger = LoggerFactory.getLogger(SpotifyPlaylistService.class);

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
	public ResultPlaylistItem getRandomPlaylistByCategory(String category)
			throws SpotifyResultException, SpotifyAuthException {
		// find random playlist in redis
		PlaylistGenreCache cachedCategory = playlistGenreCache.findOneByGenre(category);
		if (cachedCategory != null) {
			logger.debug(String.format("Get a Playlist from cache in Redis, key: playlistGenre:%s", category));
			return new ResultPlaylistItem(cachedCategory.getPlaylistId());
		} else {
			Random randow = new Random();
			List<ResultPlaylistItem> resultPlayList = getPlaylistByCategory(category);
			// sportify return list of playlist, get one here
			return resultPlayList.get(randow.nextInt(resultPlayList.size()));
		}
	}

	@Override
	public List<ResultTrack> getTracksByPlaylist(String playlistId)
			throws SpotifyResultException, SpotifyAuthException {
		List<TrackPlaylistCache> cachedTracks = trackPlaylistCache.findByPlaylistId(playlistId);
		if (cachedTracks != null && !cachedTracks.isEmpty()) {
			logger.debug(String.format("Get tracks from cache in Redis, key: trackPlaylist:%s", playlistId));
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

	private String getAuthorization() throws SpotifyAuthException {
		SpotifyToken spotifyToken = spotifyAuthService.getToken();
		return String.format("%s %s", spotifyToken.getTokenType(), spotifyToken.getAccessToken());
	}

}
