package me.mocadev.herokujavaapi.common.dto.response;

import java.util.HashMap;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

/**
 * 에러 반환
 *
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-02-22
 **/
@Getter
public class ErrorResponse {

	private final int code;
	private final String message;
	private final Map<String, String> validation = new HashMap<>();

	public void addValidation(String fieldName, String errorMessage) {
		this.validation.put(fieldName, errorMessage);
	}

	@Builder
	public ErrorResponse(int code, String message) {
		this.code = code;
		this.message = message;
	}
}
