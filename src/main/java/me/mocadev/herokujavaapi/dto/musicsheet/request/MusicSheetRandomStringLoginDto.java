package me.mocadev.herokujavaapi.dto.musicsheet.request;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 랜덤 문자열 로그인 DTO
 *
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-02-22
 **/
@Data
public class MusicSheetRandomStringLoginDto {

	@NotEmpty(message = "필수값 입니다.")
	private String randomString;

}
