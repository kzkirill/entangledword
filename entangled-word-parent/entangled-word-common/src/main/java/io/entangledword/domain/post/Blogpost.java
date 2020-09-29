package io.entangledword.domain.post;

import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class Blogpost extends BlogTextEntry {
	protected Set<String> tags = Set.of();

	protected Blogpost(String title, String text, String author) {
		super(title, text, author);
	}

	@Override
	public String toString() {
		return super.toString() + "tags: [" + tags.stream().reduce((tag1, tag2) -> tag1 + "," + tag2) + "]";
	}

}