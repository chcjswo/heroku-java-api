package me.mocadev.herokujavaapi.common.util;

import java.util.UUID;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-02-20
 **/
public class CommonUtils {

	public static String getUUID() {
		return UUID.randomUUID().toString();
	}
}