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

	private String sheetTitle;
	private String sheetUrl;

	@Builder
	public MusicSheet(String sheetTitle, String sheetUrl) {
		this.sheetTitle = sheetTitle;
		this.sheetUrl = sheetUrl;
	}
}
