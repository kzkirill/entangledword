package io.entangledword.persist.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import io.entangledword.domain.user.UserDTO;
import lombok.NoArgsConstructor;

@Document
@NoArgsConstructor
public class UserMongoDoc extends UserDTO {
}
