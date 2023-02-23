package me.mocadev.herokujavaapi.common.service;

import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-02-22
 **/
@RequiredArgsConstructor
@Service
public class MessageService {

	private final MessageSource messageSource;

	public String getErrorMessage(String propertyKey, Object... objects) {
		return messageSource.getMessage(
			propertyKey,
			objects,
			Locale.KOREA);
	}

	public String getFailValidMessage(Errors errors) {
		var fieldError = errors.getFieldError();
		if (fieldError == null) {
			return messageSource.getMessage("fail.validation", null, Locale.KOREA);
		}
		return messageSource.getMessage(fieldError, Locale.KOREA);
	}

	public String getMessageFromProperties(String messageKey) {
		return messageSource.getMessage(messageKey, null, Locale.KOREA);
	}
}
