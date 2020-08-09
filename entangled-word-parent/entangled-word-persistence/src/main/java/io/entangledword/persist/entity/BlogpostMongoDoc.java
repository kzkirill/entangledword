package io.entangledword.persist.entity;

import static java.time.LocalDateTime.now;
import static java.time.LocalDateTime.of;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.entangledword.domain.post.Blogpost;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document
@NoArgsConstructor
@Getter
@Setter
public class BlogpostMongoDoc extends Blogpost {
	@Id
	private String id;

	public static BlogpostMongoDoc newInstance(String title, String text, String author) {
		BlogpostMongoDoc blogpostMongoDoc = new BlogpostMongoDoc(title, text, author);
		blogpostMongoDoc.created = now();
		blogpostMongoDoc.updated = of(blogpostMongoDoc.created.getYear(), blogpostMongoDoc.created.getMonthValue(),
				blogpostMongoDoc.created.getDayOfMonth(), blogpostMongoDoc.created.getHour(),
				blogpostMongoDoc.created.getMinute(), blogpostMongoDoc.created.getSecond(),
				blogpostMongoDoc.created.getNano());

		return blogpostMongoDoc;
	}

	public static BlogpostMongoDoc newInstance(String id, String title, String text, String author) {
		BlogpostMongoDoc result = newInstance(title, text, author);
		result.setId(id);
		return result;
	}

	private BlogpostMongoDoc(String title, String text, String author) {
		super(title, text, author);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogpostMongoDoc other = (BlogpostMongoDoc) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
