package me.mocadev.herokujavaapi.dto.musicsheet.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.mocadev.herokujavaapi.document.musicsheet.MusicSheet;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-02-28
 **/
@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class MusicLoginResponseDto {

	private String id;
	private String roomName;
	private String roomPass;
	private List<MusicSheet> musicSheets;
	private String randomString;
	private LocalDateTime regDate;
}
