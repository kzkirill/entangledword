package io.entangledword.model.post;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BlogTextEntry {
	protected String title;
	protected String text;
	protected String author;
	protected LocalDateTime created;
	protected LocalDateTime updated;
	protected List<BlogTextEntry> replies;

	public static BlogTextEntry newInstance(String title, String text, String author) {
		return new BlogTextEntry(title, text, author);
	}

	protected BlogTextEntry(String title, String text, String author) {
		super();
		this.title = title;
		this.text = text;
		this.author = author;
	}
}