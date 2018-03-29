# Weather Playlist
## API for suggests playlists based on temperature of provided city.

## Main building blocks
 * Spring Boot 2.0.0.RELEASE
 * Redis ([docs](docs/cache.md)) (in-memory data structure store) for caching =P
 * Hystrix ([docs](docs/hystrixfallback.md)) (latency and fault tolerance library)
## Getting Started
There are two ways to run the entire application:
* On Local Machine
  * Redis required ([install](https://redis.io/topics/quickstart))
* Using Docker ([install](https://docs.docker.com/install/)) and Docker Compose ([install](https://docs.docker.com/compose/install/))
### On Local Machine
If your redis has running in default port (6379) it's ok, otherwise you need to modify application.yml according your configuration:
```java
spring:
  redis:
    host: localhost
    port: 6379
```
Run command in weather-playlist-api folder:
```sh
$ ./gradlew build
```
If you have permission error, just do:
```sh
$ chmod +x gradlew
```
When the application start the following endpoint are available:
http://localhost:8080/weather-playlist
### On docker
Fast run with:
```sh
$ sh startup.sh
```
#### OR...
Run command in weather-playlist-api folder:
```sh
$ ./gradlew build
```
In root foulder run:

```sh
$ docker-compose build
$ docker-compose up
```
After application have come up cleanly, the following endpoint are available:
http://dockerip:8080/weather-playlist

## To test the application

Run command in weather-playlist-api folder:
```sh
$ ./gradlew test
```
Check test coverage:
```sh
$ ./gradlew test jacocoTestReport
```
Reports output in: */build/reports/jacoco/test/html/index.html*

### Endpoints

| Path             | Params       | Description |
|------------------|--------------|--|
| (GET) /weather-playlist | city_name   | Search a playlist track by temperature of provided city |
| (GET) /weather-playlist | lat / lon   | Search a playlist track by temperature of provided coordinates |
