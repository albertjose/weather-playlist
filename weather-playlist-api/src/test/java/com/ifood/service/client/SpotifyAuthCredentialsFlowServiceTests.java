package com.ifood.service.client;

import static org.mockito.Mockito.when;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit4.SpringRunner;

import com.ifood.builder.SpotifyTokenBuilder;
import com.ifood.builder.SpotifyTokenCacheBuilder;
import com.ifood.cache.SpotifyTokenCacheDecorator;
import com.ifood.client.SpotifyAuthClient;
import com.ifood.domain.SpotifyToken;
import com.ifood.domain.cache.SpotifyTokenCache;
import com.ifood.exception.SpotifyAuthException;
import com.ifood.helper.MapperHelper;

@RunWith(SpringRunner.class)
public class SpotifyAuthCredentialsFlowServiceTests {

	@Mock
	SpotifyAuthClient spotifyAuthClient;

	@Mock
	SpotifyTokenCacheDecorator spotifyTokenCache;

	MapperHelper mapperHelper;

	SpotifyAuthCredentialsFlowService spotifyAuthCredentialsFlowService;

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		mapperHelper = new MapperHelper(new ModelMapper());
		spotifyAuthCredentialsFlowService = new SpotifyAuthCredentialsFlowService(spotifyAuthClient, spotifyTokenCache,
				mapperHelper);
	}

	@Test
	public void getToken_inCache() throws SpotifyAuthException {
		SpotifyTokenCache cache = SpotifyTokenCacheBuilder.build().now();
		when(spotifyTokenCache.findOne()).thenReturn(cache);

		SpotifyToken result = spotifyAuthCredentialsFlowService.getToken();

		Assert.assertEquals(result.getAccessToken(), cache.getAccessToken());
		Assert.assertEquals(result.getTokenType(), cache.getTokenType());
		Assert.assertEquals(result.getExpiresIn(), cache.getExpiresIn());
	}

	@Test
	public void getToken_inClient() throws SpotifyAuthException {
		Map<String, String> params = new HashMap<>();
		params.put("grant_type", "client_credentials");
		String credentialsHeader = "Basic "
				+ Base64.getEncoder().encodeToString(String.format("%s:%s", null, null).getBytes());

		SpotifyToken spotifyToken = SpotifyTokenBuilder.build().now();
		when(spotifyTokenCache.findOne()).thenReturn(null);
		when(spotifyAuthClient.generateTokenAccess(credentialsHeader, params))
				.thenReturn(SpotifyTokenBuilder.build().now());

		SpotifyToken result = spotifyAuthCredentialsFlowService.getToken();

		Assert.assertEquals(result.getAccessToken(), spotifyToken.getAccessToken());
		Assert.assertEquals(result.getTokenType(), spotifyToken.getTokenType());
		Assert.assertEquals(result.getExpiresIn(), spotifyToken.getExpiresIn());
	}

	@Test
	public void getToken_fallbackClient() throws SpotifyAuthException {
		expectedEx.expect(SpotifyAuthException.class);
		expectedEx.expectMessage("Sorry. Failed to authenticate on spotify partner.");

		when(spotifyTokenCache.findOne()).thenReturn(null);
		when(spotifyAuthClient.generateTokenAccess(null, null)).thenReturn(null);

		spotifyAuthCredentialsFlowService.getToken();
	}

}
