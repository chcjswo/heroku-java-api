package me.mocadev.herokujavaapi.notification.service;

import me.mocadev.herokujavaapi.notification.dto.SlackMessage;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-04-15
 **/
public interface NotificationService {

	void sendMessage(SlackMessage slackMessage);
}
