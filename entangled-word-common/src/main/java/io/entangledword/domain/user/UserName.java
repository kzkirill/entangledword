package io.entangledword.domain.user;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonFormat
public class UserName {
	private String title;
	private String first;
	private String last;

}