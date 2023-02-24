package me.mocadev.herokujavaapi.controller.musicsheet;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.removeHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import me.mocadev.herokujavaapi.common.service.MessageService;
import me.mocadev.herokujavaapi.document.musicsheet.MusicSheet;
import me.mocadev.herokujavaapi.dto.musicsheet.response.MusicResponseDto;
import me.mocadev.herokujavaapi.service.musicsheet.MusicService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-02-23
 **/
@DisplayName("콘티 공유 테스트")
@WebMvcTest(MusicSheetController.class)
@ExtendWith(RestDocumentationExtension.class)
class MusicSheetControllerTest {

	@Autowired
	protected MockMvc mockMvc;

	@MockBean
	protected MusicService musicService;

	@MockBean
	protected MessageService messageService;

	@BeforeEach
	void setup(WebApplicationContext webApplicationContext,
			   RestDocumentationContextProvider restDocumentation) {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
			.addFilters(new CharacterEncodingFilter("UTF-8", true))
			.alwaysDo(print())
			.apply(documentationConfiguration(restDocumentation)
				.operationPreprocessors()
				.withRequestDefaults(prettyPrint())
				.withResponseDefaults(
					removeHeaders(
						"Transfer-Encoding",
						"Date",
						"Keep-Alive",
						"Connection",
						"Content-Length"
					),
					prettyPrint()))
			.build();
	}

	@DisplayName("전체 조회 테스트")
	@Test
	void findAll() throws Exception {
		// given
		List<MusicResponseDto> results = new ArrayList<>();

		final MusicSheet musicSheet1 = MusicSheet.builder()
			.sheetTitle("sheet title 1")
			.sheetUrl("https://naver.com/1.jpg")
			.build();

		final MusicSheet musicSheet2 = MusicSheet.builder()
			.sheetTitle("sheet title 2")
			.sheetUrl("https://naver.com/2.jpg")
			.build();

		final MusicResponseDto data1 = MusicResponseDto.builder()
			.id("606137f5e9f41a001593cd5a")
			.roomName("1111")
			.roomPass("2222")
			.musicSheets(List.of(musicSheet1, musicSheet2))
			.videoUrl("")
			.memo("")
			.randomString("12345678")
			.regDate(LocalDateTime.now())
			.build();

		final MusicResponseDto data2 = MusicResponseDto.builder()
			.id("606137f5e9f41a001593cd5a")
			.roomName("3333")
			.roomPass("4444")
			.musicSheets(List.of(musicSheet1, musicSheet2))
			.videoUrl("https://youtube.com/2134dsafe")
			.memo("test memo")
			.randomString("12345678")
			.regDate(LocalDateTime.now())
			.build();

		results.add(data1);
		results.add(data2);

		given(musicService.findAll()).willReturn(results);

		// when
		ResultActions result = mockMvc.perform(get("/api/v1/musics"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$").isArray())
			.andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
			.andExpect(jsonPath("$.[0].musicSheets").isArray())
			.andExpect(jsonPath("$.[0].musicSheets", hasSize(greaterThanOrEqualTo(1))));

		// then
		verify(musicService, times(1)).findAll();

		// document
		result.andDo(document("music-find-all",
			responseFields(
				fieldWithPath("[].id").description("아이디"),
				fieldWithPath("[].roomName").description("방 이름"),
				fieldWithPath("[].roomPass").description("방 패스워드"),
				fieldWithPath("[].musicSheets.[].sheetTitle").description("악보 제목"),
				fieldWithPath("[].musicSheets.[].sheetUrl").description("악보 URL"),
				fieldWithPath("[].videoUrl").description("영상 URL"),
				fieldWithPath("[].memo").description("메모"),
				fieldWithPath("[].randomString").description("방 입장 문자"),
				fieldWithPath("[].regDate").description("등록일")
			)
		));
	}

	@DisplayName("악보 저장 테스트")
	@Test
	void saveMusicSheet() {
	}

	@DisplayName("악보 삭제 테스트")
	@Test
	void deleteMusicSheet() {
	}

	@DisplayName("방 이름, 패스워드로 방 입장 테스트")
	@Test
	void entranceByNameAndPass() {
	}

	@DisplayName("랜덤 문자열로 방 입장 테스트")
	@Test
	void entranceByRandomString() {
	}
}
