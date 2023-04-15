package me.mocadev.herokujavaapi.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.mocadev.herokujavaapi.notification.dto.SlackMessage;
import me.mocadev.herokujavaapi.notification.dto.SlackMessageFields;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-04-15
 **/
@Slf4j
@RequiredArgsConstructor
@Service
public class SlackNotificationService implements NotificationService {

	private final RestTemplate restTemplate;

	@Value("${notification.slack-url}")
	private String webHookUrl;

	@Override
	public void sendMessage(SlackMessage slackMessage) {
		restTemplate.postForEntity(webHookUrl, slackMessage, String.class);
		log.info("send slack message >>>>>");
	}

	public SlackMessageFields makeField(String title, String value) {
		return SlackMessageFields.builder()
			.title(title)
			.value(value)
			.build();
	}
}
