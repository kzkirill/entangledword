package io.entangledword.model.post;

import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class Blogpost extends BlogTextEntry {
	protected Set<String> tags;

	protected Blogpost(String title, String text, String author) {
		super(title, text, author);
	}

}