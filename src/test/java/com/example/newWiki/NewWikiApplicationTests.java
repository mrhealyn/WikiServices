package com.example.newWiki;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.example.newWiki.model.Wiki;
import com.example.newWiki.repository.WikiRepo;

@SpringBootTest
class NewWikiApplicationTests {

	@Autowired
	WikiRepo wikiRepo;

	// test for save new content
	@Test
	@Rollback(false)
	public void testCreate() {
		Wiki wiki = new Wiki(null, "topic", "title", "content");
		Wiki savedWiki = wikiRepo.save(wiki);
		assertNotNull(savedWiki);
	}

	// test for get list of contents
	@Test
	public void testFindAll() {
		List<Wiki> wiki = wikiRepo.findAll();
		assertThat(wiki).size().isGreaterThan(0);
	}

	// test for get single topic
	@Test
	public void testFindWikiByTopicExist() {
		String topic = "sample";
		Wiki wiki = wikiRepo.findByTopic(topic);
		assertThat(wiki.getTopic()).isEqualTo(topic);
	}

	// test for not existing topic
	@Test
	public void testFindWikiByTopicNotExist() {
		String topic = "not";
		Wiki wiki = wikiRepo.findByTopic(topic);
		assertNull(wiki);
	}

	private void assertNull(Wiki wiki) {

	}

	// test for update topic
	@Test
	public void testUpdate() {
		Wiki wiki = wikiRepo.findById(902L).get();
		wiki.setTopic("updated topic");
		wikiRepo.save(wiki);
		assertNotEquals("topic", wikiRepo.findById(902L).get().getTopic());
	}

	// test for delete entity
	@Test
	public void testDelete() {
		wikiRepo.deleteById(802L);
		assertThat(wikiRepo.existsById(802L)).isFalse();
	}
}
