package me.mocadev.herokujavaapi.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 콘티 공유 batch
 *
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-02-27
 **/
@Slf4j
@RequiredArgsConstructor
@Component
public class MocadevApiBatch {

	private final RestTemplate restTemplate;

	@Value("${heroku.health}")
	private String healthUrl;

	@Scheduled(cron = "0 0/10 * * * *")
	public void callHeroku() {
		final ResponseEntity<String> result = restTemplate.exchange(healthUrl, HttpMethod.GET, null, String.class);
		log.info("heroku url = {}", healthUrl);
		log.info("result = {}", result);
	}
}
