package me.mocadev.herokujavaapi.lunch.model.dto;

import lombok.Data;

import java.util.List;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-04-19
 **/
@Data
public class SlackRequestPayload {

	private User user;
	private List<Actions> actions;

	@Data
	public static class User {
		private String username;
	}

	@Data
	public static class Actions {
		private String value;
	}
}
