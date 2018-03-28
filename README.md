# Weather Playlist
## API for suggests playlists based on temperature of provided city.

## Main building blocks
 * Spring Boot 2.0.0.RELEASE
 * Redis (in-memory data structure store) for caching =P
 * Hystrix (latency and fault tolerance library)


# Build with docker
Fast run with:
```sh
$ sh startup.sh
```
### OR...
Run command in weather-playlist-api folder:
```sh
$ ./gradlew build -Dspring.profiles.active=docker;
```
In root foulder run:

```sh
$ docker-compose build
$ docker-compose up
```

## To test the application

Run command in weather-playlist-api folder:
```sh
$ ./gradle test
```
Check test coverage:
```sh
$ ./gradle test jacocoTestReport
```

## Resources

| Path             | Params       | Description |
|------------------|--------------|--|
| (GET) /weather-playlist | city_name   | Search a playlist track by temperature of provided city |
| (GET) /weather-playlist | lat / lon   | Search a playlist track by temperature of provided coordinates |
