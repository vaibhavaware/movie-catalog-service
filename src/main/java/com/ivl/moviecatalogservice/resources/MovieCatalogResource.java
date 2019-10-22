package com.ivl.moviecatalogservice.resources;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import resources.MovieCatalogService.models.CatalogItem;
import resources.MovieCatalogService.models.Movie;
import resources.MovieCatalogService.models.Rating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

		List<Rating> ratings = Arrays.asList(new Rating("MovieId_1", 3), new Rating("MovieId_2", 5));

		return ratings.stream().map(rating -> {
			
			Movie movie = restTemplate.getForObject("http://MOVIE-CATALOG-SERVICE/movies/" + rating.getMovieId(),
					Movie.class);
			return new CatalogItem(movie.getName(), "test", rating.getRating());
		}).collect(Collectors.toList());
		
		// return Collections.singletonList(new CatalogItem("Transformers", "test", 4));
	}

}
																