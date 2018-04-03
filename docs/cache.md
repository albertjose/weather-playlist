# Caching results
I used Redis to cache the search results, to avoid the API limitations and improve response time, in testing scenario the first request to get a playlist using London takes 5600ms, the second request took about 53ms.
## OpenWeatherMaps
Used Redis to caching search City and Coordinate temperatures for 10 minutes (configurable), this time is based in recommendation of OpenWeatherMaps, because ther is not changing so frequently. Another recomendation is call API by city ID instead of city name, city coordinates or zip code, so that implementation cache the city ID, by name and coordinate. See on next table:

| key name   | key value | value | description |
|----------|--------|--|--|
| cityName | london | 1248 | cache cityID by cityName |
| cityCoordinate | 29.95:-90.07 | 1248 | cache cityID by coordinates |
| temperature | 1248 | 30.6 | cache temperature of city by cityID |

## Spotify
Cache the playlists obtained at the first request (Spotify return 30 items) of the musical genre (party, pop, rock and classical), the following requests, for the same genre, use this cache. The first search for track set of a playlist is done in Spotify, a second of the same playlist will use the tracks cached. See the table below:

| key name  | key value     | value         | description |
|---------- |---------------|---------------|------------ |
| genre     | rock          | playlistId[]  | cache list of playlistId by genre |
| playlistId| spotif4brasil | tracks[]      | cache list of tracks by playlist |
