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
import com.ifood.service.client.SpotifyAuthService;
import com.ifood.service.client.SpotifyService;

@RunWith(SpringRunner.class)
public class SpotifyServiceTests {

	@Mock
	SpotifyClient spotifyClient;

	@Mock
	SpotifyAuthService spotifyAuthService;

	@Mock
	PlaylistGenreCacheDecorator playlistCategoryCache;

	@Mock
	TrackPlaylistCacheDecorator trackPlaylistCache;

	SpotifyService spotifyService;

	@Before
	public void setUp() throws Exception {
		spotifyService = new SpotifyService(spotifyClient, spotifyAuthService, playlistCategoryCache,
				trackPlaylistCache);
		when(spotifyAuthService.getToken()).thenReturn(new SpotifyToken("NgCXRKcMzYjw", "bearer", 3600L));
	}

}
