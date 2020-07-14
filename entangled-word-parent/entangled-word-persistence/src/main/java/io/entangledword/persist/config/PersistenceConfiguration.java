package io.entangledword.persist.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersistenceConfiguration {

	@Bean
	public ModelMapper getMapper() {
		return new ModelMapper();
	}
}
