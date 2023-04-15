package me.mocadev.herokujavaapi.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.mocadev.herokujavaapi.notification.dto.SlackChannel;
import me.mocadev.herokujavaapi.notification.dto.SlackMessage;
import me.mocadev.herokujavaapi.notification.dto.SlackMessageAttachment;
import me.mocadev.herokujavaapi.notification.dto.SlackMessageFields;
import me.mocadev.herokujavaapi.notification.service.SlackNotificationService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

	private static final String USERNAME = "코로나 알람";
	public static final String COLOR = "#CF2511";
	public static final String EMOJI = ":covid-19-coronavirus:";
	private final SlackNotificationService slackNotificationService;

	@Scheduled(cron = "0 0 10 * * 1-5")
	public void covidAlarm() {
		final String crawlingUrl = "https://ncov.kdca.go.kr/";
		Connection conn = Jsoup.connect(crawlingUrl);

		try {
			Result result = getResult(conn);
			sendSlack(result.date, result.fields);
		} catch (IOException e) {
			log.error("에러 발생: ", e);
		}
	}

	private Result getResult(Connection conn) throws IOException {
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

		List<SlackMessageFields> fields = new ArrayList<>();
		fields.add(slackNotificationService.makeField("일일 사망", dailyDeath.get(0).text() + " 명"));
		fields.add(slackNotificationService.makeField("일일 재원 위중증", dailySerious.get(0).text() + " 명"));
		fields.add(slackNotificationService.makeField("일일 신규 입원", dailyAdmission.get(0).text() + " 명"));
		fields.add(slackNotificationService.makeField("일일 확진", dailyConfirmed.get(0).text() + " 명"));
		fields.add(slackNotificationService.makeField("최근 7일간 일평균 사망", weeklyDeath.get(0).text() + " 명"));
		fields.add(slackNotificationService.makeField("최근 7일간 일평균 재원 위중증", weeklySerious.get(0).text() + " 명"));
		fields.add(slackNotificationService.makeField("최근 7일간 일평균 신규 입원", weeklyAdmission.get(0).text() + " 명"));
		fields.add(slackNotificationService.makeField("최근 7일간 일평균 확진", weeklyConfirmed.get(0).text() + " 명"));
		return new Result(date, fields);
	}

	private static class Result {
		public final Elements date;
		public final List<SlackMessageFields> fields;

		public Result(Elements date, List<SlackMessageFields> fields) {
			this.date = date;
			this.fields = fields;
		}
	}

	private void sendSlack(Elements date, List<SlackMessageFields> fields) {
		SlackMessageAttachment attachment = SlackMessageAttachment.builder()
			.title(date.get(0).text())
			.color(COLOR)
			.fields(fields)
			.build();

		SlackMessage slackMessage = SlackMessage.builder()
			.channel(SlackChannel.COMMON.getChannelName())
			.emoji(EMOJI)
			.username(USERNAME)
			.attachments(List.of(attachment))
			.build();

		slackNotificationService.sendMessage(slackMessage);
	}

}
