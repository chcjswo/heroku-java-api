package me.mocadev.herokujavaapi.dto.musicsheet.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
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

	private String roomName;
	private String roomPass;
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
