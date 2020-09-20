package io.entangledword.domain.tag;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@JsonFormat
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Tag implements TagInt {
	@NonNull
	private String value;

	public static Tag newInstance(String value) {
		return new Tag(value);
	}
}
