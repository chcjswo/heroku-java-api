package me.mocadev.herokujavaapi.common.service;

import lombok.RequiredArgsConstructor;
import me.mocadev.herokujavaapi.notification.dto.SlackChannel;
import me.mocadev.herokujavaapi.notification.dto.SlackMessage;
import me.mocadev.herokujavaapi.notification.service.SlackNotificationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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

	public void sendAttendance() {
		SlackMessage slackMessage = SlackMessage.builder()
			.channel(SlackChannel.COMMON.getChannelName())
			.text(LocalDate.now() + " WORKPLACE에서 출근 시간을 체크해주세요.")
			.username("출근 체크")
			.emoji(":스톱워치:")
			.build();

		slackNotificationService.sendMessage(slackMessage);
	}

	public void sendLeaveWork() {
		SlackMessage slackMessage = SlackMessage.builder()
			.channel(SlackChannel.COMMON.getChannelName())
			.text(LocalDate.now() + " WORKPLACE에서 퇴근 시간을 체크해주세요.")
			.username("퇴근 체크")
			.emoji(":시계_7시:")
			.build();

		slackNotificationService.sendMessage(slackMessage);
	}
}
