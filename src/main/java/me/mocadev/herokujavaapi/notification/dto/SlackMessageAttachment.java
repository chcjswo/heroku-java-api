package me.mocadev.herokujavaapi.notification.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
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
public class SlackMessageAttachment {

	@Builder.Default
	private String fallback = "APT.PLAY 알람이 도착했습니다.";
	private final String color;
	private final String pretext;
	private final String title;
	private final String text;
	private final List<SlackMessageFields> fields;
	@JsonProperty("attachment_type")
	private final String attachmentType;
	private final List<SlackMessageAction> actions;
	@JsonProperty("callback_id")
	private final String callbackId;

	@Builder
	public SlackMessageAttachment(String fallback, String color, String pretext, String title, String text, List<SlackMessageFields> fields, List<SlackMessageAction> actions, String callbackId) {
		this.fallback = fallback;
		this.color = color;
		this.pretext = pretext;
		this.title = title;
		this.text = text;
		this.fields = fields;
		this.actions = actions;
		this.callbackId = callbackId;
		this.attachmentType = "default";
	}
}
