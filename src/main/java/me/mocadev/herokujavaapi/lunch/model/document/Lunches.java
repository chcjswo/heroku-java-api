package me.mocadev.herokujavaapi.lunch.model.document;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-04-25
 **/
@Data
@Document
@NoArgsConstructor
public class Lunches {

	@Id
	@Field("_id")
	private ObjectId id;

	@Field("lunch_date")
	private LocalDate lunchDate;

	@Field("restaurant_name")
	private String restaurantName;

	@Field("username")
	private String username;

	@Builder
	public Lunches(String restaurantName, String username) {
		this.lunchDate = LocalDate.now();
		this.restaurantName = restaurantName;
		this.username = username;
	}
}
