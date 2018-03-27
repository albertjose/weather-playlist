package com.ifood.service.client;

import java.util.List;

import com.ifood.domain.ResultTrack;
import com.ifood.exception.SpotifyAuthException;
import com.ifood.exception.SpotifyResultException;

public interface SpotifyService {
	List<ResultTrack> getTracks(String category) throws SpotifyResultException, SpotifyAuthException;
}
