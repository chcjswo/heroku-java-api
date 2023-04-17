package me.mocadev.herokujavaapi.lunch.repository;

import me.mocadev.herokujavaapi.lunch.domain.Restaurants;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-04-17
 **/
public interface RestaurantsRepository extends MongoRepository<Restaurants, ObjectId> {
}
