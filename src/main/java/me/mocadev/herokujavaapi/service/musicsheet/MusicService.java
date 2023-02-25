package me.mocadev.herokujavaapi.service.musicsheet;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.mocadev.herokujavaapi.common.exception.InvalidMusicRoomEntranceException;
import me.mocadev.herokujavaapi.common.exception.MusicConflictException;
import me.mocadev.herokujavaapi.common.exception.MusicRoomNotFoundException;
import me.mocadev.herokujavaapi.common.util.CommonUtils;
import me.mocadev.herokujavaapi.document.musicsheet.Music;
import me.mocadev.herokujavaapi.dto.musicsheet.request.MusicRoomLoginDto;
import me.mocadev.herokujavaapi.dto.musicsheet.request.MusicRoomRandomStringLoginDto;
import me.mocadev.herokujavaapi.dto.musicsheet.request.MusicRoomSaveRequestDto;
import me.mocadev.herokujavaapi.dto.musicsheet.response.MusicResponseDto;
import me.mocadev.herokujavaapi.dto.musicsheet.response.MusicSaveResponseDto;
import me.mocadev.herokujavaapi.repository.musicsheet.MusicRepository;
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
	public void entranceByNameAndPass(MusicRoomLoginDto musicRoomLoginDto) {
		final Music result = musicRepository.findByRoomNameAndRoomPass(musicRoomLoginDto.getRoomName(),
			musicRoomLoginDto.getRoomPass());
		if (Objects.isNull(result)) {
			throw new InvalidMusicRoomEntranceException("invalid.roomName.roomPass");
		}
	}

	@Transactional
	public void entranceByRandomString(MusicRoomRandomStringLoginDto musicRoomRandomStringLoginDto) {
		final Music result = musicRepository.findByRandomString(musicRoomRandomStringLoginDto.getRandomString());
		if (Objects.isNull(result)) {
			throw new InvalidMusicRoomEntranceException("invalid.room.randomString");
		}
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
