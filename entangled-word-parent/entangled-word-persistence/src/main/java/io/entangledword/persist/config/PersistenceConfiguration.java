package io.entangledword.persist.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.entangledword.model.post.BlogpostDTO;
import io.entangledword.persist.entity.BlogpostMongoDoc;
import io.entangledword.services.DTOMappingService;

@Configuration
public class PersistenceConfiguration {

	@Bean
	public ModelMapper getMapper() {
		return new ModelMapper();
	}

	@Bean
	public DTOMappingService<BlogpostDTO, BlogpostMongoDoc> getMapping() {
		return new DTOMappingService<BlogpostDTO, BlogpostMongoDoc>(BlogpostMongoDoc.class, BlogpostDTO.class);
	}
}
