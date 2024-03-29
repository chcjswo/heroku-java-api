package me.mocadev.herokujavaapi.lunch.service;

import static me.mocadev.herokujavaapi.notification.dto.SlackMessageAction.SlackMessageActionConfirm;
import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.mocadev.herokujavaapi.lunch.model.document.Lunches;
import me.mocadev.herokujavaapi.lunch.model.document.Restaurants;
import me.mocadev.herokujavaapi.lunch.model.dto.SlackRequestPayload;
import me.mocadev.herokujavaapi.lunch.model.dto.response.LunchCommandListResponse;
import me.mocadev.herokujavaapi.lunch.repository.LunchesRepository;
import me.mocadev.herokujavaapi.lunch.repository.RestaurantsRepository;
import me.mocadev.herokujavaapi.notification.dto.SlackMessage;
import me.mocadev.herokujavaapi.notification.dto.SlackMessageAction;
import me.mocadev.herokujavaapi.notification.dto.SlackMessageAttachment;
import me.mocadev.herokujavaapi.notification.dto.SlackMessageFields;
import me.mocadev.herokujavaapi.notification.service.LunchSlackNotificationService;
import me.mocadev.herokujavaapi.notification.service.SlackNotificationService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-04-17
 **/
@Slf4j
@RequiredArgsConstructor
@Service
public class LunchService {

	private static final String LUNCH = "lunch";
	private static final String RESEND = "resend";
	private static final String USERNAME_SYSTEM = "system";
	private static final String USERNAME_FORCE = "force";
	private final RestaurantsRepository restaurantsRepository;
	private final LunchesRepository lunchesRepository;
	private final ModelMapper modelMapper;
	private final SlackNotificationService slackNotificationService;
	private final LunchSlackNotificationService lunchSlackNotificationService;

	private static final String USERNAME = "점심 뭐 먹지??";
	private static final String COLOR = "#2eb886";

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

	@Transactional
	public void saveRestaurant(String name) {
		restaurantsRepository.save(Restaurants.builder().name(name).build());
	}

	@Transactional(readOnly = true)
	public void sendLunchAlarm() {
		Lunches lunches = lunchesRepository.findByLunchDate(LocalDate.now().toString())
			.orElseThrow(() -> new IllegalArgumentException("점심 알람이 없습니다."));

		List<SlackMessageFields> fields = new ArrayList<>();
		fields.add(slackNotificationService.makeField("점심시간을 알려드립니다.",
													  "1시 점심시간 입니다. 그만 일하고 점심 드세요!"));

		SlackMessageAttachment attachment = SlackMessageAttachment.builder()
			.color(COLOR)
			.fields(fields)
			.build();

		SlackMessage slackMessage = SlackMessage.builder()
			.text("오늘의 점심은 " + lunches.getRestaurantName() + " 입니다.")
			.emoji(":rice:")
			.username(USERNAME)
			.attachments(List.of(attachment))
			.build();

		slackNotificationService.sendMessage(slackMessage);
	}

	@Transactional
	public void recommendsOfToday() {
		String restaurantName = getRestaurantName();
		saveLunch(restaurantName, USERNAME_SYSTEM);
		String lunchChoiceText = LocalDate.now() + " 오늘의 점심은 *" + restaurantName + "* 어떠세요?";
		SlackMessage message = getSlackMessage(restaurantName, lunchChoiceText, true);
		lunchSlackNotificationService.sendMessage(message);
	}

	private void saveLunch(String restaurantName, String username) {
		lunchesRepository.save(Lunches.builder()
								   .restaurantName(restaurantName)
								   .username(username)
								   .build());
	}

	private SlackMessage getSlackMessage(String restaurantName, String lunchChoiceText, boolean actionFlag) {
		List<SlackMessageAction> actions = new ArrayList<>();
		actions.add(SlackMessageAction.builder()
						.name(LUNCH)
						.text("점심 선택")
						.type("button")
						.value(restaurantName)
						.build());

		if (actionFlag) {
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
							.value(RESEND)
							.confirm(confirm)
							.build());
		}

		SlackMessageAttachment attachment = SlackMessageAttachment.builder()
			.text(lunchChoiceText)
			.color("#3AA3E3")
			.callbackId(LUNCH)
			.actions(actionFlag ? actions : null)
			.build();

