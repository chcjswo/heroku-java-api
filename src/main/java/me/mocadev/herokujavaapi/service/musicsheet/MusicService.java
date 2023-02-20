package me.mocadev.herokujavaapi.service.musicsheet;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.mocadev.herokujavaapi.common.util.CommonUtils;
import me.mocadev.herokujavaapi.document.musicsheet.Music;
import me.mocadev.herokujavaapi.dto.musicsheet.request.MusicSheetSaveRequestDto;
import me.mocadev.herokujavaapi.dto.musicsheet.response.MusicResponseDto;
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
		log.info("musics = {}", musics)	;
		final List<MusicResponseDto> results = musics.stream()
			.map(m -> modelMapper.map(m, MusicResponseDto.class))
			.collect(Collectors.toList());
		log.info("results = {}", results);
		return results;
	}

	@Transactional
	public void saveMusicSheet(MusicSheetSaveRequestDto musicSheetSaveRequestDto) {
		log.info("dto = {}", musicSheetSaveRequestDto);
		musicRepository.save(musicSheetSaveRequestDto.toEntity(CommonUtils.getUUID()));
	}
}
