package me.mocadev.herokujavaapi.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.mocadev.herokujavaapi.notification.dto.SlackMessage;
import me.mocadev.herokujavaapi.notification.dto.SlackMessageAttachment;
import me.mocadev.herokujavaapi.notification.dto.SlackMessageFields;
import me.mocadev.herokujavaapi.notification.service.SlackNotificationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 점심 알람
 *
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-04-15
 **/
@RequiredArgsConstructor
@Slf4j
@Component
public class LunchAlarmBatch {

	private static final String USERNAME = "점심 뭐 먹지??";
	public static final String COLOR = "#2eb886";
	public static final String EMOJI = ":gookbab:";
	private final SlackNotificationService slackNotificationService;

	@Scheduled(cron = "0 0 13 * * 1-5")
	public void lunchAlarm() {
		List<SlackMessageFields> fields = new ArrayList<>();
		fields.add(slackNotificationService.makeField("점심시간을 알려드립니다.",
			"1시 점심시간 입니다. 그만 일하고 점심 드세요!"));

		SlackMessageAttachment attachment = SlackMessageAttachment.builder()
			.color(COLOR)
			.fields(fields)
			.build();

		SlackMessage slackMessage = SlackMessage.builder()
			.emoji(EMOJI)
			.username(USERNAME)
			.attachments(List.of(attachment))
			.build();

		slackNotificationService.sendMessage(slackMessage);
	}
}
