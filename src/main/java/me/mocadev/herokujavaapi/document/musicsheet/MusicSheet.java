package me.mocadev.herokujavaapi.document.musicsheet;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-02-19
 **/
//@JsonInclude(Include.NON_NULL)
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class MusicSheet {

	private int index;
	private String sheetTitle;
	private String sheetUrl;
	@Field("youtube_url")
	private String youtubeUrl;
	private String memo;

	@Builder
	public MusicSheet(int index, String sheetTitle, String sheetUrl, String youtubeUrl, String memo) {
		this.index = index;
		this.sheetTitle = sheetTitle;
		this.sheetUrl = sheetUrl;
		this.youtubeUrl = youtubeUrl;
		this.memo = memo;
	}
}
