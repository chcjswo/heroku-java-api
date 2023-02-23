package me.mocadev.herokujavaapi.dto.musicsheet.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import me.mocadev.herokujavaapi.document.musicsheet.Music;
import me.mocadev.herokujavaapi.document.musicsheet.MusicSheet;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-02-20
 **/
@Data
public class MusicSheetSaveRequestDto {

	@NotEmpty(message = "방 이름은 필수입니다.")
	private String roomName;

	@NotEmpty(message = "방 비밀번호는 필수입니다.")
	private String roomPass;

	@NotEmpty(message = "악보 정보는 필수입니다.")
	@JsonProperty("musicSheet")
	private List<MusicSheet> musicSheets;

	@JsonProperty("video_url")
	private String videoUrl;
	private String memo;

	public Music toEntity(String randomString) {
		return Music.builder()
			.roomName(roomName)
			.roomPass(roomPass)
			.musicSheets(musicSheets)
			.videoUrl(videoUrl)
			.memo(memo)
			.randomString(randomString)
			.build();
	}
}