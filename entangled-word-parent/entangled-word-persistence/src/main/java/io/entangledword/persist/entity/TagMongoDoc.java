package io.entangledword.persist.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.entangledword.domain.tag.TagInt;
import lombok.Data;

@Document
@Data
public class TagMongoDoc implements TagInt {
	@Id
	private String value;

	public TagMongoDoc() {
		this.setValue("");
	}

	public TagMongoDoc(String value) {
		this.setValue(value);
	}

	public static TagMongoDoc newInstance(String value) {
		return new TagMongoDoc(value);
	}
}
