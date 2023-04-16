package me.mocadev.herokujavaapi.lunch.service;

import lombok.RequiredArgsConstructor;
import me.mocadev.herokujavaapi.lunch.domain.Restaurants;
import me.mocadev.herokujavaapi.lunch.dto.response.LunchCommandListResponse;
import me.mocadev.herokujavaapi.lunch.repository.RestaurantsRepository;
import me.mocadev.herokujavaapi.notification.service.SlackNotificationService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-04-17
 **/
@RequiredArgsConstructor
@Service
public class LunchService {

	private final RestaurantsRepository restaurantsRepository;
	private final ModelMapper modelMapper;
	private final SlackNotificationService slackNotificationService;

	@Transactional(readOnly = true)
	public List<LunchCommandListResponse> findAll() {
		List<Restaurants> restaurants = restaurantsRepository.findAll();
		return restaurants.stream()
			.map(m -> modelMapper.map(m, LunchCommandListResponse.class))
			.collect(Collectors.toList());
	}
}
