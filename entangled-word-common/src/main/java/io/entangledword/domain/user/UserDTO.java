package io.entangledword.domain.user;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonFormat
public class UserDTO {
	private String id;
	private LocalDateTime createdAt;
	private UserName name;
	private String pictureURL;
	private String username;
	private String email;

	public String getFullName() {
		return String.format("%s %s %s", this.getName().getTitle(), this.getName().getFirst(),
				this.getName().getLast());
	}

	public static UserDTO newInstance(String nameTitle, String nameFirst, String nameLast, String pictureURL,
			String username, String email) {
		return new UserDTO(LocalDateTime.now(), new UserName(nameTitle, nameFirst, nameLast), pictureURL, username,
				email);
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
		UserDTO other = (UserDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	protected UserDTO(LocalDateTime now, UserName userName, String pictureURL, String username2, String email) {
		this(null, now, userName, pictureURL, username2, email);
	}

}
