package com.ifood.service.client;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.ifood.builder.ResultPlaylistCategoryBuilder;
import com.ifood.builder.ResultPlaylistItemBuilder;
import com.ifood.builder.ResultTrackItemBuilder;
import com.ifood.builder.ResultTrackPlaylistBuilder;
import com.ifood.builder.SpotifyTokenBuilder;
import com.ifood.builder.TrackPlaylistCacheBuilder;
import com.ifood.cache.PlaylistGenreCacheDecorator;
import com.ifood.cache.TrackPlaylistCacheDecorator;
import com.ifood.client.SpotifyClient;
import com.ifood.domain.GenreFactory;
import com.ifood.domain.ResultPlaylistItem;
import com.ifood.domain.ResultTrack;
import com.ifood.domain.SpotifyToken;
import com.ifood.domain.cache.PlaylistGenreCache;
import com.ifood.domain.cache.TrackPlaylistCache;
import com.ifood.exception.SpotifyAuthException;
import com.ifood.exception.SpotifyResultException;

@RunWith(SpringRunner.class)
public class SpotifyPlaylistServiceTests {

	@Mock
	SpotifyClient spotifyClient;

	@Mock
	SpotifyAuthCredentialsFlowService spotifyAuthService;

	@Mock
	PlaylistGenreCacheDecorator playlistCategoryCache;

	@Mock
	TrackPlaylistCacheDecorator trackPlaylistCache;

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	SpotifyPlaylistService spotifyService;

	@Before
	public void setUp() throws Exception {
		spotifyService = new SpotifyPlaylistService(spotifyClient, spotifyAuthService, playlistCategoryCache,
				trackPlaylistCache);
		when(spotifyAuthService.getToken()).thenReturn(SpotifyTokenBuilder.build().now());
	}

	@Test
	public void getRandomPlaylistByCategory_getInCache() throws SpotifyResultException, SpotifyAuthException {
		ResultPlaylistItem playlist = ResultPlaylistItemBuilder.build().now();
		Double temperature = 10.0;
		when(playlistCategoryCache.findOneByGenre(GenreFactory.getGenreByTemperature(temperature).getName()))
				.thenReturn(new PlaylistGenreCache(GenreFactory.getGenreByTemperature(temperature).getName(), playlist.getId()));

		ResultPlaylistItem result = spotifyService
				.getRandomPlaylistByCategory(GenreFactory.getGenreByTemperature(temperature).getName());
		Assert.assertEquals(playlist.getId(), result.getId());

	}

	@Test
	public void getRandomPlaylistByCategory_notFoundInCache_getInClient()
			throws SpotifyResultException, SpotifyAuthException {
		Double temperature = 10.0;

		ResultPlaylistItem playlist = ResultPlaylistItemBuilder.build().now();
		when(playlistCategoryCache.findOneByGenre(GenreFactory.getGenreByTemperature(temperature).getName())).thenReturn(null);
		when(spotifyClient.getPlaylistByCategory(buildAuthorization(), GenreFactory.getGenreByTemperature(temperature).getName()))
				.thenReturn(ResultPlaylistCategoryBuilder.build().now());

		ResultPlaylistItem result = spotifyService
				.getRandomPlaylistByCategory(GenreFactory.getGenreByTemperature(temperature).getName());
		Assert.assertEquals(playlist.getId(), result.getId());

	}

	@Test
	public void getRandomPlaylistByCategory_notFoundInCache_notFoundInClient()
			throws SpotifyResultException, SpotifyAuthException {
		Double temperature = 10.0;

		expectedEx.expect(SpotifyResultException.class);
		expectedEx.expectMessage("Sorry. We did not find playlists for the current category.");
		when(playlistCategoryCache.findOneByGenre(GenreFactory.getGenreByTemperature(temperature).getName())).thenReturn(null);
		when(spotifyClient.getPlaylistByCategory(buildAuthorization(), GenreFactory.getGenreByTemperature(temperature).getName()))
				.thenReturn(null);

		spotifyService.getRandomPlaylistByCategory(GenreFactory.getGenreByTemperature(temperature).getName());

	}

	@Test
	public void getTracksByPlaylist_getInCache() throws SpotifyResultException, SpotifyAuthException {
		ResultPlaylistItem playlist = ResultPlaylistItemBuilder.build().now();
		TrackPlaylistCache item = TrackPlaylistCacheBuilder.build().now();
		when(trackPlaylistCache.findByPlaylistId(playlist.getId())).thenReturn(Arrays.asList(item));

		List<ResultTrack> resultTrack = spotifyService.getTracksByPlaylist(playlist.getId());

		Assert.assertEquals(resultTrack.get(0).getTrack().getName(), item.getTrackName());
	}

	@Test
	public void getTracksByPlaylist_notFoundInCache_getInClient() throws SpotifyResultException, SpotifyAuthException {
		ResultPlaylistItem playlist = ResultPlaylistItemBuilder.build().now();
		when(trackPlaylistCache.findByPlaylistId(playlist.getId())).thenReturn(null);
		when(spotifyClient.getTracksByPlaylistId(buildAuthorization(), playlist.getId()))
				.thenReturn(ResultTrackPlaylistBuilder.build().now());

		List<ResultTrack> resultTrack = spotifyService.getTracksByPlaylist(playlist.getId());

		Assert.assertEquals(resultTrack.get(0).getTrack().getName(), ResultTrackItemBuilder.build().now().getName());
	}

	@Test
	public void getTracksByPlaylist_notFoundInCache_notFoundInClient()
			throws SpotifyResultException, SpotifyAuthException {
		expectedEx.expect(SpotifyResultException.class);
		expectedEx.expectMessage("Sorry. We did not find tracks for the current category.");
		ResultPlaylistItem playlist = ResultPlaylistItemBuilder.build().now();
		when(trackPlaylistCache.findByPlaylistId(playlist.getId())).thenReturn(null);
		when(spotifyClient.getTracksByPlaylistId(buildAuthorization(), playlist.getId())).thenReturn(null);

		spotifyService.getTracksByPlaylist(playlist.getId());

	}

	private String buildAuthorization() {
		SpotifyToken spotifyToken = SpotifyTokenBuilder.build().now();
		return String.format("%s %s", spotifyToken.getTokenType(), spotifyToken.getAccessToken());
	}

}
