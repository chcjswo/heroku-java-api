package me.mocadev.herokujavaapi.common.exception;

import org.springframework.http.HttpStatus;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-02-23
 **/
public class MusicRoomNotFoundException extends MocadevException {

	private static final String MESSAGE = "해당 방이 존재하지 않습니다.";

	public MusicRoomNotFoundException() {
		super(MESSAGE);
	}

	@Override
	public int getStatusCode() {
		return HttpStatus.CONFLICT.value();
	}
}
