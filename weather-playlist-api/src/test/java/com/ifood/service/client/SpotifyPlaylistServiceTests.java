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

import com.ifood.cache.PlaylistGenreCacheDecorator;
import com.ifood.cache.TrackPlaylistCacheDecorator;
import com.ifood.client.SpotifyClient;
import com.ifood.domain.ResultPlaylist;
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
		when(spotifyAuthService.getToken()).thenReturn(createSpotifyToken());
	}

	@Test
	public void getRandomPlaylistByCategory_getInCache() throws SpotifyResultException, SpotifyAuthException {
		ResultPlaylistItem playlist = createResultPlaylistItem();
		when(playlistCategoryCache.findOneByGenre(getGenre()))
				.thenReturn(new PlaylistGenreCache(getGenre(), playlist.getId()));

		ResultPlaylistItem result = spotifyService.getRandomPlaylistByCategory(getGenre());
		Assert.assertEquals(playlist.getId(), result.getId());

	}

	@Test
	public void getRandomPlaylistByCategory_notFoundInCache_getInClient()
			throws SpotifyResultException, SpotifyAuthException {
		ResultPlaylistItem playlist = createResultPlaylistItem();
		when(playlistCategoryCache.findOneByGenre(getGenre())).thenReturn(null);
		when(spotifyClient.getPlaylistByCategory(buildAuthorization(), getGenre()))
				.thenReturn(createResultPlayListCategory());

		ResultPlaylistItem result = spotifyService.getRandomPlaylistByCategory(getGenre());
		Assert.assertEquals(playlist.getId(), result.getId());

	}

	@Test
	public void getRandomPlaylistByCategory_notFoundInCache_notFoundInClient()
			throws SpotifyResultException, SpotifyAuthException {
		expectedEx.expect(SpotifyResultException.class);
		expectedEx.expectMessage("Sorry. We did not find playlists for the current category.");
		when(playlistCategoryCache.findOneByGenre(getGenre())).thenReturn(null);
		when(spotifyClient.getPlaylistByCategory(buildAuthorization(), getGenre())).thenReturn(null);

		spotifyService.getRandomPlaylistByCategory(getGenre());

	}

	@Test
	public void getTracksByPlaylist_getInCache() throws SpotifyResultException, SpotifyAuthException {
		ResultPlaylistItem playlist = createResultPlaylistItem();
		List<TrackPlaylistCache> trackListCache = createListOfTrackPlaylistCache();
		when(trackPlaylistCache.findByPlaylistId(playlist.getId())).thenReturn(trackListCache);

		List<ResultTrack> resultTrack = spotifyService.getTracksByPlaylist(playlist.getId());

		Assert.assertEquals(resultTrack.get(0).getTrack().getName(), trackListCache.get(0).getTrackName());
	}

	@Test
	public void getTracksByPlaylist_notFoundInCache_getInClient() throws SpotifyResultException, SpotifyAuthException {
		ResultPlaylistItem playlist = createResultPlaylistItem();
		when(trackPlaylistCache.findByPlaylistId(playlist.getId())).thenReturn(null);
		when(spotifyClient.getTracksByPlaylistId(buildAuthorization(), playlist.getId()))
				.thenReturn(createResultTrackPlaylist());

		List<ResultTrack> resultTrack = spotifyService.getTracksByPlaylist(playlist.getId());

		Assert.assertEquals(resultTrack.get(0).getTrack().getName(), getTrackName());
	}

	@Test
	public void getTracksByPlaylist_notFoundInCache_notFoundInClient()
			throws SpotifyResultException, SpotifyAuthException {
		expectedEx.expect(SpotifyResultException.class);
		expectedEx.expectMessage("Sorry. We did not find tracks for the current category.");
		ResultPlaylistItem playlist = createResultPlaylistItem();
		when(trackPlaylistCache.findByPlaylistId(playlist.getId())).thenReturn(null);
		when(spotifyClient.getTracksByPlaylistId(buildAuthorization(), playlist.getId())).thenReturn(null);

		spotifyService.getTracksByPlaylist(playlist.getId());

	}

	private List<TrackPlaylistCache> createListOfTrackPlaylistCache() {
		List<TrackPlaylistCache> list = Arrays.asList(createTrackPlaylistCache());
		return list;
	}

	private TrackPlaylistCache createTrackPlaylistCache() {
		return new TrackPlaylistCache(getTrackName(), getPlaylistId());
	}

	private String getPlaylistId() {
		return "a1b2c3";
	}

	private String getTrackName() {
		return "ToxyCity";
	}

	private String getGenre() {
		return "rock";
	}

	private ResultPlaylistCategory createResultPlayListCategory() {
		return new ResultPlaylistCategory(createResultPlaylist());
	}

	private ResultPlaylist createResultPlaylist() {
		return new ResultPlaylist(Arrays.asList(createResultPlaylistItem()));
	}

	private ResultPlaylistItem createResultPlaylistItem() {
		return new ResultPlaylistItem(getPlaylistId());
	}

	private ResultTrackPlaylist createResultTrackPlaylist() {
		ResultTrackPlaylist resultTrackPlaylist = new ResultTrackPlaylist();
		List<ResultTrack> items = Arrays.asList(createResultTrack());
		resultTrackPlaylist.setItems(items);
		return resultTrackPlaylist;

	}

	private String buildAuthorization() {
		SpotifyToken spotifyToken = createSpotifyToken();
		return String.format("%s %s", spotifyToken.getTokenType(), spotifyToken.getAccessToken());
	}

	private ResultTrack createResultTrack() {
		return new ResultTrack(createResultTrackItem());
	}

	private ResultTrackItem createResultTrackItem() {
		return new ResultTrackItem(getTrackName());
	}

	private SpotifyToken createSpotifyToken() {
		return new SpotifyToken("NgCXRKcMzYjw", "bearer", 3600L);
	}

}
