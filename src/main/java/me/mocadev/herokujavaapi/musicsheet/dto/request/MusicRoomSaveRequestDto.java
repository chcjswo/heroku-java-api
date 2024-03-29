package me.mocadev.herokujavaapi.musicsheet.dto.request;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.mocadev.herokujavaapi.musicsheet.domain.Music;
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
public class MusicRoomSaveRequestDto {

	@NotEmpty(message = "방 이름은 필수입니다.")
	private String roomName;

	@NotEmpty(message = "방 비밀번호는 필수입니다.")
	private String roomPass;

	@NotEmpty(message = "악보 정보는 필수입니다.")
	private List<MusicSheet> musicSheets;

	public Music toEntity(String randomString) {
		return Music.builder()
			.roomName(roomName)
			.roomPass(roomPass)
			.musicSheets(musicSheets)
			.randomString(randomString)
			.build();
	}

	@Builder
	public MusicRoomSaveRequestDto(String roomName, String roomPass, List<MusicSheet> musicSheets) {
		this.roomName = roomName;
		this.roomPass = roomPass;
		this.musicSheets = musicSheets;
	}
}
