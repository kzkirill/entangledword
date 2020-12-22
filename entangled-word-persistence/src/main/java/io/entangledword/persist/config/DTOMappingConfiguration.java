package io.entangledword.persist.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.entangledword.domain.post.BlogpostDTO;
import io.entangledword.domain.tag.Tag;
import io.entangledword.domain.user.UserDTO;
import io.entangledword.persist.entity.BlogpostMongoDoc;
import io.entangledword.persist.entity.TagMongoDoc;
import io.entangledword.persist.entity.UserMongoDoc;
import io.entangledword.port.out.DTOMappingService;

@Configuration
public class DTOMappingConfiguration {

	@Bean
	public ModelMapper getMapper() {
		return new ModelMapper();
	}

	@Bean
	public DTOMappingService<BlogpostDTO, BlogpostMongoDoc> getBlogpostMapping() {
		return new DTOMappingService<BlogpostDTO, BlogpostMongoDoc>(BlogpostDTO.class, BlogpostMongoDoc.class);
	}

	@Bean
	public DTOMappingService<Tag, TagMongoDoc> getTagMapping() {
		return new DTOMappingService<Tag, TagMongoDoc>(Tag.class, TagMongoDoc.class);
	}

	@Bean
	public DTOMappingService<UserDTO, UserMongoDoc> getUserMapping() {
		return new DTOMappingService<UserDTO, UserMongoDoc>(UserDTO.class, UserMongoDoc.class);
	}
}
