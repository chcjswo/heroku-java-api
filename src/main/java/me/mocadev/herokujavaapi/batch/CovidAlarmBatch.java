package me.mocadev.herokujavaapi.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.mocadev.herokujavaapi.common.service.SlackNotificationService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 코비드 알람
 *
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-04-12
 **/
@RequiredArgsConstructor
@Slf4j
@Component
public class CovidAlarmBatch {

	private final SlackNotificationService slackNotificationService;

	@Scheduled(cron = "0 * * * * *")
	public void covidAlarm() {

		final String crawlingUrl = "https://ncov.kdca.go.kr/";
		Connection conn = Jsoup.connect(crawlingUrl);

		try {
			Document document = conn.get();

			var date = document.select("#content > div > div > div > div.liveToggleOuter > div > div.live_left > div.occurrenceStatus > h2 > span");
			var dailyDeath = document.select("#content > div > div > div > div.liveToggleOuter > div > div.live_left > div.occurrenceStatus > div.occur_graph > table > tbody > tr:nth-child(1) > td:nth-child(2) > span");
			var dailySerious = document.select("#content > div > div > div > div.liveToggleOuter > div > div.live_left > div.occurrenceStatus > div.occur_graph > table > tbody > tr:nth-child(1) > td:nth-child(3) > span");
			var dailyAdmission = document.select("#content > div > div > div > div.liveToggleOuter > div > div.live_left > div.occurrenceStatus > div.occur_graph > table > tbody > tr:nth-child(1) > td:nth-child(4) > span");
			var dailyConfirmed = document.select("#content > div > div > div > div.liveToggleOuter > div > div.live_left > div.occurrenceStatus > div.occur_graph > table > tbody > tr:nth-child(1) > td:nth-child(5) > span");
			var weeklyDeath = document.select("#content > div > div > div > div.liveToggleOuter > div > div.live_left > div.occurrenceStatus > div.occur_graph > table > tbody > tr:nth-child(2) > td:nth-child(2) > span");
			var weeklySerious = document.select("#content > div > div > div > div.liveToggleOuter > div > div.live_left > div.occurrenceStatus > div.occur_graph > table > tbody > tr:nth-child(2) > td:nth-child(3) > span");
			var weeklyAdmission = document.select("#content > div > div > div > div.liveToggleOuter > div > div.live_left > div.occurrenceStatus > div.occur_graph > table > tbody > tr:nth-child(2) > td:nth-child(4) > span");
			var weeklyConfirmed = document.select("#content > div > div > div > div.liveToggleOuter > div > div.live_left > div.occurrenceStatus > div.occur_graph > table > tbody > tr:nth-child(2) > td:nth-child(5) > span");
			var totalDeath = document.select("#content > div > div > div > div.liveToggleOuter > div > div.live_left > div.occurrenceStatus > div.occur_num > div:nth-child(1)");
			var totalConfirmed = document.select("#content > div > div > div > div.liveToggleOuter > div > div.live_left > div.occurrenceStatus > div.occur_num > div:nth-child(2)");

			log.info("date = {}", date.get(0).text());
			log.info("dailyDeath = {}", dailyDeath.get(0).text());
			log.info("dailySerious = {}", dailySerious.get(0).text());
			log.info("dailyAdmission = {}", dailyAdmission.get(0).text());
			log.info("dailyConfirmed = {}", dailyConfirmed.get(0).text());
			log.info("weeklyDeath = {}", weeklyDeath.get(0).text());
			log.info("weeklySerious = {}", weeklySerious.get(0).text());
			log.info("weeklyAdmission = {}", weeklyAdmission.get(0).text());
			log.info("weeklyConfirmed = {}", weeklyConfirmed.get(0).text());
			log.info("totalDeath = {}", totalDeath.get(0).text());
			log.info("totalConfirmed = {}", totalConfirmed.get(0).text());

			slackNotificationService.sendMessage();

			log.info("slack send ###");
		} catch (IOException e) {
			log.error("에러 발생: ", e);
		}
	}
}
