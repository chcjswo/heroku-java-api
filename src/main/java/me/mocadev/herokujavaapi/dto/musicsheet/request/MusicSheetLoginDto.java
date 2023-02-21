package me.mocadev.herokujavaapi.dto.musicsheet.request;

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

	private String roomName;
	private String roomPass;

}
