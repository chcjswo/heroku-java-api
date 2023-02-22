package me.mocadev.herokujavaapi.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Custom Exception 정의
 *
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-02-22
 **/
public abstract class MocadevException extends RuntimeException {

	@Getter
	private final HttpStatus httpStatus;
	@Getter
	private final String message;
	@Getter
	private final Object data;

	protected MocadevException(HttpStatus httpStatus, String message, Object data) {
		super(message);
		this.httpStatus = httpStatus;
		this.message = message;
		this.data = data;
	}
}
