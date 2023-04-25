package me.mocadev.herokujavaapi.lunch.model.dto.request;

import lombok.Data;
import me.mocadev.herokujavaapi.lunch.model.document.Restaurants;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-04-18
 **/
@Data
public class LunchCommandSaveRequest {

	private String text;

	public Restaurants toEntity() {
		return Restaurants.builder()
			.name(text)
			.build();
	}
}
