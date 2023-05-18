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
 * @since 2023-04-18
 **/
@ToString
@Getter
public class SlackMessageAction {

	private final String name;
	private final String text;
	private final String type;
	private final String value;
	private final String style;
	private final SlackMessageActionConfirm confirm;

	@Builder
	public SlackMessageAction(String name, String text, String type, String value, String style, SlackMessageActionConfirm confirm) {
		this.name = name;
		this.text = text;
		this.type = type;
		this.value = value;
		this.style = style;
		this.confirm = confirm;
	}

	@ToString
	@Getter
	public static class SlackMessageActionConfirm {

		private final String title;
		private final String text;
		@JsonProperty("ok_text")
		private final String okText;
		@JsonProperty("dismiss_text")
		private final String dismissText;

		@Builder
		public SlackMessageActionConfirm(String title, String text, String okText, String dismissText) {
			this.title = title;
			this.text = text;
			this.okText = okText;
			this.dismissText = dismissText;
		}
	}
}
