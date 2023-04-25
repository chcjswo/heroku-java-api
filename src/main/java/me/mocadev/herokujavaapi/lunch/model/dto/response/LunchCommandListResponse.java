package me.mocadev.herokujavaapi.lunch.model.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-04-17
 **/
@Data
@NoArgsConstructor
public class LunchCommandListResponse {

	private Integer choiceCount;
	private String name;
	private Integer visitCount;
	private LocalDateTime createdAt;

	@Builder
	public LunchCommandListResponse(Integer choiceCount, String name, Integer visitCount, LocalDateTime createdAt) {
		this.choiceCount = choiceCount;
		this.name = name;
		this.visitCount = visitCount;
		this.createdAt = createdAt;
	}
}
