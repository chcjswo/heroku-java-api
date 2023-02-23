package me.mocadev.herokujavaapi.dto.musicsheet.request;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 로그인 DTO
 *
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-02-22
 **/
@Data
public class MusicSheetLoginDto {

	@NotEmpty(message = "방 이름은 필수입니다.")
	private String roomName;

	@NotEmpty(message = "방 비밀번호는 필수입니다.")
	private String roomPass;

}
