package me.mocadev.herokujavaapi.common.util;

import javax.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import me.mocadev.herokujavaapi.common.service.MessageService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * 유효성 검사
 *
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-02-22
 **/
@RequiredArgsConstructor
@Component
public class ValidationUtils {

	private final MessageService messageService;

	public void hasErrors(Errors errors) {
		if (errors.hasErrors()) {
			throw new ValidationException(messageService.getFailValidMessage(errors));
		}
	}
}
