package me.mocadev.controller.musicsheet;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/api/v1/music")
@RequiredArgsConstructor
@RestController
public class MusicSheetController {

	@GetMapping
	public void findAll() {

	}

	@PostMapping
	public void saveMusicSheet() {

	}

	@DeleteMapping("/{id}")
	public void deleteMusicSheet(@PathVariable String id) {

	}

	@PostMapping("/entrance")
	public void entrance() {

	}

	@PostMapping("/entrance/random")
	public void entranceRandom() {

	}
}
