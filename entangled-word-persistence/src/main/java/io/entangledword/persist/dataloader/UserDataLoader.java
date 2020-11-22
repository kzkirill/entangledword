package io.entangledword.persist.dataloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import io.entangledword.domain.user.UserDTO;
import io.entangledword.port.out.user.CreateUserPort;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserDataLoader implements ApplicationRunner {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final CreateUserPort createUser;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		/*
		 * UserDTO user1 = UserDTO.newInstance("Mrs", "Amalia", "Smith",
		 * "https://randomuser.me/api/portraits/med/women/47.jpg", "amalsm*123",
		 * "kjdlkj&jlkj_12@gmail.com"); UserDTO user2 = UserDTO.newInstance("Mrs",
		 * "Roseneide", "Porto", "https://randomuser.me/api/portraits/med/women/82.jpg",
		 * "Gwendolyn8", "Bradley_Homenick@gmail.com"); UserDTO newUser =
		 * this.createUser.save(user1).then(this.createUser.save(user2)).block();
		 * logger.info("New user ID {} {} class {}", newUser.getId(),
		 * newUser.toString(), newUser.getClass().getName());
		 */	}

}
