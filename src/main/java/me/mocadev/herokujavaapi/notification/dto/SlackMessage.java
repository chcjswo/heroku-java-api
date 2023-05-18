package me.mocadev.herokujavaapi.notification.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-04-15
 **/
@ToString
@Getter
@NoArgsConstructor
public class SlackMessage {

	@JsonProperty("icon_emoji")
	private String emoji;
	private String channel;
	private String username;
	private String text;
	private List<SlackMessageAttachment> attachments;

	@Builder
	public SlackMessage(String emoji, String channel, String username, String text, List<SlackMessageAttachment> attachments) {
		this.emoji = emoji;
		this.channel = channel;
		this.username = username;
		this.text = text;
		this.attachments = attachments;
	}
}
