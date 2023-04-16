package me.mocadev.herokujavaapi.lunch.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-04-17
 **/
@Data
public class LunchCommandListResponse {

	private final Integer choiceCount;
	private final String room;
	private final Integer visitCount;
	private final LocalDateTime createdAt;
}
