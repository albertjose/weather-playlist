package com.ifood.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(url = "${app.spotify.url}", name = "spotify-client")
public interface SpotifyClient {

}
