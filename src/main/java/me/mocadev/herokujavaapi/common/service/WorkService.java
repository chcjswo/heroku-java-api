package me.mocadev.herokujavaapi.common.service;

import lombok.RequiredArgsConstructor;
import me.mocadev.herokujavaapi.notification.dto.SlackChannel;
import me.mocadev.herokujavaapi.notification.dto.SlackMessage;
import me.mocadev.herokujavaapi.notification.dto.SlackMessageAttachment;
import me.mocadev.herokujavaapi.notification.dto.SlackMessageFields;
import me.mocadev.herokujavaapi.notification.service.SlackNotificationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-04-27
 **/
@RequiredArgsConstructor
@Service
public class WorkService {

	private final SlackNotificationService slackNotificationService;
	public static final String COLOR = "#2eb886";

	public void sendAttendance() {
		slackNotificationService.sendMessage(
			getSlackMessage(getSlackMessageAttachment("출근"),
				"출근",
				":stopwatch:"));
	}

	public void sendLeaveWork() {
		slackNotificationService.sendMessage(
			getSlackMessage(getSlackMessageAttachment("퇴근"),
				"퇴근",
				":clock7:"));
	}

	private static SlackMessage getSlackMessage(SlackMessageAttachment attachment, String type, String emoji) {
		return SlackMessage.builder()
			.channel(SlackChannel.COMMON.getChannelName())
			.text("WORKPLACE " + type + " 체크")
			.username(type + " 체크")
			.emoji(emoji)
			.attachments(Collections.singletonList(attachment))
			.build();
	}

	private static SlackMessageAttachment getSlackMessageAttachment(String type) {
		SlackMessageFields fields = SlackMessageFields.builder()
			.title("WORKPLACE " + type + " 시간 체크")
			.value(LocalDate.now() + type + " 시간을 체크해주세요.")
			.build();

		return SlackMessageAttachment.builder()
			.color(COLOR)
			.fields(Collections.singletonList(fields))
			.build();
	}
}
