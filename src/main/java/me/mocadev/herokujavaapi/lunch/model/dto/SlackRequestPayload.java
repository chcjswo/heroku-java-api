package me.mocadev.herokujavaapi.lunch.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-04-19
 **/
@Data
@NoArgsConstructor
public class SlackRequestPayload {

	private User user;
	private List<Actions> actions;
	private Payload payload;
	private String type;

	@Data
	@NoArgsConstructor
	public static class Payload {
		private User user;
		private List<Actions> actions;
	}

	@Data
	@NoArgsConstructor
	public static class User {
		private String username;
	}

	@Data
	@NoArgsConstructor
	public static class Actions {
		private String value;
	}
}

