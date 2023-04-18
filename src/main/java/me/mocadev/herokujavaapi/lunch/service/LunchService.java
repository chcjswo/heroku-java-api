package me.mocadev.herokujavaapi.lunch.service;

import lombok.RequiredArgsConstructor;
import me.mocadev.herokujavaapi.lunch.domain.Restaurants;
import me.mocadev.herokujavaapi.lunch.dto.SlackRequestPayload;
import me.mocadev.herokujavaapi.lunch.dto.response.LunchCommandListResponse;
import me.mocadev.herokujavaapi.lunch.repository.RestaurantsRepository;
import me.mocadev.herokujavaapi.notification.dto.SlackMessage;
import me.mocadev.herokujavaapi.notification.dto.SlackMessageAction;
import me.mocadev.herokujavaapi.notification.dto.SlackMessageAttachment;
import me.mocadev.herokujavaapi.notification.dto.SlackMessageFields;
import me.mocadev.herokujavaapi.notification.service.SlackNotificationService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static me.mocadev.herokujavaapi.notification.dto.SlackMessageAction.SlackMessageActionConfirm;

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

	public static final String LUNCH = "lunch";
	private final RestaurantsRepository restaurantsRepository;
	private final ModelMapper modelMapper;
	private final SlackNotificationService slackNotificationService;

	private static final String USERNAME = "점심 뭐 먹지??";
	public static final String COLOR = "#2eb886";

	@Transactional(readOnly = true)
	public SlackMessage findAll() {
		List<Restaurants> restaurants = restaurantsRepository.findAll();
		List<LunchCommandListResponse> list = restaurants.stream()
			.map(m -> modelMapper.map(m, LunchCommandListResponse.class))
			.collect(Collectors.toList());

		List<SlackMessageFields> fields = new ArrayList<>();
		list.forEach(item -> fields.add(SlackMessageFields.builder()
			.title(item.getName())
			.build()));

		SlackMessageAttachment attachment = SlackMessageAttachment.builder()
			.color("#F35A00")
			.fields(fields)
			.build();

		return SlackMessage.builder()
			.text("선택 가능한 식당")
			.attachments(List.of(attachment))
			.build();
	}

	public void saveRestaurant(String name) {
		restaurantsRepository.save(Restaurants.builder().name(name).build());
	}

	public void sendLunchAlarm() {
		List<SlackMessageFields> fields = new ArrayList<>();
		fields.add(slackNotificationService.makeField("점심시간을 알려드립니다.",
			"1시 점심시간 입니다. 그만 일하고 점심 드세요!"));

		SlackMessageAttachment attachment = SlackMessageAttachment.builder()
			.color(COLOR)
			.fields(fields)
			.build();

		SlackMessage slackMessage = SlackMessage.builder()
			.text("점심시간 입니다.")
			.emoji(":gookbab:")
			.username(USERNAME)
			.attachments(List.of(attachment))
			.build();

		slackNotificationService.sendMessage(slackMessage);
	}

	public void recommendsOfToday() {
		String restaurantName = getRestaurantName();
		String lunchChoiceText = LocalDate.now() + " 오늘의 점심은 *" + restaurantName + "* 어떠세요?";
		SlackMessage message = getSlackMessage(restaurantName, lunchChoiceText);
		slackNotificationService.sendMessage(message);
	}

	private SlackMessage getSlackMessage(String restaurantName, String lunchChoiceText) {
		List<SlackMessageAction> actions = new ArrayList<>();
		actions.add(SlackMessageAction.builder()
			.name(LUNCH)
			.text("점심 선택")
			.type("button")
			.value(restaurantName)
			.build());

		SlackMessageActionConfirm confirm = SlackMessageActionConfirm.builder()
			.title("점심 다시 선택??")
			.text(restaurantName + " 말고 다시 선택 하시겠습니까?")
			.okText("다시 선택")
			.dismissText("그냥 먹을래")
			.build();

		actions.add(SlackMessageAction.builder()
			.name(LUNCH)
			.text("다시 선택")
			.type("button")
			.style("danger")
			.value("resend")
			.confirm(confirm)
			.build());

		SlackMessageAttachment attachment = SlackMessageAttachment.builder()
			.text(lunchChoiceText)
			.color("#3AA3E3")
			.callbackId(LUNCH)
			.actions(actions)
			.build();

		return SlackMessage.builder()
			.text("점심 선택의 시간입니다.")
			.username(USERNAME)
			.emoji(":rice_ball:")
			.attachments(List.of(attachment))
			.build();
	}

	private String getRestaurantName() {
		List<Restaurants> restaurants = restaurantsRepository.findAll();
		Random r = new Random();
		Restaurants restaurant = restaurants.stream().skip(r.nextInt(restaurants.size())).findFirst().get();
		return restaurant.getName();
	}

	public void decision(SlackRequestPayload payload) {
		String userName = payload.getPayload().getUser().getName();
		String value = payload.getPayload().getActions().get(0).getValue();
		String restaurantName = getRestaurantName();
		String lunchChoiceText = "오늘의 점심은 *" + restaurantName + "* 어떠세요?";

		if ("resend".equals(value)) {
			lunchChoiceText = "오늘의 점심은 " + userName +"님이 선택한 *" + restaurantName + "* 입니다.";
		}

		SlackMessage message = getSlackMessage(restaurantName, lunchChoiceText);
		slackNotificationService.sendMessage(message);
	}
}
