package me.mocadev.herokujavaapi.common.exception;

import javax.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.mocadev.herokujavaapi.common.dto.response.ErrorResponse;
import me.mocadev.herokujavaapi.common.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-02-22
 **/
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {

	private final MessageService messageService;

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public final ResponseEntity<ErrorResponse> invalidRequestHandler(MethodArgumentNotValidException e) {
		final ErrorResponse response = ErrorResponse.builder()
			.code(HttpStatus.BAD_REQUEST.value())
			.message(messageService.getMessageFromProperties("fail.badRequest"))
			.build();
		for (FieldError fieldError : e.getFieldErrors()) {
			response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
		}
		log.error("[FAIL VALIDATION] {} ", response.getValidation(), e);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(ValidationException.class)
	public final ResponseEntity<ErrorResponse> invalidRequestHandler(ValidationException e) {
		final ErrorResponse response = ErrorResponse.builder()
			.code(HttpStatus.BAD_REQUEST.value())
			.message(e.getMessage())
			.build();
		log.error("[FAIL VALIDATION] {} ", e.getMessage(), e);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(MocadevException.class)
	public ResponseEntity<ErrorResponse> mocadevException(MocadevException e) {
		final ErrorResponse response = ErrorResponse.builder()
			.code(e.getStatusCode())
			.message(messageService.getMessageFromProperties(e.getMessage()))
			.build();
		log.error("[ERROR] {} ", e.getMessage(), e);
		return ResponseEntity.status(e.getStatusCode()).body(response);
	}
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorResponse> handleException(Exception e) {
		final ErrorResponse response = ErrorResponse.builder()
			.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
			.message(messageService.getMessageFromProperties("internal.server.error"))
			.build();
		log.error("[INTERNAL_SERVER_ERROR] ", e);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public final ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		final ErrorResponse response = ErrorResponse.builder()
			.code(HttpStatus.METHOD_NOT_ALLOWED.value())
			.message("Method Not Allowed")
			.build();
		log.error("[ERROR] handleHttpRequestMethodNotSupportedException {}", e.toString());
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);
	}
}
