package me.mocadev.herokujavaapi.controller.musicsheet;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.mocadev.herokujavaapi.dto.musicsheet.request.MusicSheetLoginDto;
import me.mocadev.herokujavaapi.dto.musicsheet.request.MusicSheetRandomStringLoginDto;
import me.mocadev.herokujavaapi.dto.musicsheet.request.MusicSheetSaveRequestDto;
import me.mocadev.herokujavaapi.dto.musicsheet.response.MusicResponseDto;
import me.mocadev.herokujavaapi.dto.musicsheet.response.MusicSaveResponseDto;
import me.mocadev.herokujavaapi.service.musicsheet.MusicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 콘티 공유 Controller
 *
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-02-18
 **/
@RequestMapping("/api/v1/musics")
@RequiredArgsConstructor
@RestController
public class MusicSheetController {

	private final MusicService musicService;

	@GetMapping
	public List<MusicResponseDto> findAll() {
		return musicService.findAll();
	}

	@PostMapping
	public ResponseEntity<MusicSaveResponseDto> saveMusicSheet(@RequestBody @Valid MusicSheetSaveRequestDto musicSheetSaveRequestDto) {
		final MusicSaveResponseDto savedMusic = musicService.saveMusicSheet(musicSheetSaveRequestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedMusic);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMusicSheet(@PathVariable String id) {
		musicService.deleteMusicSheet(id);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/entrance")
	public ResponseEntity<Void> entranceByNameAndPass(@RequestBody @Valid MusicSheetLoginDto musicSheetLoginDto) {
		musicService.entranceByNameAndPass(musicSheetLoginDto);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/entrance/random")
	public ResponseEntity<Void> entranceByRandomString(@RequestBody @Valid MusicSheetRandomStringLoginDto musicSheetRandomStringLoginDto) {
		musicService.entranceByRandomString(musicSheetRandomStringLoginDto);
		return ResponseEntity.ok().build();

	}
}
