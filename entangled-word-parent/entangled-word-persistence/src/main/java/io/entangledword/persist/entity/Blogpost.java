package io.entangledword.persist.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Blogpost {
	@Id
	private String id;

	private String title;
	private String text;
	private String author;
	public static Blogpost newInstance(String id, String title, String text, String author) {
		return new Blogpost(id, title, text, author);
	}
}
