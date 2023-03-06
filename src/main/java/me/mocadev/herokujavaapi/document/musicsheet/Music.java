package me.mocadev.herokujavaapi.document.musicsheet;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 콘티 공유 Document
 *
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-02-19
 **/
@Data
@Document(collection = "musics")
public class Music {

	@Id
	@Field("_id")
	private ObjectId id;
	@Field("room_name")
	@Indexed(unique = true)
	private String roomName;
	@Field("room_pass")
	private String roomPass;
	@Field("music_sheet")
	private List<MusicSheet> musicSheets;
	@Field("random_string")
	private String randomString;
	@Field("reg_date")
	private LocalDateTime regDate;

	@Builder
	public Music(String roomName,
				 String roomPass,
				 List<MusicSheet> musicSheets,
				 String randomString) {
		this.roomName = roomName;
		this.roomPass = roomPass;
		this.musicSheets = musicSheets;
		this.randomString = randomString;
		this.regDate = LocalDateTime.now().plusHours(9);
	}
}
