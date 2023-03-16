package me.mocadev.herokujavaapi.musicsheet.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.mocadev.herokujavaapi.common.exception.MusicConflictException;
import me.mocadev.herokujavaapi.common.exception.MusicRoomNotFoundException;
import me.mocadev.herokujavaapi.common.util.CommonUtils;
import me.mocadev.herokujavaapi.common.util.ValidationUtils;
import me.mocadev.herokujavaapi.musicsheet.domain.Music;
import me.mocadev.herokujavaapi.musicsheet.dto.request.MusicRoomLoginDto;
import me.mocadev.herokujavaapi.musicsheet.dto.request.MusicRoomRandomStringLoginDto;
import me.mocadev.herokujavaapi.musicsheet.dto.request.MusicRoomSaveRequestDto;
import me.mocadev.herokujavaapi.musicsheet.dto.response.MusicLoginResponseDto;
import me.mocadev.herokujavaapi.musicsheet.dto.response.MusicResponseDto;
import me.mocadev.herokujavaapi.musicsheet.dto.response.MusicSaveResponseDto;
import me.mocadev.herokujavaapi.musicsheet.repository.MusicRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-02-19
 **/
@Slf4j
@RequiredArgsConstructor
@Service
public class MusicService {

	private final MusicRepository musicRepository;
	private final ModelMapper modelMapper;
	private final ValidationUtils validationUtils;

	@Transactional(readOnly = true)
	public List<MusicResponseDto> findAll() {
		final List<Music> musics = musicRepository.findAll();
		return musics.stream()
			.map(m -> modelMapper.map(m, MusicResponseDto.class))
			.collect(Collectors.toList());
	}

	@Transactional
	public MusicSaveResponseDto saveMusicSheet(MusicRoomSaveRequestDto musicRoomSaveRequestDto) {
		canSave(musicRoomSaveRequestDto);
		return saveRoom(musicRoomSaveRequestDto);
	}

	@Transactional
	public void deleteMusicSheet(String id) {
		musicRepository.delete(
			musicRepository.findById(id)
				.orElseThrow(MusicRoomNotFoundException::new));
	}

	@Transactional
	public MusicLoginResponseDto entranceByNameAndPass(MusicRoomLoginDto musicRoomLoginDto) {
		final Music music = musicRepository.findByRoomNameAndRoomPass(musicRoomLoginDto.getRoomName(),
			musicRoomLoginDto.getRoomPass());
		validationUtils.canEnterMusicRoom(music, "invalid.roomName.roomPass");
		return modelMapper.map(music, MusicLoginResponseDto.class);
	}

	@Transactional
	public MusicLoginResponseDto entranceByRandomString(MusicRoomRandomStringLoginDto musicRoomRandomStringLoginDto) {
		final Music music = musicRepository.findByRandomString(musicRoomRandomStringLoginDto.getRandomString());
		validationUtils.canEnterMusicRoom(music, "invalid.room.randomString");
		return modelMapper.map(music, MusicLoginResponseDto.class);
	}

	private MusicSaveResponseDto saveRoom(MusicRoomSaveRequestDto musicRoomSaveRequestDto) {
		final Music music = musicRepository.save(
			musicRoomSaveRequestDto.toEntity(CommonUtils.getUUID().split("-")[0]));
		return modelMapper.map(music, MusicSaveResponseDto.class);
	}

	private void canSave(MusicRoomSaveRequestDto musicRoomSaveRequestDto) {
		final Music music = musicRepository.findByRoomName(musicRoomSaveRequestDto.getRoomName());
		if (!Objects.isNull(music)) {
			throw new MusicConflictException();
		}
	}
}
