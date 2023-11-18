package me.mocadev.herokujavaapi.batch;

import lombok.RequiredArgsConstructor;
import me.mocadev.herokujavaapi.lunch.service.LunchService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
@Component
public class LunchAlarmBatch {

	private final LunchService lunchService;

	@Scheduled(cron = "0 0 13 * * 1-5")
	public void lunchAlarm() throws InterruptedException {
		for (int i = 0; i < 1; i++) {
			lunchService.sendLunchAlarm();
			Thread.sleep(2000);
		}
	}

	@Scheduled(cron = "0 30 11 * * 1-5")
	public void lunchRecommendAlarm() {
		lunchService.recommendsOfToday();
	}

}
