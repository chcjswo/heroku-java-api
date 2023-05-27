package me.mocadev.herokujavaapi.lunch.repository;

import me.mocadev.herokujavaapi.lunch.model.document.Restaurants;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-04-17
 **/
public interface RestaurantsRepository extends MongoRepository<Restaurants, ObjectId> {
	Optional<Restaurants> findByName(String restaurantName);
}
