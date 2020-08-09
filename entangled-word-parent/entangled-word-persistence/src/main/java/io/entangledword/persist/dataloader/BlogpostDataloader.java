package io.entangledword.persist.dataloader;

import static java.lang.String.format;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import io.entangledword.domain.post.BlogTextEntry;
import io.entangledword.persist.entity.BlogpostMongoDoc;
import io.entangledword.persist.repos.BlogpostRepository;

@Component
public class BlogpostDataloader implements ApplicationRunner {

	private final BlogpostRepository repo;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	public BlogpostDataloader(BlogpostRepository repo) {
		super();
		this.repo = repo;
		logger.info("{} instance was created.", this.getClass().getName());
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		logger.info("{} is running.", this.getClass().getName());

		if (repoCount() == 0) {
			IntStream.range(0, 5).forEach(index -> {
				logger.info("Creating from index {}", index);
				repo.save(instanceFromIndex(index)).block();
			});

			logger.info("{} blogpost records created.", repoCount());
		}

	}

	private BlogpostMongoDoc instanceFromIndex(int index) {
		String title = format("Title N%d", index);
		BlogpostMongoDoc newInstance = BlogpostMongoDoc.newInstance(title, format("From loader N%d", index),
				"Author from loader");
		List<BlogTextEntry> replies = Arrays
				.asList(BlogTextEntry.newInstance("Reply to " + title, "Comment 1", "Commenter 1"));
		newInstance.setReplies(replies);
		Set<String> tags = new HashSet<String>(Arrays.asList("tag1", "tag2"));
		newInstance.setTags(tags);
		return newInstance;
	}

	private Long repoCount() {
		return repo.findAll().count().block();
	}

}
