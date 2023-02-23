package me.mocadev.herokujavaapi.common.exception;

import org.springframework.http.HttpStatus;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-02-23
 **/
public class MusicConflictException extends MocadevException {

	private static final String MESSAGE = "이미 등록된 방 이름입니다.";

	public MusicConflictException() {
		super(MESSAGE);
	}

	@Override
	public int getStatusCode() {
		return HttpStatus.CONFLICT.value();
	}
}
