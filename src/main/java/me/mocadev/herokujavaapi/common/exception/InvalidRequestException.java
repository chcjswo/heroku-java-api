package me.mocadev.herokujavaapi.common.exception;

import org.springframework.http.HttpStatus;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-02-23
 **/
public class InvalidRequestException extends MocadevException {

	private static final String MESSAGE = "잘못된 요청입니다.";

	public InvalidRequestException() {
		super(MESSAGE);
	}

	@Override
	public int getStatusCode() {
		return HttpStatus.BAD_REQUEST.value();
	}
}
