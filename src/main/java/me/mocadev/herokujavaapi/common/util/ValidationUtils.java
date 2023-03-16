package me.mocadev.herokujavaapi.common.util;

import java.util.Objects;
import javax.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import me.mocadev.herokujavaapi.common.exception.InvalidMusicRoomEntranceException;
import me.mocadev.herokujavaapi.common.service.MessageService;
import me.mocadev.herokujavaapi.musicsheet.domain.Music;
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

	public void canEnterMusicRoom(Music music, String message) {
		if (Objects.isNull(music)) {
			throw new InvalidMusicRoomEntranceException(message);
		}
	}
}
