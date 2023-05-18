package me.mocadev.herokujavaapi.notification.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-04-15
 **/
@ToString
@Getter
public class SlackMessageFields {

	private final String title;
	private final String value;
	@Builder.Default
	@JsonProperty("short")
	private boolean shorts = false;

	@Builder
	public SlackMessageFields(String title, String value, boolean shorts) {
		this.title = title;
		this.value = value;
		this.shorts = shorts;
	}
}
