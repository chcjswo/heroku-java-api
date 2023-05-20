package me.mocadev.herokujavaapi.lunch.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.mocadev.herokujavaapi.lunch.model.dto.SlackRequestPayload;
import me.mocadev.herokujavaapi.lunch.service.LunchService;
import me.mocadev.herokujavaapi.notification.dto.SlackMessage;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-04-16
 **/
@Slf4j
@RequestMapping("/api/v1/lunch")
@RequiredArgsConstructor
@RestController
public class LunchController {

	private final LunchService lunchService;
	private final ModelMapper modelMapper;

	@PostMapping("/commands/restaurants")
	public ResponseEntity<SlackMessage> findRestaurantsBySlashCommand() {
		SlackMessage result = lunchService.findAll();
		return ResponseEntity.ok().body(result);
	}

	@PostMapping(path = "/commands/restaurants/new",
		consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public ResponseEntity<String> saveRestaurant(@RequestParam String text) {
		lunchService.saveRestaurant(text);
		return ResponseEntity.status(HttpStatus.CREATED).body(text + " 식당을 추가했습니다.");
	}

	@PostMapping("/restaurants/recommends")
	public void recommendsOfToday() {
		lunchService.recommendsOfToday();
	}

	@PostMapping("/restaurants/decision")
	public void decision(Map<String, Object> dto, HttpServletRequest request) throws JsonProcessingException {
		log.info("decision >>>>>>>>>>>>>>>>>>>>>>>>>> ");
		log.info("dto >>> {}", dto);
//		lunchService.decision(dto);

		log.info("content-type >>> {}", request.getHeader("content-type"));
		String payload = request.getParameter("payload").replaceAll("\n", "").replaceAll("\r", "");
		log.info("payload >>> {}", payload);
		log.info("dto >>> {}", dto);

		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(payload);
		log.info("json >>> {}", json);

		SlackRequestPayload slackRequestPayload = objectMapper.readValue(json, SlackRequestPayload.class);
		log.info("slackRequestPayload >>> {}", slackRequestPayload);

//		ObjectMapper mapper = new ObjectMapper();
//		SlackRequestPayload payload2 = mapper.readValue(payload , SlackRequestPayload.class);
//		log.info("myObject >>> {}", payload2);
//
//		SlackRequestPayload myObject = new ObjectMapper().convertValue(payload, SlackRequestPayload.class);
//		log.info("myObject >>> {}", myObject);
	}

	@Data
	public static class SlackPayload {
		private String action_id;
		private String block_id;
		private String value;
	}
}
