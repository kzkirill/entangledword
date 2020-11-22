package io.entangledword.domain.user;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonFormat
public class User {
	private String ID;
	private LocalDateTime createdAt;
	private UserName name;
	private String pictureURL;
	private String username;
	private String email;

	public static User newInstance(String nameTitle, String nameFirst, String nameLast, String pictureURL,
			String username, String email) {
		return new User("",LocalDateTime.now(), new UserName(nameTitle, nameFirst, nameLast), pictureURL, username, email);
	}
}
