package me.mocadev.herokujavaapi.common.exception;

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

	public MocadevException(String message) {
		super(message);
	}

	public MocadevException(String message, Throwable cause) {
		super(message, cause);
	}

	public abstract int getStatusCode();
}
