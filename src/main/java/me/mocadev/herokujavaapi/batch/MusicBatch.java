package me.mocadev.herokujavaapi.batch;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@Component
public class MusicBatch {

	private final RestTemplate restTemplate;

	@Scheduled(cron = "0 0/10 * * * *")
	public void callHeroku() {
		System.out.println("cron call " + LocalDateTime.now());
	}
}
