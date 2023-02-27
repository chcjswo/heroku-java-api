package me.mocadev.herokujavaapi.document.musicsheet;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-02-19
 **/
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class MusicSheet {

	private int index;
	private String sheetTitle;
	private String sheetUrl;
	private String videoUrl;
	private String memo;

	@Builder
	public MusicSheet(int index, String sheetTitle, String sheetUrl, String videoUrl, String memo) {
		this.index = index;
		this.sheetTitle = sheetTitle;
		this.sheetUrl = sheetUrl;
		this.videoUrl = videoUrl;
		this.memo = memo;
	}
}
