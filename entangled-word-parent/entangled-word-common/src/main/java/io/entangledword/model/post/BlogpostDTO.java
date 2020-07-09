package io.entangledword.model.post;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonFormat
public class BlogpostDTO {
	private String ID;
	private String title;
	private String text;
	private String author;

	public static BlogpostDTO newInstance(String id, String title, String text, String author) {
		return new BlogpostDTO(id, title, text, author);
	}
}
