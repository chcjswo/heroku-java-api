package me.mocadev.herokujavaapi.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-04-15
 **/
@Getter
@AllArgsConstructor
public enum SlackChannel {

	LUNCH("#점심"),
	COMMON("#일반");

	private final String channelName;
}
