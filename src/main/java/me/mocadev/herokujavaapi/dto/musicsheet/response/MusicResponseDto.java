package me.mocadev.herokujavaapi.dto.musicsheet.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import me.mocadev.herokujavaapi.document.musicsheet.MusicSheet;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-02-20
 **/
@Builder
@Data
public class MusicResponseDto {

	private String id;
	private String roomName;
	private String roomPass;
	private List<MusicSheet> musicSheets;
	private String videoUrl;
	private String memo;
	private String randomString;
	private LocalDateTime regDate;
}