		return SlackMessage.builder()
			.text(LocalDate.now() + " 점심은 " + restaurantName + " 입니다.")
			.username(USERNAME)
			.emoji(":rice_ball:")
			.attachments(List.of(attachment))
			.build();
	}

	private String getRestaurantName() {
		List<Restaurants> restaurants = restaurantsRepository.findAll();
		return getRestaurant(restaurants).getName();
	}

	private Restaurants getRestaurant(List<Restaurants> restaurants) {
		try {
			Random rand = SecureRandom.getInstanceStrong();
			return restaurants.stream().skip(rand.nextInt(restaurants.size())).findFirst().orElseThrow();
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Transactional
	public void decision(HttpServletRequest request) {
		JsonElement element = getElement(request);
		Gson gson = new Gson();

		String value = getActionValue(element, gson);
		String username;
		String restaurantName;
		String lunchChoiceText;
		boolean actionFlag;
		Lunches lunches = lunchesRepository.findByLunchDate(LocalDate.now().toString())
			.orElseThrow(() -> new IllegalArgumentException("점심 알람이 없습니다."));

		if (RESEND.equals(value)) {
			if (lunches.getUsername().equals(USERNAME_SYSTEM)) {
				username = USERNAME_SYSTEM;
				restaurantName = getRestaurantName();
				lunchChoiceText = "오늘의 점심은 *" + restaurantName + "* 어떠세요?";
				actionFlag = true;
				removeTodayLunchAndSave(username, restaurantName);
			} else {
				username = lunches.getUsername();
				restaurantName = lunches.getRestaurantName();
				if (username.equals(USERNAME_FORCE)) {
					lunchChoiceText = "오늘의 점심은 무조건 *" + restaurantName + "* 입니다.";
				} else {
					lunchChoiceText = "오늘의 점심은 이미 " + username + "님이 *" + restaurantName + "*을 선택하셨습니다.";
				}
				actionFlag = false;
			}
		} else {
			username = getUsername(element, gson);
			restaurantName = lunches.getRestaurantName();
			lunchChoiceText = "오늘의 점심은 " + username + "님이 선택한 *" + restaurantName + "* 입니다.";
			actionFlag = false;
			removeTodayLunchAndSave(username, restaurantName);
		}

		lunchSlackNotificationService.sendMessage(getSlackMessage(restaurantName, lunchChoiceText, actionFlag));
	}

	private void removeTodayLunchAndSave(String username, String restaurantName) {
		lunchesRepository.deleteLunchesByLunchDate(LocalDate.now().toString());
		saveLunch(restaurantName, username);
	}

	private static String getActionValue(JsonElement element, Gson gson) {
		JsonArray jsonActions = element.getAsJsonObject().get("actions").getAsJsonArray();
		List<SlackRequestPayload.Actions> actions = gson.fromJson(jsonActions.toString(),
																  new TypeToken<List<SlackRequestPayload.Actions>>() {
																  }.getType());
		return actions.get(0).getValue();
	}

	private static String getUsername(JsonElement element, Gson gson) {
		JsonObject jsonUser = element.getAsJsonObject().get("user").getAsJsonObject();
		SlackRequestPayload.User user = gson.fromJson(jsonUser, SlackRequestPayload.User.class);
		return user.getName();
	}

	private JsonElement getElement(HttpServletRequest request) {
		String payload = request.getParameter("payload");
		JsonParser parser = new JsonParser();
		return parser.parse(payload);
	}

	@Transactional
	public String remove(String restaurantName) {
		Optional<Restaurants> restaurants = restaurantsRepository.findByName(restaurantName);
		if (restaurants.isPresent()) {
			restaurantsRepository.delete(restaurants.get());
			return restaurantName + " 식당을 삭제했습니다.";
		}
		return restaurantName + " 식당은 점심 식당 리스트에 없습니다.";
	}

	@Transactional
	public void forceChoice(String restaurantName) {
		Optional<Lunches> lunch = lunchesRepository.findByLunchDate(LocalDate.now().toString());
		if (lunch.isPresent() && (lunch.get().getUsername().equals(USERNAME_SYSTEM))) {
			String lunchChoiceText = "오늘의 점심은 무조건 *" + restaurantName + "* 입니다.";
			removeTodayLunchAndSave(USERNAME_FORCE, restaurantName);
			lunchSlackNotificationService.sendMessage(getSlackMessage(restaurantName, lunchChoiceText, false));
		}
	}
}
