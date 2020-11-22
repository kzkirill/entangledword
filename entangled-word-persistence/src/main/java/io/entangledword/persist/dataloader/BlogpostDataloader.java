package io.entangledword.persist.dataloader;

import static java.lang.String.format;
import static java.util.stream.IntStream.range;

import java.util.Arrays;
import java.util.HashSet;
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
import io.entangledword.port.out.blogpost.CreatePostPort;
import io.entangledword.port.out.blogpost.CreateTagPort;

@Component
public class BlogpostDataloader implements ApplicationRunner {

	private final CreateTagPort createTagPort;
	private final CreatePostPort createPostPort;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	public BlogpostDataloader(CreateTagPort createTagPort, CreatePostPort createPost) {
		super();
		this.createTagPort = createTagPort;
		this.createPostPort = createPost;
		logger.info("{} instance was created.", this.getClass().getName());
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		logger.info("{} is running.", this.getClass().getName());

		IntStream.range(0, 5).forEach(index -> {
			logger.info("Creating from index {}", index);
			this.createPostPort.save(instanceDTOFromIndex(index)).block();
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
				"Author from loader");
		List<BlogTextEntry> replies = Arrays
				.asList(BlogTextEntry.newInstance("Reply to " + title, "Comment 1", "Commenter 1"));
		newInstance.setReplies(replies);
		newInstance.setTags(range(0, index)
							.collect(() -> new HashSet<String>(),
									(t, value) -> t.add(format("Tag2_%s", value)),
									(t, u) -> t.addAll(u)));
		return newInstance;
	}
}
