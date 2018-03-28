# Redis Cache

## OpenWeatherMaps
Caching search City and Coordinate temperatures for 10 minutes (configurable), this time is based in recommendation of 
OpenWeatherMaps, because ther is not changing so frequently. Another recomendation is call API by city ID instead of city name, 
city coordinates or zip code, so that implementation cache the city ID, by name and coordinate.

| key name   | key value | value | description |
|----------|--------|--|--|
| cityName | london | 1248 | cache cityID by cityName |
| cityCoordinate | 29.95:-90.07 | 1248 | cache cityID by coordinates |
| temperature | 1248 | 30.6 | cache temperature of city by cityID |

## Spotify
Cache a list of playlist obtained at the first requisition (Spotify return 30 items) of each category, and use these items to find tracks.
The first search for track set of a playlist is done in Spotify, a second of the same playlist, will use the tracks from the cache.

| key name  | key value     | value         | description |
|---------- |---------------|---------------|------------ |
| genre     | rock          | playlistId[]  | cache list of playlistId by genre |
| playlistId| spotif4brasil | tracks[]      | cache list of tracks by playlist |
