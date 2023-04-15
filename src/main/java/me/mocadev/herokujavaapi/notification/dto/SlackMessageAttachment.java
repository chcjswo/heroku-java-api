package me.mocadev.herokujavaapi.notification.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-04-15
 **/
@Getter
public class SlackMessageAttachment {

	@Builder.Default
	private String fallback = "APT.PLAY 알람이 도착했습니다.";
	private final String color;
	private final String pretext;
	private final String title;
	private final List<SlackMessageFields> fields;

	@Builder
	public SlackMessageAttachment(String fallback, String color, String pretext, String title, List<SlackMessageFields> fields) {
		this.fallback = fallback;
		this.color = color;
		this.pretext = pretext;
		this.title = title;
		this.fields = fields;
	}
}
