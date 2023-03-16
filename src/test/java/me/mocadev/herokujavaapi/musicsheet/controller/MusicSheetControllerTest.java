package me.mocadev.herokujavaapi.musicsheet.controller;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.removeHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import me.mocadev.herokujavaapi.common.service.MessageService;
import me.mocadev.herokujavaapi.common.util.CommonUtils;
import me.mocadev.herokujavaapi.musicsheet.domain.MusicSheet;
import me.mocadev.herokujavaapi.musicsheet.dto.request.MusicRoomLoginDto;
import me.mocadev.herokujavaapi.musicsheet.dto.request.MusicRoomRandomStringLoginDto;
import me.mocadev.herokujavaapi.musicsheet.dto.request.MusicRoomSaveRequestDto;
import me.mocadev.herokujavaapi.musicsheet.dto.response.MusicLoginResponseDto;
import me.mocadev.herokujavaapi.musicsheet.dto.response.MusicResponseDto;
import me.mocadev.herokujavaapi.musicsheet.dto.response.MusicSaveResponseDto;
import me.mocadev.herokujavaapi.musicsheet.service.MusicService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
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

	private static final String API_URL = "/api/v1/musics";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MusicService musicService;

	@MockBean
	private MessageService messageService;

	@Autowired
	private ObjectMapper objectMapper;

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
		List<MusicResponseDto> results = new ArrayList<>();

		final MusicSheet musicSheet1 = getMusicSheet(0, "sheet title 1", "https://naver.com/1.jpg", "", "");
		final MusicSheet musicSheet2 = getMusicSheet(1, "sheet title 2", "https://naver.com/2.jpg", "https://youtube.com/22222", "memo 2");

		final MusicResponseDto data1 = MusicResponseDto.builder()
			.id("606137f5e9f41a001593cd5a")
			.roomName("1111")
			.roomPass("2222")
			.musicSheets(List.of(musicSheet1, musicSheet2))
			.randomString(CommonUtils.getMusicRandomString())
			.build();

		final MusicResponseDto data2 = MusicResponseDto.builder()
			.id("606137f5e9f41a001593cd5a")
			.roomName("3333")
			.roomPass("4444")
			.musicSheets(List.of(musicSheet1, musicSheet2))
			.randomString(CommonUtils.getMusicRandomString())
			.build();

		results.add(data1);
		results.add(data2);

		given(musicService.findAll()).willReturn(results);

		ResultActions resultActions = mockMvc.perform(get(API_URL))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$").isArray())
			.andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
			.andExpect(jsonPath("$.[0].musicSheets").isArray())
			.andExpect(jsonPath("$.[0].musicSheets", hasSize(greaterThanOrEqualTo(1))));

		verify(musicService, times(1)).findAll();

		resultActions.andDo(document("find-all-music-room",
			responseFields(
				fieldWithPath("[].id").description("아이디"),
				fieldWithPath("[].roomName").description("방 이름"),
				fieldWithPath("[].roomPass").description("방 패스워드"),
				fieldWithPath("[].musicSheets.[].index").description("악보 순서"),
				fieldWithPath("[].musicSheets.[].sheetTitle").description("악보 제목"),
				fieldWithPath("[].musicSheets.[].sheetUrl").description("악보 URL"),
				fieldWithPath("[].musicSheets.[].youtubeUrl").description("영상 URL").optional(),
				fieldWithPath("[].musicSheets.[].memo").description("메모").optional(),
				fieldWithPath("[].randomString").description("방 입장 문자"),
				fieldWithPath("[].regDate").description("등록일")
			)
		));
	}

	@DisplayName("악보 저장 테스트")
	@Test
	void saveMusicSheet() throws Exception {
		final MusicSheet musicSheet1 = getMusicSheet(0, "sheet title 1", "https://naver.com/1.jpg", "https://youtube.com/11111", "memo 1");
		final MusicSheet musicSheet2 = getMusicSheet(1, "sheet title 2", "https://naver.com/2.jpg", "", "");

		MusicRoomSaveRequestDto requestDto = MusicRoomSaveRequestDto.builder()
			.roomName("room name")
			.roomPass("password")
			.musicSheets(List.of(musicSheet1, musicSheet2))
			.build();

		MusicSaveResponseDto result = MusicSaveResponseDto.builder()
			.id("234sdfdsf3asdf")
			.roomName(requestDto.getRoomName())
			.roomPass(requestDto.getRoomPass())
			.musicSheets(requestDto.getMusicSheets())
			.randomString(CommonUtils.getMusicRandomString())
			.build();

		given(musicService.saveMusicSheet(any())).willReturn(result);

		ResultActions resultActions = mockMvc.perform(post(API_URL)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(requestDto)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("id").exists())
			.andExpect(jsonPath("roomName").exists())
			.andExpect(jsonPath("roomPass").exists())
			.andExpect(jsonPath("musicSheets").exists())
			.andExpect(jsonPath("musicSheets").isArray())
			.andExpect(jsonPath("musicSheets.[0].sheetTitle").exists())
			.andExpect(jsonPath("musicSheets.[0].sheetUrl").exists())
			.andExpect(jsonPath("randomString").exists())
			.andExpect(jsonPath("regDate").exists());

		verify(musicService, times(1)).saveMusicSheet(any());

		resultActions.andDo(document("save-music-room",
			requestFields(
				fieldWithPath("roomName").type(JsonFieldType.STRING).description("방 이름"),
				fieldWithPath("roomPass").type(JsonFieldType.STRING).description("방 패스워드"),
				fieldWithPath("musicSheets.[].index").type(JsonFieldType.NUMBER).description("순서"),
				fieldWithPath("musicSheets.[].sheetTitle").type(JsonFieldType.STRING).description("악보 제목"),
				fieldWithPath("musicSheets.[].sheetUrl").type(JsonFieldType.STRING).description("악보 URL"),
				fieldWithPath("musicSheets.[].youtubeUrl").type(JsonFieldType.STRING).description("영상 URL").optional(),
				fieldWithPath("musicSheets.[].memo").type(JsonFieldType.STRING).description("메모").optional()
			),
			responseFields(
				fieldWithPath("id").description("아이디"),
				fieldWithPath("roomName").description("방 이름"),
				fieldWithPath("roomPass").description("방 패스워드"),
				fieldWithPath("musicSheets.[].index").description("악보 순서"),
				fieldWithPath("musicSheets.[].sheetTitle").description("악보 제목"),
				fieldWithPath("musicSheets.[].sheetUrl").description("악보 URL"),
				fieldWithPath("musicSheets.[].youtubeUrl").description("영상 URL").optional(),
				fieldWithPath("musicSheets.[].memo").description("메모").optional(),
				fieldWithPath("randomString").description("방 입장 문자"),
				fieldWithPath("regDate").description("등록일")
			)
		));
	}

	@DisplayName("악보 삭제 테스트")
	@Test
	void deleteMusicSheet() throws Exception {
		ResultActions resultActions = mockMvc.perform(delete(API_URL + "/{id}", "606137f5e9f41a001593cd5a"))
			.andExpect(status().isNoContent());

		verify(musicService, times(1)).deleteMusicSheet(any());

		resultActions.andDo(document("delete-music-room",
			pathParameters(
				parameterWithName("id").description("방 고유 아이디")
			)
		));
	}

	@DisplayName("방 이름, 패스워드로 방 입장 테스트")
	@Test
	void entranceByNameAndPass() throws Exception {
		MusicRoomLoginDto requestDto = MusicRoomLoginDto.builder()
			.roomName("room")
			.roomPass("password")
			.build();

		final MusicSheet musicSheet1 = getMusicSheet(0, "sheet title 1", "https://naver.com/1.jpg", "", "");
		final MusicSheet musicSheet2 = getMusicSheet(1, "sheet title 2", "https://naver.com/2.jpg", "https://youtube.com/22222", "memo 2");

		MusicLoginResponseDto result = MusicLoginResponseDto.of("123sdfd34",
			"room",
			"password",
			List.of(musicSheet1, musicSheet2),
			CommonUtils.getMusicRandomString(),
			LocalDateTime.now());

		given(musicService.entranceByNameAndPass(any())).willReturn(result);

		ResultActions resultActions = mockMvc.perform(post(API_URL + "/entrance")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(requestDto)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("id").exists())
			.andExpect(jsonPath("roomName").exists())
			.andExpect(jsonPath("musicSheets").exists())
			.andExpect(jsonPath("musicSheets").isArray())
			.andExpect(jsonPath("musicSheets.[0].sheetTitle").exists())
			.andExpect(jsonPath("musicSheets.[0].sheetUrl").exists())
			.andExpect(jsonPath("randomString").exists())
			.andExpect(jsonPath("regDate").exists());

		verify(musicService, times(1)).entranceByNameAndPass(any());

		resultActions.andDo(document("login-music-room-pass",
			requestFields(
				fieldWithPath("roomName").type(JsonFieldType.STRING).description("방 이름"),
				fieldWithPath("roomPass").type(JsonFieldType.STRING).description("방 비번")
			),
			responseFields(
				fieldWithPath("id").description("아이디"),
				fieldWithPath("roomName").description("방 이름"),
				fieldWithPath("roomPass").description("방 패스워드"),
				fieldWithPath("musicSheets.[].index").description("악보 순서"),
				fieldWithPath("musicSheets.[].sheetTitle").description("악보 제목"),
				fieldWithPath("musicSheets.[].sheetUrl").description("악보 URL"),
				fieldWithPath("musicSheets.[].youtubeUrl").description("영상 URL").optional(),
				fieldWithPath("musicSheets.[].memo").description("메모").optional(),
				fieldWithPath("randomString").description("방 입장 문자"),
				fieldWithPath("regDate").description("등록일")
			)
		));
	}

	@DisplayName("랜덤 문자열로 방 입장 테스트")
	@Test
	void entranceByRandomString() throws Exception {
		MusicRoomRandomStringLoginDto requestDto = new MusicRoomRandomStringLoginDto();
		requestDto.setRandomString(CommonUtils.getMusicRandomString());

		final MusicSheet musicSheet1 = getMusicSheet(0, "sheet title 1", "https://naver.com/1.jpg", "", "");
		final MusicSheet musicSheet2 = getMusicSheet(1, "sheet title 2", "https://naver.com/2.jpg", "https://youtube.com/22222", "memo 2");

		MusicLoginResponseDto result = MusicLoginResponseDto.of("123sdfd34",
			"room",
			"password",
			List.of(musicSheet1, musicSheet2),
			CommonUtils.getMusicRandomString(),
			LocalDateTime.now());

		given(musicService.entranceByRandomString(any())).willReturn(result);

		ResultActions resultActions = mockMvc.perform(post(API_URL + "/entrance/random")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(requestDto)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("id").exists())
			.andExpect(jsonPath("roomName").exists())
			.andExpect(jsonPath("musicSheets").exists())
			.andExpect(jsonPath("musicSheets").isArray())
			.andExpect(jsonPath("musicSheets.[0].sheetTitle").exists())
			.andExpect(jsonPath("musicSheets.[0].sheetUrl").exists())
			.andExpect(jsonPath("randomString").exists())
			.andExpect(jsonPath("regDate").exists());

		verify(musicService, times(1)).entranceByRandomString(any());

		resultActions.andDo(document("login-music-room-random-string",
			requestFields(
				fieldWithPath("randomString").type(JsonFieldType.STRING).description("방 입장 문자열")
			),
			responseFields(
				fieldWithPath("id").description("아이디"),
				fieldWithPath("roomName").description("방 이름"),
				fieldWithPath("roomPass").description("방 패스워드"),
				fieldWithPath("musicSheets.[].index").description("악보 순서"),
				fieldWithPath("musicSheets.[].sheetTitle").description("악보 제목"),
				fieldWithPath("musicSheets.[].sheetUrl").description("악보 URL"),
				fieldWithPath("musicSheets.[].youtubeUrl").description("영상 URL").optional(),
				fieldWithPath("musicSheets.[].memo").description("메모").optional(),
				fieldWithPath("randomString").description("방 입장 문자"),
				fieldWithPath("regDate").description("등록일")
			)
		));
	}

	private static MusicSheet getMusicSheet(int index, String title, String url, String youtubeUrl, String memo) {
		return MusicSheet.builder()
			.index(index)
			.sheetTitle(title)
			.sheetUrl(url)
			.youtubeUrl(youtubeUrl)
			.memo(memo)
			.build();
	}
}
