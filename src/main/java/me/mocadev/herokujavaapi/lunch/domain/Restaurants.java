package me.mocadev.herokujavaapi.lunch.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-04-16
 **/
@Data
@Document
@NoArgsConstructor
public class Restaurants {

	@Id
	@Field("_id")
	private ObjectId id;
	@Field
	private String choiceCount;
	@Field
	private String name;
	@Field
	private String visitCount;
	@Field
	private LocalDateTime createdAt;

	@Builder
	public Restaurants(String name) {
		this.name = name;
		this.createdAt = LocalDateTime.now().plusHours(9);
	}
}
