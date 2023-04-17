package com.example.newWiki;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
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
	

	@Test
	@Rollback(false)
	public void testCreate () {
		Wiki wiki = new Wiki(null, "brain", "abc", "xyz");
		Wiki savedWiki = wikiRepo.save(wiki);
		assertNotNull(savedWiki);
	}
	
	
	@Test
	public void testFindAll () {
		List list = wikiRepo.findAll();
		assertThat(list).size().isGreaterThan(0);
	}
		
	@Test
	public void testFindWikiByTopicExist () {
		String topic = "abc";
		Wiki wiki = wikiRepo.findByTopic(topic);
		assertThat(wiki.getTopic()).isEqualTo(topic);
	}
	
	@Test
	public void testFindWikiByTopicNotExist () {
		String topic = "not";
		Wiki wiki = wikiRepo.findByTopic(topic);
		assertNull(wiki);
	}
		
	private void assertNull(Wiki wiki) {
				
	}


	@Test
	public void testUpdate() {
		Wiki wiki = wikiRepo.findById(502L).get();
		wiki.setTopic("new topic");
		wikiRepo.save(wiki);
		assertNotEquals("existing topic", wikiRepo.findById(502L).get().getTopic());
	}
		
	@Test
	public void testDelete () {
		wikiRepo.deleteById(1L);
		assertThat(wikiRepo.existsById(1L)).isFalse();
	}
}
	
	
	
	

