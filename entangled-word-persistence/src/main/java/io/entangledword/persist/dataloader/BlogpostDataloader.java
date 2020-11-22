package io.entangledword.persist.dataloader;

import static java.lang.String.format;
import static java.util.stream.IntStream.range;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import io.entangledword.domain.post.BlogTextEntry;
import io.entangledword.domain.post.BlogpostDTO;
import io.entangledword.domain.tag.Tag;
import io.entangledword.domain.user.UserDTO;
import io.entangledword.port.out.blogpost.CreatePostPort;
import io.entangledword.port.out.blogpost.CreateTagPort;
import io.entangledword.port.out.user.CreateUserPort;

@Component
public class BlogpostDataloader implements ApplicationRunner {

	private final CreateTagPort createTagPort;
	private final CreatePostPort createPostPort;
	private final CreateUserPort createUser;
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final List<UserDTO> users = new LinkedList<>();

	public BlogpostDataloader(CreateTagPort createTagPort, CreatePostPort createPost, CreateUserPort createUser) {
		super();
		this.createTagPort = createTagPort;
		this.createPostPort = createPost;
		this.createUser = createUser;
		logger.info("{} instance was created.", this.getClass().getName());
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		logger.info("{} is running.", this.getClass().getName());
		LocalDateTime current = LocalDateTime.now();
		UserDTO user1 = UserDTO.newInstance("Mrs", "Amalia", "Smith",
				"https://randomuser.me/api/portraits/med/women/47.jpg", "amalsm*123", "kjdlkj&jlkj_12@gmail.com");
		user1.setCreatedAt(current);
		UserDTO user2 = UserDTO.newInstance("Mrs", "Roseneide", "Porto",
				"https://randomuser.me/api/portraits/med/women/82.jpg", "Gwendolyn8", "Bradley_Homenick@gmail.com");
		user2.setCreatedAt(current);
		this.users.add(this.createUser.save(user1).block());
		this.users.add(this.createUser.save(user2).block());
		logger.info("New user ID {} ", this.users.get(0).getId());
		logger.info("New user ID {} ", this.users.get(1).getId());

		IntStream.range(0, 5).forEach(index -> {
			logger.info("Creating from index {}", index);
			BlogpostDTO newPost = this.createPostPort.save(instanceDTOFromIndex(index)).block();
			logger.info("New post ID {} {}", newPost.getID(), newPost.toString());
		});
		logger.info("blogpost records created.");
		this.createTagPort.save(Tag.newInstance("tag from loader by port save 01")).block();
		this.createTagPort.save(Tag.newInstance("tag from loader by port save 01")).block();
		this.createTagPort.save(Tag.newInstance("tag from loader by port save 02")).block();
		logger.info("tag records created.");
	}

	private BlogpostDTO instanceDTOFromIndex(int index) {
		String title = format("Title N%d", index);
		BlogpostDTO newInstance = BlogpostDTO.newInstance(title, format("From loader N%d", index),
				this.users.get(index % 2).getId());
		List<BlogTextEntry> replies = Arrays
				.asList(BlogTextEntry.newInstance("Reply to " + title, "Comment 1", "Commenter 1"));
		newInstance.setReplies(replies);
		newInstance.setTags(range(0, index).collect(() -> new HashSet<String>(),
				(t, value) -> t.add(format("Tag2_%s", value)), (t, u) -> t.addAll(u)));
		LocalDateTime current = LocalDateTime.now();
		newInstance.setCreated(current);
		newInstance.setUpdated(current);
		return newInstance;
	}
}
