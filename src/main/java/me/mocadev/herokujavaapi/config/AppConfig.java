package me.mocadev.herokujavaapi.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-02-20
 **/
@Configuration
public class AppConfig {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
}
