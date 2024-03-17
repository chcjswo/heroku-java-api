package me.mocadev.herokujavaapi.batch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
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
import org.springframework.stereotype.Component;

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
	public static final String WEELKY_FORMAT = "%s 주간 양성자";
	public static final String PERSON_FORMAT = "%s명";
	private final SlackNotificationService slackNotificationService;

	public void covidAlarm() throws IOException {
		final String crawlingUrl = "https://ncov.kdca.go.kr/bdBoardListR.do?brdId=1&brdGubun=11";
		Connection conn = Jsoup.connect(crawlingUrl);

		Result result = getResult(conn);
		sendSlack(result.date, result.fields);
	}

	private Result getResult(Connection conn) throws IOException {
		Document document = conn.get();

		var date = document.select("#content > div > p.s_descript");

		var weekly1 = document.select("#content > div > div:nth-child(5) > table > thead > tr > th:nth-child(2)");
		var weekly2 = document.select("#content > div > div:nth-child(5) > table > thead > tr > th:nth-child(3)");
		var weekly3 = document.select("#content > div > div:nth-child(5) > table > thead > tr > th:nth-child(4)");
		var weekly4 = document.select("#content > div > div:nth-child(5) > table > thead > tr > th:nth-child(5)");

		var infected1 = document.select("#content > div > div:nth-child(5) > table > tbody > tr > td:nth-child(2)");
		var infected2 = document.select("#content > div > div:nth-child(5) > table > tbody > tr > td:nth-child(3)");
		var infected3 = document.select("#content > div > div:nth-child(5) > table > tbody > tr > td:nth-child(4)");
		var infected4 = document.select("#content > div > div:nth-child(5) > table > tbody > tr > td:nth-child(5)");

		List<SlackMessageFields> fields = new ArrayList<>();
		fields.add(slackNotificationService.makeField(getFormat(WEELKY_FORMAT, weekly1), getFormat(PERSON_FORMAT, infected1)));
		fields.add(slackNotificationService.makeField(getFormat(WEELKY_FORMAT, weekly2), getFormat(PERSON_FORMAT, infected2)));
		fields.add(slackNotificationService.makeField(getFormat(WEELKY_FORMAT, weekly3), getFormat(PERSON_FORMAT, infected3)));
		fields.add(slackNotificationService.makeField(getFormat(WEELKY_FORMAT, weekly4), getFormat(PERSON_FORMAT, infected4)));
		return new Result(date, fields);
	}

	private static String getFormat(String stringFormat, Elements elements) {
		return String.format(stringFormat, elements.get(0).text());
	}

	@ToString
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
