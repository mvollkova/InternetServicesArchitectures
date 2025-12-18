package com.example.music_lab2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}


	@Bean
	public RouteLocator routeLocator(
			RouteLocatorBuilder builder,
			@Value("${routing.gateway.host}") String host,
			@Value("${routing.genre.url}") String genreUrl,
			@Value("${routing.song.url}") String songUrl
	){
		return builder.routes()
				.route("genre", r -> r
						.host(host)
						.and()
						.path(
								"/api/genres",
								"/api/genres/{uuid}"
						).uri(genreUrl)
				)
				.route("song", r -> r
						.host(host)
						.and()
						.path(
								"/api/songs",
								"/api/songs/{uuid}",
								"/api/genres/{uuid}/songs"
						).uri(songUrl)
				)
				.build();
	}

}