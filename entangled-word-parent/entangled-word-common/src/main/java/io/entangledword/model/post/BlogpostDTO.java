package io.entangledword.model.post;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonFormat
public class BlogpostDTO extends Blogpost {
	private String ID;

	public static BlogpostDTO newInstance(String id, String title, String text, String author) {
		BlogpostDTO newInstance = new BlogpostDTO(title, text, author);
		newInstance.setID(id);
		return newInstance;
	}

	protected BlogpostDTO(String title, String text, String author) {
		super(title,text,author);
	}
}
