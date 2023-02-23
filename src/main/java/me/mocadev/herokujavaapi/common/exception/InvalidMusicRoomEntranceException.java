package me.mocadev.herokujavaapi.common.exception;

import org.springframework.http.HttpStatus;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-02-23
 **/
public class InvalidMusicRoomEntranceException extends MocadevException {

	public InvalidMusicRoomEntranceException(String message) {
		super(message);
	}

	@Override
	public int getStatusCode() {
		return HttpStatus.UNAUTHORIZED.value();
	}
}
