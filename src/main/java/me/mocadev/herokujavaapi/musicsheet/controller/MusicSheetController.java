package me.mocadev.herokujavaapi.musicsheet.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.mocadev.herokujavaapi.musicsheet.dto.request.MusicRoomLoginDto;
import me.mocadev.herokujavaapi.musicsheet.dto.request.MusicRoomRandomStringLoginDto;
import me.mocadev.herokujavaapi.musicsheet.dto.request.MusicRoomSaveRequestDto;
import me.mocadev.herokujavaapi.musicsheet.dto.response.MusicLoginResponseDto;
import me.mocadev.herokujavaapi.musicsheet.dto.response.MusicResponseDto;
import me.mocadev.herokujavaapi.musicsheet.dto.response.MusicSaveResponseDto;
import me.mocadev.herokujavaapi.musicsheet.service.MusicService;
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
	public ResponseEntity<MusicSaveResponseDto> saveMusicSheet(@RequestBody @Valid MusicRoomSaveRequestDto musicRoomSaveRequestDto) {
		final MusicSaveResponseDto savedMusic = musicService.saveMusicSheet(musicRoomSaveRequestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedMusic);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMusicSheet(@PathVariable String id) {
		musicService.deleteMusicSheet(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/entrance")
	public ResponseEntity<MusicLoginResponseDto> entranceByNameAndPass(@RequestBody @Valid MusicRoomLoginDto musicRoomLoginDto) {
		final MusicLoginResponseDto result = musicService.entranceByNameAndPass(musicRoomLoginDto);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@PostMapping("/entrance/random")
	public ResponseEntity<MusicLoginResponseDto> entranceByRandomString(@RequestBody @Valid MusicRoomRandomStringLoginDto musicRoomRandomStringLoginDto) {
		final MusicLoginResponseDto result = musicService.entranceByRandomString(musicRoomRandomStringLoginDto);
		return ResponseEntity.status(HttpStatus.OK).body(result);

	}
}
