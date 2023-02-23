package me.mocadev.herokujavaapi.service.musicsheet;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.mocadev.herokujavaapi.common.exception.MusicConflictException;
import me.mocadev.herokujavaapi.common.util.CommonUtils;
import me.mocadev.herokujavaapi.document.musicsheet.Music;
import me.mocadev.herokujavaapi.dto.musicsheet.request.MusicSheetLoginDto;
import me.mocadev.herokujavaapi.dto.musicsheet.request.MusicSheetRandomStringLoginDto;
import me.mocadev.herokujavaapi.dto.musicsheet.request.MusicSheetSaveRequestDto;
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
	public MusicSaveResponseDto saveMusicSheet(MusicSheetSaveRequestDto musicSheetSaveRequestDto) {
		canSave(musicSheetSaveRequestDto);
		return saveRoom(musicSheetSaveRequestDto);
	}

	@Transactional
	public void deleteMusicSheet(String id) {
		musicRepository.deleteById(id);
	}

	@Transactional
	public void entranceByNameAndPass(MusicSheetLoginDto musicSheetLoginDto) {
		final Music result = musicRepository.findByRoomNameAndRoomPass(musicSheetLoginDto.getRoomName(),
			musicSheetLoginDto.getRoomPass());
		if (Objects.isNull(result)) {
			throw new RuntimeException("login fail, data not match");
		}
	}

	@Transactional
	public void entranceByRandomString(MusicSheetRandomStringLoginDto musicSheetRandomStringLoginDto) {
		final Music result = musicRepository.findByRandomString(musicSheetRandomStringLoginDto.getRandomString());
		if (Objects.isNull(result)) {
			throw new RuntimeException("login fail, data not match");
		}
	}

	private MusicSaveResponseDto saveRoom(MusicSheetSaveRequestDto musicSheetSaveRequestDto) {
		final Music music = musicRepository.save(
			musicSheetSaveRequestDto.toEntity(CommonUtils.getUUID().split("-")[0]));
		return modelMapper.map(music, MusicSaveResponseDto.class);
	}

	private void canSave(MusicSheetSaveRequestDto musicSheetSaveRequestDto) {
		final Music music = musicRepository.findByRoomName(musicSheetSaveRequestDto.getRoomName());
		if (!Objects.isNull(music)) {
			throw new MusicConflictException();
		}
	}
}
