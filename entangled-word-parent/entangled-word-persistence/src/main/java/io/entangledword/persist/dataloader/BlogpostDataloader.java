package io.entangledword.persist.dataloader;

import static java.lang.String.format;

import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import io.entangledword.persist.entity.Blogpost;
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

	private Blogpost instanceFromIndex(int index) {
		return Blogpost.newInstance(format("ID%d", index), format("Title N%d", index), format("From loader N%d", index),
				"Author from loader");
	}

	private Long repoCount() {
		return repo.findAll().count().block();
	}

}
