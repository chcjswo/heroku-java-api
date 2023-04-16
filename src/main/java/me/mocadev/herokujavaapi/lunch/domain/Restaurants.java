package me.mocadev.herokujavaapi.lunch.domain;

import lombok.Getter;
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
@Getter
@Document
public class Restaurants {

	@Id
	@Field("_id")
	private ObjectId id;
	@Field
	private final Integer choiceCount;
	@Field
	private final String room;
	@Field
	private final Integer visitCount;
	@Field
	private final LocalDateTime createdAt;

	public Restaurants(String room) {
		this.room = room;
		this.visitCount = 0;
		this.choiceCount = 0;
		this.createdAt = LocalDateTime.now().plusHours(9);
	}
}
