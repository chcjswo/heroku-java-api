package me.mocadev.herokujavaapi.lunch.controller;

import lombok.RequiredArgsConstructor;
import me.mocadev.herokujavaapi.lunch.service.LunchService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public void findRestaurantsBySlashCommand() {
		lunchService.findAll();
	}
}
