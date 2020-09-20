package io.entangledword.domain.post;

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
	
	public static BlogpostDTO newInstance(String title, String text, String author) {
		BlogpostDTO newInstance = new BlogpostDTO(title, text, author);
		return newInstance;
	}
	
	protected BlogpostDTO(String title, String text, String author) {
		super(title,text,author);
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((ID == null) ? 0 : ID.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogpostDTO other = (BlogpostDTO) obj;
		if (ID == null) {
			if (other.ID != null)
				return false;
		} else if (!ID.equals(other.ID))
			return false;
		return true;
	}
}
