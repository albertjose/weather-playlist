package com.ifood.service;

import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.ifood.cache.PlaylistGenreCacheDecorator;
import com.ifood.cache.TrackPlaylistCacheDecorator;
import com.ifood.client.SpotifyClient;
import com.ifood.domain.SpotifyToken;
import com.ifood.service.client.SpotifyAuthCredentialsFlowService;
import com.ifood.service.client.SpotifyPlaylistService;

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

	SpotifyPlaylistService spotifyService;

	@Before
	public void setUp() throws Exception {
		spotifyService = new SpotifyPlaylistService(spotifyClient, spotifyAuthService, playlistCategoryCache,
				trackPlaylistCache);
		when(spotifyAuthService.getToken()).thenReturn(new SpotifyToken("NgCXRKcMzYjw", "bearer", 3600L));
	}

}
