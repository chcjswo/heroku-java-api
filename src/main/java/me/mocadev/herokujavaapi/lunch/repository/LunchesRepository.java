package me.mocadev.herokujavaapi.lunch.repository;

import me.mocadev.herokujavaapi.lunch.model.document.Lunches;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-04-25
 **/
public interface LunchesRepository extends MongoRepository<Lunches, ObjectId> {

	Optional<Lunches> findByLunchDate(String now);
	void deleteLunchesByLunchDate(String now);
}
