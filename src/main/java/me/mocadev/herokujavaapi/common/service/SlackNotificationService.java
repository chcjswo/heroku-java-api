package me.mocadev.herokujavaapi.common.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-04-15
 **/
@RequiredArgsConstructor
@Service
public class SlackNotificationService implements NotificationService {

	private final RestTemplate restTemplate;

	@Value("${notification.slack-url}")
	private String webHookUrl;

	@Override
	public void sendMessage() {
		SlackMessageDto dto = SlackMessageDto.builder()
			.title("테스트 제목")
			.color("#CF2511")
			.emoji(":gookbab:")
			.username("점심 뭐 먹지??")
			.value("신나는 점심 시간 입니다.\n식당으로 고고고~~")
			.build();

		SlackMessageFields fields = SlackMessageFields.builder()
			.title(dto.getTitle())
			.value(dto.getValue())
			.build();

		SlackMessageAttachment attachment = SlackMessageAttachment.builder()
			.color(dto.getColor())
			.fields(List.of(fields))
			.fallback("어쩌구저쩌구 알람이 도착했습니다.")
			.build();

		SlackMessage slackMessage = SlackMessage.builder()
//			.channel("#일반")
			.emoji(dto.getEmoji())
			.username(dto.getUsername())
			.attachments(List.of(attachment))
			.build();
		restTemplate.postForEntity(webHookUrl, slackMessage, String.class);
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class SlackMessage {
		@JsonProperty("icon_emoji")
		private String emoji;
		private String channel;
		private String username;
		private List<SlackMessageAttachment> attachments;

	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class SlackMessageAttachment {
		private String color;
		@Builder.Default
		private String fallback = "어쩌구저쩌구 알람이 도착했습니다.";
		private List<SlackMessageFields> fields;
	}

	@Getter
	@NoArgsConstructor
	public static class SlackMessageDto {
		private String emoji;
		private String color;
		private String title;
		private String value;
		private String channel;
		private String username;

		@Builder
		public SlackMessageDto(String emoji, String color, String title, String value, String channel, String username) {
			this.emoji = emoji;
			this.color = color;
			this.title = title;
			this.value = value;
			this.channel = channel;
			this.username = username;
		}
	}


	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	private static class SlackMessageFields {
		private String title;
		private String value;
		@Builder.Default
		@JsonProperty("short")
		private boolean shorts = false;
	}
}
