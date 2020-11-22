package io.entangledword.persist.dataloader;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import io.entangledword.domain.user.User;
import io.entangledword.port.out.user.CreateUserPort;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserDataLoader implements ApplicationRunner {

	private final CreateUserPort createUser;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		User user1 = User.newInstance("Mrs", "Amalia", "Smith", "https://randomuser.me/api/portraits/med/women/47.jpg",
				"amalsm*123", "kjdlkj&jlkj_12@gmail.com");
		User user2 = User.newInstance("Mrs", "Roseneide", "Porto",
				"https://randomuser.me/api/portraits/med/women/82.jpg", "Gwendolyn8", "Bradley_Homenick@gmail.com");
		this.createUser.save(user1).then(this.createUser.save(user2)).block();
	}

}
