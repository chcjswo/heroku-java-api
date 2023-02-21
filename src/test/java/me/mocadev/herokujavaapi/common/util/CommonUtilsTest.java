package me.mocadev.herokujavaapi.common.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-02-20
 **/
@DisplayName("공통 유틸 테스트")
class CommonUtilsTest {

	@DisplayName("UUID 테스트")
	@Test
	void getUUID() {
		final String[] split = CommonUtils.getUUID().split("-");
		Assertions.assertThat(split[0].length()).isEqualTo(8);
	}

}
