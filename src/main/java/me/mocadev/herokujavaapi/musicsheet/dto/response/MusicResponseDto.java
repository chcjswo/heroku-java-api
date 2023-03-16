package me.mocadev.herokujavaapi.musicsheet.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.mocadev.herokujavaapi.musicsheet.domain.MusicSheet;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-02-20
 **/
@Data
@NoArgsConstructor
public class MusicResponseDto {

	private String id;
	private String roomName;
	private String roomPass;
	private List<MusicSheet> musicSheets;
	private String randomString;
	private LocalDateTime regDate;

	@Builder
	public MusicResponseDto(String id, String roomName, String roomPass, List<MusicSheet> musicSheets, String randomString) {
		this.id = id;
		this.roomName = roomName;
		this.roomPass = roomPass;
		this.musicSheets = musicSheets;
		this.randomString = randomString;
		this.regDate = LocalDateTime.now();
	}
}
