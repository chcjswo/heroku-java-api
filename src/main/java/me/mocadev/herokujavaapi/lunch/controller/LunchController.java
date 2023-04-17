package me.mocadev.herokujavaapi.lunch.controller;

import lombok.RequiredArgsConstructor;
import me.mocadev.herokujavaapi.lunch.service.LunchService;
import me.mocadev.herokujavaapi.notification.dto.SlackMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-04-16
 **/
@RequestMapping("/api/v1/lunch")
@RequiredArgsConstructor
@RestController
public class LunchController {

	private final LunchService lunchService;

	@PostMapping("/commands/restaurants")
	public ResponseEntity<SlackMessage> findRestaurantsBySlashCommand() {
		SlackMessage result = lunchService.findAll();
		return ResponseEntity.ok().body(result);
	}

	@PostMapping(path = "/commands/restaurants/new",
		consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public ResponseEntity<String> saveRestaurant(@RequestParam String text) {
		lunchService.saveRestaurant(text);
		return ResponseEntity.status(HttpStatus.CREATED).body("식당을 추가했습니다.");
	}
}
